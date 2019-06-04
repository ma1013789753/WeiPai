package com.jokerdata.common.config.push;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.jokerdata.common.config.push.config.MessagePush;

public interface IJPushService {

    /**
     * @Description: 推送消息到所有平台
     * @param: [messagePush]
     * @return: java.lang.Boolean
     * @Author: zhuys
     * @Date: 2019/4/25 17:35
     */
    Boolean sendMessageToAll(MessagePush messagePush) throws APIConnectionException, APIRequestException;

    /**
     * @Description: 推送消息到指定分组
     * @param: [messagePush]
     * @return: java.lang.Boolean
     * @Author: zhuys
     * @Date: 2019/4/25 17:36
     */
    Boolean sendMessageToGroup(MessagePush messagePush) throws APIConnectionException, APIRequestException;

    /**
     * @Description: 推送消息到个人
     * @param: [messagePush]
     * @return: java.lang.Boolean
     * @Author: zhuys
     * @Date: 2019/4/27 15:27
     */
    Boolean sendMessageToPersonalDriver(MessagePush messagePush) throws APIConnectionException, APIRequestException;



}
