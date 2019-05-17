package com.jokerdata.parames;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class LoginParames {


    @NotEmpty
    @Length(min = 6,max = 16)
    String password;

    @Pattern(regexp="^1[0-9]{10}$",message = "手机号不正确")
    @NotEmpty
    String mobile;
}
