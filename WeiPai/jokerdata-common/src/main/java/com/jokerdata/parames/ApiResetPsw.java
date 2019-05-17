package com.jokerdata.parames;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class ApiResetPsw {

    @NotEmpty
    @Length(min = 6,max = 16)
    String user_password;
    @NotEmpty
    @Length(min = 6,max = 16)
    String newpassword;
    @NotEmpty
    @Length(min = 6,max = 16)
    String renewpassword;
}
