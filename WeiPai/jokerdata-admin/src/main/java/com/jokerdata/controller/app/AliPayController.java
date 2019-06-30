package com.jokerdata.controller.app;

import com.alibaba.druid.util.StringUtils;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayOpenPublicTemplateMessageIndustryModifyRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayOpenPublicTemplateMessageIndustryModifyResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.jokerdata.common.config.AlipayConfig;
import com.jokerdata.common.exception.ApiException;
import com.jokerdata.entity.app.generator.Coin;
import com.jokerdata.service.app.CoinService;
import com.jokerdata.vo.ApiResult;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static cn.jiguang.common.connection.IHttpClient.CHARSET;
import static com.alipay.api.AlipayConstants.APP_ID;

@RestController
@RequestMapping("/Notify")
@Transactional(rollbackFor = ApiException.class)
public class AliPayController {
    Logger log = LoggerFactory.getLogger(AliPayController.class);

    @Autowired
    private CoinService coinService;

    @GetMapping(value = "/alipay_orderinfo",produces = "application/json;charset=UTF-8")
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResult getAliPayOrderStr(String order_sn) {

        Coin coin = coinService.getOne(new QueryWrapper<Coin>().eq("order_sn",order_sn));

        //最终返回加签之后的，app需要传给支付宝app的订单信息字符串
        String orderString = "";

        try{
            //实例化客户端（参数：网关地址、商户appid、商户私钥、格式、编码、支付宝公钥、加密类型），为了取得预付订单信息
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
                    AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                    AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            ali_request.setNotifyUrl(AlipayConfig.notify_url);
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            //业务参数传入,可以传很多，参考API
            //model.setPassbackParams(URLEncoder.encode(request.getBody().toString())); //公用参数（附加数据）
            model.setBody("商品购买");                       //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
            model.setSubject("商品");                 //商品名称
            model.setOutTradeNo(coin.getOrderSn());           //商户订单号(自动生成)
//            // model.setTimeoutExpress("30m");     			  //交易超时时间
            model.setTotalAmount(coin.getOrderAmount().doubleValue()+"");         //支付金额
            model.setProductCode("QUICK_MSECURITY_PAY");        	  //销售产品码（固定值）
            ali_request.setBizModel(model);

//            ali_request.setNotifyUrl(AlipayConfig.notify_url);        //异步回调地址（后台）
//            ali_request.setReturnUrl(AlipayConfig.return_url);	    //同步回调地址（APP）

            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.sdkExecute(ali_request); //返回支付宝订单信息(预处理)
            orderString=alipayTradeAppPayResponse.getBody();//就是orderString 可以直接给APP请求，无需再做处理。
            if(StringUtils.isEmpty(orderString)){
                return ApiResult.error("失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ApiResult.error("失败");
        }
        Map<String,String> data = new HashMap<>();
        data.put("orderinfo",orderString);
        return ApiResult.success(data);
    }

    @RequestMapping(value = "/alipay_notifyurl", produces = "application/json;charset=UTF-8", method = {
            RequestMethod.GET, RequestMethod.POST })
    public void getAlipayNotify(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        // 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        try {
            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET,
                    AlipayConfig.SIGNTYPE);
            if (flag) {
                String trade_status = params.get("trade_status");
                String out_trade_no = params.get("out_trade_no");
                String trade_no = params.get("trade_no");
                if ("TRADE_SUCCESS".equals(trade_status)) { // 交易支付成功的执行相关业务逻辑

                } else if ("TRADE_CLOSED".equals(trade_status)) { // 未付款交易超时关闭,或支付完成后全额退款,执行相关业务逻辑

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/wxPayNotifyReturn")
    @ResponseBody
    public ApiResult wxPayNotifyReturn(HttpServletRequest request,
                                      HttpServletResponse response) {
        log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA-------进入微信回调函数wxPayNotifyReturn！");
        try {
            // 把如下代码贴到的你的处理回调的servlet 或者.do 中即可明白回调操作
            String inputLine;
            String notityXml = "";
            String resXml = "";

            try {
                while ((inputLine = request.getReader().readLine()) != null) {
                    notityXml += inputLine;
                }
                request.getReader().close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("接收到的报文：" + notityXml);

            Map<String,String> m = parseXmlToList2(notityXml);
//            WxPayResult wpr = new WxPayResult();
//            wpr.setAppid(m.get("appid").toString());
//            wpr.setBankType(m.get("bank_type").toString());
//            wpr.setCashFee(m.get("cash_fee").toString());
//            wpr.setFeeType(m.get("fee_type").toString());
//            wpr.setIsSubscribe(m.get("is_subscribe").toString());
//            wpr.setMchId(m.get("mch_id").toString());
//            wpr.setNonceStr(m.get("nonce_str").toString());
//            wpr.setOpenid(m.get("openid").toString());
//            wpr.setOutTradeNo(m.get("out_trade_no").toString());
//            wpr.setResultCode(m.get("result_code").toString());
//            wpr.setReturnCode(m.get("return_code").toString());
//            wpr.setSign(m.get("sign").toString());
//            wpr.setTimeEnd(m.get("time_end").toString());
//            wpr.setTotalFee(m.get("total_fee").toString());
//            wpr.setTradeType(m.get("trade_type").toString());
//            wpr.setTransactionId(m.get("transaction_id").toString());

            boolean payFlag = false;

//            log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA-------wpr.getReturnCode():"+wpr.getReturnCode());

            if ("SUCCESS".equals(m.get("return_code").toString())) {
                // 支付成功
                resXml = "<xml>"
                        + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                payFlag = true;
            } else {
                resXml = "<xml>"
                        + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>"
                        + "</xml> ";
            }

            // 支付成功
            if (payFlag) {
                log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA----------------payFlag:"+payFlag);
                //支付成功后，商户操作处理
            }

            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            // 保存错误LOG
            log.error(e.getLocalizedMessage());
            // 返回登录信息错误信息
            return ApiResult.error("支付失败!");
        }
        // 返回处理成功信息以及课程列表
        return ApiResult.error("支付成功");
    }

    /**
     * description: 解析微信通知xml
     *
     * @param xml
     * @return
     * @author ex_yangxiaoyi
     * @see
     */
    private static Map<String,String> parseXmlToList2(String xml) {
        Map<String,String> retMap = new HashMap<String,String>();
        try {
            StringReader read = new StringReader(xml);
            // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            // 创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            // 通过输入源构造一个Document
            Document doc = (Document) sb.build(source);
            Element root = doc.getRootElement();// 指向根节点
            List<Element> es = root.getChildren();
            if (es != null && es.size() != 0) {
                for (Element element : es) {
                    retMap.put(element.getName(), element.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }





}
