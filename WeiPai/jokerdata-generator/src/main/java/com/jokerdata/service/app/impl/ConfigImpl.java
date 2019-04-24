package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.Config;
import com.jokerdata.mapper.app.generator.ConfigMapper;
import com.jokerdata.service.app.ConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    *  服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
@Service
public class ConfigImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {
    @Resource
    ConfigMapper targetMapper;

    @Override
    public MyPage<Config> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
