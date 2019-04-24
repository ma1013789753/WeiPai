package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.ShareLog;
import com.jokerdata.mapper.app.generator.ShareLogMapper;
import com.jokerdata.service.app.ShareLogService;
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
public class ShareLogImpl extends ServiceImpl<ShareLogMapper, ShareLog> implements ShareLogService {
    @Resource
    ShareLogMapper targetMapper;

    @Override
    public MyPage<ShareLog> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
