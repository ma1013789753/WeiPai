package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.ArticleCate;
import com.jokerdata.mapper.app.generator.ArticleCateMapper;
import com.jokerdata.service.app.ArticleCateService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class ArticleCateImpl extends ServiceImpl<ArticleCateMapper, ArticleCate> implements ArticleCateService {
    @Resource
    ArticleCateMapper targetMapper;

}
