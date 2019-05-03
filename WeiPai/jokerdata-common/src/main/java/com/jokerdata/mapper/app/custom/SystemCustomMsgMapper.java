package com.jokerdata.mapper.app.custom;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.entity.app.generator.SystemMsg;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
    * 系统信息 Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
public interface SystemCustomMsgMapper extends BaseMapper<SystemMsg> {

//    MyPage<SystemMsg> selectPage(@Param("param") MyPage page);

    List<SystemMsg> getMsgList(@Param("userId")Integer userId, Page<SystemMsg> page);

    IPage<Map<String, Object>> getPageList(@Param("param")IPage<Map<String, Object>> systemMsgIPage, @Param("userId")Integer userId);

    IPage<Map<String, Object>> getPageListShop(@Param("param")IPage<Map<String, Object>> systemMsgIPage, @Param("userId")Integer userId);

    List<Map<String, Object>> countSum(Integer userId);
}
