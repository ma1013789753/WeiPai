package com.jokerdata.service.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Transmit;
import com.jokerdata.mapper.app.generator.TransmitMapper;
import com.jokerdata.service.app.TransmitService;
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
public class TransmitImpl extends ServiceImpl<TransmitMapper, Transmit> implements TransmitService {
    @Resource
    TransmitMapper targetMapper;

}
