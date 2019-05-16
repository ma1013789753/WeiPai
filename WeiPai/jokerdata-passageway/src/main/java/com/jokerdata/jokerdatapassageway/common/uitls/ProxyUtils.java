package com.jokerdata.jokerdatapassageway.common.uitls;

import lombok.extern.slf4j.Slf4j;
import sun.net.www.protocol.https.Handler;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * @ClassName ProxyUtils
 * @Author Albert
 * @Description //代理IP验证
 * @Date 2019/5/6 16:05
 **/
@Slf4j
public final class ProxyUtils {
    private static final String VALIDATE_URL = "http://www.baidu.com/";

    public static boolean validateIp(String ip, int port, ProxyType proxyType) {
        boolean available = false;
        if (proxyType.getType().equalsIgnoreCase("http")) {
            available = validateHttp(ip, port);
        } else if (proxyType.getType().equalsIgnoreCase("https")) {
            available = validateHttps(ip, port);
        }
        return available;
    }

    public static boolean validateHttp(String ip, int port) {
        boolean available = false;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(VALIDATE_URL);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            connection = (HttpURLConnection) url.openConnection(proxy);
            connection.setRequestProperty("accept", "");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:66.0) Gecko/20100101 Firefox/66.0");
            connection.setConnectTimeout(2 * 1000);
            connection.setReadTimeout(3 * 1000);
            connection.setInstanceFollowRedirects(false);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s = null;
            StringBuilder sb = new StringBuilder();
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            if (sb.toString().contains("baidu.com") && connection.getResponseCode() == 200) {
                available = true;
            }
            log.info("validateHttp ==> ip:{} port:{} info:{}", ip, port, connection.getResponseMessage());
        } catch (Exception e) {
            available = false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return available;
    }

    public static boolean validateHttps(String ip, int port) {
        boolean available = false;
        HttpsURLConnection httpsURLConnection = null;
        try {
            URL url = new URL(null, VALIDATE_URL, new Handler());
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            httpsURLConnection = (HttpsURLConnection) url.openConnection(proxy);
            httpsURLConnection.setSSLSocketFactory(HttpsUtils.getSslSocketFactory());
            httpsURLConnection.setHostnameVerifier(HttpsUtils.getTrustAnyHostnameVerifier());
            httpsURLConnection.setRequestProperty("accept", "");
            httpsURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpsURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:66.0) Gecko/20100101 Firefox/66.0");
            httpsURLConnection.setConnectTimeout(2 * 1000);
            httpsURLConnection.setReadTimeout(3 * 1000);
            httpsURLConnection.setInstanceFollowRedirects(false);
            BufferedReader br = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
            String s = null;
            StringBuilder sb = new StringBuilder();
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            if (sb.toString().contains("baidu.com") && httpsURLConnection.getResponseCode() == 200) {
                available = true;
            }
            log.info("validateHttps ==> ip:{} port:{} info:{}", ip, port, httpsURLConnection.getResponseMessage());
        } catch (Exception e) {
            available = false;
        } finally {
            if (httpsURLConnection != null) {
                httpsURLConnection.disconnect();
            }
        }
        return available;
    }

    public enum ProxyType {
        HTTP("HTTP"),
        HTTPS("HTTPS");
        private String type;

        ProxyType(String proxyType) {
            this.type = proxyType;
        }

        public String getType() {
            return type;
        }
    }
}