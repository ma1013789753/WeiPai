package com.jokerdata.vo;

import lombok.Data;

@Data
public class AliPayVo  {

    private String out_biz_no;

    private String payee_type;

    private String amount;

    private String payee_account;

    private String payer_show_name;

    private String payee_real_name;

    private String remark;
}
