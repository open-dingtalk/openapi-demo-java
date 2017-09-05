package com.alibaba.dingtalk.openapi.demo.utils.aes;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 钉钉jsapi签名工具类
 */
public class DingTalkJsApiSingnature {
    /**
     * 获取jsapi签名
     * @param url
     * @param nonce
     * @param timeStamp
     * @param jsTicket
     * @return
     * @throws DingTalkEncryptException
     */
    public static String getJsApiSingnature(String url,String nonce,Long timeStamp,String jsTicket) throws DingTalkEncryptException{
        String plainTex = "jsapi_ticket=" + jsTicket +"&noncestr=" + nonce +"&timestamp=" + timeStamp + "&url=" + url;
        System.out.println(plainTex);
        String signature = "";
        try{
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(plainTex.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            return signature;
        }catch (Exception e){
            throw new DingTalkEncryptException(DingTalkEncryptException.COMPUTE_SIGNATURE_ERROR);
        }
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash){
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


    public static void main(String args[]) throws Exception{



                // signature:810e6657e9f411e6491b3e97dfaf7660e89eb874,serverSign:0e781e79966d6f27e2b6456b83d5cee0ebaeb81b
        String url="http://10.62.53.138:3000/jsapi";
        String nonce="abcdefgh";
        Long timeStamp = 1437027269927L;
        String tikcet="zHoQdGJuH0ZDebwo7sLqLzHGUueLmkWCC4RycYgkuvDu3eoROgN5qhwnQLgfzwEXtuR9SDzh6BdhyVngzAjrxV";
        System.err.println(getJsApiSingnature(url,nonce,timeStamp,tikcet));
    }
}
