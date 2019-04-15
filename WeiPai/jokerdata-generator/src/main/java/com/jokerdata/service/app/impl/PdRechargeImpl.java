package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.PdRecharge;
import com.jokerdata.mapper.app.generator.PdRechargeCustomMapper;
import com.jokerdata.service.app.PdRechargeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 预存款充值表 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
@Service
public class PdRechargeImpl extends ServiceImpl<PdRechargeCustomMapper, PdRecharge> implements PdRechargeService {
    @Resource
    PdRechargeCustomMapper targetMapper;

    @Override
    public MyPage<PdRecharge> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
