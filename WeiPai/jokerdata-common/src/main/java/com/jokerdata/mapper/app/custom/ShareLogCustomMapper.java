package com.jokerdata.mapper.app.custom;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.app.generator.ShareLog;
import com.jokerdata.parames.vo.ShareIndexVo;
import com.jokerdata.parames.vo.UserListVo;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}
