package com.springtour.otg.infrastructure.channel.chinapnr;

import com.springtour.otg.application.exception.CannotMapIdentityTypeException;
import com.springtour.otg.domain.shared.IdentityType;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import chinapnr.SecureLink;
import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.CardInfo;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithCardInfoAdapter;
import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.infrastructure.channel.StatusCodeTranslator;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

/**
 * 
 * @author jker
 * 
 *         2011-5-7
 */
public class ChinapnrRequestWithCardInfoAdapterImpl extends AbstractRequestWithCardInfoAdapter {

    public ChinapnrRequestWithCardInfoAdapterImpl(String channelId) {
        super(channelId);
    }

    @Override
    public String dialect(IdentityType identityType) throws CannotMapIdentityTypeException {
        return ChinapnrIdentityTypeConverter.convert(identityType);
    }

    /**
     * 发送get请求到汇付天下语音支付平台
     * 
     */
    public String request(Transaction transaction, String encrytedCardInfo) throws CannotLaunchSecurityProcedureException {
        String signature = sign(transaction);
        List<NameValuePair> nvps = assembleToNvps(transaction, signature,
                encrytedCardInfo);
        return httpGet(nvps);
    }

    @Override
    public String request(Transaction transaction, CardInfo cardInfo) throws CannotMapIdentityTypeException, CannotLaunchSecurityProcedureException {
        return parseStatusCode(request(transaction, encrypt(cardInfo)));
    }

    /**
     * 解析返回数据
     * 
     * @param text
     * @return
     */
    private String analyzingSynchronousResults(String text) {
        StringTokenizer st = new StringTokenizer(text, "=");
        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            if (str instanceof String && str.contains("RespCode")) {
                return st.nextToken().substring(0, 2);
            }
        }
        return null;

    }

    private String httpGet(List<NameValuePair> nvps) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            URI uri = URIUtils.createURI(configurations.getChinapnrRequestScheme(),
                    configurations.getChinapnrRequestHost(), Integer.valueOf(configurations.getChinapnrRequestPort()),
                    configurations.getChinapnrRequestPath(), URLEncodedUtils.format(nvps, "UTF-8"), null);
            HttpGet httpget = new HttpGet(uri);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String statusCodeDialect = analyzingSynchronousResults(EntityUtils.toString(entity));
            LoggerFactory.getLogger().info("同步响应结果:   " + statusCodeDialect);
            return statusCodeDialect;
        } catch (IOException e) {
            LoggerFactory.getLogger().error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            LoggerFactory.getLogger().error(e.getMessage(), e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return null;
    }

    private String parseStatusCode(String chinapnrResultCode) {
        if (ChinapnrConstants.CHARGED_SUCCESS.equals(chinapnrResultCode)
                || ChinapnrConstants.PENDING.equals(chinapnrResultCode)) {
            return StatusCode.SUCCESS;
        } else {
            return statusCodeTranslator.convertTo(chinapnrResultCode);
        }
    }

    private List<NameValuePair> assembleToNvps(Transaction transaction,
            String signature, String encryptedCardInfo) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("Version", ChinapnrConstants.VERSION));
        nvps.add(new BasicNameValuePair("CmdId", ChinapnrConstants.COMMAND_TELPAY));
        nvps.add(new BasicNameValuePair("MerId", transaction.getMerchantCode().trim()));
        nvps.add(new BasicNameValuePair("OrdId", transaction.getTransactionNo().getNumber().trim()));
        nvps.add(new BasicNameValuePair("OrdAmt", String.valueOf(transaction.getAmount().getAmount())));
        nvps.add(new BasicNameValuePair("GateId", transaction.gatewayChannelDialect().trim()));
        nvps.add(new BasicNameValuePair("BgRetUrl", configurations.chinapnrNotifyUrl().trim()));
        nvps.add(new BasicNameValuePair("CardInfo", encryptedCardInfo.trim()));
        nvps.add(new BasicNameValuePair("ChkValue", signature.trim()));
        return nvps;
    }

    /**
     * 需要加密的证件信息数据项和格式为： 
     * CardNo + "|" + ValidDate + "|" + Cvv2 + "|" + IdType + "|" + IdNo + "|" + Name
     * @param cardInfo
     * @return
     */
    private String encrypt(CardInfo cardInfo) throws CannotMapIdentityTypeException, CannotLaunchSecurityProcedureException {
        try {
            SecureLink link = new SecureLink();
            final String keyfilePath = this.getClass().getResource(configurations.chinapnrPublicKeyClasspathResourceName()).getPath();
            StringBuilder cardInfoToEncrypt = new StringBuilder();
            String idType = ChinapnrIdentityTypeConverter.convert(cardInfo.getCardHolder().getIdType());
            cardInfoToEncrypt.append(cardInfo.getCardNo()).append("|").append(cardInfo.getExpireDate()).append("|").append(cardInfo.getCvv2()).append("|").append(idType).append("|").append(cardInfo.getCardHolderIdNo()).append("|").append(cardInfo.getCardHolderFullname());
            link.EncOrderMsg(keyfilePath, cardInfoToEncrypt.toString());
            return link.getEncMsg().trim();
        } catch (Exception e) {
            throw new CannotLaunchSecurityProcedureException(e.getMessage(), e.getCause());
        }
    }

    private String sign(Transaction transaction) throws CannotLaunchSecurityProcedureException {
        try {
            SecureLink link = new SecureLink();
            StringBuilder sb = new StringBuilder();
            sb.append(ChinapnrConstants.VERSION).append(ChinapnrConstants.COMMAND_TELPAY).append(transaction.getMerchantCode()).append(transaction.getTransactionNo().getNumber()).append(
                    String.valueOf(transaction.getAmount().getAmount())).append(
                    transaction.gatewayChannelDialect()).append(configurations.chinapnrNotifyUrl());
            final Merchant merchant = transaction.getMerchant();
            String keyfilePath = this.getClass().getResource(
                    configurations.chinapnrPrivateKeyClasspathResourceName(merchant.getKey())).getPath();
            link.SignMsg(transaction.getMerchantCode(), keyfilePath, sb.toString());
            return link.getChkValue();
        } catch (Exception e) {
            throw new CannotLaunchSecurityProcedureException(e.getMessage(), e.getCause());
        }
    }
    private Configurations configurations;
    private StatusCodeTranslator statusCodeTranslator;

    public void setStatusCodeTranslator(StatusCodeTranslator statusCodeTranslator) {
        this.statusCodeTranslator = statusCodeTranslator;
    }

    public void setConfigurations(Configurations configurations) {
        this.configurations = configurations;
    }
}
