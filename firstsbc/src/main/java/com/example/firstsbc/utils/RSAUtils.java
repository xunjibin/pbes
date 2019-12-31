package com.example.firstsbc.utils;

import javax.crypto.Cipher;


import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

public class RSAUtils {

    private RSAUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 生成公钥和私钥
     *
     * @throws NoSuchAlgorithmException
     */
    public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException {
        HashMap<String, Object> map = new HashMap<>();
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        map.put("public", publicKey);
        map.put("private", privateKey);
        return map;
    }

    /**
     * 使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {


            return null;
        }
    }

    /**
     * 使用模和指数生成RSA私钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus, 16);
            BigInteger b2 = new BigInteger(exponent, 16);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {


            return null;
        }
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 模长
        int keyLen = publicKey.getModulus().bitLength() / 8;
        // 加密数据长度 <= 模长-11
        String[] datas = splitString(data, keyLen - 11);
        String mi = "";
        //如果明文长度大于模长-11则要分组加密
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes()));
        }
        return mi;
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //模长
        int keyLen = privateKey.getModulus().bitLength() / 8;
        byte[] bytes = data.getBytes();
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        //如果密文长度大于模长则要分组解密
        String ming = "";
        byte[][] arrays = splitArray(bcd, keyLen);
        for (byte[] arr : arrays) {
            ming += new String(cipher.doFinal(arr));
        }
        return ming;
    }

    /**
     * ASCII码转BCD码
     */
    public static byte[] ASCII_To_BCD(byte[] ascii, int ascLen) {
        byte[] bcd = new byte[ascLen / 2];
        int j = 0;
        for (int i = 0; i < (ascLen + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            //  bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
            bcd[i] = (byte) (((j >= ascLen) ? 0x00 : (asc_to_bcd(ascii[j++]) & 0xff)) + (bcd[i] << 4));
        }
        return bcd;
    }

    public static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9')) {
            bcd = (byte) (asc - '0');
        } else if ((asc >= 'A') && (asc <= 'F')) {
            bcd = (byte) (asc - 'A' + 10);
        } else if ((asc >= 'a') && (asc <= 'f')) {
            bcd = (byte) (asc - 'a' + 10);
        } else {
            bcd = (byte) (asc - 48);
        }
        return bcd;
    }

    /**
     * BCD转字符串
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 拆分字符串
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * 拆分数组
     */
    public static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }


    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        HashMap<String, Object> map = RSAUtils.getKeys();
        //生成公钥和私钥
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");

        //模 公钥私钥模相等  一般为10001？
        String modulus = publicKey.getModulus().toString();
        //公钥指数
        String public_exponent = publicKey.getPublicExponent().toString();
        //私钥指数
        String private_exponent = privateKey.getPrivateExponent().toString();
        //明文
        String ming = "012";
        //使用模和指数生成公钥和私钥
        RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, public_exponent);
        // RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, private_exponent);
        RSAPrivateKey priKey = RSAUtils.getPrivateKey(publicKey.getModulus().toString(16), privateKey.getPrivateExponent().toString(16));
        //加密后的密文
        String mi = RSAUtils.encryptByPublicKey(ming, publicKey);
        System.err.println("mi:" + mi);

        System.err.println("modulus:" + modulus);
        System.err.println("moduluspri:" + privateKey.getModulus().toString());
        System.err.println("public_exponent:" + public_exponent);
        System.err.println("private_exponent:" + private_exponent);

        System.err.println("modulus16:" + publicKey.getModulus().toString(16));
        System.err.println("public_exponent16:" + publicKey.getPublicExponent().toString(16));
        System.err.println("private_exponent16:" + privateKey.getPrivateExponent().toString(16));
        System.out.println(privateKey.getPrivateExponent().equals(priKey.getPrivateExponent()));
        System.out.println(privateKey.getModulus().equals(priKey.getModulus()));
        //解密后的明文
        ming = RSAUtils.decryptByPrivateKey(mi, privateKey);
        System.err.println(ming);
        String ming1 = RSAUtils.decryptByPrivateKey(mi, priKey);
        System.err.println(ming1);
        //用模和指数生成的私钥用16进制处理了。
        /*RSAPrivateKey priKey = RSAUtils.getPrivateKey("b82089a38d6dc2445b4187d07940c31b3c08000cfb4178868110dfa7224e148dbe0968966d7a7a7490017cf7a0a139d21e7bb491fdb91b5da2d9a8efba4242d31e5245e4a3a5a17ee085e5f4007b9905d150fce9f35f971e26d748e2472e667965289a7377e7208efb192b8f553ae4ff35ba1bca7d52d8cdbecaf8376bca7bc7", "9199a93d9be8df660e839352578fceb90a8f88fabaf4daca01e5c5f5f43aaa262bd064d40f5e7ad711c91b78f59c888c8f6e632bcee741ac99ca64bcad1d18995dbf15a52f3f8265ced4a88f98904dabb5db7062f3199f80fe32bdadddda8fff5356a69bd3921391685c15d5f175591753db399ee40cc5980e45dbf6862a3c81");
        String mingcs = RSAUtils.decryptByPrivateKey("865F975870468A7BC58CE1FC1FE26E6EAE949CC585552DC86BB0E10E6C071C9985DD6E4698B343AA0F22550434BBACBE9933572FADE47ACDB7FD370F64A215745BB547E3D5F9B8F4DF2B08F466BBD65BDD459753EF3652873D28D5DB1B962DD11FCA79060B14B79D41096972FEA1504BD9115CF587F14ADEBBB22291228956A7", priKey);
        System.err.println("mingcs"+mingcs);    */
    }


}
