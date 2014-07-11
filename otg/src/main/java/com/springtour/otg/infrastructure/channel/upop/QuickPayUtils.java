package com.springtour.otg.infrastructure.channel.upop;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class QuickPayUtils {

    /**
     * 生成发送银联报文页面
     * 
     * @param map
     * @param signature
     * @return
     */
    public String createPayHtml(String[] valueVo, String signType, String actionUrl, String key) {
        Map<String, String> map = new TreeMap<String, String>();
        for (int i = 0; i < QuickPayConf.reqVo.length; i++) {
            map.put(QuickPayConf.reqVo[i], valueVo[i]);
        }
        map.put("signature", signMap(map, key));
        map.put("signMethod", signType);
        String str = joinMapValue(map, '&');
        LoggerFactory.getLogger().info(actionUrl + "?" + str);
        String payForm = actionUrl + "?" + str;
        return payForm.substring(0, payForm.length() - 1);
        // String payForm = generateAutoSubmitForm(actionUrl, map);
        // return payForm;
    }

    public String createBackStr(String[] valueVo, String[] keyVo, String key) {

        Map<String, String> map = new TreeMap<String, String>();
        for (int i = 0; i < keyVo.length; i++) {
            map.put(keyVo[i], valueVo[i]);
        }
        map.put("signature", signMap(map, key));
        map.put("signMethod", QuickPayConf.signType);
        return joinMapValue(map, '&');
    }

    public static String[] getResArr(String str) {
        String regex = "(.*?cupReserved\\=)(\\{[^}]+\\})(.*)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);

        String reserved = "";
        if (matcher.find()) {
            reserved = matcher.group(2);
        }

        String result = str.replaceFirst(regex, "$1$3");
        String[] resArr = result.split("&");
        for (int i = 0; i < resArr.length; i++) {
            if ("cupReserved=".equals(resArr[i])) {
                resArr[i] += reserved;
            }
        }
        return resArr;
    }

    /**
     * 生成加密钥
     * 
     * @param map
     * @param secretKey 商城密钥
     * @return
     */
    private String signMap(Map<String, String> map, String key) {
        String strBeforeMd5 = joinMapValue(map, '&') + md5(key);
        return md5(strBeforeMd5);
    }

    /**
     * 查询验证签名
     * 
     * @param valueVo
     * @return 0:验证失败 1验证成功 2没有签名信息（报文格式不对）
     */
    public int checkSecurity(String[] valueVo, String key) {
        Map<String, String> map = new TreeMap<String, String>();
        for (int i = 0; i < valueVo.length; i++) {
            String[] keyValue = valueVo[i].split("=");
            map.put(keyValue[0], keyValue.length >= 2 ? valueVo[i].substring(keyValue[0].length() + 1) : "");
        }
        if ("".equals(map.get("signature"))) {
            return 2;
        }
        String signature = map.get("signature");
        boolean isValid = false;
        map.remove("signature");
        map.remove("signMethod");
        isValid = signature.equals(md5(joinMapValue(map, '&') + md5(key)));

        return (isValid ? 1 : 0);
    }

    private String joinMapValue(Map<String, String> map, char connector) {
        StringBuffer b = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            b.append(entry.getKey());
            b.append('=');
            if (entry.getValue() != null) {
                b.append(entry.getValue());
            }
            b.append(connector);
        }
        return b.toString();
    }

    /**
     * 验证签名
     * 
     * @param map
     * @param key 商城密钥
     * @return
     */
    public boolean checkSign(String[] valueVo, String signMethod, String signature, String key) {

        Map<String, String> map = new TreeMap<String, String>();
        for (int i = 0; i < QuickPayConf.notifyVo.length; i++) {
            map.put(QuickPayConf.notifyVo[i], valueVo[i]);
        }
        if (signature == null)
            return false;
        LoggerFactory.getLogger().info(">>>" + joinMapValue(map, '&') + md5(key));
        LoggerFactory.getLogger().info(">>>" + signature.equals(md5(joinMapValue(map, '&') + md5(key))));
        return signature.equals(md5(joinMapValue(map, '&') + md5(key)));

    }

    /**
     * get the md5 hash of a string
     * 
     * @param str
     * @return
     */
    private String md5(String str) {

        if (str == null) {
            return null;
        }

        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance(QuickPayConf.signType);
            messageDigest.reset();
            messageDigest.update(str.getBytes(QuickPayConf.charset));
        } catch (NoSuchAlgorithmException e) {

            return str;
        } catch (UnsupportedEncodingException e) {
            return str;
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    /**
     * Generate an form, auto submit data to the given <code>actionUrl</code>
     * 
     * @param actionUrl
     * @param paramMap
     * @return
     */
    private static String generateAutoSubmitForm(String actionUrl, Map<String, String> paramMap) {
        StringBuilder html = new StringBuilder();
        html.append("<script language=\"javascript\">window.onload=function(){document.pay_form.submit();}</script>\n");
        html.append("<form id=\"pay_form\" name=\"pay_form\" action=\"").append(actionUrl).append(
                "\" method=\"post\">\n");

        for (String key : paramMap.keySet()) {
            html.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key + "\" value=\"" + paramMap.get(key)
                    + "\">\n");
        }
        html.append("</form>\n");
        return html.toString();
    }

}
