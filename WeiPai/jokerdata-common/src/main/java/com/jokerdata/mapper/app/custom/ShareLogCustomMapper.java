package com.jokerdata.mapper.app.custom;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jokerdata.entity.app.generator.Share;
import com.jokerdata.entity.app.generator.ShareLog;
import com.jokerdata.parames.vo.ShareIndexVo;
import com.jokerdata.parames.vo.UserListVo;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
    *  Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-20
 */
public interface ShareLogCustomMapper extends BaseMapper<ShareLog> {

    MyPage<ShareLog> selectPage(@Param("param") MyPage page);

    List<ShareIndexVo> getshareInfo();

    List<ShareIndexVo> getshareInfoLimit();

    List<UserListVo> selectUserListVo(@Param("i")int i);

    List<Map<String, Object>> getshareInfoUser(Integer shareId);

    IPage<Map<String, Object>> shareUserList(IPage<ShareLog> iPage, @Param("share_id")String share_id);
}
