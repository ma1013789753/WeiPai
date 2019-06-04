package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.PdCash;
import com.jokerdata.mapper.app.generator.PdCashMapper;
import com.jokerdata.service.admin.CustomPdCashService;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 预存款提现记录表 服务实现类
 * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
@Service
public class CustomPdCashServiceImpl extends ServiceImpl<PdCashMapper, PdCash> implements CustomPdCashService {

}
