package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Ad;
import com.jokerdata.mapper.app.generator.AdCustomMapper;
import com.jokerdata.service.app.AdService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Service
public class AdImpl extends ServiceImpl<AdCustomMapper, Ad> implements AdService {
    @Resource
    AdCustomMapper targetMapper;

    @Override
    public MyPage<Ad> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
