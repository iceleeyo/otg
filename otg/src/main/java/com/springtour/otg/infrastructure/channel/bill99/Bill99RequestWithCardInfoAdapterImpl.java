/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

import com.springtour.otg.application.ApplicationEvents;
import com.springtour.otg.application.NotificationService;
import com.springtour.otg.application.ResponseProcedure;
import com.springtour.otg.application.exception.CannotMapIdentityTypeException;
import com.springtour.otg.domain.model.transaction.CardInfo;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.shared.IdentityType;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithCardInfoAdapter;
import com.springtour.otg.infrastructure.time.Clock;
import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.infrastructure.channel.StatusCodeTranslator;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;
import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import lombok.Setter;
import sun.misc.BASE64Encoder;

/**
 *
 */
public class Bill99RequestWithCardInfoAdapterImpl extends AbstractRequestWithCardInfoAdapter {

    //
    private Configurations configurations;
    private Clock clock;
    private NotificationService notificationService;
    @Setter
    private ApplicationEvents applicationEvents;
    private StatusCodeTranslator statusCodeTranslator;
    
    public Bill99RequestWithCardInfoAdapterImpl(String channelId) {
        super(channelId);
    }
    
    @Override
    public String request(Transaction transaction, CardInfo cardInfo) {
        try {
            Bill99KeyManager bill99KeyManager = new Bill99KeyManager();
            String key = bill99KeyManager.key(transaction.getMerchant().getKey());
            //创建发送数据
            String data = GenerateRequestData.generatePurchaseTr1XmlData(transaction, cardInfo, configurations.bill99NotifyUrl(), clock.now());

            //初始化安全数据传输
            HttpsURLConnection httpsURLConnection = this.initConnection(bill99KeyManager.merchantId(transaction.getMerchant().getKey()), key);

            //发送请求
            String res = this.postRequest(httpsURLConnection, data);

            //解析返回数据
            Map<String, String> resolvedData = ResolveResponseData.resolvePurchaseTr2PesponseData(res, transaction);
            
            if (Bill99Constants.CHARGED_SUCCESS.equals(resolvedData.get("RespCode"))) {
                //扣款成功，且后续不再发送异步通知
                LoggerFactory.getLogger().info("99Bill response success! ResponseCode = " + resolvedData.get("RespCode")
                        + " transactionNo = " + resolvedData.get("OrdId"));
                Notification notification = notificationService.receive(this.getChannel(), resolvedData);
                applicationEvents.notificationWasReceived(notification);
                return StatusCode.SUCCESS;
            } else if (!Bill99Constants.TX_WAITING.equals(resolvedData.get("RespCode"))) {
                //扣款失败，且后续不再发送异步通知
                LoggerFactory.getLogger().error("99Bill response failed! ResponseCode = " + resolvedData.get("RespCode")
                        + " transactionNo = " + resolvedData.get("OrdId"));
                return statusCodeTranslator.convertTo(resolvedData.get("RespCode"));
            } else {
                //目前暂不返回扣款结果，等待异步通知
                LoggerFactory.getLogger().info("99Bill response success! ResponseCode = " + resolvedData.get("RespCode")
                        + " transactionNo = " + resolvedData.get("OrdId"));
                return StatusCode.SUCCESS;
            }
        } catch (Exception ex) {
            LoggerFactory.getLogger().error(ex.getMessage(), ex);
        }
        return StatusCode.UNKNOWN_ERROR;
    }
    
    private HttpsURLConnection initConnection(String merchantCode, String key) throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(Bill99Constants.HTTPS_SECURITY_ALGORITHM);
        KeyStore ks = KeyStore.getInstance(Bill99Constants.KEY_TYPE);
        ks.load(this.getClass().getResourceAsStream(configurations.bill99PrivateKeyClasspathResourceName(merchantCode)), key.toCharArray());
        kmf.init(ks, key.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(Bill99Constants.HTTPS_SECURITY_ALGORITHM);
        tmf.init(ks);
        X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
        SSLContext ssl = SSLContext.getInstance(Bill99Constants.SSL_TYPE);
        SavingTrustManager trustManager = new SavingTrustManager(defaultTrustManager);
        ssl.init(kmf.getKeyManagers(), new TrustManager[]{trustManager}, new java.security.SecureRandom());
        SocketFactory sf = ssl.getSocketFactory();
        URL url = new URL(null, configurations.getBill99RequestUrl(), new sun.net.www.protocol.https.Handler());
        BASE64Encoder encoder = new BASE64Encoder();
        String authorization = "Basic " + encoder.encode((merchantCode + ":" + key).getBytes());
        
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setRequestProperty("Authorization", authorization);
        httpsURLConnection.setSSLSocketFactory((SSLSocketFactory) sf);
        
        return httpsURLConnection;
    }
    
    private String postRequest(HttpsURLConnection httpsURLConnection, String data) throws ProtocolException, IOException, Exception {
        //请求
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setDoInput(true);
        httpsURLConnection.setRequestMethod("POST");
        httpsURLConnection.setRequestProperty("Content-Type",
                "text/xml;charset=UTF-8");
        httpsURLConnection.setRequestProperty("SOAPAction",
                "http://WebXml.com.cn/getWeatherbyCityName");
        httpsURLConnection.setRequestProperty("User-Agent",
                "Jakarta Commons-HttpClient/3.1");
        httpsURLConnection.setConnectTimeout(1000 * 50);
        httpsURLConnection.setReadTimeout(1000 * 50);
        try {
            httpsURLConnection.connect();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    httpsURLConnection.getOutputStream()));
            
            out.write(data);
            out.flush();
            out.close();
            //应答
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpsURLConnection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            
            httpsURLConnection.disconnect();
            return sb.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            httpsURLConnection.disconnect();
        }
    }
    
    public void setConfigurations(Configurations configurations) {
        this.configurations = configurations;
    }
    
    public void setClock(Clock clock) {
        this.clock = clock;
    }
    
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    public void setStatusCodeTranslator(StatusCodeTranslator statusCodeTranslator) {
        this.statusCodeTranslator = statusCodeTranslator;
    }
    
    @Override
    public String dialect(IdentityType identityType) throws CannotMapIdentityTypeException {
        return Bill99IdentityTypeConverter.convert(identityType);
    }
}
