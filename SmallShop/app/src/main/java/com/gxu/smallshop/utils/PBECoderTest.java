package com.gxu.smallshop.utils;


/**
 *
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
public class PBECoderTest {

    public static void main(String []args) throws Exception {
        String inputStr = "abc";
        System.err.println("原文: " + inputStr);
        byte[] input = inputStr.getBytes();

        String pwd = "efg";
        System.err.println("密码: " + pwd);

        byte[] salt = PBECoder.initSalt();

        byte[] data = PBECoder.encrypt(input, pwd, salt);

        System.err.println("加密后: " + PBECoder.encryptBASE64(data));
        System.err.println("加密后: " + PBECoder.encryptBASE64(PBECoder.encrypt(input, pwd, PBECoder.initSalt())));

        byte[] output = PBECoder.decrypt(data, pwd, salt);
        String outputStr = new String(output);

        System.err.println("解密后: " + outputStr);
    }

}  