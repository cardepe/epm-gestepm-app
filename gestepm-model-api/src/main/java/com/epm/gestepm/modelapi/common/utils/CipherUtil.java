package com.epm.gestepm.modelapi.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtil {

    public static byte[] encryptMessage(byte[] message, byte[] keyBytes) {

        try {

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(message);

        } catch (Exception e) {
            // TODO: CHANGE
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decryptMessage(byte[] encryptedMessage, byte[] keyBytes) {

        try {

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(encryptedMessage);

        } catch (Exception e) {
            // TODO: CHANGE
            e.printStackTrace();
            return null;
        }
    }
}
