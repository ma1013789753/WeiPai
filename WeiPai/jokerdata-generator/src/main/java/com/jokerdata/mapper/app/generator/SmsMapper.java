package com.jokerdata.mapper.app.generator;

import com.jokerdata.entity.app.generator.Sms;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
    * 验证码表 Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
public interface SmsMapper extends BaseMapper<Sms> {

    MyPage<Sms> selectPage(@Param("param") MyPage page);
}
