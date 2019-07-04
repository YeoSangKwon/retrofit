package retro.winitech.com.retrofitapi.AES;

import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import retro.winitech.com.retrofitapi.ApplicationRetro;

public class AESCipher {
    private static final String TAG = "AESCipher";
    ApplicationRetro mApplication;

    public static final String algorithm = "AES/ECB/PKCS5PADDING";

    KeyGenerator keyGenerator;
    SecureRandom random;
    Cipher cipher = null;

    byte[] encripteBytes, decryptedBytes;

    public AESCipher(ApplicationRetro mApplication) {
        try {
            this.mApplication = mApplication;

            keyGenerator = keyGenerator.getInstance("AES");
            random = SecureRandom.getInstance("SHA1PRNG");
            keyGenerator.init(128, random);
            mApplication.setAesKey(keyGenerator.generateKey());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public byte[] encrypted(String std) {
        try {
            Key sKey = mApplication.getAesKey();
            cipher = Cipher.getInstance(algorithm);
            SecretKeySpec secretKeySpec = new SecretKeySpec(sKey.getEncoded(),"AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encripteBytes = cipher.doFinal(std.getBytes());

            Log.e(TAG, "real  msg = "+std);
            Log.e(TAG, "encripteBytes = "+encripteBytes);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != cipher){
                cipher = null;
            }
        }
        return encripteBytes;
    }


    public byte[] decrypted(byte[] enMessage) {
        try {
            Key sKey = mApplication.getAesKey();

            cipher = Cipher.getInstance(algorithm);
            SecretKeySpec secretKeySpec = new SecretKeySpec(sKey.getEncoded(),"AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            decryptedBytes = cipher.doFinal(enMessage);

            Log.e(TAG, "decryptedBytes = "+ new String(decryptedBytes));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } finally {
            if(null != cipher){
                cipher = null;
            }
        }
        return decryptedBytes;
    }
}
