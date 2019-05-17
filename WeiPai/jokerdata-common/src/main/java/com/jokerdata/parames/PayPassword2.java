package com.jokerdata.parames;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class PayPassword2 {

    @NotEmpty
    @Length(min = 6,max = 16,message = "密码长度不正确")
    private String user_pay_pwd;
    @NotEmpty
    private String r_user_pay_pwd;

    private String code;
}
