package com.jokerdata.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.common.ShareUtil;
import com.jokerdata.common.annotation.Auth;
import com.jokerdata.common.utils.RequestHolder;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.PageResule;
import com.jokerdata.parames.vo.SpreadBeanVo;
import com.jokerdata.service.app.*;
import com.jokerdata.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class ShareController {


    @Autowired
    private SmsService smsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private ShareLogService shareLogService;

    @Autowired
    private CoinLogService coinLogService;



    @GetMapping(value = "/share_list",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public PageResule share_list(String key, @RequestParam(required = false) int curpage){
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

    @GetMapping(value = "/my_spread",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public PageResule my_spread(String key, @RequestParam(required = false) int curpage) {
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
