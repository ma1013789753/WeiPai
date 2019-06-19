package com.jokerdata.service.common;


import com.jokerdata.entity.Jweibo;

/**
 * <p>
 *  微博抓取服务
 * </p>
 *
 * @author aozhang
 * @since 2019-05-1
 */
public interface WeiboService {
    Jweibo pickDataByUrl(String url);
    void crawlingShareData();
}
