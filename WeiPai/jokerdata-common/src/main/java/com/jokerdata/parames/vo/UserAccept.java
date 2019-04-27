package com.jokerdata.parames.vo;

import lombok.Data;

@Data
public class UserAccept {
//share_id ,a.account_id,account_name,access_token,avatar_hd,account_state,sl_id
    private Long account_id;
    private String account_name;
    private String access_token;
    private String avatar_hd;
    private Long account_state;
    private Long sl_id;
}
