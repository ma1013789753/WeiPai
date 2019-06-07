package com.jokerdata.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jokerdata.entity.admin.generator.CustomerUser;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.mapper.admin.custom.CustomerMapper;
import com.jokerdata.mapper.app.generator.*;
import com.jokerdata.service.admin.CustomerService;
import com.jokerdata.vo.MyPage;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [前台用户] 用户表 服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-5-1
 */
@Service
public class CustomerServiceImpl  extends ServiceImpl<CustomerMapper, CustomerUser> implements CustomerService {

    @Resource
    CustomerMapper customerMapper;

    @Resource
    PdLogMapper pdLogMapper;

    @Resource
    CoinLogMapper coinLogMapper;

    @Resource
    SignMapper signMapper;

    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    GzhTagMapper gzhTagMapper;

    /**
     * 分页获取用户列表
     * @param page
     * @return
     */
    @Override
    public MyPage<CustomerUser> selectUserPage(MyPage page) {
        return customerMapper.selectPageVo(page);
    }

    /**
     * 分页获取分享榜单
     * @param page
     * @return
     */
    @Override
    public MyPage<CustomerUser> selectRankingPage(MyPage page) {
        return customerMapper.selectRankingPageVo(page);
    }

    /**
     * 后台充值
     * @param map
     *
     */
    @Override
    public int rechargeForuser(Map<String,String> map) {
        CustomerUser customerUser = new CustomerUser();
        customerUser.setUserId(Integer.parseInt(map.get("userId")));
        if (map.get("chargetype").equals("0")) {
            customerUser.setUserName(map.get("userName"));
            String availablePredeposit = map.get("availablePredeposit");
            String amount = map.get("amount");
            customerUser.setAvailablePredeposit(new BigDecimal(availablePredeposit).add(new BigDecimal(amount)));
            PdLog pdLog = new PdLog();
            pdLog.setLgAdminName("admin");
            pdLog.setLgMemberId(customerUser.getUserId());
            pdLog.setLgMemberName(customerUser.getUserName());
            pdLog.setLgType("admin_change");
            pdLog.setLgAvAmount(new BigDecimal(map.get("amount")));
            pdLog.setLgDesc(map.get("desc"));
            pdLog.setLgAddTime(new Date().getTime()/1000);
            pdLogMapper.insert(pdLog);
        }else{
            customerUser.setUserName(map.get("userName"));
            customerUser.setUserCoin(Integer.parseInt(map.get("userCoin")) + Integer.parseInt(map.get("amount")));
            CoinLog coinLog = new CoinLog();
            coinLog.setLogAdminName("admin");
            coinLog.setLogUserId(customerUser.getUserId()+"");
            coinLog.setLogUserName(customerUser.getUserName());
            coinLog.setLogAvCoin(new BigDecimal(map.get("amount")));
            coinLog.setLogType("admin_change");
            coinLog.setLogMark(map.get("desc"));
            coinLog.setAddTime(new Date().getTime()/1000);
            coinLogMapper.insert(coinLog);
        }

        return customerMapper.updateById(customerUser);
    }

    @Override
    public int freezeUser(String uid) {
        CustomerUser customerUser = new CustomerUser();
        customerUser.setUserId(Integer.parseInt(uid));
        customerUser.setUserState(true);
        return customerMapper.updateById(customerUser);
    }

    @Override
    public int unfreezeUser(String uid) {
        CustomerUser customerUser = new CustomerUser();
        customerUser.setUserId(Integer.parseInt(uid));
        customerUser.setUserState(false);
        return customerMapper.updateById(customerUser);
    }

    @Override
    public int deleteUser(String uid) {
        CustomerUser customerUser = new CustomerUser();
        customerUser.setUserId(Integer.parseInt(uid));
        customerUser.setDeleteFlg("1");
        return customerMapper.updateById(customerUser);
    }

    @Override
    public int ban(Map<String, String> map) {
        UserAccount userAccount = new UserAccount();
        userAccount.setAccountId(Integer.parseInt(map.get("userId")));
        long limit = Integer.parseInt(map.get("amount"))*86400;
        userAccount.setAccountLimit((new Date().getTime()/1000 + limit)+"");
        userAccount.setDisableReason(map.get("desc"));
        userAccount.setAccountState(-1);
        return userAccountMapper.updateById(userAccount);
    }

    @Override
    public int unbanned(int cid) {
        UserAccount userAccount = new UserAccount();
        userAccount.setAccountId(cid);
        userAccount.setAccountLimit("");
        userAccount.setDisableReason("");
        userAccount.setAccountState(1);
        return userAccountMapper.updateById(userAccount);
    }

    @Override
    public int delAccountById(int cid) {
        UserAccount userAccount = new UserAccount();
        userAccount.setAccountId(cid);
        userAccount.setAccountState(3);
        return userAccountMapper.updateById(userAccount);
    }

    @Override
    public int rejectAccount(UserAccount userAccount) {
        UserAccount ua = new UserAccount();
        ua.setAccountId(userAccount.getAccountId());
        ua.setAccountState(2);
        ua.setNopassReason(userAccount.getNopassReason());
        return userAccountMapper.updateById(ua);
    }

    @Override
    public int passAccount(int cid) {
        UserAccount userAccount = new UserAccount();
        userAccount.setAccountId(cid);
        userAccount.setAccountState(1);
        userAccount.setNopassReason("");
        return userAccountMapper.updateById(userAccount);
    }

    @Override
    public int insertTag(GzhTag gzhTag) {
        return gzhTagMapper.insert(gzhTag);
    }

    @Override
    public int updateTag(GzhTag gzhTag) {
        return gzhTagMapper.updateById(gzhTag);
    }

    @Override
    public int deleteTag(int tid) {
        return gzhTagMapper.deleteById(tid);
    }

    @Override
    public List<GzhTag> getTagList() {
        return gzhTagMapper.selectList(null);
    }

    @Override
    public UserAccount getAccById(int cid) {
        return userAccountMapper.selectById(cid);
    }

    @Override
    public MyPage<Sign> selectSignPage(MyPage page,int userId) { return signMapper.selectPageVo(page,userId); }

    @Override
    public List<UserAccount> getUserAccount(int userId, int accType) {
        return userAccountMapper.getUserAccount(userId,accType);
    }

    @Override
    public MyPage<UserAccount> getAccountPage(MyPage page, int accType, int accState) {
        return userAccountMapper.selectAccountsPageVo(page,accType,accState);
    }

    @Override
    public List<Integer> getRegisterReport() {
        return customerMapper.selectRegisterReport();
    }

}
