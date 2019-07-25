package com.jokerdata.parames;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class TTBindPms {

    @NotEmpty
    String wechat_name;
    @NotEmpty
    String follow_num;
    @NotEmpty
    String num_screen;
    @NotEmpty
    String avatarPic;

    @NotEmpty
    String tag_id;
}
