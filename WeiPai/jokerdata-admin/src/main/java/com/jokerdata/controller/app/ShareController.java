package com.jokerdata.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.common.ShareUtil;
import com.jokerdata.common.annotation.Auth;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.common.utils.RequestHolder;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.PageResule;
import com.jokerdata.parames.vo.SpreadBeanVo;
import com.jokerdata.service.app.*;
import com.jokerdata.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.Math;
import java.math.BigDecimal;
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
        UserAccount userAccount = userAccountService.getById(account_id);
        //创建coinLog记录
        CoinLog coinLog = new CoinLog();

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
        }
        PdLog pdLog = new PdLog();
        pdLog.setLgMemberId(user.getUserId());
        pdLog.setLgMemberName(user.getUserName());
        pdLog.setLgType("task_in_check");
        Double t = Math.random()*(share.getCoinMax().doubleValue()-share.getCoinMin().doubleValue());

//        pdLog.setLgAvAmount();
        pdLog.setLgAddTime(new Date().getTime()/1000);
        pdLog.setLgDesc("分享微博现金");


        if("11".equals(share.getShareStatus())){
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
        }


        ShareLog shareLog = new ShareLog();
        shareLog.setUserId(user.getUserId());
        shareLog.setShareId(share.getShareId());
        shareLog.setShareCoin(share.getShareCoin());
        shareLog.setAddTime(new Date().getTime()/1000+"");
        shareLog.setIsPass(0);
        shareLog.setRewardType(0);
        shareLog.setContent(content);
        shareLog.setAccountId(userAccount.getAccountId()+"");
        shareLog.setMasterId(share.getUserId());
        //审核后要将logid记录进去
        shareLog.setLogId(coinLog.getLogId()+"");
        if(!shareLogService.save(shareLog)){
            throw new ApiException("保存失败");
        }
        share.setHaveSharedNum(share.getHaveSharedNum()+1);
        if(!shareService.updateById(share)){
            throw new ApiException("更新失败");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("reward_type",share.getShareStatus());
        map.put("reward",share.getShareCoin());
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

    public static void main(String[] args) {
    }
}
