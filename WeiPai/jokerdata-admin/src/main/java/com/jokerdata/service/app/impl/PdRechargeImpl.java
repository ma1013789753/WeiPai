package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.PdRecharge;
import com.jokerdata.mapper.app.generator.PdRechargeMapper;
import com.jokerdata.service.app.PdRechargeService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
    * 预存款充值表 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class PdRechargeImpl extends ServiceImpl<PdRechargeMapper, PdRecharge> implements PdRechargeService {
    @Resource
    PdRechargeMapper targetMapper;

    @Override
    public MyPage<PdRecharge> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
