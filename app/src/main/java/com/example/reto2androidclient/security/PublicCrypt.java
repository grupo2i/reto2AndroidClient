package com.example.reto2androidclient.security;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author Aitor Fidalgo
 */
public class PublicCrypt {

    /**
     * Cifra un texto con RSA, modo ECB y padding PKCS1Padding (asim�trica) y lo
     * retorna
     *
     * @param mensaje El mensaje a cifrar
     * @return El mensaje cifrado
     */
    public static String encode(Context context, String mensaje) {
        String encodedMessageStr = null;
        try {
            byte[] encodedMessage = null;

            //Reading public key...
            byte[] fileKey = fileReader(context);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(fileKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encodedMessage = cipher.doFinal(mensaje.getBytes());
            //Encoding encoded message to hexadecimal.
            encodedMessageStr = encodeHexadecimal(encodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encodedMessageStr;
    }

    /**
     * Retorna el contenido de un fichero
     *
     * @return El texto del fichero
     */
    private static byte[] fileReader(Context context) throws Exception {
        byte[] ret;

        try {
            int id = context.getResources().getIdentifier("public_key", "raw", context.getPackageName());
            InputStream inputStream = context.getResources().openRawResource(id);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int leidos;
            byte[] data = new byte[1024];
            while((leidos = inputStream.read(data, 0, data.length)) != -1) {
                byteArrayOutputStream.write(data, 0, leidos);
            }
            byteArrayOutputStream.flush();
            ret = byteArrayOutputStream.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception();
        }

        return ret;
    }

    static String encodeHexadecimal(byte[] message) {
        String hexadecimalString = "";
        for (int i = 0; i < message.length; i++) {
            String h = Integer.toHexString(message[i] & 0xFF);
            if (h.length() == 1)
                hexadecimalString += "0";
            hexadecimalString += h;
        }
        return hexadecimalString.toUpperCase();
    }
}

