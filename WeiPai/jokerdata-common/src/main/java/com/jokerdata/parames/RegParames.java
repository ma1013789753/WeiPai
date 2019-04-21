package com.jokerdata.parames;


import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class RegParames extends LoginParames implements Serializable{

    @NotEmpty
    @Length(min = 6,max = 6,message = "验证码为6位")
    String sms;
}
