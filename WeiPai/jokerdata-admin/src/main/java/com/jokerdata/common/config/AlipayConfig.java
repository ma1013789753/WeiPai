package com.jokerdata.common.config;


public class AlipayConfig {
    // 1.商户appid
    public static String APPID = "2018082261170084";
    
    //2.私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAimziXoU5m0Z8VGCZsP2Dy8H3IXBMqYMUyV1F2Vhgivj8X2hWSaQx2eqSOi+h3odmTr0denqMHVNXKWWmCj6OBZhYmzHsv8fh1rvya4FM+jBhjL9ChIkrTv2Z/zqWstwleaWF7Mo4UWGUzLBRO9TGAIeNcMmulWJBbhaT3jBO1bDOx//4Nu9s0WSzmFiEVqKoP3C0RzzU7Qdn8ulVg6xpU/jilYPD1UuQdPwu0bopuo25XGOQ9ZBoKGrD7gZHDiml37nL/5dKIE8Fa5kSTcdmbdh8qHKO/dT/ThLMbdW0R0bUxwJTEOXzFKk+/+SdwkVVskY2zF2taeTBDKAfM3wmZQIDAQAB";
    
    // 3.支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAovqfEURPMGSPbL/MMIWKin77B7neDUAQ+qBW0tnTnMmgVoNn36w/YKR3XpdWN/iVe17jCfI0CMbw9FunFTx35wgm7F04BA4J+n3/DhDDzmuPXmKCH9laHC92/029KkO69yqSXpYBtC6fZR7qJtNwgFrXLJJp3zJM/GocspJ0aYYkPSSAOtXfkAcpjD2tVOEb+13C9pl7AyawmdZL8jTOOwns0LIfOpNXFuKOYxs5eFpnZEGgiuMGbyXB7smGvEUbk96o+CzYfYCrL4SsGW75XC+tZvv5lley4X7cdPPHSKQTGoDDHQspWbGad3cT3CCSFNfoAN+ETEITc9dlrVy3HwIDAQAB";
    
    // 4.服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "";
    
     //5.页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "";
    
    // 6.请求支付宝的网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";    
    
    // 7.编码
    public static String CHARSET = "UTF-8";
    
    // 8.返回格式
    public static String FORMAT = "json";
    
    // 9.加密类型
    public static String SIGNTYPE = "RSA2";
    
}