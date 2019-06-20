package com.jokerdata.vo;


import lombok.Data;

@Data
public class OrderSnVo {

    private String order_amount;
    private String order_sn;
    private int coin_amount;
    private String rate;
}
