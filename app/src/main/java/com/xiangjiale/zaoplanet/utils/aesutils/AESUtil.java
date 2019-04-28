package com.xiangjiale.zaoplanet.utils.aesutils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    // 加密
    public static String Encrypt(String sSrc, String sKey)  {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = new byte[0];
        try {
            raw = sKey.getBytes("utf-8");

        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        return new Base64().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

//    public static void main(String[] args)  {
//        // 密钥
////        String key = "XiangjialeMACAPP";
////        // 需要加密的字符串
////        String src = "123456";
////
////        System.out.println(src);
////        String ss= Encrypt(src,key);
////        // 加密
////        System.out.println("加密后的字串是：" + ss);
////
////        // 解密
////        String DeString = Decrypt("Jza7dHBBCUcpTMQPdFD62g==", key);
////        System.out.println("解密后的字串是：" + DeString);
//
////        finds();
////        emoticons();
//
//        String[] version1Array1 = "1.0.2".split(".");
//        String[] version1Array2 = "1.0.3".split(".");
//        a("1.0.1","1.0.3");
//        a("1.0.2","1.0.3");
//        a("1.0.3","1.0.3");
//        a("1.0.11","1.0.3");
//        a("1.10.2","1.0.3");
//        a("2.2.2","1.0.3");
//        System.out.println("1.0.1".compareTo("1.0.2")); //-2
//        System.out.println("1.0.2".compareTo("1.0.2")); //0
//        System.out.println("1.0.3".compareTo("1.0.2")); //1
//        System.out.println("1.0.11".compareTo("1.0.2"));// -1
//        System.out.println("1.1.0".compareTo("1.0.2")); //1
//        System.out.println("1.10.11".compareTo("1.10.12"));// 1
//        System.out.println("1.0.8".compareTo("1.0.2"));// 1
//    }


    public static  void  a(String s, String s1) {
        String[] version1Array1 = s.split("\\.");
        String[] version1Array2 = s1.split("\\.");

        if (version1Array1.length == version1Array2.length){
            for ( int i = 0 ; i<  version1Array1.length;i++){
                System.out.print("version1Array1 = "+version1Array1[i]);
                System.out.println( " ,"+version1Array2[i] );
                if (Integer.valueOf(version1Array1[i]) - Integer.valueOf(version1Array2[i]) >0){
                    System.out.println("true"); //-2
                    return;
                }
//                System.out.println("false"); //-2
            }
            System.out.println("false"); //-2
        }else{
            System.out.println("false"); //-2
        }
    }
    public static void emoticons() {
        String str = "[Smil],[微笑],Smil";
        Pattern pattern = Pattern.compile("\\[[a-zA-Z\\u4e00-\\u9fa5]+\\]");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
                System.out.println("Group 0:" + matcher.group());//得到第0组——整个匹配
        }
    }


        public static void finds() {
        String str = "@264978&我是谁,121323@123456&我是谁1,";
        Pattern pattern = Pattern.compile("[0-9]*&我是谁1[,]");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            Pattern pattern1 = Pattern.compile("[0-9]*");
            Matcher matcher2 = pattern1.matcher(matcher.group(0));
            if(matcher2.find()){
                System.out.println("Group 0:" + matcher2.group(0));//得到第0组——整个匹配
            }
        }
    }


}
