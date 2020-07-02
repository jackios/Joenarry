package com.cs2c.common.exception.file;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

    public class rsacode {

    private  Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
    public rsacode() {
        keyMap.put(0,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnBUXlQTb8UIQLeXhjj+IpsXZJsJ8SdChFuurrnq4R6UxY9UFhgBBpNivFmmtb1yVkYMRbCGF10mDH69jcu"
                + "jGLWUixA6QRqc0CczdnC7BI3gQOv7LLgl8PaeKyeMnCK+9zQckwvuywUOcJ2C8/LwGoyNZ4wrVbEnwDeapFT9M+xAqGBVfW/g93DRyrWZ6n/umPS97z6QXLn6Yyn2fyjB"
                + "dwW2M8cFw5GhMdgyObPfywJLV4VbgIV2EyLJJHz/vn4EtFs6BN9gAXlcJnaLhobSJw6E+WqRdWiQMcHatgVCDzeMXR/+k6Mwy8Pc7VaOxXbrusB+YNbV6V1OgFBOV7rT8"
                + "9wIDAQAB");  //0表示公钥
        keyMap.put(1,"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCcFReVBNvxQhAt5eGOP4imxdkmwnxJ0KEW66uuerhHpTFj1QWGAEGk2K8Waa1vXJWRgxFsIYXXSY"
                + "Mfr2Ny6MYtZSLEDpBGpzQJzN2cLsEjeBA6/ssuCXw9p4rJ4ycIr73NByTC+7LBQ5wnYLz8vAajI1njCtVsSfAN5qkVP0z7ECoYFV9b+D3cNHKtZnqf+6Y9L3vPpBcufpj"
                + "KfZ/KMF3BbYzxwXDkaEx2DI5s9/LAktXhVuAhXYTIskkfP++fgS0WzoE32ABeVwmdouGhtInDoT5apF1aJAxwdq2BUIPN4xdH/6TozDLw9ztVo7Fduu6wH5g1tXpXU6AU"
                + "E5XutPz3AgMBAAECggEARTFAhApkan6L55z6MfJOZQhebMVZDewUDbIqKK40lWZRD922liH099wrOjdc8HpNv/UnuxTdXYQ0Qc6huqYgfK1SDTIbmYa0Fggg0gUUD+JA5"
                + "7ZeYilIT5i/eSJfAoXOEV8yV9H2+6A6UGadQTklhBq+1VzPIjNg3Pi9SjMGsGFVYl6VoieqzD/GftSuU8ATWUnt2LqTZKIoVxtlby5NZ2NaDJ4M5ZOslnbRG8eBwrFWVc"
                + "V9lyRowpiYip6m0MX7MYLLKezb9MElcGMiEWXNmnxzTjnX0ZBDcQU3kq2/jh//JL/jRCq8sEtBFZvEL4CX94lTWc7MhBfq2k280NIZeQKBgQDsPFga/xi8dFCgwE8QVB3"
                + "wr8KsdNTbm975c1/0/GKtn7BWB1NrwqFxnEtoDbVufuxvJDPu5EkhzlC3sED05UkX5BpZ6PhENx17MKabXqDTyudCRZBPEJb7+Egp8MYejituENDP+WuZ4UUIOuT8FqGc"
                + "kg9ZHQjVX2qfIKLiz2eYEwKBgQCpJAmuIRoldW/atvPA9ljZSeHwmDsKRUoConKUJP7TEnHNT3YavbDgqjsIuZt7PZQHdZR5oD563a3SSwIaiHrkSj1DWUV0rYmQGqDXr"
                + "prKdKsYWjiCLmAtuJi9a/YYoBex6iuZCv4T9q5Is99JNhp/U/gzcAe1TktL2Lo4vlUsDQKBgQCkWU8KaRKyeVFmpKPcvkieHAkZpaY4qnYzoVrOu4+DLyekkjAtNhjPS2"
                + "cCmxbwKokewVcICfX++OQbBxoJxkJVxrG2f2fJPXbbL3uZd/n3np/I8VB2URpZbd/2nbWXqFz6k+RR7Do5sgkuspp/7ta8cLkQzCxLFbi9wBzSImz11QKBgEDEKuzBgii"
                + "L0nv2iFoAOr9heiHNuPXS2Gcv2x39GJKsQmVyou/UaRFuPdK2d+H2D2PX0zXLdYFIt1GKJVERp7UY9GKAoYbvGHjUqXhNRLp3eDiH+5FlvrBjOiF2YLLHQqt+1pjwy5/L"
                + "Wqs+LSb0j6rn9WeE4366bzyozY4C28xVAoGAM0eVIVFAdMnZoZSFElMACHgALH+dy7X5SygmlJ4pDmnDiAlGH6GSbHwkLToZ5XTWY68xqZAYFkSu24kCFzOKlhzbAKE3W"
                + "gg2QXLsmzsJ1+jtz8j690tHqQh/MHyC72CBajPzEPdfDdmuX+MgAt43hg+kgLz5k9pCb+TpB/c+DOk=");  //1表示私钥
    }
    
    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        rsacode rsa = new rsacode();
        //rsa.genKeyPair();
        //加密字符串
        String message = "qwe123,";
        System.out.println("随机生成的公钥为:" + rsa.keyMap.get(0));
        System.out.println("随机生成的私钥为:" + rsa.keyMap.get(1));
        String messageEn = rsa.encrypt(message,rsa.keyMap.get(0));
        System.out.println(message + "\n加密后的字符串为:" + messageEn);
        String messageDe = rsa.decrypt(messageEn);
        System.out.println("还原后的字符串为:" + messageDe);
    }

    /** 
     * 随机生成密钥对 
     * @throws NoSuchAlgorithmException 
     */  
    public void genKeyPair() throws NoSuchAlgorithmException {  
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
        // 初始化密钥对生成器，密钥大小为96-1024位  
        keyPairGen.initialize(1024,new SecureRandom());  
        // 生成一个密钥对，保存在keyPair中  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥  
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));  
        // 得到私钥字符串  
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));  
        // 将公钥和私钥保存到Map
        keyMap.put(0,publicKeyString);  //0表示公钥
        keyMap.put(1,privateKeyString);  //1表示私钥
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
    public String encrypt( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
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
    public String decrypt(String str) throws Exception{
        //System.out.println("dec 1");
        String privateKey = keyMap.get(1);
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



}
    

    