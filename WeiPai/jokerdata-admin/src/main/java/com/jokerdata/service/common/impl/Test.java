package com.jokerdata.service.common.impl;

import com.jokerdata.common.JsonUtils;
import com.jokerdata.common.ShareUtil;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

public class Test {
    private static String str = "[ {\n" +
            "    userInfo: {\n" +
            "      id: 0,\n" +
            "      userName: '',\n" +
            "      avatarUrl: '',\n" +
            "      isPgc: false,\n" +
            "      isOwner: false\n" +
            "    },\n" +
            "    headerInfo: {\n" +
            "      id: 0,\n" +
            "      isPgc: false,\n" +
            "      userName: '',\n" +
            "      avatarUrl: '',\n" +
            "      isHomePage: false,\n" +
            "      chineseTag: '时政',\n" +
            "      crumbTag: 'search/?keyword=%E6%97%B6%E6%94%BF',\n" +
            "      hasBar: true\n" +
            "    },\n" +
            "    articleInfo: {\n" +
            "      title: '&quot;东西部“携手奔小康”，总书记指示这么干&quot;'.slice(6, -6),\n" +
            "      content: '&quot;\\u003Cdiv\\u003E\\u003Cp\\u003E“小康路上一个都不能掉队！”2020年我国全面建成小康社会，是我们党向人民、向历史作出的庄严承诺，是13亿多中国人民的共同期盼。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E2016年7月20日，在宁夏银川召开的东西部扶贫协作座谈会上，习近平用“大战略、大布局、大举措”来形容东西部扶贫协作。习近平指出，“\\u003Cstrong\\u003E这在世界上只有我们党和国家能够做到，充分彰显了我们的政治优势和制度优势。东西部扶贫协作和对口支援必须长期坚持下去。\\u003C\\u002Fstrong\\u003E”\\u003C\\u002Fp\\u003E\\u003Cp\\u003E脱贫攻坚，全国一盘棋；全面小康，决胜在合力。近年来，习近平对进一步做好东西部扶贫协作和对口支援工作有哪些重要指示？一起来学习！\\u003C\\u002Fp\\u003E\\u003Cdiv class&#x3D;\\&quot;pgc-img\\&quot;\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp9.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002Fcdc4a94cd31d4c0882fc89e2df445848\\&quot; img_width&#x3D;\\&quot;1080\\&quot; img_height&#x3D;\\&quot;950\\&quot; alt&#x3D;\\&quot;东西部“携手奔小康”，总书记指示这么干\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp class&#x3D;\\&quot;pgc-img-caption\\&quot;\\u003E\\u003C\\u002Fp\\u003E\\u003C\\u002Fdiv\\u003E\\u003Cdiv class&#x3D;\\&quot;pgc-img\\&quot;\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp3.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002F30a3caf7d6a94940b7b4e34c09aa6f51\\&quot; img_width&#x3D;\\&quot;1080\\&quot; img_height&#x3D;\\&quot;878\\&quot; alt&#x3D;\\&quot;东西部“携手奔小康”，总书记指示这么干\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp class&#x3D;\\&quot;pgc-img-caption\\&quot;\\u003E\\u003C\\u002Fp\\u003E\\u003C\\u002Fdiv\\u003E\\u003Cdiv class&#x3D;\\&quot;pgc-img\\&quot;\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp1.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002F4a86656687d241f293f5b04fc903f9bd\\&quot; img_width&#x3D;\\&quot;1080\\&quot; img_height&#x3D;\\&quot;640\\&quot; alt&#x3D;\\&quot;东西部“携手奔小康”，总书记指示这么干\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp class&#x3D;\\&quot;pgc-img-caption\\&quot;\\u003E\\u003C\\u002Fp\\u003E\\u003C\\u002Fdiv\\u003E\\u003Cdiv class&#x3D;\\&quot;pgc-img\\&quot;\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp3.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002Fc5cee2a8a2154b719a94d0694dad23b6\\&quot; img_width&#x3D;\\&quot;1080\\&quot; img_height&#x3D;\\&quot;1150\\&quot; alt&#x3D;\\&quot;东西部“携手奔小康”，总书记指示这么干\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp class&#x3D;\\&quot;pgc-img-caption\\&quot;\\u003E\\u003C\\u002Fp\\u003E\\u003C\\u002Fdiv\\u003E\\u003Cdiv class&#x3D;\\&quot;pgc-img\\&quot;\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp1.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002Fd9d623f6bf2b48f98e9a506baf5365fb\\&quot; img_width&#x3D;\\&quot;1080\\&quot; img_height&#x3D;\\&quot;1096\\&quot; alt&#x3D;\\&quot;东西部“携手奔小康”，总书记指示这么干\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp class&#x3D;\\&quot;pgc-img-caption\\&quot;\\u003E\\u003C\\u002Fp\\u003E\\u003C\\u002Fdiv\\u003E\\u003Cdiv class&#x3D;\\&quot;pgc-img\\&quot;\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp1.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002Fc5042ee5b7c24a379ad070ccca82a54c\\&quot; img_width&#x3D;\\&quot;1080\\&quot; img_height&#x3D;\\&quot;1077\\&quot; alt&#x3D;\\&quot;东西部“携手奔小康”，总书记指示这么干\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp class&#x3D;\\&quot;pgc-img-caption\\&quot;\\u003E\\u003C\\u002Fp\\u003E\\u003C\\u002Fdiv\\u003E\\u003Cdiv class&#x3D;\\&quot;pgc-img\\&quot;\\u003E\\u003Cimg src&#x3D;\\&quot;http:\\u002F\\u002Fp1.pstatp.com\\u002Flarge\\u002Fpgc-image\\u002F3894f4830243496fa8ac88fc20af8b9c\\&quot; img_width&#x3D;\\&quot;1080\\&quot; img_height&#x3D;\\&quot;640\\&quot; alt&#x3D;\\&quot;东西部“携手奔小康”，总书记指示这么干\\&quot; inline&#x3D;\\&quot;0\\&quot;\\u003E\\u003Cp class&#x3D;\\&quot;pgc-img-caption\\&quot;\\u003E\\u003C\\u002Fp\\u003E\\u003C\\u002Fdiv\\u003E\\u003Cp\\u003E东西部结对牵手、协作扶贫，中国的脱贫攻坚，为全球减贫事业贡献了中国实践。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E我国贫困人口从2012年的9899万人减少到2018年的1660万人，6年时间减少了8000多万人，连续6年平均每年减贫1300多万人。今年7月，国务院扶贫办公布，今年我国宣布脱贫摘帽的贫困县有283个。至此，全国已经有436个贫困县脱贫摘帽，占全部贫困县的52.4%。贫困县摘帽进程过半，解决区域性整体贫困步伐加快。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E脱贫攻坚进入倒计时。行百里者半九十。从现在到2020年，是全面建成小康社会决胜期，最艰巨的任务依然是脱贫攻坚。习近平强调：“‘\\u003Cstrong\\u003E人心齐，泰山移。’脱贫致富不仅仅是贫困地区的事，也是全社会的事。要更加广泛、更加有效地动员和凝聚各方面力量。要强化东西部扶贫协作。\\u003C\\u002Fstrong\\u003E”\\u003C\\u002Fp\\u003E\\u003Cp\\u003E山海牵手，共同圆小康梦。\\u003C\\u002Fp\\u003E\\u003Cp\\u003E\\u003Cstrong\\u003E▌\\u003C\\u002Fstrong\\u003E本文来源：中央广播电视总台中国之声\\u003C\\u002Fp\\u003E\\u003C\\u002Fdiv\\u003E&quot;'.slice(6, -6),\n" +
            "      groupId: '6715639983048245771',\n" +
            "      itemId: '6715639983048245771',\n" +
            "      type: 1,\n" +
            "      subInfo: {\n" +
            "        isOriginal: false,\n" +
            "        source: '央视新闻',\n" +
            "        time: '2019-07-20 18:15:39'\n" +
            "      },\n" +
            "      tagInfo: {\n" +
            "        tags: [{\"name\":\"习近平\"},{\"name\":\"广播\"},{\"name\":\"银川\"}],\n" +
            "        groupId: '6715639983048245771',\n" +
            "        itemId: '6715639983048245771',\n" +
            "        repin: 0,\n" +
            "      },\n" +
            "      has_extern_link: 0,\n" +
            "      coverImg: 'http://p9.pstatp.com/list/300x196/pgc-image/cf57567e5d6a418cb5865ed58da01d28.jpg'\n" +
            "    },\n" +
            "    commentInfo: {\n" +
            "      groupId: '6715639983048245771',\n" +
            "      itemId: '6715639983048245771',\n" +
            "      comments_count: 608,\n" +
            "      ban_comment: 0\n" +
            "    },\n" +
            "    mediaInfo: {\n" +
            "      uid: '4492956276',\n" +
            "      name: '央视新闻',\n" +
            "      avatar: '//p10.pstatp.com/large/6ee30000539441f6c83a',\n" +
            "      openUrl: '/c/user/4492956276/',\n" +
            "      follow: false\n" +
            "    },\n" +
            "    pgcInfo: [{\"item_id\":\"6716009664980451844\",\"url\":\"\\u002Fitem\\u002F6716009664980451844\",\"title\":\"开仓放粮、打倒土豪劣绅……短短3天1500人加入红军\"},{\"item_id\":\"6716009251405300237\",\"url\":\"\\u002Fitem\\u002F6716009251405300237\",\"title\":\"甘肃舟曲发生高位山体滑坡，暂未造成安全威胁\"},{\"item_id\":\"6716008847418327556\",\"url\":\"\\u002Fitem\\u002F6716008847418327556\",\"title\":\"北京2019年积分落户申报结束，超10万人参与申报\"},{\"item_id\":\"6715982788891247112\",\"url\":\"\\u002Fitem\\u002F6715982788891247112\",\"title\":\"日本京都动画遭遇纵火惨剧，为何震惊世界？\"}],\n" +
            "    feedInfo: {\n" +
            "      url: '/api/pc/feed/',\n" +
            "      category: '__all__',\n" +
            "      initList: []\n" +
            "    }}]";

    public static void main(String[] args) {
        String jsonStr = str.replaceAll(" ","");
        jsonStr = ShareUtil.htmlEncode(jsonStr);
        jsonStr = jsonStr.replaceAll("(\\.slice\\((.+?)\\))", "");
        jsonStr = jsonStr.replaceAll("\'", "").replaceAll("\"","").replaceAll("“","");
        jsonStr = jsonStr.replaceAll("(\\w+)(\\s*: \\s*)", "\\\"$1\\\"$2");
        jsonStr = jsonStr.replaceAll("\r|\n|\\s", "").replaceAll(",\\}", "\\}");
        //统一格式化
        jsonStr = jsonStr.replaceAll("\\[","\n[\n").replaceAll("\\]","\n]\n");
        jsonStr = jsonStr.replaceAll("\\{","\n{\n").replaceAll("\\}","\n}\n");
        jsonStr = jsonStr.replaceAll(",",",\n");
        String[] strs = jsonStr.split("\n");
        String data = "";
        for (String str:strs) {
            if(str.contains(":")){
                String[] map = str.split(":");
                if(map.length==2){
                    String key = map[0];
                    String value = map[1];
                    if(value.endsWith(",")){
                        value = value.replaceAll(",","");
                        value = "\""+value+"\"";
                        data += ("\""+key+"\""+":"+value+",\n");
                    }else{
                        value = "\""+value+"\"";
                        data += ("\""+key+"\""+":"+value+"\n");
                    }
                }else{
                    System.out.println(str+"----------------");
                }



            }else{
                data += str;
            }

        }
        System.out.println(data);
    }
}
