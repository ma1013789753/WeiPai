package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Shop;
import com.jokerdata.mapper.app.generator.ShopMapper;
import com.jokerdata.service.app.ShopService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oldMa
 * @since 2019-08-15
 */
@Service
public class ShopImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

}
