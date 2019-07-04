package retro.winitech.com.retrofitapi.callBack;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

import retro.winitech.com.retrofitapi.ApplicationRetro;
import retro.winitech.com.retrofitapi.retroModel.Contributor;
import retro.winitech.com.retrofitapi.retroModel.dataModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class retroCallBack {
    private static final String TAG = "retroCallBack";

    ApplicationRetro mApplication;

    public retroCallBack(ApplicationRetro mApplication) {
        this.mApplication = mApplication;
    }

    //다중용 콜백
    public Callback<List<Contributor.data>> mRetrofitCallback = new Callback<List<Contributor.data>>() {

        /**
         * @param call 서버로전송되는 값
         * @param response 서버에서부터 수신하는 값
         *
         * 해당 콜백으로 데이터가 들어오며 통신성공 시 onResponse로 값이 들어온다
         * 들어온 값은 IF와 연결된 모델에 있는 getter를 통해 사용이 가능하다
         *
         * 오류 발생시 아래 onFailure로 떨어지며 해당 오류는 {@link Throwable} 을 통해 확인이 가능하다
         * 대부분의 경우 서버와 클라이언트간의 형식 (Json, Array등)이 맞지 않는경우가 많음
         * */
        @Override
        public void onResponse(Call<List<Contributor.data>> call, Response<List<Contributor.data>> response) {
            List<Contributor.data> data = response.body();
            Log.e(TAG,"data getNode_id = "+data.get(0).getNode_id());
            Log.e(TAG,"data getId = "+data.get(0).getId());
        }

        @Override
        public void onFailure(Call<List<Contributor.data>> call, Throwable t) {
            Log.e(TAG,"mRetrofitCallback error"+t);
        }
    };

    //단일용 콜백
    public Callback<Contributor> mContributorCallback = new Callback<Contributor>() {
        @Override
        public void onResponse(Call<Contributor> call, Response<Contributor> response) {
            Contributor data = response.body();
//            Log.e(TAG,"data getCurrent_user_url =  = "+data.getCurrent_user_url());
            Log.e(TAG,"data getCurrent_user_url =  = "+data.toString());
        }

        @Override
        public void onFailure(Call<Contributor> call, Throwable t) {
            Log.e(TAG,"mContributorCallback error"+t);
        }
    };


    //POST 전송용 콜백
    public Callback<dataModel.data> mDatamodelCallback = new Callback<dataModel.data>() {
        @Override
        public void onResponse(Call<dataModel.data> call, Response<dataModel.data> response) {
            dataModel.data data = response.body();

            Log.e(TAG,"PARM SERVICENM = "+data.getRESULT());

            JsonObject object = (JsonObject) data.getRESULT().get("PARM");
            JsonParser parser = new JsonParser();
            JsonElement SERVICENM = parser.parse(String.valueOf(data.getRESULT()));
            JsonElement PARM = parser.parse(String.valueOf(object));

            String sNM = SERVICENM.getAsJsonObject().get("SERVICENM").getAsString();
            String SEARCH_IDNTFCCD = PARM.getAsJsonObject().get("SEARCH_IDNTFCCD").getAsString();
            String USER_ID = PARM.getAsJsonObject().get("USER_ID").getAsString();

            Log.e(TAG,"SERVICENM = "+sNM);
            Log.e(TAG,"SEARCH_IDNTFCCD = "+SEARCH_IDNTFCCD);
            Log.e(TAG,"USER_ID = "+USER_ID);

        }

        @Override
        public void onFailure(Call<dataModel.data> call, Throwable t) {
            Log.e(TAG,"mDatamodelCallback error = "+ t);
            t.printStackTrace();
        }
    };

    //POST 전송용 콜백 (List)
    public Callback<List<dataModel.dataArray>> mDatamodelListCallback = new Callback<List<dataModel.dataArray>>() {
        @Override
        public void onResponse(Call<List<dataModel.dataArray>> call, Response<List<dataModel.dataArray>> response) {
            List<dataModel.dataArray> data = response.body();
            Log.e(TAG,"data.get(0).toString = "+ response.toString());
            Log.e(TAG,"data.get(0).toString = "+ data.get(0).getPubKey());

            try {
                PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(data.get(0).getPubKey(),Base64.DEFAULT)));
                mApplication.setPublicKey(publicKey);
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<List<dataModel.dataArray>> call, Throwable t) {
            Log.e(TAG,"mDatamodelListCallback error = "+ call.request());
            Log.e(TAG,"mDatamodelListCallback error = "+ t);
            t.printStackTrace();
        }
    };

    //POST 전송용 콜백 (List)
    public Callback<List<dataModel.dataArray>> mDatamodelListCallback2 = new Callback<List<dataModel.dataArray>>() {
        @Override
        public void onResponse(Call<List<dataModel.dataArray>> call, Response<List<dataModel.dataArray>> response) {
            List<dataModel.dataArray> data = response.body();
            Log.e(TAG,"mDatamodelListCallback error = "+ call.request());
            Log.e(TAG,"data.get(0).toString = "+ response.toString());
            Log.e(TAG,"data.get(0).toString = "+ data.get(0).getPubKey());
        }

        @Override
        public void onFailure(Call<List<dataModel.dataArray>> call, Throwable t) {
            Log.e(TAG,"mDatamodelListCallback error = "+ call.request());
            Log.e(TAG,"mDatamodelListCallback error = "+ t);
            t.printStackTrace();
        }
    };
}
