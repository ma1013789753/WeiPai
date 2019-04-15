package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.Feedback;
import com.jokerdata.vo.MyPage;

/**
 * <p>
    * 意见反馈 服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface FeedbackService extends IService<Feedback> {

     MyPage<Feedback> selectPage(MyPage page);
}
