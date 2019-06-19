package com.jokerdata.controller.admin;

import com.jokerdata.entity.Jweibo;
import com.jokerdata.service.common.ToutiaoService;
import com.jokerdata.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 头条内容抓取控制器
 * </p>
 * @author aozhang
 * @since 2019-5-1
 */
@RestController
@RequestMapping("/toutiao")
@Api(value = "ToutiaoCrawlerController",description  = "头条内容抓取控制")
public class ToutiaoCrawlerController {

    @Autowired
    ToutiaoService toutiaoService;

    @GetMapping("/pick")
    @ApiOperation(value = "抓取指定url头条",notes = "")
    public Result getToutiaoByUrl(String url){
        Jweibo jweibo = toutiaoService.pickToutiaoByUrl(url);
        return Result.success("success",jweibo);
    }
}
