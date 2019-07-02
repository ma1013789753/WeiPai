package com.jokerdata.service.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.common.push.config.MessagePush;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.service.app.IJPushService;
import com.jokerdata.service.app.SystemMsgService;
import com.jokerdata.service.app.SystemmsgUserService;
import com.jokerdata.service.app.UserAccountService;
import com.jokerdata.service.common.JpushService;
import com.jokerdata.vo.PShareLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JpushServiceImpl implements JpushService {

    @Autowired
    IJPushService ijPushService;
    @Autowired
    SystemMsgService systemMsgService;

    @Autowired
    SystemmsgUserService systemmsgUserService;

    @Autowired
    UserAccountService userAccountService;

    /**
     * 发布成功
     * @param share
     */
    @Override
    @Async
    public void shareStart(Share share) {
        MessagePush messagePush = new MessagePush();
        messagePush.setContent("您的任务《"+share.getShareUrl()+"》发布成功，正在推广");
        messagePush.setTitle("WeiPro");
        messagePush.setMsgType("push");
        messagePush.setAwardType(0);
        messagePush.setType("dynamic");
        messagePush.setData(0.0);
        List<String> tags = new ArrayList<>();
        tags.add("u_"+share.getUserId());
        messagePush.setTags(tags);
        ijPushService.sendMessageToPersonal(messagePush);

        saveMessageShareStart(messagePush,share.getUserId());

    }

    /**
     * 分享成功
     * @param shareLog
     */
    @Override
    @Async
    public void shareWeiBo(ShareLog shareLog,Share share) {
        MessagePush messagePush = new MessagePush();
        messagePush.setContent("您成功转发《"+share.getShareUrl()+"》,获得预推广任务奖励");
        messagePush.setTitle("WeiPro");
        messagePush.setType("dynamic");
        messagePush.setMsgType("succ_user");
        if(share.getShareStatus().equals("0")){
            messagePush.setAwardType(1);
            messagePush.setData(shareLog.getShareCoin().doubleValue());
        }else{
            messagePush.setAwardType(2);
            messagePush.setData(shareLog.getShareMoney().doubleValue());

        }
        List<String> tags = new ArrayList<>();
        tags.add("u_"+shareLog.getUserId());
        messagePush.setTags(tags);

        ijPushService.sendMessageToPersonal(messagePush);
        saveMessageShareStart(messagePush,shareLog.getUserId());


        UserAccount userAccount = userAccountService.getById(shareLog.getAccountId());
        messagePush = new MessagePush();
        messagePush.setContent("您发布的《"+share.getShareUrl()+"》任务,被"+userAccount.getAccountName()+"成功转发");
        messagePush.setTitle("WeiPro");
        messagePush.setMsgType("succ_master");
        messagePush.setAwardType(0);
        messagePush.setType("dynamic");
        messagePush.setData(0.0);

        tags = new ArrayList<>();
        tags.add("u_"+share.getUserId());
        messagePush.setTags(tags);
        ijPushService.sendMessageToPersonal(messagePush);
        saveMessageShareStart(messagePush,share.getUserId());

    }

    //账号审核通知
    @Override
    @Async
    public void approveState(UserAccount userAccount, int i) {
        MessagePush messagePush = new MessagePush();
        if(1 == i){
            messagePush.setContent(userAccount.getAccountName()+"账号未通过审核");
        }else{
            messagePush.setContent(userAccount.getAccountName()+"账号已通过审核");
        }

        messagePush.setTitle("WeiPro");
        messagePush.setType("system");
        messagePush.setMsgType("system");

        messagePush.setData(0.0);
        messagePush.setAwardType(0);
        List<String> tags = new ArrayList<>();
        tags.add("u_"+userAccount.getUserId());
        messagePush.setTags(tags);

        ijPushService.sendMessageToPersonal(messagePush);
        saveMessageShareStart(messagePush,userAccount.getUserId());


    }

    @Override
    public void approveWeibo(PShareLog pShareLog, int i) {
        MessagePush messagePush = new MessagePush();
        if(1 == i){
            messagePush.setContent("您转发的《"+pShareLog.getShareLog().getContent()+"》,未检测到转发的微博，任务奖励取消");
            messagePush.setMsgType("push_false");
        }else{
            messagePush.setContent("您转发的《"+pShareLog.getShareLog().getContent()+"》,检测成功，任务奖励到账");
            messagePush.setMsgType("succ_user");
        }
        messagePush.setTitle("WeiPro");
        messagePush.setType("dynamic");
        if(pShareLog.getShare().getShareStatus().equals("0")){
            messagePush.setAwardType(1);
            messagePush.setData(pShareLog.getShareLog().getShareCoin().doubleValue());
        }else{
            messagePush.setAwardType(2);
            messagePush.setData(pShareLog.getShareLog().getShareMoney().doubleValue());

        }
        List<String> tags = new ArrayList<>();
        tags.add("u_"+pShareLog.getShareLog().getUserId());
        messagePush.setTags(tags);

        ijPushService.sendMessageToPersonal(messagePush);
        saveMessageShareStart(messagePush,pShareLog.getShareLog().getUserId());
    }

    public int saveMessageShareStart(MessagePush messagePush,Integer userId){
        SystemMsg systemMsg = new SystemMsg();
        systemMsg.setNoticeType(messagePush.getMsgType());
        systemMsg.setNoticeContent(messagePush.getContent());
        systemMsg.setAddTime(new Date().getTime()/1000+"");
        systemMsg.setIsNew(0);
        systemMsg.setType(messagePush.getAwardType());
        systemMsg.setData(new BigDecimal(messagePush.getData()));
        if(!systemMsgService.save(systemMsg)){
            throw new ApiException("保存失败");
        }
        systemMsg = systemMsgService.getOne(new QueryWrapper<SystemMsg>().eq("add_time",systemMsg.getAddTime()));

        SystemmsgUser systemmsgUser = new SystemmsgUser();
        systemmsgUser.setIsRead(0);
        systemmsgUser.setNoticeId(systemMsg.getNoticeId());
        systemmsgUser.setUserId(userId);
        if(!systemmsgUserService.save(systemmsgUser)){
            throw new ApiException("保存失败");
        }
        return 1;

    }

}
