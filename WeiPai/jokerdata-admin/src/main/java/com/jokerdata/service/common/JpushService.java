package com.jokerdata.service.common;

import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.entity.app.generator.ShareLog;
import com.jokerdata.entity.app.generator.UserAccount;
import com.jokerdata.vo.PShareLog;

public interface JpushService {

    void shareStart(Share share);

    void shareWeiBo(ShareLog shareLog,Share share);

    void approveState(UserAccount userAccount, int i);

    void approveWeibo(PShareLog pShareLog, int i);
}
