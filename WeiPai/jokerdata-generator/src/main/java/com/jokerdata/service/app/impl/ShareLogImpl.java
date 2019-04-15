package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.ShareLog;
import com.jokerdata.mapper.app.generator.ShareLogCustomMapper;
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
 * @since 2019-04-15
 */
@Service
public class ShareLogImpl extends ServiceImpl<ShareLogCustomMapper, ShareLog> implements ShareLogService {
    @Resource
    ShareLogCustomMapper targetMapper;

    @Override
    public MyPage<ShareLog> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
