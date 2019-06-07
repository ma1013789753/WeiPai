package com.jokerdata.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jokerdata.entity.admin.generator.CustomerUser;
import com.jokerdata.entity.app.generator.GzhTag;
import com.jokerdata.entity.app.generator.Sign;
import com.jokerdata.entity.app.generator.User;
import com.jokerdata.entity.app.generator.UserAccount;
import com.jokerdata.vo.MyPage;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * [前台用户] 用户表 服务类
 * </p>
 *
 * @author aozhang
 * @since 2019-5-1
 */
public interface CustomerService extends IService<CustomerUser> {

    MyPage<CustomerUser> selectUserPage(MyPage page);
    MyPage<CustomerUser> selectRankingPage(MyPage page);
    int rechargeForuser(Map<String,String> map);
    int freezeUser(String uid);
    int unfreezeUser(String uid);
    int deleteUser(String uid);
    int ban(Map<String,String> map);
    int unbanned(int cid);
    int delAccountById(int cid);
    int rejectAccount(UserAccount userAccount);
    int passAccount(int cid);
    int insertTag(GzhTag gzhTag);
    int updateTag(GzhTag gzhTag);
    int deleteTag(int tid);
    List<GzhTag> getTagList();
    UserAccount getAccById(int cid);
    MyPage<Sign> selectSignPage(MyPage page,int userId);
    List<UserAccount> getUserAccount(int userId,int accType);
    MyPage<UserAccount> getAccountPage(MyPage page,int accType,int accState);
    List<Integer> getRegisterReport();
}
