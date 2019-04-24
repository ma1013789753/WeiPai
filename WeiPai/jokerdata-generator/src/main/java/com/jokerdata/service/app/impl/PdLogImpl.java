package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.PdLog;
import com.jokerdata.mapper.app.generator.PdLogMapper;
import com.jokerdata.service.app.PdLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 预存款变更日志表 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
@Service
public class PdLogImpl extends ServiceImpl<PdLogMapper, PdLog> implements PdLogService {
    @Resource
    PdLogMapper targetMapper;

    @Override
    public MyPage<PdLog> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
