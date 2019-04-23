package com.jokerdata.mapper.app.custom;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.entity.app.generator.Sms;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.parames.vo.UserListVo;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
    * 验证码表 Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface ShareCustomMapper extends BaseMapper<Share> {

    MyPage<Share> selectPage(@Param("param") MyPage page);

    IPage<MonetListVo> moneyList(IPage<MonetListVo> sharePage);

    IPage<MonetListVo> moneyMore(IPage<MonetListVo> sharePage);

    IPage<MonetListVo> tuiJianList(IPage<MonetListVo> recPage);
}