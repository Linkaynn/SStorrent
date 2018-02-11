package com.jeseromero.core.controller;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Linkaynn on 12/02/2017.
 * TorrentSearcher
 */
public class JSOUPController {

    public Document getHtmlDocument(String url) {

        Document doc = null;

	    try {
		    // Create a trust manager that does not validate certificate chains
		    TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				    return null;
			    }

			    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			    }

			    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			    }
		    } };

		    // Install the all-trusting trust manager
		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		    Connection.Response response = Jsoup
				    .connect(url)
				    .timeout(60000)
				    .method(Connection.Method.GET)
				    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0") //
				    .execute();

		    doc = response.parse();
	    } catch (Exception e) {
		    e.printStackTrace();
	    }

	    return doc;
    }
}
