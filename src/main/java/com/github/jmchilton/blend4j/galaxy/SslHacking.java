package com.github.jmchilton.blend4j.galaxy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

public class SslHacking {
  public static void disableCertificateCheck() {
    javax.net.ssl.TrustManager x509 = new javax.net.ssl.X509TrustManager() {
      public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws java.security.cert.CertificateException {
        return;
      }

      public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws java.security.cert.CertificateException {
        return;
      }

      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null;
      }
    };
    SSLContext ctx = null;
    try {
      ctx = SSLContext.getInstance("SSL");
      ctx.init(null, new javax.net.ssl.TrustManager[] {x509}, null);
    } catch(java.security.GeneralSecurityException ex) {
    }


    // Create all-trusting host name verifier
    HostnameVerifier allHostsValid = new HostnameVerifier() {
      public boolean verify(String hostname, SSLSession session) {
        return true;
      }
    };
    // Install the all-trusting host verifier
    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
  }
}
