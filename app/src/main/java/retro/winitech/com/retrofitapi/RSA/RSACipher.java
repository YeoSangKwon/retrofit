package retro.winitech.com.retrofitapi.RSA;

import android.util.Base64;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import retro.winitech.com.retrofitapi.ApplicationRetro;

public class RSACipher {
    private static final String TAG = "RSACipher";
    ApplicationRetro mApplication;

    KeyPairGenerator keyPairGenerator;
    KeyPair keyPair;
    PublicKey publicKey;
    PrivateKey privateKey;
    Cipher cipher;

    byte[] encripteBytes, decryptedBytes;

    public RSACipher(ApplicationRetro mApplication) {
        this.mApplication = mApplication;
//        try {
//            if(null == mApplication.getPrivateKey()){
//                keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//                keyPairGenerator.initialize(2048);
//                keyPair = keyPairGenerator.genKeyPair();
//
//                mApplication.setPrivateKey(keyPair.getPrivate());
//                mApplication.setPublicKey(keyPair.getPublic());
//            }
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
    }

    public byte[] encrypted(byte[] aesKey){
        try {
//            byte[] be = publicKey.getEncoded();
//            PublicKey publicKey1 = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(be));

            publicKey = mApplication.getPublicKey();

            cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encripteBytes = cipher.doFinal(aesKey);
            Log.e(TAG, "real  msg = "+aesKey);
            Log.e(TAG, "encrypted = "+encripteBytes);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } finally {
            if(null != cipher){
                cipher = null;
            }
        }
        return encripteBytes;
    }

    public byte[] decrypted(byte[] enMessage){

        try {
            privateKey = mApplication.getPrivateKey();

//            byte[] be = privateKey.getEncoded();
//            PrivateKey privateKey1 = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(be));

            cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptedBytes = cipher.doFinal(enMessage);
            Log.e(TAG, "decrypted = "+ new String(decryptedBytes));

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
