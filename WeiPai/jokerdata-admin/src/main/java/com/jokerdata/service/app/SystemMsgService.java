package com.jokerdata.service.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.SystemMsg;
import com.jokerdata.vo.MyPage;

import java.util.List;
import java.util.Map;

/**
 * <p>
    * 系统信息 服务类
    * </p>
 *
 * @author oldMa
 * @since 2019-04-15
 */
public interface SystemMsgService extends IService<SystemMsg> {


     Page<SystemMsg> getMsgList(Integer userId, Integer curpage);

    IPage<Map<String, Object>> getPageList(IPage<Map<String, Object>> systemMsgIPage, Integer userId);

    IPage<Map<String, Object>> getPageListShop(IPage<Map<String, Object>> systemMsgIPage, Integer userId);

    List<Map<String, Object>> countSum(Integer userId);
}
