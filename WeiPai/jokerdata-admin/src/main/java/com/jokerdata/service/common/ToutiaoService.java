package com.jokerdata.service.common;


import com.jokerdata.entity.Jweibo;

/**
 * <p>
 *  头条抓取服务
 * </p>
 *
 * @author aozhang
 * @since 2019-05-1
 */
public interface ToutiaoService {
    Jweibo pickToutiaoByUrl(String url);
    void crawlingToutiaoShare();
}
