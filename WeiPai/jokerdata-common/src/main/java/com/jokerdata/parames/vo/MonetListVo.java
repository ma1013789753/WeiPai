package com.jokerdata.parames.vo;

import com.jokerdata.entity.app.generator.Share;
import lombok.Data;

import java.util.List;


@Data
public class MonetListVo extends Share {

    private List<UserListVo> userList;
    private String addTimeText;

//    private String account_avatar;

    private String avatar_hd;

    private String share_num;

    private String user_avatr;
    private Object shareVideoJson;
    private String ratio;
    private Object shareImageJson;
    private Object shareImgJson;
}
