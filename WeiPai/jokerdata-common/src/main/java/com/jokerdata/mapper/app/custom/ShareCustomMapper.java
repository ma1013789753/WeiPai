package com.jokerdata.mapper.app.custom;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.parames.ShareIndexParams;
import com.jokerdata.parames.vo.MonetListVo;
import com.jokerdata.vo.CShareLog;
import com.jokerdata.vo.MyPage;
import com.jokerdata.vo.PShareLog;
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

    IPage<MonetListVo> moneyList(IPage<MonetListVo> sharePage);
    IPage<MonetListVo> userShareList(IPage<MonetListVo> sharePage,@Param("userId")int userId);
    IPage<MonetListVo> moneyMore(IPage<MonetListVo> sharePage);

    IPage<MonetListVo> tuiJianList(IPage<MonetListVo> recPage);

    IPage<MonetListVo> shareList(IPage<MonetListVo> sharePage, @Param("shareIndexParams") ShareIndexParams shareIndexParams);

    IPage<MonetListVo> shareMoneyList(IPage<MonetListVo> sharePage, @Param("shareIndexParams")ShareIndexParams shareIndexParams);

    IPage<PShareLog> getSharePPage(@Param("page") MyPage page);

    IPage<CShareLog> getShareCPage(@Param("page") MyPage page);
}