package com.jokerdata.parames;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ShareIndexParams {
    @NotEmpty
    public String key;
    public String tag_id;
    public String is_new;
    public String is_self;
    public String is_hot;
    public String is_rich;
    public String curpage;
}
