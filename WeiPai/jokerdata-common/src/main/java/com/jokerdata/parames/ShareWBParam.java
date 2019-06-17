package com.jokerdata.parames;

import com.jokerdata.entity.Jweibo;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class ShareWBParam {

    private double share_coin;
    @NotBlank
    private String wtoken;
    private int share_num;
    @NotEmpty
    private String is_original;
    private int tag_id;
    @NotEmpty
    private String share_url;
    private String json;
    private Double free;

    private String t;
    private String jweibo;

}
