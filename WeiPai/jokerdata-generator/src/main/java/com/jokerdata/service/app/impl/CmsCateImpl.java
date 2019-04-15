package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.CmsCate;
import com.jokerdata.mapper.app.generator.CmsCateCustomMapper;
import com.jokerdata.service.app.CmsCateService;
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
 * @since 2019-04-15
 */
@Service
public class CmsCateImpl extends ServiceImpl<CmsCateCustomMapper, CmsCate> implements CmsCateService {
    @Resource
    CmsCateCustomMapper targetMapper;

    @Override
    public MyPage<CmsCate> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
