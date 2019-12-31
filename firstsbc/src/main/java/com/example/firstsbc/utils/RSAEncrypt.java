package com.example.firstsbc.utils;

/**
 * @description:
 * @author: pengbin
 * @createDate: 2019/12/23
 * @version: 1.0
 */


import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAEncrypt {
    /**
     * 用于封装随机产生的公钥与私钥
     */
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();
    public static String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMIFRzKzB+S1qAuPKWgQtb2iS/Is1d8y9H1G7Pggt+LiHLoVi+RJ9o+nKpFt3puii+ADtuZCAnZALsENxbS3gnrkTeJfa+v8eKPhCjJeAgoQheylFHygZOvbDaVbe4I08n+zxpmRs2sjdaa+OwQcXlujll1IL9niq2F3oWPg7/SwIDAQAB";
    public static String prikey ="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIwgVHMrMH5LWoC48paBC1vaJL8izV3zL0fUbs+CC34uIcuhWL5En2j6cqkW3em6KL4AO25kICdkAuwQ3FtLeCeuRN4l9r6/x4o+EKMl4CChCF7KUUfKBk69sNpVt7gjTyf7PGmZGzayN1pr47BBxeW6OWXUgv2eKrYXehY+Dv9LAgMBAAECgYBx4PBg7I+rg/fYNvZHpDYj/sPwZgs6uCELojQwBgLrF/i1MboaX1CiEy/OBVxxz62+xBeInmyXNqs9SHKc61eIxikVzmmptDj4vAz4lNMJRBTWJvLLQUm2Q4kDHSbDkG3acNTmsSLgGaTyFggTUNRy99lrmkL3Qg64639RITbKAQJBAOboVtxW8JVB3F+RaBi4OaYZOHlUrMetLvE/Im/sfmxAlO2sqaZm9xJ+drrgOvuP/VJf0YDfi4NXFWSj/NWmCEsCQQCbWod0E/WIM+pqr3JWQpK9l8fMFPp3W7Kmp27Ri2khZPP35GArGxoiqAjE4Ml5rg6u77w7Yczz45+6AuXWDoUBAkEAqAJjuRzVHnT2aN7+9AWPhKiTK80NjpgXMNe0MeXMsu1mr2zTX81Jdr5nm1+t6v6A3p/zaya8d0s0uK6NN9L/8wJAa/Ea7KxxjZGE71u4NfdLvjAXwtVQsl4kY2cyuKYFBg7+9N7ZcxoBrtLSRPlrBADMcwBf6WKq5UrjUHty9qhgAQJAcVmHmWj+IMoF4haqlA6nxy5lcrlzA93tCeL0Q7AWCzFe2I1xJSjOD1SuKzjtb45u2Fl/l7QrfupAaV4LbSwvhQ==";

    public static void main(String[] args) throws Exception {
         //生成公钥和私钥
        genKeyPair();
        //加密字符串
        String message = "df723820";
        System.out.println("随机生成的公钥为:" + keyMap.get(0));
        System.out.println("随机生成的私钥为:" + keyMap.get(1));
        String messageEn = encrypt(message,pubkey);
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn,prikey);
        System.out.println("还原后的字符串为:" + messageDe);
    }

    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        //0表示公钥
        keyMap.put(0,publicKeyString);
        //1表示私钥
        keyMap.put(1,privateKeyString);
    }
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
       byte[] decoded = Base64.decodeBase64(publicKey);

       // byte[] decoded = (new BASE64Decoder()).decodeBuffer(publicKey); // 正确方式
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
    //字符串转私钥
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //字符串转公钥
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
}


