package com.jokerdata.jokerdatapassageway.common.uitls;

import javax.net.ssl.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpsUtils {

    public static SSLSocketFactory getSslSocketFactory(){
        SSLContext sslContext=null;
        try {
            sslContext= SSLContext.getInstance("TLS");
            // 指定信任https
            sslContext.init(null, new TrustManager[] { new UnSafeTrustManager() }, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext.getSocketFactory();
    }

    public static HostnameVerifier getTrustAnyHostnameVerifier(){
        return new UnSafeHostnameVerifier();
    }


    public static class UnSafeTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class UnSafeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
