package com.jokerdata.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.app.generator.UserAccount;
import com.jokerdata.parames.vo.UserAccept;
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
public interface UserAccountService extends IService<UserAccount> {

    List<UserAccept> getUserAccept(String user_id, String share_id);
}
