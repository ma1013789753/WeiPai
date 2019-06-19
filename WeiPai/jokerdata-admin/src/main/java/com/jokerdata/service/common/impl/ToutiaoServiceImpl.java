package com.jokerdata.service.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.common.JsonUtils;
import com.jokerdata.entity.Jweibo;
import com.jokerdata.entity.admin.generator.TshareLog;

import com.jokerdata.mapper.admin.generator.TshareLogMapper;
import com.jokerdata.service.common.ProxyIpService;
import com.jokerdata.service.common.ToutiaoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

    @Resource
    private TshareLogMapper tshareLogMapper;

    @Autowired
    private ProxyIpService proxyIpService;

    @Override
    public Jweibo pickToutiaoByUrl(String url) {
        Map<String, Object> proIp = proxyIpService.getProxyIp();
        Jweibo toutiao = new Jweibo();
        try {
            Map<String,String> m = getToutiaoContent(url,(String)proIp.get("ip"),(int)proIp.get("port"));
            String jsonStr = m.get("json");
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
        Connection con = Jsoup.connect(url).proxy(ip, port);
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
     * @param toutiao
     */
    private  Jweibo touTiaoHandler(List<Map> rootNode,Jweibo toutiao,String type,LinkedHashMap<String, Object> childNode) {
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
                //是否有图片内容
                if (statusNode.get("images")!=null) {
                    toutiao.setImages((List<String>) statusNode.get("images"));
                }
                if (statusNode.get("ugcInfo")!=null) {
                    Map<String, Object> userInfo = (HashMap<String, Object>) statusNode.get("ugcInfo");
                    toutiao.setScreenName((String)userInfo.get("name"));
                    toutiao.setUserId(Long.parseLong((String)userInfo.get("userId")));
                    toutiao.setProfileImage((String)userInfo.get("avatarUrl"));
                    toutiao.setCreatedAt((String)userInfo.get("publishTime"));
                }else if("1".equals(type)) {
                    toutiao.setScreenName((String)statusNode2.get("name"));
                    toutiao.setUserId(Long.parseLong((String)statusNode2.get("uid")));
                    toutiao.setProfileImage((String)statusNode2.get("avatar"));
                }if (statusNode.get("subInfo")!=null) {
                    Map<String, Object> create = (HashMap<String, Object>) statusNode.get("subInfo");
                    toutiao.setCreatedAt((String)create.get("time"));
                }

            }else if ("2".equals(type)) {
                toutiao.setId((String) statusNode.get("groupId"));
                toutiao.setText("title:"+statusNode.get("title")+"content:"+statusNode.get("content"));
                toutiao.setScreenName((String)statusNode.get("name"));
                toutiao.setUserId(Long.parseLong((String)statusNode.get("mediaId")));
                toutiao.setProfileImage((String)statusNode.get("avatarUrl"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return toutiao;
    }

    /**
     * 定时批量抓取转发头条
     */
    @Override
    public void crawlingToutiaoShare() {
        QueryWrapper<TshareLog> query = new QueryWrapper<>();
        query.eq("platform",1);
        query.eq("share_status",0);
        List<TshareLog> shareList = tshareLogMapper.selectList(query);
        if (shareList != null && shareList.size() > 0) {
            Map<String, Object> proIp = proxyIpService.getProxyIp();
            for (TshareLog slog:shareList) {
                log.info("start crawling data for {}",slog.getShareUrl());
                try {
                    Jweibo shareToutiao = getToutiaoForJob(slog.getShareUrl(),(String)proIp.get("ip"),(int)proIp.get("port"));
                    if (slog.getAccountId().equals(shareToutiao.getUserId()+"") && slog.getShareId().equals(shareToutiao.getRetweeted().getId())) {
                        slog.setShareStatus("1");
                        slog.setForwardContent(shareToutiao.getText());
                        slog.setUpdateTime(new Date());
                        tshareLogMapper.updateById(slog);
                    }
                }catch (Exception e){
                    log.error("loop crawling data error {}",e.getMessage());
                    continue;
                }

            }
        }

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


}
