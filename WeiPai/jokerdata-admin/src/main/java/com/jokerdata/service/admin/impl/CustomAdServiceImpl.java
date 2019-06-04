package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Ad;
import com.jokerdata.mapper.app.generator.AdMapper;
import com.jokerdata.service.admin.CustomAdService;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  广告 服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-05-30
 */
@Service
public class CustomAdServiceImpl extends ServiceImpl<AdMapper, Ad> implements CustomAdService {

}
