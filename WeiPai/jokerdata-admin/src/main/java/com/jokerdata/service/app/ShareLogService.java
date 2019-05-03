package com.jokerdata.service.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.entity.app.generator.ShareLog;
import com.jokerdata.parames.vo.ShareIndexVo;
import com.jokerdata.parames.vo.SpreadBeanVo;
import com.jokerdata.vo.MyPage;

import java.util.List;
import java.util.Map;

/**
 * <p>
    *  服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface ShareLogService extends IService<ShareLog> {


    List<ShareIndexVo> getshareInfo();

    List<ShareIndexVo> getshareInfoLimit();

    List<Map<String, Object>> getshareInfoUser(Integer shareId);

    IPage<Map<String, Object>> shareUserList(IPage<ShareLog> iPage, String share_id);

    IPage<SpreadBeanVo> getSPreadList(IPage<SpreadBeanVo> shareListPage, Integer userId);
}
