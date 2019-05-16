package com.jokerdata.jokerdatapassageway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jokerdata.common.JsonUtils;
import com.jokerdata.entity.admin.generator.TshareLog;
import com.jokerdata.jokerdatapassageway.common.entity.Jweibo;
import com.jokerdata.jokerdatapassageway.service.ProxyIpService;
import com.jokerdata.jokerdatapassageway.service.WeiboService;
import com.jokerdata.mapper.admin.generator.TshareLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 微博抓取服务实现类
 * </p>
 *
 * @author aozhang
 * @since 2019-5-1
 */
@Slf4j
@Service
public class WeiboServiceImpl implements WeiboService {

    @Resource
    private TshareLogMapper tshareLogMapper;

    @Autowired
    private ProxyIpService proxyIpService;

    @Override
    public Jweibo pickDataByUrl(String url) {
        Map<String, Object> proIp = proxyIpService.getProxyIp();
        Jweibo weibo = new Jweibo();
        try {
            String jsonStr = getWeiboJsonStr(url,(String)proIp.get("ip"),(int)proIp.get("port"));
            List<Map> rootNode = JsonUtils.jsonToList(jsonStr, Map.class);
            JweiboHandler(rootNode,weibo,null);
        } catch (Exception e) {
            log.error("pick data error");
            e.printStackTrace();
        }
        return weibo;
    }

    /**
     * 抓取指定转发微博
     * @param url
     * @param proxyIp
     * @param port
     * @return Jweibo
     */
    private Jweibo getWeiboData(String url,String proxyIp,int port) {
        Jweibo weibo = new Jweibo();
        try {
            String jsonStr = getWeiboJsonStr(url, proxyIp, port);
            List<Map> rootNode = JsonUtils.jsonToList(jsonStr, Map.class);
            shareHandler(rootNode,weibo,null);
        } catch (Exception e) {
            log.error("getWeiboData() error!");
            e.printStackTrace();
        }
        return weibo;
    }

    /**
     * 定时批量抓取转发微博
     */
    @Override
    public void crawlingShareData() {
        QueryWrapper<TshareLog> query = new QueryWrapper<>();
        query.eq("platform",0);
        query.eq("share_status",0);
        List<TshareLog> shareList = tshareLogMapper.selectList(query);
        if (shareList != null && shareList.size() > 0) {
            Map<String, Object> proIp = proxyIpService.getProxyIp();
            for (TshareLog slog:shareList) {
                log.info("start crawling data for {}",slog.getShareUrl());
                try {
                    Jweibo shareWeibo = getWeiboData(slog.getShareUrl(),(String)proIp.get("ip"),(int)proIp.get("port"));
                    if (slog.getAccountId().equals(shareWeibo.getUserId()+"") && slog.getShareId().equals(shareWeibo.getRetweeted().getId())) {
                        slog.setShareStatus("1");
                        slog.setForwardContent(shareWeibo.getText());
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


    /**step1
     * 获取指定微博内容的JSON字符串
     * @param url
     * @return jsonstring
     * @throws IOException
     */
    private String getWeiboJsonStr(String url,String proxyIp,int port)  {
        String jsonResult = "";
        Connection con = Jsoup.connect(url).proxy(proxyIp, port);
        con.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:66.0) Gecko/20100101 Firefox/66.0");
        Response rs = null;
        try {
            rs = con.execute();
        } catch (IOException e) {
            log.error("Jsoup connection error");
            e.printStackTrace();
        }
        Document d1 = Jsoup.parse(rs.body());
        Elements js = d1.getElementsByTag("script").eq(1);
        for (Element element : js) {
            String[] data = element.data().split("var");
            for (String v1:data) {
                if (v1.contains("render_data")) {
                    String str1 = StringUtils.substringAfter(v1, "=");
                    String str2 = StringUtils.substringBeforeLast(str1, "[");
                    jsonResult = str2;
                }

            }
        }
        return jsonResult;
    }

    /**
     * 微博时间格式转换
     * @param datestr
     * @return
     */
    public static String dateFormatter(String datestr) {
        String dest = "";
        SimpleDateFormat fromFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss +0800 yyyy", Locale.ENGLISH);
        SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            dest = toFormat.format(fromFormat.parse(datestr));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return dest;
    }


    /**step2
     * 处理json字符串并封装到Jweibo
     * @param rootNode
     * @param weibo
     */
    public static Jweibo JweiboHandler(List<Map> rootNode, Jweibo weibo, HashMap<String, Object> childNode) {
        try {
            //根节点
            HashMap<String, Object> statusNode = new HashMap<String, Object>();
            if(rootNode != null) {
                for(Map root:rootNode) {
                    statusNode =  (HashMap<String, Object>) root.get("status");
                }
            }else {
                statusNode = childNode;
            }
            //status节点
            for(String k:statusNode.keySet()) {
                String gg = (String) statusNode.get("id");
                weibo.setId(gg);
                String datestr = dateFormatter((String)statusNode.get("created_at"));
                weibo.setCreatedAt(datestr);
                weibo.setSource((String)statusNode.get("source"));
                weibo.setText((String)statusNode.get("text"));
                //是否有图片内容
                if (statusNode.get("bmiddle_pic")!=null) {
                    List<String> finalPicList = new ArrayList<>();
                    String prefix = StringUtils.substringBeforeLast((String)statusNode.get("bmiddle_pic"),"/");
                    List<String> picList = (List<String>)statusNode.get("pic_ids");
                    for(String pic:picList) {
                        finalPicList.add(prefix+"/"+pic);
                    }
                    weibo.setImages(finalPicList);
                }
                //是否有视频内容
                if (statusNode.get("page_info")!=null) {
                    HashMap<String, Object> pageInfo = (HashMap<String, Object>) statusNode.get("page_info");
                    for(String pagek:pageInfo.keySet()) {
                        if ("video".equals(pageInfo.get("type"))) {
                            //视频播放地址
                            List<String> finalvideoList = new ArrayList<String>();
                            HashMap<String, Object> videoList = (HashMap<String, Object>)pageInfo.get("urls");
                            for (String v:videoList.keySet()) {
                                finalvideoList.add((String) videoList.get(v));
                                weibo.setVideo(finalvideoList);
                            }
                        }
                    }

                }

                HashMap<String, Object> userInfo = (HashMap<String, Object>) statusNode.get("user");
                for(String userk:userInfo.keySet()) {
                    weibo.setUserId((long)userInfo.get("id"));
                    weibo.setScreenName((String)userInfo.get("screen_name"));
                    weibo.setProfileImage((String)userInfo.get("profile_image_url"));
                }
                //是否是原创微博
                if (statusNode.get("retweeted_status")!=null) {
                    HashMap<String, Object> retweetedInfo = (HashMap<String, Object>) statusNode.get("retweeted_status");
                    Jweibo weibo2 = new Jweibo();
                    weibo.setRetweeted(JweiboHandler(null,weibo2,retweetedInfo));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return weibo;
    }

    /**
     * 处理转发微博json字符串并封装
     * @param rootNode
     * @param weibo
     * @param childNode
     */
    public static Jweibo shareHandler(List<Map> rootNode, Jweibo weibo, HashMap<String, Object> childNode) {
        try {
            //根节点
            HashMap<String, Object> statusNode = new HashMap<String, Object>();
            if(rootNode != null) {
                for(Map root:rootNode) {
                    statusNode =  (HashMap<String, Object>) root.get("status");
                }
            }else {
                statusNode = childNode;
            }
            //status节点
                weibo.setId((String) statusNode.get("id"));
                weibo.setText((String)statusNode.get("text"));
                HashMap<String, Object> userInfo = (HashMap<String, Object>) statusNode.get("user");
                weibo.setUserId((long)userInfo.get("id"));
                //是否是原创微博
                if (statusNode.get("retweeted_status")!=null) {
                    HashMap<String, Object> retweetedInfo = (HashMap<String, Object>) statusNode.get("retweeted_status");
                    Jweibo weibo2 = new Jweibo();
                    weibo.setRetweeted(shareHandler(null,weibo2,retweetedInfo));
                }

        } catch (Exception e) {
            log.error("shareHandler error!");
            e.printStackTrace();
        }
        return weibo;
    }
}
