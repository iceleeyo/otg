package com.springtour.otg.infrastructure.channel.icbc;

import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

/*
 * 包装HttpClient对象使之能访问https
 */
public class WebClientDevWrapper {

	private URL keystoreUrl;

	private String keystorePassword;

	public WebClientDevWrapper(URL keystoreUrl, String keystorePassword) {
		this.keystoreUrl = keystoreUrl;
		this.keystorePassword = keystorePassword;
	}

	public KeyManager[] getKeyManagers() {
		try {
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(generateKeystore(), keystorePassword.toCharArray());
			return kmf.getKeyManagers();
		} catch (Exception e) {
			throw new IllegalArgumentException("cannot get KeyManagers", e);
		}
	}

	private KeyStore generateKeystore() throws Exception {
		KeyStore keystore = KeyStore.getInstance("jks");
		keystore.load(keystoreUrl.openStream(),
				keystorePassword != null ? keystorePassword.toCharArray()
						: null);
		return keystore;
	}

	public HttpClient wrapClient(HttpClient base) {
		try {

			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {

				@Override
				public void checkClientTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
					// TODO Auto-generated method stub

				}

				@Override
				public void checkServerTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
					// TODO Auto-generated method stub
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					X509Certificate[] chain = null;
					try {
						TrustManagerFactory tmf = TrustManagerFactory
								.getInstance("SunX509");
						tmf.init(generateKeystore());
						chain = ((X509TrustManager) tmf.getTrustManagers()[0])
								.getAcceptedIssuers();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					return chain;
				}
			};

			ctx.init(getKeyManagers(), new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = base.getConnectionManager();
			SchemeRegistry srRegistry = ccm.getSchemeRegistry();
			srRegistry.register(new Scheme("https", 443, ssf));
			return new DefaultHttpClient(ccm, base.getParams());
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
}
