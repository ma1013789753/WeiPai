package com.jokerdata.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jokerdata.common.ShareUtil;
import com.jokerdata.common.MD5;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.common.utils.CommonUtil;
import com.jokerdata.common.utils.RundomUtil;
import com.jokerdata.entity.app.generator.*;
import com.jokerdata.parames.LoginParames;
import com.jokerdata.parames.PageParames;
import com.jokerdata.parames.RegParames;
import com.jokerdata.parames.ResetPassword;
import com.jokerdata.parames.vo.PageResule;
import com.jokerdata.parames.vo.SystemMsgvo;
import com.jokerdata.parames.vo.UserInfo;
import com.jokerdata.service.app.*;
import com.jokerdata.service.common.AliSmsService;
import com.jokerdata.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oldMa
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/Public")
@Transactional(rollbackFor = ApiException.class)
public class PublicController {

    @Autowired
    private AliSmsService aliSmsService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShareTagService shareTagService;

    @Autowired
    private GzhTagService gzhTagService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private ArticleCateService articleCateService;
    @Autowired
    private SystemMsgService systemMsgService;


    @Autowired
    private UserTokenService userTokenService;


    @GetMapping(value = "/get_sms",produces = "application/json;charset=UTF-8")
    public ApiResult getId(int type,String mobile){

        if(mobile.length()!=11 || !CommonUtil.isMobileNO(mobile)){
            return new ApiResult().error("手机号码错误");
        }

        if(type != 1 && type != 2 && type != 3  && type != 4){
            return new ApiResult().error("验证类型错误");
        }
        if(type == 4){
            User user = userService.getUserByMobile(mobile);
            if(user == null){
                Map<String,Object> data = new HashMap<>();
                data.put("state",1);
                return ApiResult.success(data);
            }
        }

        String code = RundomUtil.randomCode();
        aliSmsService.sendSms(type,mobile,code);
        return ApiResult.success();
    }


    /**
     * 用户注册
     * @param parames
     * @return
     */
    @ApiOperation(value="用户注册", notes="根据手机号注册")
    @GetMapping(value = "/reg",produces = "application/json;charset=UTF-8")
    public ApiResult reg(@Validated RegParames parames){
        QueryWrapper<Sms> wrapper = new QueryWrapper<>();
        wrapper.eq("sms_mobi",parames.getMobile())
                .eq("sms_content",parames.getSms())
                .eq("sms_type",1);
        Sms sms = smsService.getOne(wrapper);
        if(sms == null){
            return ApiResult.error("验证码不存在");
        }
        if((new Date().getTime()/1000-Long.parseLong(sms.getAddTime()))>5*60){
            return ApiResult.error("验证码已过期");
        }

        User user = userService.getOne(new QueryWrapper<User>().eq("user_mobile",parames.getMobile()));
        if(user!=null){
            return ApiResult.error("手机号码已被注册");
        }
        user = new User();
        user.setUserMobile(parames.getMobile());
        user.setUserPassword(MD5.MD5Encode(parames.getPassword(),"utf-8"));
        user.setUserName("手机用户"+parames.getMobile().substring(7));
        user.setUserState(0);
        user.setAddTime(new Date().getTime()/1000+"");
        if(userService.save(user)){
            return ApiResult.success("注册成功");
        }
        return ApiResult.error("注册失败");

    }

    /**
     * 忘记密码
     * @param parames
     * @return
     */
    @ApiOperation(value="忘记密码", notes="重置密码")
    @GetMapping(value = "/forget",produces = "application/json;charset=UTF-8")
    public ApiResult forget(@Validated ResetPassword parames) {
        if(!parames.getRepassword().equals(parames.getPassword())){
           return ApiResult.error("确认密码不正确");
        }

        String isOk = smsService.valiteSms(parames.getMobile(),parames.getSms(),"2");
        if(!"1".equals(isOk)){
            return ApiResult.error(isOk);
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("user_mobile",parames.getMobile()));
        if(user == null){
            return ApiResult.error("用户不存在");
        }
        user.setUserPassword(ShareUtil.Base64Encode(parames.getPassword()));

        if(userService.updateById(user)){
            return ApiResult.success("成功");
        }
        return ApiResult.error("设置失败");
    }

    @GetMapping(value = "/login_psw",produces = "application/json;charset=UTF-8")
    public ApiResult login(@Validated LoginParames parames) {

        User user =  userService.getOne(new QueryWrapper<User>().eq("user_mobile",parames.getMobile()));
        if(user == null){
            return ApiResult.error("账号不存在");
        }
        if(!MD5.MD5Encode(parames.getPassword(),"utf-8").equals(user.getUserPassword())){
            return ApiResult.error("密码错误");
        }
        if("1".equals(user.getUserState())){
            return ApiResult.error("您的帐号异常,请联系管理员");
        }
        return getUserToken(user);
    }


    @GetMapping(value = "/login_sms",produces = "application/json;charset=UTF-8")
    public ApiResult loginBySms(String mobile,String code){
        if(StringUtils.isEmpty(mobile) || !CommonUtil.isMobileNO(mobile)){
            return ApiResult.error("手机号码不正确");
        }
        if(StringUtils.isEmpty(mobile)){
            return ApiResult.error("验证码不正确");
        }
        String flag = smsService.valiteSms(mobile,code,"3");
        if(!"1".equals(flag)){
            return ApiResult.error(flag);
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("user_mobile",mobile));
        if(user == null){
            return ApiResult.error("账号不存在,请注册");
        }
        if("1".equals(user.getUserState())){
            return ApiResult.error("您的帐号异常,请联系管理员");
        }
        return getUserToken(user);
    }

    @GetMapping(value = "/login_wx",produces = "application/json;charset=UTF-8")
    public ApiResult loginByWx(String openId){
        if(StringUtils.isEmpty(openId)){
            return ApiResult.error("参数异常");
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("wechat_openid",openId));
        if(user == null){
            Map<String,Object> data = new HashMap<>();
            //未绑定手机号
            data.put("state",1);
            return ApiResult.success(data);
        }
        if("1".equals(user.getUserState())){
            return ApiResult.error("您的帐号异常,请联系管理员");
        }
        return getUserToken(user);
    }

    @GetMapping(value = "/bind_wx",produces = "application/json;charset=UTF-8")
    public ApiResult bindByWx(String openId,String mobile,String code){
        if(StringUtils.isEmpty(openId)||StringUtils.isEmpty(mobile)||StringUtils.isEmpty(code)){
            return ApiResult.error("参数异常");
        }
        String valiteSms = smsService.valiteSms(mobile,code,"4");
        if(!"1".equals(valiteSms)){
            return ApiResult.error(valiteSms);
        }
        User user = userService.getUserByMobile(mobile);
        if("1".equals(user.getUserState())){
            return ApiResult.error("您的帐号异常,请联系管理员");
        }
        user.setWechatOpenid(openId);
        if(!userService.saveOrUpdate(user)){
            throw new ApiException("绑定失败");
        }
        return getUserToken(user);
    }
    /**
     * 系统通知
     * @param key
     * @param curpage
     * @return
     */
    @ApiOperation(value="系统通知", notes="用户登录状态下")
    @GetMapping(value = "/sys_notice",produces = "application/json;charset=UTF-8")
    @Auth(value = true)
    public PageResule sys_notice(String key,@RequestParam(required = false) int curpage) {
        User user = RequestHolder.getUser();
        if (curpage == 0) {
            curpage = 1;
        }
        Page<SystemMsg> datas = systemMsgService.getMsgList(user.getUserId(),curpage);

        List<SystemMsgvo> list = new ArrayList<>();
        datas.getRecords().forEach(systemMsg -> {
            SystemMsgvo sys = ShareUtil.beanToBean(systemMsg,SystemMsgvo.class);
            sys.setAdd_time_text(ShareUtil.getTaxt(systemMsg.getAddTime()));
            if(sys.getIsBase64()==1){
                sys.setNoticeContent(ShareUtil.Base64Decode(systemMsg.getNoticeContent()));
            }
            list.add(sys);
        });
        Map<String,List<Map<String,Object>>>  data = new HashMap<>();
        data.put("list", ShareUtil.toLowBeanList(list));

        return PageResule.success(data).setPage(datas);
    }


    /**
     *文章详情
     * @param article_id
     * @param code
     * @return
     */
    @ApiOperation(value="文章详情", notes="文章详情")
    @GetMapping(value = "/article_info",produces = "application/json;charset=UTF-8")
    public ApiResult article_info(@RequestParam(required = false) String article_id,@RequestParam(required = false)String code) {

        if(StringUtils.isEmpty(article_id) && StringUtils.isEmpty(code)){
            return ApiResult.error("参数为空");
        }
        QueryWrapper<Article> queryWrapper = null;
        if(!StringUtils.isEmpty(article_id)){
            queryWrapper = new QueryWrapper<Article>()
                    .eq("article_id",article_id);
        }else{
            queryWrapper = new QueryWrapper<Article>()
                    .eq("article_code",code);
        }
        Article article = articleService.getOne(queryWrapper);
        article.setArticleContent(ShareUtil.htmlEncode(article.getArticleContent()));
        Map<String,Object> map = new HashMap<>();
        map.put("article_info", ShareUtil.toLowBean(article));
        return ApiResult.success(map);

    }


    /**
     * 文章分类详情
     *
     * @return
     */
    @ApiOperation(value="文章分类", notes="文章分类详情")
    @GetMapping(value = "/article_cate",produces = "application/json;charset=UTF-8")
    public ApiResult article_cate() {

        List<ArticleCate> data = articleCateService.list(new QueryWrapper<>());

        Map<String,List<Map<String,Object>>> map = new HashMap<>();
        map.put("article_cate", ShareUtil.toLowBeanList(data));
        return ApiResult.success(map);
    }

    /**
     * 获取配置
     * @param code
     * @return
     */
    @ApiOperation(value="获取配置", notes="获取配置")
    @GetMapping(value = "/config_get",produces = "application/json;charset=UTF-8")
    public ApiResult config_get(String code) {

        Config data = configService.getOne(
                new QueryWrapper<Config>().eq("config_code",code)
        );
        Map<String,Object> map = new HashMap<>();
        map.put("config_msg", ShareUtil.toLowBean(data));
        return ApiResult.success(map);
    }

    /**
     * 转发微信部分
     * @return
     */
    @ApiOperation(value="转发微信部分", notes="转发微信部分")
    @GetMapping(value = "/gzh_tag_list",produces = "application/json;charset=UTF-8")
    public ApiResult gzh_tag_list() {
        List<GzhTag> data = gzhTagService.list(
                new QueryWrapper<GzhTag>().orderByDesc("tag_sort")
        );
        Map<String,List<Map<String,Object>>> map = new HashMap<>();
        map.put("tag_list", ShareUtil.toLowBeanList(data));
        return ApiResult.success(map);
    }

    /**
     * 帮助中心
     * @param name
     * @return
     */
    @ApiOperation(value="帮助中心", notes="帮助中心")
    @GetMapping(value = "/help_center",produces = "application/json;charset=UTF-8")
    public ApiResult help_center(String name) {

        ArticleCate articleCate = articleCateService.getOne(new QueryWrapper<ArticleCate>().eq("cate_name",name));
        if(articleCate==null){
            return ApiResult.error("参数错误");
        }

        List<Article> data = articleService.list(
                new QueryWrapper<Article>().eq("cate_id",articleCate.getCateId())
                .orderByDesc("article_id")
        );
        data.forEach(article -> {
            article.setArticleContent(ShareUtil.htmlEncode(article.getArticleContent()));
        });
        Map<String,List<Map<String,Object>>> map = new HashMap<>();
        map.put("article_list", ShareUtil.toLowBeanList(data));
        return ApiResult.success(map);
    }

    /**
     * 标签列表
     * @return
     */
    @GetMapping(value = "/tag_list",produces = "application/json;charset=UTF-8")
    @ApiOperation(value="标签列表", notes="标签列表")
    public ApiResult tag_list() {

        List<ShareTag> data = shareTagService.list(
                new QueryWrapper<ShareTag>().orderByDesc("tag_sort"));
        Map<String,List<Map<String,Object>>> map = new HashMap<>();
        map.put("tag_list", ShareUtil.toLowBeanList(data));
        return ApiResult.success(map);
    }

}
