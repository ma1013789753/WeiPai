package com.jokerdata.common.config;


public class AlipayConfig {
    // 1.商户appid
    public static String APPID = "2018082261170084";
    
    //2.私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCKbOJehTmbRnxUYJmw/YPLwfchcEypgxTJXUXZWGCK+PxfaFZJpDHZ6pI6L6Heh2ZOvR16eowdU1cpZaYKPo4FmFibMey/x+HWu/JrgUz6MGGMv0KEiStO/Zn/Opay3CV5pYXsyjhRYZTMsFE71MYAh41wya6VYkFuFpPeME7VsM7H//g272zRZLOYWIRWoqg/cLRHPNTtB2fy6VWDrGlT+OKVg8PVS5B0/C7Ruim6jblcY5D1kGgoasPuBkcOKaXfucv/l0ogTwVrmRJNx2Zt2Hyoco791P9OEsxt1bRHRtTHAlMQ5fMUqT7/5J3CRVWyRjbMXa1p5MEMoB8zfCZlAgMBAAECggEAD9zGW/p8TXTYTnr7YbLLZZAGl8hcoJgqy6Bqnqtn9MTvdqdoK+wq4kPlGdHXhL6TVLaLKDvezoV8WXwSeN4OPEnm7XHdJWTsVkUXVIZuocTliNFp9/IYCmc5PGwkVL6xdK0pGadaqMi72nDh/i81uV3PMXuLDAdEZC8mJrH2drousdodW4AxNpDORfgnEOLPNSlRyp080DGQ3nEleio2SJHcP8EzyMEEXvwMoVyAEttdy/bOl2VtUy8PJXwWbdOLkZ7cf7nxeuFss72Prd6vaNlGQ2SKnkDiNv+gHndsgKeeH6HXZKmyBehsPWlJ6fLMb7IpNCWP7Q2hV18K5ghTgQKBgQDR28nbfJuYMTv5X0WWZWbI1DmakBJtBA0+NKJJXXTtblHX7k2p6nnyd7W0+ZBeGuDPCnX6kb8pcNjnnLqVstCTqSxi1GjV09mB3a7sJBHbdE9oUzLWY5hAci7MPoqBmOtW3UbKHEdCeZ+tzVKxyitg5W2Ru+wQPxGjUrpRLdaOFQKBgQCo3F30LSabDzd+M6xtDgIf6gESoKHwAh8Cirxy6eRLVEv/whdeo9IOV8xi46V8Sf9ivcL1a4VFyVm9nrOHBcnJSyB2PQdHwHaeca5PfzBrj8WDb89d+ETGOdefcLCcSFzCuloCORlzdylmdP1A370FAlgI3PHrEjp1BnIrYt6bEQKBgQCBBhV8KYdsMdECx8174wonrTthSPTbK6OMvEWOv7PVCn+tN6luL2gcZ/dCtjFJelL/WBojHRqfuu7Qr66TvSW5s0hD1v5BgQd4bbhPHPoRp9fud0CJSSBhdxJIpqb3ePIwLMmKF4rEVwySiesuvfKfl08fS5+eeXg5pvJAWONR5QKBgDm6ZLBmgy09jYYqmkcKI/XPJSC+I76OqxVOYtCSX4bVQcolC6JCNTzMDUa9dsS/83xJTY5jfMN366Mkz1FYzDxx354sjQGdra/LQ4FQBTiwhSDDJ8yDZNezzKBogShcoMG9vanG5YmQ8mlhk3cLRm6g94HILzhjGfMKChwFB/9RAoGBAJfGrpLMGte1gT/xW36mJj+ZsePCLznflbD2AeDlVZgD7CohWfcUCVjc3tFhtn04hOntoZVZHP4AP1VWadxPU4GwWpb11dfUWntXbeo8vhM1hrC1ir3KjJM4yiGfpksVj3oW9TYVh9B108vXuHtu26xY+JF1hDH+GEX24gQbWAZj";
    
    // 3.支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAovqfEURPMGSPbL/MMIWKin77B7neDUAQ+qBW0tnTnMmgVoNn36w/YKR3XpdWN/iVe17jCfI0CMbw9FunFTx35wgm7F04BA4J+n3/DhDDzmuPXmKCH9laHC92/029KkO69yqSXpYBtC6fZR7qJtNwgFrXLJJp3zJM/GocspJ0aYYkPSSAOtXfkAcpjD2tVOEb+13C9pl7AyawmdZL8jTOOwns0LIfOpNXFuKOYxs5eFpnZEGgiuMGbyXB7smGvEUbk96o+CzYfYCrL4SsGW75XC+tZvv5lley4X7cdPPHSKQTGoDDHQspWbGad3cT3CCSFNfoAN+ETEITc9dlrVy3HwIDAQAB";
    
    // 4.服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://youdianshare.com/index.php/Api/Notify/alipay_notifyurl";
    
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