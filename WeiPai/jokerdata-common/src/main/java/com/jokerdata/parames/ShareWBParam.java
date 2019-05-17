package com.jokerdata.parames;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class ShareWBParam {

    private int share_coin;
    @NotBlank
    private String wtoken;
    private int share_num;
    @NotEmpty
    private String is_original;
    private int tag_id;
    @NotEmpty
    private String share_url;
}
