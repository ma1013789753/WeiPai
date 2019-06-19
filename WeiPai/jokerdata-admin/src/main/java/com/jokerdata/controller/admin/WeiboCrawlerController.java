package com.jokerdata.controller.admin;

import com.jokerdata.entity.Jweibo;
import com.jokerdata.service.common.WeiboService;
import com.jokerdata.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 微博内容抓取控制器
 * </p>
 * @author aozhang
 * @since 2019-5-1
 */
@RestController
@RequestMapping("/weibo")
@Api(value = "WeiboCrawlerController",description  = "微博内容抓取控制")
public class WeiboCrawlerController {

    @Autowired
    WeiboService weiboService;

    @GetMapping("/pick")
    @ApiOperation(value = "抓取指定url微博",notes = "")
    public Result getWeiboByUrl(String url){
        Jweibo jweibo = weiboService.pickDataByUrl(url);
        return Result.success("success",jweibo);
    }
}
