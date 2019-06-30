package com.jokerdata.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.common.ShareUtil;
import com.jokerdata.common.annotation.Auth;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.common.utils.RequestHolder;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.PageResule;
import com.jokerdata.parames.vo.SpreadBeanVo;
import com.jokerdata.service.app.*;
import com.jokerdata.service.common.JpushService;
import com.jokerdata.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.Math;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/Share")
@Transactional(rollbackFor = ApiException.class)
public class ShareController {



    @Autowired
    private UserService userService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private ShareLogService shareLogService;

    @Autowired
    private CoinLogService coinLogService;

    @Autowired
    private PdLogService pdLogService;

    @Autowired
    private JpushService jpushService;

    @GetMapping(value = "/share_list",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public PageResule share_list(String key, @RequestParam(required = false,defaultValue = "1") int curpage){
        User user = RequestHolder.getUser();
        if (curpage == 0) {
            curpage = 1;
        }
        IPage<MonetListVo> shareListPage = new Page<>();
        shareListPage.setCurrent(curpage);
        shareListPage.setSize(10);
        shareListPage = shareService.userShareList(shareListPage,user.getUserId());

        Map<String,Object> data = new HashMap<>();
        data.put("list", ShareUtil.toLowBeanList(shareListPage.getRecords()));
        data.put("curpage",shareListPage.getCurrent());
        return PageResule.success(data).setPage((Page) shareListPage);

    }

    @PostMapping(value = "/repeat_wb",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult repeat_wb(String key, String share_id,String account_id,String content){
        User user = RequestHolder.getUser();

        if(StringUtils.isEmpty(share_id)||StringUtils.isEmpty(account_id)||StringUtils.isEmpty(content)){
            return ApiResult.error("参数错误");
        }
        Share share = shareService.getById(share_id);
        //微博
        if(!share.getShareType().equals("1")){
            return ApiResult.error("公众号转发失败");
        }

        if(share.getHaveSharedNum()>= share.getShareNum()){
            return ApiResult.error("已达分享上限");
        }

        UserAccount userAccount = userAccountService.getById(account_id);
        //创建coinLog记录
        CoinLog coinLog = new CoinLog();
        //创建现金记录
        PdLog pdLog = new PdLog();

        if("0".equals(share.getShareStatus())){
            coinLog.setLogUserId(user.getUserId()+"");
            coinLog.setLogUserName(user.getUserName());
            coinLog.setLogAdminId("-1");
            coinLog.setLogAdminName("发布接口创建");
            coinLog.setLogType("task_income_check");//待审核积分
            coinLog.setLogAvCoin(new BigDecimal(share.getShareCoin()));
            coinLog.setLogFreezeCoin(new BigDecimal(0));
            coinLog.setLogMark("发布接口创建");
            coinLog.setAddTime(new Date().getTime()/1000);
            if(!coinLogService.save(coinLog)){
                throw new ApiException("保存失败");
            }
            coinLog = coinLogService.getOne(new QueryWrapper<CoinLog>().eq("log_user_id",coinLog.getLogUserId())
                    .eq("add_time",coinLog.getAddTime())
            );
        }else{
            pdLog.setLgMemberId(user.getUserId());
            pdLog.setLgMemberName(user.getUserName());
            pdLog.setLgType("task_check_in");
            DecimalFormat df = new DecimalFormat("#.0");
            //随机金额
            Double t = Math.random()*(share.getCoinMax().doubleValue()-share.getCoinMin().doubleValue())+share.getCoinMin().doubleValue();
            pdLog.setLgAvAmount(new BigDecimal(df.format(t)));
            pdLog.setLgAddTime(new Date().getTime()/1000);
            pdLog.setLgDesc("分享微博现金");
            if(!pdLogService.save(pdLog)){
                throw new ApiException("保存失败");
            }
            pdLog = pdLogService.getOne(new QueryWrapper<PdLog>().eq("lg_add_time",pdLog.getLgAddTime())
                            .eq("lg_member_id",pdLog.getLgMemberId())
            );
        }


        ShareLog shareLog = new ShareLog();
        shareLog.setUserId(user.getUserId());
        shareLog.setShareId(share.getShareId());
        if("0".equals(share.getShareStatus())){
            shareLog.setShareCoin(share.getShareCoin());
            shareLog.setShareMoney(new BigDecimal(0));
            shareLog.setRewardType(0);
            shareLog.setLogId(coinLog.getLogId()+"");
            //实际消耗数量
            share.setOriginalCoin(String.valueOf(Double.parseDouble(share.getOriginalCoin())+share.getShareCoin()));
        }else{
            shareLog.setShareMoney(pdLog.getLgAvAmount());
            shareLog.setShareCoin(0);
            shareLog.setRewardType(1);
            shareLog.setLogId(pdLog.getLgId()+"");
            //实际消耗数量
            share.setOriginalCoin(String.valueOf(Double.parseDouble(share.getOriginalCoin())+pdLog.getLgAvAmount().doubleValue()));
        }

        shareLog.setAddTime(new Date().getTime()/1000+"");
        shareLog.setIsPass(0);
        shareLog.setContent(content);
        shareLog.setAccountId(userAccount.getAccountId()+"");
        shareLog.setMasterId(share.getUserId());

        jpushService.shareWeiBo(shareLog,share);
        //审核后要将logid记录进去
        if(!shareLogService.save(shareLog)){
            throw new ApiException("保存失败");
        }

        share.setHaveSharedNum(share.getHaveSharedNum()+1);
        if(!shareService.updateById(share)){
            throw new ApiException("更新失败");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("reward_type",share.getShareStatus());
        if("0".equals(share.getShareStatus())){
            map.put("reward",share.getShareCoin());

        }else{
            map.put("reward",pdLog.getLgAvAmount());
        }
        return ApiResult.success(map);

    }

    @GetMapping(value = "/my_spread",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public PageResule my_spread(String key, @RequestParam(required = false,defaultValue = "1") int curpage) {
        User user = RequestHolder.getUser();
        if (curpage == 0) {
            curpage = 1;
        }

        IPage<SpreadBeanVo> shareListPage = new Page<>();
        shareListPage.setCurrent(curpage);
        shareListPage.setSize(10);
        shareListPage = shareLogService.getSPreadList(shareListPage,user.getUserId());
        Map<String,Object> data = new HashMap<>();
        data.put("list", ShareUtil.toLowBeanList(shareListPage.getRecords()));
        data.put("curpage",shareListPage.getCurrent());
        return PageResule.success(data).setPage((Page) shareListPage);
    }


    @GetMapping(value = "/cansel_coin_push",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult cansel_coin_push(String key, String share_id) {
        User user = RequestHolder.getUser();
        if(StringUtils.isEmpty(share_id)){
            return  ApiResult.error("参数错误");
        }

        Share share = shareService.getOne(new QueryWrapper<Share>()
                    .eq("share_id",share_id)
                    .eq("user_id",user.getUserId())
        );
        if(share == null){
            return  ApiResult.error("不存在");

        }
        if("2".equals(share.getShareStatus())){
            return  ApiResult.error("已取消");
        }
        if(1 != (share.getFromApp())){
            return  ApiResult.error("仅客户端发布的任务可取消");
        }

        if(share.getHaveSharedNum()< share.getShareNum()){
            CoinLog coinLog = new CoinLog();
            coinLog.setLogUserId(user.getUserId()+"");
            coinLog.setLogUserName(user.getUserName());
            coinLog.setLogType("refound");
            coinLog.setLogAvCoin(new BigDecimal(Long.parseLong(share.getOriginalCoin())*(share.getShareNum()-share.getHaveSharedNum())));
            coinLog.setLogFreezeCoin(new BigDecimal(0));
            coinLog.setAddTime(new Date().getTime()/1000);

            user.setUserCoin(user.getUserCoin()+coinLog.getLogAvCoin().intValue());
            userService.updateById(user);
            coinLogService.save(coinLog);
        }

        share = shareService.getOne(new QueryWrapper<Share>().eq("share_id",share_id));
        share.setShareState("4");
       ;

        if( shareService.updateById(share)){
            return ApiResult.success();
        }
        return  ApiResult.error("失败");
    }

}
