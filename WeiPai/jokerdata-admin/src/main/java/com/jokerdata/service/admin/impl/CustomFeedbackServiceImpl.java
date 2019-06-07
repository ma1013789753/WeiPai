package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.app.generator.Feedback;
import com.jokerdata.mapper.app.generator.FeedbackMapper;
import com.jokerdata.service.admin.CustomFeedbackService;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 意见反馈 服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-06-05
 */
@Service
public class CustomFeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements CustomFeedbackService {

}
