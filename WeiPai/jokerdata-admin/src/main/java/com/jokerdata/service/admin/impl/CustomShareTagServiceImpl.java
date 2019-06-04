package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.ShareTag;
import com.jokerdata.mapper.app.generator.ShareTagMapper;
import com.jokerdata.service.admin.CustomShareTagService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * [互推标签]  服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-5-28
 */
@Service
public class CustomShareTagServiceImpl extends ServiceImpl<ShareTagMapper, ShareTag> implements CustomShareTagService {
}
