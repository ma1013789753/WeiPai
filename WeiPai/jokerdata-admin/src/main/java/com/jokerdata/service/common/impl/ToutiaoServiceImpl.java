package com.jokerdata.service.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.common.JsonUtils;
import com.jokerdata.common.ShareUtil;
import com.jokerdata.controller.admin.CLogController;
import com.jokerdata.controller.admin.PLogController;
import com.jokerdata.entity.Jweibo;
import com.jokerdata.entity.admin.generator.TshareLog;

import com.jokerdata.entity.app.generator.*;
import com.jokerdata.mapper.admin.generator.TshareLogMapper;
import com.jokerdata.service.app.*;
import com.jokerdata.service.common.ProxyIpService;
import com.jokerdata.service.common.ToutiaoService;
import com.jokerdata.vo.CShareLog;
import com.jokerdata.vo.PShareLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 头条抓取服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-5-1
 */
@Slf4j
@Service
public class ToutiaoServiceImpl implements ToutiaoService {

    Logger logger = LoggerFactory.getLogger(WeiboServiceImpl.class);


    @Resource
    private TshareLogMapper tshareLogMapper;

    @Autowired
    private ProxyIpService proxyIpService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private ShareLogService shareLogService;
    @Autowired
    private PdLogService pdLogService;
    @Autowired
    private CoinLogService coinLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private CLogController cLogController;

    @Autowired
    private PLogController pLogController;


    @Override
    public Jweibo pickToutiaoByUrl(String url) {
        Map<String, Object> proIp = proxyIpService.getProxyIp();
        Jweibo toutiao = new Jweibo();
        try {
            Map<String,String> m = getToutiaoContent(url,(String)proIp.get("ip"),(int)proIp.get("port"));
            String jsonStr = m.get("json");
            jsonStr = ShareUtil.htmlEncode(jsonStr);
            jsonStr = jsonStr.replaceAll("(\\.slice\\((.+?)\\))", "");
            jsonStr = jsonStr.replaceAll("\'\"", "\"").replaceAll("\"\'", "\"");
            jsonStr = jsonStr.replaceAll("\'", "\"");
            jsonStr = jsonStr.replaceAll("(\\w+)(\\s*: \\s*)", "\\\"$1\\\"$2");
            jsonStr = jsonStr.replaceAll("\r|\n|\\s", "").replaceAll(",\\}", "\\}");
            List<Map> rootNode = JsonUtils.jsonToList(jsonStr, Map.class);
            touTiaoHandler(rootNode,toutiao,m.get("type"),null);
        } catch (Exception e) {
            log.error("pick data error!");
            e.printStackTrace();
        }
        return toutiao;
    }


    /**
     * 返回目标头条的JSON字符串
     * @param url
     * @return jsonstring
     * @throws IOException
     */
    private  Map getToutiaoContent(String url,String ip,int port) throws IOException {
        Map jsonResult = new HashMap();
        url = url.split("/\\?")[0];
        url = url.substring(url.lastIndexOf("/")+1);
        Connection con = Jsoup.connect("https://www.toutiao.com/a"+url).proxy(ip, port);

        con.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:66.0) Gecko/20100101 Firefox/66.0");// 配置模拟浏览器
        Response rs = con.execute();
        Document d1 = Jsoup.parse(rs.body());
        Elements js = d1.getElementsByTag("script");
        String strtemp = "";
        for (Element element : js) {
            //原创手机微头条内容或者电脑端文章内容以及视频
            if (element.data().contains("BASE_DATA")) {
                //微头条或PC端文章
                if (element.data().contains("articleInfo")) {
                    strtemp = StringUtils.substringBeforeLast(StringUtils.substringAfter(element.data(), "="), "shareInfo");
                    strtemp = StringUtils.substringBeforeLast(strtemp, ",");
                    jsonResult.put("json","[" +strtemp + "}]" );
                    if (element.data().contains("ugcInfo")) {
                        jsonResult.put("type","0" );
                    }else {
                        jsonResult.put("type","1" );
                    }
                    //抓取视频
                }else if(element.data().contains("abstractInfo")) {
                    strtemp = StringUtils.substringBeforeLast(StringUtils.substringAfter(element.data(), "abstractInfo"), "userInfo");
                    strtemp = StringUtils.substringBeforeLast(strtemp, ",");
                    jsonResult.put("json","[{abstractInfo" +strtemp + "}]" );
                    jsonResult.put("type","2" );
                }
                //转发手机微头条内容或者电脑端文章内容
            }else if(element.data().contains("REACT_SSR_HYDRATED_DATA")) {
                strtemp = StringUtils.substringAfter(element.data(), ":");
                strtemp = StringUtils.substringBeforeLast(strtemp, "}");
                jsonResult.put("json","[" +strtemp + "}]" );
            }
        }
        return jsonResult;
    }


    /**
     * 处理json字符串并封装到weibo实体类
     * @param rootNode
     */
    private  Jweibo touTiaoHandler(List<Map> rootNode,Jweibo weibo,String type,LinkedHashMap<String, Object> c) {
        try {
            //第一层根节点
            //Map map = rootNode.get(0);
            //Map<String, Object> object = (Map<String, Object>) map.get("articleInfo");
            Map<String, Object> statusNode = new HashMap<String, Object>();
            Map<String, Object> statusNode2 = new HashMap<String, Object>();
            if(rootNode != null) {
                for(Map root:rootNode) {
                    switch (type) {
                        case "0":
                            statusNode =  (Map<String, Object>) root.get("articleInfo");
                            break;
                        case "1":
                            statusNode =  (Map<String, Object>) root.get("articleInfo");
                            statusNode2 =  (Map<String, Object>) root.get("mediaInfo");
                            break;
                        default:
                            statusNode =  (Map<String, Object>) root.get("abstractInfo");
                            break;
                    }
                }
            }else {
                statusNode = c;
            }
            //第二层status节点
            //for(String k:statusNode.keySet()) {
            //微头条和文章
            if ("0".equals(type) || "1".equals(type)) {
                weibo.setId((String) statusNode.get("itemId"));
                weibo.setText((String)statusNode.get("content"));
                //判断目标头条是否有图片内容
                if (statusNode.get("images")!=null) {
                    weibo.setImages((List<String>) statusNode.get("images"));
                }
                if (statusNode.get("ugcInfo")!=null) {
                    Map<String, Object> userInfo = (HashMap<String, Object>) statusNode.get("ugcInfo");
                    weibo.setScreenName((String)userInfo.get("name"));
                    weibo.setUserId(Long.valueOf(userInfo.get("userId").toString()));
                    weibo.setProfileImage((String)userInfo.get("avatarUrl"));
                    weibo.setCreatedAt((String)userInfo.get("publishTime"));
                }else if("1".equals(type)) {
                    weibo.setScreenName((String)statusNode2.get("name"));
                    weibo.setUserId(Long.valueOf(statusNode2.get("uid").toString()));
                    weibo.setProfileImage((String)statusNode2.get("avatar"));
                }if (statusNode.get("subInfo")!=null) {
                    Map<String, Object> create = (HashMap<String, Object>) statusNode.get("subInfo");
                    weibo.setCreatedAt((String)create.get("time"));
                }if (statusNode.get("repostParams")!=null) {
                    Map<String, Object> retweetedInfo = (HashMap<String, Object>) statusNode.get("repostParams");
                    Jweibo reweibo = new Jweibo();
                    reweibo.setId(retweetedInfo.get("fw_id") +"");
                    weibo.setRetweeted(reweibo);
                }

            }else if ("2".equals(type)) {
                weibo.setId((String) statusNode.get("groupId"));
                weibo.setText("title:"+(String)statusNode.get("title")+"content:"+(String)statusNode.get("content"));
                weibo.setScreenName((String)statusNode.get("name"));
                weibo.setUserId(Long.valueOf(statusNode.get("mediaId").toString()));
                weibo.setProfileImage((String)statusNode.get("avatarUrl"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return weibo;
    }

    /**
     * 定时批量抓取转发头条
     */
    @Override
    public void crawlingToutiaoShare() {
        logger.error("\"开始抓取头条\" = " + "开始抓取头条");
        QueryWrapper<Share> shareQueryWrapper = new QueryWrapper<>();
        shareQueryWrapper.eq("share_type",3);//微博
        shareQueryWrapper.in("share_state","0","4");//進行中
        List<Share> shareList = shareService.list(shareQueryWrapper);
        shareList.forEach(share -> {
            QueryWrapper<ShareLog> shareLogQueryWrapper = new QueryWrapper<>();
            shareLogQueryWrapper.eq("share_id",share.getShareId());
            shareLogQueryWrapper.eq("is_pass","0");
            List<ShareLog> shareLogs = shareLogService.list(shareLogQueryWrapper);
            shareLogs.forEach(shareLog -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.error("\"开始抓取头条\" = " + shareLog.toString());
//                Map<String, Object> proIp = proxyIpService.getProxyIp();
                UserAccount userAccount = userAccountService.getById(shareLog.getAccountId());
                Jweibo shareWeibo = null;
                logger.error("shareLog.getContent()"+shareLog.getContent());
                if(ShareUtil.verifyUrl(shareLog.getContent())){
                    shareWeibo = pickToutiaoByUrl(shareLog.getContent());
                }
                //积分
                if(share.getShareStatus().equals("0")){
                    CoinLog coinLog = coinLogService.getById(shareLog.getLogId());
                    CShareLog cShareLog = new CShareLog();
                    BeanUtils.copyProperties(coinLog, cShareLog);
                    cShareLog.setShare(share);
                    cShareLog.setShareLog(shareLog);
                    if(shareWeibo != null &&
                            shareWeibo.getRetweeted()!=null &&
                            !StringUtils.isEmpty(shareWeibo.getId()) &&
                            share.getWbId().equals(shareWeibo.getRetweeted().getId()) && share.getWbId().equals(shareWeibo.getRetweeted().getId())
                            && String.valueOf(shareWeibo.getUserId()).equals(userAccount.getUid())
                            ){
                        logger.error(shareLog.toString()+"——————获取成功");
                        cLogController.approve(cShareLog);
                    }else{
                        logger.error(shareLog.toString()+"——————未获取到相关转发内容");
                        cLogController.approveFail(cShareLog);

                    }
                }
                //现金
                if(share.getShareStatus().equals("1")){
                    PdLog pdLog = pdLogService.getById(shareLog.getLogId());
                    PShareLog pShareLog = new PShareLog();
                    BeanUtils.copyProperties(pdLog, pShareLog);
                    pShareLog.setShare(share);
                    pShareLog.setShareLog(shareLog);
                    if(shareWeibo != null && shareWeibo.getRetweeted()!=null
                            && !StringUtils.isEmpty(shareWeibo.getId()) && share.getWbId().equals(shareWeibo.getRetweeted().getId())
                            && String.valueOf(shareWeibo.getUserId()).equals(userAccount.getUid())
                    ){

                        logger.error(shareLog.toString()+"——————获取成功");
                        pLogController.approve(pShareLog);
                    }else{
                        logger.error(shareLog.toString()+"——————未获取到相关转发内容");
                        pLogController.approveFail(pShareLog);

                    }
                }
            });
        });

    }


    private Jweibo getToutiaoForJob(String url,String ip,int port) {
        Jweibo toutiao = new Jweibo();
        try {
            Map<String,String> m = getToutiaoContent(url,ip,port);
            String jsonStr = m.get("json");
            jsonStr = jsonStr.replaceAll("\'", "\"");
            jsonStr = jsonStr.replaceAll("(\\w+)(\\s*: \\s*)", "\\\"$1\\\"$2");
            jsonStr = jsonStr.replaceAll("\r|\n|\\s", "").replaceAll(",\\}", "\\}");
            List<Map> rootNode = JsonUtils.jsonToList(jsonStr, Map.class);
            shareTouTiaoHandler(rootNode,toutiao,m.get("type"),null);
        } catch (Exception e) {
            log.error("pick data error!");
            e.printStackTrace();
        }
        return toutiao;
    }

    /**
     * 处理json字符串并封装到实体类
     * @param rootNode
     * @param toutiao
     */
    private  Jweibo shareTouTiaoHandler(List<Map> rootNode,Jweibo toutiao,String type,LinkedHashMap<String, Object> childNode) {
        try {
            //第一层根节点
            Map<String, Object> statusNode = new HashMap<>();
            Map<String, Object> statusNode2 = new HashMap<>();
            if(rootNode != null) {
                for(Map root:rootNode) {
                    switch (type) {
                        case "0":
                            statusNode =  (Map<String, Object>) root.get("articleInfo");
                            break;
                        case "1":
                            statusNode =  (Map<String, Object>) root.get("articleInfo");
                            statusNode2 =  (Map<String, Object>) root.get("mediaInfo");
                            break;
                        default:
                            statusNode =  (Map<String, Object>) root.get("abstractInfo");
                            break;
                    }
                }
            }else {
                statusNode = childNode;
            }
            //status节点
            //微头条和文章
            if ("0".equals(type) || "1".equals(type)) {
                toutiao.setId((String) statusNode.get("itemId"));
                toutiao.setText((String)statusNode.get("content"));

                if (statusNode.get("ugcInfo")!=null) {
                    Map<String, Object> userInfo = (HashMap<String, Object>) statusNode.get("ugcInfo");
                    toutiao.setUserId(Long.parseLong((String)userInfo.get("userId")));
                }else if("1".equals(type)) {
                    toutiao.setUserId(Long.parseLong((String)statusNode2.get("uid")));
                }

            }else if ("2".equals(type)) {
                toutiao.setId((String) statusNode.get("groupId"));
                toutiao.setText("title:"+statusNode.get("title")+"content:"+statusNode.get("content"));
                toutiao.setUserId(Long.parseLong((String)statusNode.get("mediaId")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return toutiao;
    }

    public static void main(String[] args) {
        String url = "https://weitoutiao.zjurl.cn/ugc/share/thread/1640300875984900/?app=&target_app=13&tt_from=copy_link&utm_medium=toutiao_android&utm_campaign=client_share";
        url = url.split("/\\?")[0];
        url = url.substring(url.lastIndexOf("/")+1);
        System.out.printf(url);
    }

}
