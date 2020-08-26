package com.example.chattingapp;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESDecrypt {
    //Membuat variabel
    //Membuat byte kunci ( array )
    private byte encryptionKey[] = {9,115,51,86,105,4,-31,-33,-68,88,17,20,3,-105,119,-53};
    private Cipher decipher;
    private SecretKeySpec secretKeySpec;

    public String AESDEcryptt (String string) throws UnsupportedEncodingException {
        try {
            //Mendeklarasikan bahwa variabel decipher sebuah object Cipher dengan menggunakan metode static getInstance()
            decipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        //Mendeklarasikan bahwa variabel secretKeySpec sebuah object SecretKeySpec
        secretKeySpec = new SecretKeySpec(encryptionKey, "AES");

        byte[] encryptedByte = string.getBytes("ISO-8859-1");
        //ISO 8859-1 adalah pengodean bita tunggal yang dapat mewakili 256 karakter Unicode pertama.
        //Menyandikan ASCII dengan cara yang persis sama.

        //Membuat variabel string untuk menampung hasilnya
        String decryptedString = string;
        byte[] decryption;

        try {
            //Inisialisasi objek menggunakan metode init(). Parameter yang digunakan adalah Cipher.DECRYPT_MODE, keySpecnya
            decipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            //Proses dekripsi dilakukan dengan metode doFinal() dengan satu parameter yaitu encryptedByte
            decryption = decipher.doFinal(encryptedByte);
            //Mendeklarasikan String dari decryptedString
            decryptedString = new String(decryption);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        //Mengembalikan hasil dekripsi yaitu String decryptedString
        return decryptedString;

    }
}
