package retro.winitech.com.retrofitapi;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

public class ApplicationRetro extends Application {
    private static final String TAG = "ApplicationIDSI";
    Context mContext;

    private SharedPreferences mPref;
    private SharedPreferences.Editor editor;

    private PrivateKey privateKey = null;
    private PublicKey publicKey = null;
    private Key aesKey = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mPref = getSharedPreferences("Idsi_Pref", Service.MODE_PRIVATE);
        editor = mPref.edit();
        mContext = getApplicationContext();
    }


    // /////////////////////////////////////  Getter and Setter  ///////////////////////////////////////
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public Key getAesKey() {
        return aesKey;
    }

    public void setAesKey(Key aesKey) {
        this.aesKey = aesKey;
    }
    // ////////////////////////////////////////////////////////////////////////////////////////////////////
}
