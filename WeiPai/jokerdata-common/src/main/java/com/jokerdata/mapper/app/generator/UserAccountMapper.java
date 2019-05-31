package com.jokerdata.mapper.app.generator;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jokerdata.entity.admin.generator.CustomerUser;
import com.jokerdata.entity.app.generator.UserAccount;
import com.jokerdata.parames.vo.UserAccept;
import com.jokerdata.vo.MyPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
    *  Mapper 接口
    * </p>
 *
 * @author oldMa
 * @since 2019-04-24
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {

    List<UserAccept> getUserAccept(@Param("user_id") String user_id,@Param("share_id") String share_id);

    List<UserAccount> getUserAccount(@Param("userId") int userId,@Param("accType") int accType);

    MyPage<UserAccount> selectAccountsPageVo(@Param("page") MyPage<UserAccount> myPage,@Param("accType") int accType,@Param("accountState") int accountState);
}
