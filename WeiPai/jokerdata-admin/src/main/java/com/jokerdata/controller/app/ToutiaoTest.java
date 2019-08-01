package com.jokerdata.controller.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jokerdata.common.JsonUtils;
import com.jokerdata.entity.Jweibo;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;

public class ToutiaoTest {

	public static void main(String[] args) {
		//原创https://www.toutiao.com/a1640271093624844
		//转发https://www.toutiao.com/a6717877986915647492
		//原创https://www.toutiao.com/a1640102485066759
		Jweibo w = getToutiaoPojo("https://www.toutiao.com/a6719752390951747596");
		System.out.print(JsonUtils.objectToJson(w));
	}
	
	/**
	 * 返回目标头条的JSON字符串
	 * @param url
	 * @return jsonstring
	 * @throws IOException
	 */
	public static Map getContentByToutiao(String url) throws IOException {
		Map jsonResult = new HashMap();
		//int type = 0;//0为原创微头条，文章;1为视频;2为转发微头条文章
		//Connection con = Jsoup.connect(url).proxy("122.247.77.11", 54260);// 获取连接
		Connection con = Jsoup.connect(url);// 获取连接
		//Connection proxy = con.proxy("", 80);
		con.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:66.0) Gecko/20100101 Firefox/66.0");// 配置模拟浏览器
		Response rs = con.execute();// 获取响应
		Document d1 = Jsoup.parse(rs.body());
		Elements js = d1.getElementsByTag("script");
		String strtemp = "";
		//String str2 = "";
		for (Element element : js) {
				//抓取原创手机微头条内容或者电脑端文章内容以及视频
				if (element.data().contains("BASE_DATA")) {
					 //抓取微头条或PC端文章
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
				//抓取转发手机微头条内容或者电脑端文章内容
				}else if(element.data().contains("REACT_SSR_HYDRATED_DATA")) {
					strtemp = StringUtils.substringAfter(element.data(), ":");
					strtemp = StringUtils.substringBeforeLast(strtemp, "}");
					jsonResult.put("json","[" +strtemp + "}]" );
					//System.out.print("[" +str2 +"}]");
				}
		}
		//return jsonResult;
		return jsonResult;
	}
	
	/**
	 * 获取目标头条内容并包装为头条实体类
	 * @param weiboUrl
	 * @return weibo
	 */
	public static Jweibo getToutiaoPojo(String weiboUrl) {
		Jweibo weibo = new Jweibo();
		try {
			Map<String,String> m = getContentByToutiao(weiboUrl);
			String jsonStr = m.get("json");
			jsonStr = jsonStr.replaceAll(".slice\\(6, -6\\)", "");
			jsonStr = jsonStr.replaceAll(".slice\\(1, -1\\)", "");
			jsonStr = jsonStr.replaceAll("\'\"", "\'");
			jsonStr = jsonStr.replaceAll("\"\'", "\'");
			jsonStr = jsonStr.replaceAll("\'", "\"");//单引号换成双引号
			jsonStr = jsonStr.replaceAll("(\\w+)(\\s*: \\s*)", "\\\"$1\\\"$2"); 
			jsonStr = jsonStr.replaceAll("\r|\n|\\s", "").replaceAll(",\\}", "\\}"); 
			//System.out.print(jsonStr);
			List<Map> rootNode = JsonUtils.jsonToList(jsonStr, Map.class);//JSONObject.parseArray(jsonStr, Map.class);
			//System.out.print(jsonStr);
			handleWeiboJsonNew(rootNode,weibo,m.get("type"),null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weibo;
	}
	
	/**
	 * 处理json字符串并封装到weibo实体类
	 * @param rootNode
	 * @param weibo
	 */
	public static Jweibo handleWeiboJsonNew(List<Map> rootNode,Jweibo weibo,String type,LinkedHashMap<String, Object> c) {
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
}
