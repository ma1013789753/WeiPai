package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Cms;
import com.jokerdata.mapper.app.custom.CmsCustomMapper;
import com.jokerdata.service.admin.CustomCmsService;
import com.jokerdata.service.app.CmsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  资讯 服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-05-29
 */
@Service
public class CustomCmsServiceImpl extends ServiceImpl<CmsCustomMapper, Cms> implements CustomCmsService {

}
