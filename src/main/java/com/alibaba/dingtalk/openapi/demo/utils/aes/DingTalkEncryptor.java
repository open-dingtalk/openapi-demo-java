package com.alibaba.dingtalk.openapi.demo.utils.aes;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * 钉钉开放平台加解密方法
 * 在ORACLE官方网站下载JCE无限制权限策略文件
 *     JDK6的下载地址：http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html
 *     JDK7的下载地址： http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
 */
public class DingTalkEncryptor {

    private static final Charset CHARSET = Charset.forName("utf-8");
    private static final Base64         base64  = new Base64();
    private byte[]         aesKey;
    private String         token;
    private String         corpId;
    /**ask getPaddingBytes key固定长度**/
    private static final Integer AES_ENCODE_KEY_LENGTH = 43;
    /**加密随机字符串字节长度**/
    private static final Integer RANDOM_LENGTH = 16;

    /**
     * 构造函数
     * @param token             钉钉开放平台上，开发者设置的token
     * @param encodingAesKey  钉钉开放台上，开发者设置的EncodingAESKey
     * @param corpId           ISV进行配置的时候应该传对应套件的SUITE_KEY，普通企业是Corpid
     * @throws DingTalkEncryptException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public DingTalkEncryptor(String token, String encodingAesKey, String corpId) throws DingTalkEncryptException{
        if (null==encodingAesKey ||  encodingAesKey.length() != AES_ENCODE_KEY_LENGTH) {
            throw new DingTalkEncryptException(DingTalkEncryptException.AES_KEY_ILLEGAL);
        }
        this.token = token;
        this.corpId = corpId;
        aesKey = Base64.decodeBase64(encodingAesKey + "=");
    }

    /**
     * 将和钉钉开放平台同步的消息体加密,返回加密Map
     * @param plaintext     传递的消息体明文
     * @param timeStamp      时间戳
     * @param nonce           随机字符串
     * @return
     * @throws DingTalkEncryptException
     */
    public Map<String,String> getEncryptedMap(String plaintext, Long timeStamp, String nonce) throws DingTalkEncryptException {
        if(null==plaintext){
            throw new DingTalkEncryptException(DingTalkEncryptException.ENCRYPTION_PLAINTEXT_ILLEGAL);
        }
        if(null==timeStamp){
            throw new DingTalkEncryptException(DingTalkEncryptException.ENCRYPTION_TIMESTAMP_ILLEGAL);
        }
        if(null==nonce){
            throw new DingTalkEncryptException(DingTalkEncryptException.ENCRYPTION_NONCE_ILLEGAL);
        }
        // 加密
        String encrypt = encrypt(Utils.getRandomStr(RANDOM_LENGTH), plaintext);
        String signature = getSignature(token, String.valueOf(timeStamp), nonce, encrypt);
        Map<String,String> resultMap = new HashMap<String, String>();
        resultMap.put("msg_signature", signature);
        resultMap.put("encrypt", encrypt);
        resultMap.put("timeStamp", String.valueOf(timeStamp));
        resultMap.put("nonce", nonce);
        return  resultMap;
    }

    /**
     * 密文解密
     * @param msgSignature     签名串
     * @param timeStamp        时间戳
     * @param nonce             随机串
     * @param encryptMsg       密文
     * @return                  解密后的原文
     * @throws DingTalkEncryptException
     */
    public String getDecryptMsg(String msgSignature, String timeStamp, String nonce, String encryptMsg)throws DingTalkEncryptException {
        //校验签名
        String signature = getSignature(token, timeStamp, nonce, encryptMsg);
        if (!signature.equals(msgSignature)) {
            throw new DingTalkEncryptException(DingTalkEncryptException.COMPUTE_SIGNATURE_ERROR);
        }
        // 解密
        String result = decrypt(encryptMsg);
        return result;
    }


    /*
     * 对明文加密.
     * @param text 需要加密的明文
     * @return 加密后base64编码的字符串
     */
    private String encrypt(String random, String plaintext) throws DingTalkEncryptException {
        try {
            byte[] randomBytes = random.getBytes(CHARSET);
            byte[] plainTextBytes = plaintext.getBytes(CHARSET);
            byte[] lengthByte = Utils.int2Bytes(plainTextBytes.length);
            byte[] corpidBytes = corpId.getBytes(CHARSET);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            byteStream.write(randomBytes);
            byteStream.write(lengthByte);
            byteStream.write(plainTextBytes);
            byteStream.write(corpidBytes);
            byte[] padBytes = PKCS7Padding.getPaddingBytes(byteStream.size());
            byteStream.write(padBytes);
            byte[] unencrypted = byteStream.toByteArray();
            byteStream.close();
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] encrypted = cipher.doFinal(unencrypted);
            String result = base64.encodeToString(encrypted);
            return result;
        } catch (Exception e) {
            throw new DingTalkEncryptException(DingTalkEncryptException.COMPUTE_ENCRYPT_TEXT_ERROR);
        }
    }

    /*
     * 对密文进行解密.
     * @param text 需要解密的密文
     * @return 解密得到的明文
     */
    private String decrypt(String text) throws DingTalkEncryptException {
        byte[] originalArr;
        try {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            // 使用BASE64对密文进行解码
            byte[] encrypted = Base64.decodeBase64(text);
            // 解密
            originalArr = cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new DingTalkEncryptException(DingTalkEncryptException.COMPUTE_DECRYPT_TEXT_ERROR);
        }

        String plainText;
        String fromCorpid;
        try {
            // 去除补位字符
            byte[] bytes = PKCS7Padding.removePaddingBytes(originalArr);
            // 分离16位随机字符串,网络字节序和corpId
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
            int plainTextLegth = Utils.bytes2int(networkOrder);
            plainText = new String(Arrays.copyOfRange(bytes, 20, 20 + plainTextLegth), CHARSET);
            fromCorpid = new String(Arrays.copyOfRange(bytes, 20 + plainTextLegth, bytes.length), CHARSET);
        } catch (Exception e) {
            throw new DingTalkEncryptException(DingTalkEncryptException.COMPUTE_DECRYPT_TEXT_LENGTH_ERROR);
        }

        // corpid不相同的情况
        if (!fromCorpid.equals(corpId)) {
            throw new DingTalkEncryptException(DingTalkEncryptException.COMPUTE_DECRYPT_TEXT_CORPID_ERROR);
        }
        return plainText;
    }

    /**
     * 数字签名
     * @param token         isv token
     * @param timestamp     时间戳
     * @param nonce          随机串
     * @param encrypt       加密文本
     * @return
     * @throws DingTalkEncryptException
     */
    public String getSignature(String token, String timestamp, String nonce, String encrypt) throws DingTalkEncryptException {
        try {
            String[] array = new String[] { token, timestamp, nonce, encrypt };
            Arrays.sort(array);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            throw new DingTalkEncryptException(DingTalkEncryptException.COMPUTE_SIGNATURE_ERROR);
        }
    }

}
