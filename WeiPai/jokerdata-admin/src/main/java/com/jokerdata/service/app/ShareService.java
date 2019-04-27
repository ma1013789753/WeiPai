package com.jokerdata.service.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.parames.ShareIndexParams;
import com.jokerdata.parames.vo.MonetListVo;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface ShareService extends IService<Share> {


    IPage<MonetListVo> moneyList(IPage<MonetListVo> sharePage, boolean b);

    IPage<MonetListVo> tuiJianList(IPage<MonetListVo> recPage, boolean b);

    IPage<MonetListVo> moneyMore(IPage<MonetListVo> sharePage, boolean b);

    IPage<MonetListVo> shareList(IPage<MonetListVo> sharePage, ShareIndexParams shareIndexParams);

    IPage<MonetListVo> shareMoneyList(IPage<MonetListVo> sharePage, ShareIndexParams shareIndexParams);
}
