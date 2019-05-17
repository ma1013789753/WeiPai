package com.jokerdata.service.common.impl;

import com.jokerdata.service.common.WeiBoService;
import com.jokerdata.vo.Result;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeiBoServiceImpl implements WeiBoService {
    Logger logger = LoggerFactory.getLogger(WeiBoServiceImpl.class);

    @Override
    public Result getWeiBoByUrl(String url) {

        return null;
    }
}
