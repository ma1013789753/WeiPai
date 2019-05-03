package com.jokerdata.parames;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class PayPassword {

    @NotEmpty
    private String wechat_openid;
    @NotEmpty
    private String alipay_account;
    @NotEmpty
    @Length(min = 6,max = 16,message = "密码长度不正确")
    private String user_pay_pwd;
    @NotEmpty
    private String code;
}
