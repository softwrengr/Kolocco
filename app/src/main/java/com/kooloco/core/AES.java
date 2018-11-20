package com.kooloco.core;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import okio.ByteString;

/**
 * Created by hlink21 on 28/4/17.
 */
@Singleton
public class AES {

    private byte[] arrKey;
    private byte[] ivKey;


    @Inject
    public AES(@Named("aes-key") String key) {

        try {
            // arrKey = Base64.decode(key, Base64.DEFAULT);
            arrKey = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            arrKey = key.getBytes();
            e.printStackTrace();
        }
        ivKey = new byte[16];
    }

    public String encrypt(String plainText) {
        try {
            if (plainText != null) {
                byte[] plainTextBytes = plainText.getBytes("UTF-8");
                return new String(encrypt(plainTextBytes));
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public byte[] encrypt(byte[] plainTextBytes) {

        try {

            System.arraycopy(arrKey, 0, ivKey, 0, 16);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(arrKey, "AES"), new IvParameterSpec(ivKey));

            int l = plainTextBytes.length;
            int r = l % 16;
            int n = l - r + 16;
            byte[] enc = cipher.doFinal(Arrays.copyOf(plainTextBytes, n));
            return ByteString.of(enc).base64().getBytes();

        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException
                | InvalidAlgorithmParameterException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String decrypt(String cypherText) {

        try {
            return new String(decrypt(cypherText.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] decrypt(byte[] cypherText) {

        try {
            if (cypherText != null) {
                System.arraycopy(arrKey, 0, ivKey, 0, 16);
                Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(arrKey, "AES")
                        , new IvParameterSpec(ivKey));
                byte[] bytes = Base64.decode(cypherText, Base64.DEFAULT);
                if (bytes != null) {
                    byte[] enc = cipher.doFinal(bytes);
                    return enc;
                }
            }

        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | InvalidAlgorithmParameterException
                | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }


}
