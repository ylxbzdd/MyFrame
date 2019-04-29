package com.example.administrator.myframe.utils;

/**
 * //


 * Created by FengZi on 2017/6/5 10:06.
 */

public class DES {
//    static String key = "YUyujiHU";
//    private static DES des;
//
//    private DES() {
//        key= common.key;
//    }
//
//    public static String decrypt(String msg) {
//        if (des == null) {
//            des = new DES();
//        }
//        try {
//            return des.decrypt(msg, key);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    } public static String decrypt2(String msg) {
//        if (des == null) {
//            des = new DES();
//        }
//        try {
//            return des.decrypt(msg, common.shouquan_key);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//    public static String encrypt(String msg) {
//        if (des == null) {
//            des = new DES();
//        }
//        try {
//            byte[] encrypt = des.encrypt(msg, key);
//            return des.toHexString(encrypt);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//
//    //解密数据
//    private String decrypt(String message, String key) throws Exception {
//        byte[] bytesrc = convertHexString(message);
//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
//        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
//        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
//        byte[] retByte = cipher.doFinal(bytesrc);
//        return new String(retByte);
//    }
//
//    private byte[] encrypt(String message, String key)
//            throws Exception {
//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//
//        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
//
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
//        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
//        return cipher.doFinal(message.getBytes("UTF-8"));
//    }
//
//    private byte[] convertHexString(String ss) {
//        return Base64Utils.decode(ss);
//    }
//
//
//    public void main(String[] args) throws Exception {
//        String key = "12345678";
//        String value = "test1234 ";
//
//        System.out.println("加密数据:" + value);
//        String a = toHexString(encrypt(value, key));
//        System.out.println("加密后的数据为:" + a);
//        String b = decrypt(a, key);
//        System.out.println("解密后的数据:" + b);
//    }
//
//    private String toHexString(byte b[]) {
//        return Base64Utils.encode(b);
//    }
}
