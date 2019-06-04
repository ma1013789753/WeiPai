package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.CmsCate;
import com.jokerdata.mapper.app.generator.CmsCateMapper;
import com.jokerdata.service.admin.CustomCmsCateService;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  资讯分类 服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-05-29
 */
@Service
public class CustomCmsCateServiceImpl extends ServiceImpl<CmsCateMapper, CmsCate> implements CustomCmsCateService {

}
