package com.jokerdata.parames.vo;

import com.jokerdata.entity.app.generator.Share;
import lombok.Data;

import java.util.List;


@Data
public class MonetListVo extends Share {

    private List<UserListVo> user_list;
    private String add_time_text;
}
