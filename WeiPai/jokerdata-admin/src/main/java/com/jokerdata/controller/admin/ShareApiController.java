package com.jokerdata.controller.admin;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.common.ShareUtil;
import com.jokerdata.common.annotation.Auth;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.common.utils.RequestHolder;
import com.jokerdata.controller.app.ShareController;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.PageResule;
import com.jokerdata.parames.vo.SpreadBeanVo;
import com.jokerdata.service.admin.CustomShareService;
import com.jokerdata.service.admin.CustomShareTagService;
import com.jokerdata.service.app.*;
import com.jokerdata.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.Math;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/Api/Share")
@Transactional(rollbackFor = ApiException.class)
public class ShareApiController {


    @Autowired
    private ShareService shareService;

    @Autowired
    private CustomShareService customShareService;

    @Autowired
    private CustomShareTagService customShareTagService;

    @Autowired
    private CoinLogService coinLogService;

    @Autowired
    private PdLogService pdLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShareLogService shareLogService;

    @Autowired
    private CLogController cLogController;

    @Autowired
    private PLogController pLogController;

    @Login
    @PostMapping(value = "/getPage",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取列表",notes = "")
    public Result getPage(@RequestBody MyPage page,@RequestParam int type){
        if (type != 2) {
            IPage<Share> data = customShareService.getSharePage(page,type);
            return Result.success(data);
        }else {
            IPage<Share> data = shareService.page(page,new QueryWrapper<Share>()
                    .eq("share_state",1)
                    .orderByDesc("add_time")
                    .like("user_name",page.getSearch1())
                    .like("share_state",page.getSearch2())
            );
            return Result.success(data);
        }


    }

    @Login
    @PostMapping(value = "/approve",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "审核订单",notes = "")
    public Result approve(@RequestBody Share param){
        User user = userService.getById(param.getUserId());
        //通过
        if("0".equals(param.getShareState())){
            if(!shareService.updateById(param)){
                throw new ApiException("更新失败");
            }
        }

        if(param.getShareStatus().equals("0")){
            //更新发布者积分
            CoinLog coinLog = new CoinLog();
            coinLog.setLogUserId(user.getUserId()+"");
            coinLog.setLogUserName(user.getUserName());
            coinLog.setLogType("refound");
            coinLog.setLogAvCoin(new BigDecimal((param.getShareCoin())*(param.getShareNum()-param.getHaveSharedNum())));
            coinLog.setLogFreezeCoin(new BigDecimal(0));
            coinLog.setAddTime(new Date().getTime()/1000);
            coinLogService.save(coinLog);

            user.setUserCoin(user.getUserCoin()+coinLog.getLogAvCoin().intValue());
            userService.updateById(user);
            //更新所有转发者积分
            QueryWrapper<ShareLog> shareLogQueryWrapper = new QueryWrapper<>();
            shareLogQueryWrapper.eq("share_id",param.getShareId());
            shareLogQueryWrapper.eq("is_pass","0");
            List<ShareLog> shareLogs = shareLogService.list(shareLogQueryWrapper);
            shareLogs.forEach(shareLog -> {
                CoinLog userCoinLog = coinLogService.getById(shareLog.getLogId());
                CShareLog cShareLog = new CShareLog();
                BeanUtils.copyProperties(userCoinLog, cShareLog);
                cShareLog.setShare(param);
                cShareLog.setShareLog(shareLog);
                cLogController.approve(cShareLog);
            });
        }

        if(param.getShareStatus().equals("1")){
            PdLog pdCash = pdLogService.getOne(new QueryWrapper<PdLog>()
                    .eq("lg_type","task_freeze")
                    .eq("lg_desc",param.getShareId())
            );
            if(pdCash==null){
                throw new ApiException("不存在");
            }
            //查看花了多少
            BigDecimal count = new BigDecimal(param.getOriginalCoin());

            if(count == null ){
                throw new ApiException("错误");
            }
            //退还金额
            PdLog refunt = new PdLog();
            refunt.setLgType("refund");
            refunt.setLgMemberId(pdCash.getLgMemberId());
            refunt.setLgMemberName(pdCash.getLgMemberName());
            refunt.setLgAddTime(new Date().getTime()/1000);
            refunt.setLgAvAmount(new BigDecimal(-pdCash.getLgAvAmount().doubleValue()).subtract(count));
            if(!pdLogService.save(refunt)){
                throw new ApiException("更新失败");
            }
            User target = userService.getById(param.getUserId());
            target.setAvailablePredeposit(target.getAvailablePredeposit().add(refunt.getLgAvAmount()));

            if(!userService.updateById(target)){
                throw new ApiException("更新失败");
            }


            //更新所有转发者积分
            QueryWrapper<ShareLog> shareLogQueryWrapper = new QueryWrapper<>();
            shareLogQueryWrapper.eq("share_id",param.getShareId());
            shareLogQueryWrapper.eq("is_pass","0");
            List<ShareLog> shareLogs = shareLogService.list(shareLogQueryWrapper);
            shareLogs.forEach(shareLog -> {
                PdLog pdLog = pdLogService.getById(shareLog.getLogId());
                PShareLog pShareLog = new PShareLog();
                BeanUtils.copyProperties(pdLog, pShareLog);
                pShareLog.setShare(param);
                pShareLog.setShareLog(shareLog);
                pLogController.approve(pShareLog);
            });


        }
        return Result.success("保存失败");
    }

    @Login
    @PostMapping(value = "/tuiJian",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "推荐订单",notes = "")
    public Result tuiJian(@RequestBody Share param){
        if(param == null ||param.getShareId() == null){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        param = shareService.getById(param.getShareId());
        param.setShareRecommend(1);
        if(shareService.updateById(param)){
            return Result.success();
        };
        return Result.error500("保存失败");
    }

    @Login
    @PostMapping(value = "/getShare",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "互推查看",notes = "")
    public Result getShareById(@RequestBody Share share){
        if(share == null ||share.getShareId() == null){
            throw new MyException("参数异常", ConstCode.CODE_403);
        }
        share = customShareService.getShareById(share.getShareId());
        return Result.success("操作成功",share);
    }

    @Login
    @PostMapping(value = "/getShareTag")
    @ApiOperation(value = "获取互推标签",notes = "")
    public Result getShareTag(){
        List<ShareTag> shareTags = customShareTagService.list(null);
        return Result.success("操作成功",shareTags);
    }

    @Login
    @PostMapping(value = "/updateTag",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "更新互推标签",notes = "")
    public Result updateTag(@RequestBody ShareTag shareTag){
        boolean res = customShareTagService.updateById(shareTag);
        if(!res){
            throw new MyException("更新失败", ConstCode.CODE_403);
        }
        return Result.success("操作成功");
    }

    @Login
    @PostMapping(value = "/addTag",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加互推标签",notes = "")
    public Result addTag(@RequestBody ShareTag shareTag){
        boolean res = customShareTagService.save(shareTag);
        if(!res){
            throw new MyException("操作失败", ConstCode.CODE_403);
        }
        return Result.success("操作成功");
    }
}
