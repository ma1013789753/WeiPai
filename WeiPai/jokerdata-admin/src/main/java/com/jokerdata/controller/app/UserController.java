package com.jokerdata.controller.app;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.common.ShareUtil;
import com.jokerdata.common.MD5;
import com.jokerdata.common.annotation.Auth;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.common.utils.CommonUtil;
import com.jokerdata.common.utils.RequestHolder;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.parames.*;
import com.jokerdata.parames.vo.PageResule;
import com.jokerdata.service.app.*;
import com.jokerdata.service.common.WeiBoService;
import com.jokerdata.vo.ApiResult;
import com.jokerdata.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/User")
@Transactional(rollbackFor = ApiException.class)
public class UserController {

    @Autowired
    UserTokenService userTokenService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    GzhTagService gzhTagService;

    @Autowired
    SignService signService;

    @Autowired
    CoinService coinService;

    @Autowired
    SignCoinService signCoinService;

    @Autowired
    CoinLogService coinLogService;

    @Autowired
    ShareTagService shareTagService;

    @Autowired
    UserService userService;

    @Autowired
    SystemMsgService systemMsgService;


    @Autowired
    ArticleService articleService;

    @Autowired
    PdLogService pdLogService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    SmsService smsService;

    @Autowired
    WeiBoService weiBoService;

    @Autowired
    ConfigService configService;

    @Autowired
    ShareService shareService;

    @GetMapping(value = "/weibo_list", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult weibo_list(String key) {
        User user = RequestHolder.getUser();
        List<Map<String, Object>> data = userAccountService.listMaps(new QueryWrapper<UserAccount>()
                .eq("user_id", user.getUserId())
        );
        List<Map<String, Object>> dataA = new ArrayList<>();
        List<Map<String, Object>> dataB = new ArrayList<>();
        List<Map<String, Object>> dataC = new ArrayList<>();
        data.forEach(userAccount -> {
            if (ShareUtil.isExit(userAccount.get("account_limit"))) {
                userAccount.put("time_text", ShareUtil.getTaxt(userAccount.get("account_limit").toString()));
            }
            if (ShareUtil.isExit(userAccount.get("num_screen"))) {
                userAccount.put("num_screen", ShareUtil.URL + userAccount.get("num_screen").toString());
            }
            if (ShareUtil.isExit(userAccount.get("wechat_pic"))) {
                userAccount.put("wechat_pic", ShareUtil.URL + userAccount.get("wechat_pic").toString());
            }
            if ("1".equals(userAccount.get("acc_type"))) {
                userAccount.put("account_avatar", ShareUtil.URL + userAccount.get("account_avatar").toString());
                userAccount.put("avatar_hd", ShareUtil.URL + userAccount.get("avatar_hd").toString());
            } else if ("2".equals(userAccount.get("acc_type"))) {
                userAccount.put("account_name", ShareUtil.Base64Decode(userAccount.get("account_name").toString()));

            }

            data.forEach(stringObjectMap -> {
                String accType = userAccount.get("acc_type").toString();
                switch (accType) {
                    case "0":
                        dataA.add(stringObjectMap);
                        break;
                    case "1":
                        dataB.add(stringObjectMap);
                        break;
                    case "2":
                        dataC.add(stringObjectMap);
                        break;

                }
            });
            if (dataB.size() > 0) {

                List<Map<String, Object>> tag = gzhTagService.listMaps(new QueryWrapper<GzhTag>()
                        .select("tag_id", "tag_name").orderByDesc("tag_sort")
                );
                dataB.forEach(stringObjectMap -> {
                    stringObjectMap.put("tag_name", tag.get(Integer.parseInt(stringObjectMap.get("gzh_tag_id").toString())) != null ? tag.get(Integer.parseInt(stringObjectMap.get("gzh_tag_id").toString())) : "");
                });

            }

        });
        Map<String, Object> result = new HashMap<>();
        result.put("weibo_list", dataA);
        result.put("public_list", dataB);
        result.put("wechat_list", dataC);


        return ApiResult.success(result);
    }

    @GetMapping(value = "/band_weibo", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult band_weibo(String key) {

        return ApiResult.error("功能未实现");
    }

    @GetMapping(value = "/weibo_news_list", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult weibo_news_list(String key) {

        return ApiResult.error("功能未实现");
    }

    @GetMapping(value = "/add_weibo_t", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult add_weibo_t(String key, @Validated ShareWBParam param) {
        User user = RequestHolder.getUser();
        ShareTag shareTag = shareTagService.getById(param.getTag_id());
        UserAccount account = userAccountService.getOne(new QueryWrapper<UserAccount>().eq("access_token",param.getWtoken()));
        if(param.getShare_num()*param.getShare_coin()>=user.getUserCoin()){
            return ApiResult.error("积分数量不够");
        }
        Result result =  weiBoService.getWeiBoByUrl("");//获取微博内容
        Config config = configService.getById(4);
        Share share = new Share();
        share.setUserId(user.getUserId());
        share.setUserName(user.getUserName());
        share.setAccountId(account.getAccountId());
        share.setShareCoin(param.getShare_coin());
        share.setShareNum(param.getShare_num());
        share.setHaveSharedNum(0);
        share.setTotalCoin(param.getShare_coin()*param.getShare_num());
        share.setIsOriginal(param.getIs_original());
        share.setAddTime(new Date().getTime()/1000+"");
        share.setTagId(shareTag.getTagId());
        share.setTagName(shareTag.getTagName());
        share.setShareState("1");//审核中
        share.setShareExtraCoin(Integer.parseInt(config.getConfigContent()));
        share.setShareStatus("0");//积分
        //微博内容
        share.setShareUrl(param.getShare_url());
        share.setShareContent("");
        share.setShareImage("");
        share.setShareType("1");
        share.setShareVideo("");
        share.setWbId("");
        share.setBackgroundImage("");
        share.setShortUrl("");
        share.setShareImg("");


        if(shareService.save(share)){
            return ApiResult.success();
        };
        return ApiResult.error("失败");
    }

    @GetMapping(value = "/user_info", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult user_info(String key) {
        User user = RequestHolder.getUser();
        Map<String, Object> userInfo = ShareUtil.beanToMap(user);
        userInfo.put("user_avatar", ShareUtil.getAvatar(user.getUserId() + ""));

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Map<String, Object> sign = signService.getMap(
                new QueryWrapper<Sign>().eq("user_id", user.getUserId())
                        .eq("sign_date", date)
                        .select("sign_id"));

        Map<String, Object> coinNum = coinLogService.getMap(
                new QueryWrapper<CoinLog>().in("log_type", "task_income", "sign")
                        .between("add_time", new Date().getTime() / 1000 - 24 * 3600, new Date().getTime() / 1000)
                        .select("sum(log_av_coin)")
        );
        double num = 0;
        if (coinNum.get("sum(log_av_coin)") != null) {
            num = ((BigDecimal) coinNum.get("sum(log_av_coin)")).doubleValue();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("user_info", userInfo);
        data.put("is_sign", sign.get("sign_id") != null ? 1 : 0);
        data.put("yesterday_coin", num);
        return ApiResult.success(data);
    }

    @GetMapping(value = "/coin_list", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public PageResule coin_list(String key, @RequestParam(required = false) int curpage) {
        User user = RequestHolder.getUser();
        if (curpage == 0) {
            curpage = 1;
        }

        IPage<CoinLog> coinIPage = new Page<>();
        coinIPage.setCurrent(curpage);
        coinIPage.setSize(10);
        coinIPage = coinLogService.page(coinIPage, new QueryWrapper<CoinLog>()
                .eq("log_user_id", user.getUserId()).orderByDesc("log_id")
                .select("log_id", "log_user_id", "log_type", "log_av_coin", "add_time")
        );
        List<Map<String, Object>> data = ShareUtil.toLowBeanList(coinIPage.getRecords());
        data.forEach(stringObjectMap -> {
            stringObjectMap.put("add_time_text", ShareUtil.getTaxt(stringObjectMap.get("add_time").toString()));
        });

        Map<String, Object> result = new HashMap<>();
        result.put("list", data);
        result.put("user_coin", user.getCoinTotal());
        return PageResule.success(result).setPage((Page) coinIPage);

    }


    @GetMapping(value = "/user_save", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult user_save(String key, UserSaveParams userSaveParams) {
        User user = RequestHolder.getUser();
        if (userSaveParams == null) {
            return ApiResult.error("无参数");
        }
        User update = new User();
        update.setUserId(user.getUserId());
        if (!StringUtils.isEmpty(userSaveParams.getUser_name())) {
            update.setUserName(userSaveParams.getUser_name());
        }
        if (!StringUtils.isEmpty(userSaveParams.getUser_gender())) {
            update.setUserGender(Integer.parseInt(userSaveParams.getUser_gender()));
        }
        if (!StringUtils.isEmpty(userSaveParams.getUser_qq())) {
            update.setUserQq(userSaveParams.getUser_qq());

        }
        if (!StringUtils.isEmpty(userSaveParams.getUser_email())) {
            update.setUserEmail(userSaveParams.getUser_email());
        }
        if (!StringUtils.isEmpty(userSaveParams.getUser_wx())) {
            update.setUserWx(userSaveParams.getUser_wx());
        }
        if (!StringUtils.isEmpty(userSaveParams.getAlipay_name())) {
            update.setAlipayName(userSaveParams.getAlipay_name());
        }
        boolean flag = userService.updateById(update);

        if (flag) {
            return ApiResult.success(1);
        }
        return ApiResult.error("false");

    }

    @GetMapping(value = "/push_notice", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public PageResule push_notice(String key, @RequestParam(required = false) int curpage) {
        User user = RequestHolder.getUser();
        if (curpage == 0) {
            curpage = 1;
        }

        IPage<Map<String, Object>> systemMsgIPage = new Page<>();
        systemMsgIPage.setCurrent(curpage);
        systemMsgIPage.setSize(10);
//        systemMsgIPage = systemMsgService.page(systemMsgIPage,new QueryWrapper<SystemMsg>()
//                .eq("user_id",user.getUserId())
//                .in("notice_type","push","succ_master","succ_user","push_false")
//        );
        systemMsgIPage = systemMsgService.getPageList(systemMsgIPage, user.getUserId());
        systemMsgIPage.getRecords().forEach(stringObjectMap -> {
            stringObjectMap.put("add_time_text", ShareUtil.getTaxt(stringObjectMap.get("add_time").toString()));
            if (stringObjectMap.get("is_base64") != null && "1".equals(stringObjectMap.get("is_base64").toString())) {
                stringObjectMap.put("notice_content", ShareUtil.Base64Decode(stringObjectMap.get("notice_content").toString()));
            }
        });

        Map<String, Object> result = new HashMap<>();
        result.put("list", systemMsgIPage.getRecords());
        return PageResule.success(result).setPage((Page) systemMsgIPage);

    }

    @GetMapping(value = "/shop_notice", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public PageResule shop_notice(String key, @RequestParam(required = false) int curpage) {
        User user = RequestHolder.getUser();
        if (curpage == 0) {
            curpage = 1;
        }

        IPage<Map<String, Object>> systemMsgIPage = new Page<>();
        systemMsgIPage.setCurrent(curpage);
        systemMsgIPage.setSize(10);
        systemMsgIPage = systemMsgService.getPageListShop(systemMsgIPage, user.getUserId());
        systemMsgIPage.getRecords().forEach(stringObjectMap -> {
            stringObjectMap.put("add_time_text", ShareUtil.getTaxt(stringObjectMap.get("add_time").toString()));
            if (stringObjectMap.get("is_base64") != null && "1".equals(stringObjectMap.get("is_base64").toString())) {
                stringObjectMap.put("notice_content", ShareUtil.Base64Decode(stringObjectMap.get("notice_content").toString()));
            }
        });

        Map<String, Object> result = new HashMap<>();
        result.put("list", systemMsgIPage.getRecords());
        return PageResule.success(result).setPage((Page) systemMsgIPage);

    }

    @GetMapping(value = "/user_sign", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult user_sign(String key) {
        User user = RequestHolder.getUser();
        int sign = user.getSignLimit();
        int coinTotal = user.getCoinTotal();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        if ("24".equals(simpleDateFormat.format(new Date())) || "00".equals(simpleDateFormat.format(new Date()))) {
            return ApiResult.error("请凌晨后重试");
        }

        List<SignCoin> SignCoins = signCoinService.list(new QueryWrapper<SignCoin>().select("limit_num", "sign_coin"));

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime() - 24 * 3600 * 1000);
        List<Sign> msg = signService.list(new QueryWrapper<Sign>().eq("user_id", user.getUserId())
                .in("sign_date", today, yesterday).select("sign_date"));

        Sign flag = new Sign();
        flag.setSignDate(today);

        if (msg.contains(flag)) {
            return ApiResult.error("今日已签到");
        }
        flag.setSignDate(yesterday);
        if (msg.contains(flag)) {
            sign++;
        } else {
            sign = 1;
        }

        int item = sign % SignCoins.size();
        if (item == 0) {
            item = SignCoins.size();
        }
        SignCoin coin = SignCoins.get(item);
        user.setSignLimit(sign);
        user.setUserCoin(user.getUserCoin() + coin.getSignCoin());
        user.setCoinTotal(user.getCoinTotal() + coin.getSignCoin());
        if (!userService.updateById(user)) {
            throw new ApiException("");
        }
        Sign sign1 = new Sign();
        sign1.setUserId(user.getUserId());
        sign1.setSignCoin(new BigDecimal(coin.getSignCoin()));
        sign1.setAddTime(new Date().getTime() / 1000 + "");
        sign1.setSignState("0");
        sign1.setSignDate(today);
        if (!signService.save(sign1)) {
            throw new ApiException("");
        }

        CoinLog coinLog = new CoinLog();
        coinLog.setLogUserId(user.getUserId() + "");
        coinLog.setLogUserName(user.getUserName());
        coinLog.setLogType("sign");
        coinLog.setLogAvCoin(new BigDecimal(coin.getSignCoin()));
        coinLog.setLogFreezeCoin(new BigDecimal(0));
        coinLog.setAddTime(new Date().getTime() / 1000);
        if (!coinLogService.save(coinLog)) {
            throw new ApiException("");
        }

        return ApiResult.success(1);

    }

    @GetMapping(value = "/sign_index", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult sign_index(String key) {

        User user = RequestHolder.getUser();
        int signLimit = user.getSignLimit();
        List<SignCoin> signCoins = signCoinService.list(new QueryWrapper<>());
        Article article = articleService.getArticleByCode("sign_rule");
        int limit = signLimit % signCoins.size();

        Map<String, Object> map = new HashMap<>();
        map.put("sign_limit", signLimit);
        map.put("limit", limit);
        map.put("sign_coin", ShareUtil.toLowBeanList(signCoins));
        map.put("sign_rule", ShareUtil.toLowBean(article));
        return ApiResult.success(map);
    }

    @GetMapping(value = "/money_list", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public PageResule money_list(String key, @RequestParam(required = false) int curpage) {
        User user = RequestHolder.getUser();
        if (curpage == 0) {
            curpage = 1;
        }
        IPage<PdLog> logList = new Page<>();
        logList.setCurrent(curpage);
        logList.setSize(10);
        logList = pdLogService.page(logList, new QueryWrapper<PdLog>()
                .eq("lg_member_id", user.getUserId())
                .orderByDesc("lg_id")
                .select("lg_id", "lg_member_id", "lg_type", "lg_av_amount", "lg_add_time", "lg_from_data"));
        List<Map<String, Object>> datas = ShareUtil.toLowBeanList(logList.getRecords());
        datas.forEach(stringObjectMap -> {
            stringObjectMap.put("lg_add_time_text", ShareUtil.getTaxt(stringObjectMap.get("lg_add_time").toString()));
        });

        Map<String, Object> data = new HashMap<>();
        data.put("list", datas);
        data.put("available_predeposit", user.getAvailablePredeposit());
        return PageResule.success(data).setPage((Page) logList);
    }

    @GetMapping(value = "/password_edit", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult password_edit(String key, @Validated ApiResetPsw apiResetPsw) {
        User user = RequestHolder.getUser();
        if(!apiResetPsw.getNewpassword().equals(apiResetPsw.getRenewpassword())){
            return  ApiResult.error("两次密码不一致");
        }
        if(!MD5.MD5Encode(apiResetPsw.getUser_password(),"utf-8").equals(user.getUserPassword())){
            return ApiResult.error("原密码错误");
        }
        user.setUserPassword(MD5.MD5Encode(apiResetPsw.getUser_password(),"utf-8"));
        if(userService.updateById(user)){
            return ApiResult.success(1);
        }
        return ApiResult.error("修改失败");
    }

    @GetMapping(value = "/feedback_add", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult feedback_add(String key, String feedback_content,String email) {

        User user = RequestHolder.getUser();

        if(StringUtils.isEmpty(feedback_content)){
            return ApiResult.error("请输入信息");
        }

        if((feedback_content).length()>150){
            return ApiResult.error("文字过长");
        }

        if(!CommonUtil.isEmail(email)){
            return ApiResult.error("邮箱不合法");
        }
        Feedback feedback = new Feedback();
        feedback.setUserId(user.getUserId());
        feedback.setAddTime(new Date().getTime()/1000);
        feedback.setFeedbackContent(feedback_content);
        feedback.setUserName(user.getUserName());
        feedback.setIsRead(0);
        feedback.setUserMail(email);
        if(feedbackService.save(feedback)){
            return ApiResult.success(1);
        }
        return ApiResult.error("提交失败");
    }

    @GetMapping(value = "/save_pay", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult save_pay(String key, @Validated PayPassword password) {
        User user = RequestHolder.getUser();

        String flag = smsService.valiteSms(user.getUserMobile(),password.getCode(),"2");
        if(!"1".equals(flag)){
            return ApiResult.error(flag);
        }
        user.setWechatOpenid(password.getWechat_openid());
        user.setAlipayAccount(password.getAlipay_account());
        if(userService.updateById(user)){
            return ApiResult.success(1);
        }
        return ApiResult.error("更新失败");

    }

    @GetMapping(value = "/save_pay2", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult save_pay2(String key, @Validated PayPassword2 password) {
        User user = RequestHolder.getUser();
        if(!password.getR_user_pay_pwd().equals(password.getUser_pay_pwd())){
            return ApiResult.error("确认密码不一致");
        }
        if(!MD5.MD5Encode(password.getCode(),"utf-8").equals(user.getUserPayPwd())){
            return ApiResult.error("旧支付密码错误");
        }
        user.setUserPayPwd(MD5.MD5Encode(password.getUser_pay_pwd(),"utf-8"));
        if(userService.updateById(user)){
            return ApiResult.success(1);
        }
        return ApiResult.error("更新失败");

    }

    @GetMapping(value = "/count_num", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult count_num(String key) {
        User user = RequestHolder.getUser();

//        List<Map<String,Object>> data = systemMsgService.listMaps(new QueryWrapper<SystemMsg>()
//                .in("notice_type","shop","push","succ_master","succ_user","push_false")
//                .eq("is_read",0)
//                .eq("user_id",user.getUserId())
//                .groupBy("notice_type")
//                .select("notice_type","count(a.notice_id) as num")
//        );

        List<Map<String,Object>> data = systemMsgService.countSum(user.getUserId());

        Long push = 0l;
        Long shop = 0l;
        for (int i = 0; i < data.size(); i++) {
            Map<String,Object> map = data.get(i);
            if("shop".equals(map.get("notice_type").toString())){
                shop += (Long)map.get("num");
            }else{
                push +=  (Long)map.get("num");
            }
        }
        Map<String,Long> map = new HashMap<>();
        map.put("shop",shop);
        map.put("push",push);

        return ApiResult.success(map);
    }


    /**
     * 积分提现
     * @param count 数量
     * @return
     */
    @GetMapping(value = "/with_draw", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult with_draw(String key,String count) {
        User user = RequestHolder.getUser();
        if(StringUtils.isEmpty(count)){
            return ApiResult.error("参数错误");
        }
        int num = Integer.parseInt(count);
        if (user.getUserCoin() < num){
            return ApiResult.error("账户余额不足");
        }
        //更新用户金额
        user.setUserCoin(user.getUserCoin()-num);
        user.setAvailablePredeposit(new BigDecimal(user.getAvailablePredeposit().doubleValue()+num/1000));

        CoinLog coinLog = new CoinLog();
        coinLog.setAddTime(new Date().getTime()/1000);
        coinLog.setLogUserId(user.getUserId()+"");
        coinLog.setLogUserName(user.getUserName());
        coinLog.setLogType("with_draw");
        coinLog.setLogAvCoin(new BigDecimal(num));
        if(!coinLogService.save(coinLog)){
            throw new ApiException("");
        }
        PdLog pdLog = new PdLog();
        pdLog.setLgMemberId(user.getUserId());
        pdLog.setLgMemberName(user.getUserName());
        pdLog.setLgType("with_draw");
        pdLog.setLgAvAmount(new BigDecimal(num/1000));
        pdLog.setLgAddTime(new Date().getTime()/1000);
        if(!pdLogService.save(pdLog)){
            throw new ApiException("");
        }

        if(!userService.saveOrUpdate(user)){
            throw new ApiException("");
        }
        return ApiResult.success("1");
    }

    //详情
    @GetMapping(value = "/add_befor", produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public ApiResult add_befor(String key,String url,String is_wb,String token) {
        User user = RequestHolder.getUser();

        if(StringUtils.isEmpty(url)||StringUtils.isEmpty(token)){
            return ApiResult.error("参数错误");
        }
        if("1".equals(is_wb)){
            UserAccount account = userAccountService.getOne(new QueryWrapper<UserAccount>().eq("access_token",token));
            Map<String,Object> map = new HashMap<>();
            map.put("user",ShareUtil.toLowBean(account));
            //此处需要数据
            map.put("text","这是测试数据");
            return ApiResult.success(map);
        }

        return ApiResult.error("-1");
    }


























}