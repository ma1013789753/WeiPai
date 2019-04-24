package com.jokerdata.service.app.impl;

import com.jokerdata.entity.app.generator.Feedback;
import com.jokerdata.mapper.app.generator.FeedbackMapper;
import com.jokerdata.service.app.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 意见反馈 服务实现类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
@Service
public class FeedbackImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {
    @Resource
    FeedbackMapper targetMapper;

    @Override
    public MyPage<Feedback> selectPage(MyPage page) {
        return targetMapper.selectPage(page);
    }
}
