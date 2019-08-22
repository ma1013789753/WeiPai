package com.jokerdata.vo;

import com.jokerdata.entity.app.generator.Shop;
import com.jokerdata.entity.app.generator.ShopHis;
import com.jokerdata.entity.app.generator.User;
import com.jokerdata.parames.vo.UserInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;


@Data
public class ShopHisVo extends ShopHis {


    private User user;
    private Shop shop;
}
