package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.ShareLog;
import com.jokerdata.parames.vo.ShareIndexVo;
import com.jokerdata.vo.MyPage;

import java.util.List;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface ShareLogService extends IService<ShareLog> {

     MyPage<ShareLog> selectPage(MyPage page);

    List<ShareIndexVo> getshareInfo();

    List<ShareIndexVo> getshareInfoLimit();
}
