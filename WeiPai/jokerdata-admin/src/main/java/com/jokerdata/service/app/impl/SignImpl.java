package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Sign;
import com.jokerdata.mapper.app.generator.SignMapper;
import com.jokerdata.service.app.SignService;
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
public class SignImpl extends ServiceImpl<SignMapper, Sign> implements SignService {
    @Resource
    SignMapper targetMapper;

    @Override
    public MyPage<Sign> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
