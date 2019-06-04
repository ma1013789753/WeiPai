package com.jokerdata.common.config.push.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.DefaultResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.jokerdata.common.config.push.IJPushService;
import com.jokerdata.common.config.push.config.MessagePush;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


import static cn.jpush.api.push.model.notification.PlatformNotification.ALERT;

@Service
public class jPushService implements IJPushService {

    @Resource
    private JPushClient pushDriverClient;

    @Resource
    private JPushClient pushOwnerClient;

    @Override
    public Boolean sendMessageToAll(MessagePush messagePush)
            throws APIConnectionException, APIRequestException {
        PushResult pushResult = pushDriverClient.sendPush(getAllMessage(messagePush));
        return handlePushResult(pushResult);
    }

    @Override
    public Boolean sendMessageToGroup(MessagePush messagePush)
            throws APIConnectionException, APIRequestException {
        PushResult pushResult;
        pushResult = pushDriverClient.sendPush(getAssignationMessage(messagePush));

        return handlePushResult(pushResult);
    }

    @Override
    public Boolean sendMessageToPersonalDriver(MessagePush messagePush) throws APIConnectionException, APIRequestException {
        messagePush.setSegment("");
        PushResult pushResult = pushDriverClient.sendPush(getAssignationMessage(messagePush));
        return handlePushResult(pushResult);
    }


    /**
     * 获取发送至所有平台需要的消息格式
     *
     * @param messagePush
     * @return
     */
    private PushPayload getAllMessage(MessagePush messagePush){
        PushPayload pushPayload =
                PushPayload.newBuilder()
                        .setPlatform(Platform.all())
                        .setAudience(Audience.all())
                        .setMessage(Message.newBuilder()
                                .setMsgContent(messagePush.getContent())
                                .setTitle(messagePush.getTitle())
                                .build())
                        .build();
        return pushPayload;
    }

    /**
     *  获取发送至指定分组需要的消息格式
     *
     * @param messagePush
     * @return
     */
    public PushPayload getAssignationMessage(MessagePush messagePush){
        Notification notification = Notification.newBuilder()
                .addPlatformNotification(IosNotification.newBuilder()
                        .setAlert(messagePush.getContent())
                        .setBadge(5)
                        .setSound("happy")
                        .addExtra("from", "JPush")
                        .build())
                .addPlatformNotification(AndroidNotification.newBuilder()
                        .setAlert(messagePush.getContent())
                        .setTitle(messagePush.getTitle())
                        .addExtra("from", "JPush")
                        .build())
                .build();
        PushPayload pushPayload =
                PushPayload.newBuilder()
                        .setPlatform(Platform.all())
                        .setAudience(Audience.segment(messagePush.getSegment()))
//                        .setMessage(Message.newBuilder()
//                                .setMsgContent(messagePush.getContent())
//                                .setTitle(messagePush.getTitle())
//                                .build())
                        .setNotification(notification)
                        .build();
        return pushPayload;
    }

    /**
     * 构建所有平台通知消息
     * @param messagePush
     * @return
     */
    public static PushPayload getSingleAllMessage(MessagePush messagePush) {
        Notification notification = Notification.newBuilder()
                .addPlatformNotification(IosNotification.newBuilder()
                        .setAlert(messagePush.getContent())
                        .setBadge(5)
                        .setSound("happy")
                        .addExtra("from", "JPush")
                        .build())
                .addPlatformNotification(AndroidNotification.newBuilder()
                        .setAlert(messagePush.getContent())
                        .setTitle(messagePush.getTitle())
                        .addExtra("from", "JPush")
                    .build())
                .build();

        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.registrationId(messagePush.getRegistrationIds()))
//                .setMessage(Message.newBuilder()
//                        .setMsgContent(messagePush.getContent())
//                        .setTitle(messagePush.getTitle())
//                        .build())
                .setNotification(notification)
                .build();
    }

    /**
     * 构建安卓通知消息
     * @param messagePush
     * @return
     */
    public static PushPayload getSingleAndroidMessage(MessagePush messagePush) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(messagePush.getRegistrationIds()))
                .setNotification(Notification.android(ALERT, messagePush.getTitle(), null))
                .build();
    }

    /**
     *
     * 构建IOS通知消息
     * @param messagePush
     * @return
     */
    public static PushPayload getSingleIosMessage(MessagePush messagePush) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(messagePush.getRegistrationIds()))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(ALERT)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                .setMessage(Message.content(messagePush.getContent()))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

    /**
     * 获取发送至指定个人分组需要的消息格式
     *
     * @param messagePush
     * @return
     */
    public PushPayload getSingleMessage(MessagePush messagePush) {
        PushPayload pushPayload =
                PushPayload.newBuilder()
                        .setPlatform(Platform.all())
                        .setAudience(Audience.registrationId(messagePush.getRegistrationIds()))
                        .setMessage(Message.newBuilder()
                                .setMsgContent(messagePush.getContent())
                                .setTitle(messagePush.getTitle())
                                .build())
                        .build();
        return pushPayload;
    }

    /**
     * @Description: 处理推送结果
     * @param: [PushResult]
     * @return: Boolean
     * @Author: zhuys
     * @Date: 2019/4/27 15:30
     */
    public Boolean handlePushResult(PushResult pushResult) {
        if(pushResult.getResponseCode() == 200){
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description: 处理添加标签结果
     * @param: [PushResult]
     * @return: Boolean
     * @Author: zhuys
     * @Date: 2019/4/27 15:30
     */
    public Boolean handleDefaultResult(DefaultResult defaultResult) {
        if(defaultResult.getResponseCode() ==200){
            return true;
        } else {
            return false;
        }
    }
}
