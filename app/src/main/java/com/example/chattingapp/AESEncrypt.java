package com.example.chattingapp;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESEncrypt {
    //Membuat variabel
    //Membuat byte kunci ( array )
    private byte encryptionKey[] = {9,115,51,86,105,4,-31,-33,-68,88,17,20,3,-105,119,-53};
    private Cipher cipher;
    private SecretKeySpec secretKeySpec;

    public String AESEncrypt(String string){
        try {
            //Mendeklarasikan bahwa variabel cipher sebuah object Cipher dengan menggunakan metode static getInstance()
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        //Mendeklarasikan bahwa variabel secretKeySpec sebuah object SecretKeySpec
        secretKeySpec = new SecretKeySpec(encryptionKey, "AES");

        //Membuat variabel byte unutk proses enkripsi
        byte[] stringByte = string.getBytes();
        byte[] encryptedByte = new byte[stringByte.length];

        try {
            //Inisialisasi objek menggunakan metode init(). Parameter yang digunakan adalah Cipher.ENCRYPT_MODE, keySpecnya
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            //Proses dekripsi dilakukan dengan metode doFinal() dengan satu parameter yaitu encryptedByte
            encryptedByte = cipher.doFinal(stringByte);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        ////Membuat String untuk menampung hasilnya
        String returnString=null;
        try {
            returnString = new String(encryptedByte, "ISO-8859-1");
            //ISO 8859-1 adalah pengodean bita tunggal yang dapat mewakili 256 karakter Unicode pertama.
            //Menyandikan ASCII dengan cara yang persis sama.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Mengembalikan hasil enkripsi yaitu String returnString
        return returnString;
    }
}
