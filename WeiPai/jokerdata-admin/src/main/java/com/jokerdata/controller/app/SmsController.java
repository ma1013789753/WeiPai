package com.jokerdata.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.jokerdata.common.annotation.Login;
import com.jokerdata.common.exception.MyException;
import com.jokerdata.common.utils.ConstCode;
import com.jokerdata.entity.app.generator.Spare;
import com.jokerdata.service.app.SpareService;
import com.jokerdata.service.common.AliSmsService;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/Public")
public class SmsController {

    @Autowired
    private AliSmsService aliSmsService;


    @GetMapping(value = "/get_sms",produces = "application/json;charset=UTF-8")
    public void getId(){

        aliSmsService.sendSms(1,"15501699126","123456");

    }

}
