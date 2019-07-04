package retro.winitech.com.retrofitapi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retro.winitech.com.retrofitapi.AES.AESCipher;
import retro.winitech.com.retrofitapi.RSA.RSACipher;
import retro.winitech.com.retrofitapi.callBack.retroCallBack;
import retro.winitech.com.retrofitapi.retroIF.RetroInterface;
import retro.winitech.com.retrofitapi.retroIF.TestRetroIF;
import retro.winitech.com.retrofitapi.retroModel.Contributor;
import retro.winitech.com.retrofitapi.retroModel.dataModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 *  레트로 핏 공식 사이트 : http://square.github.io/retrofit/
 *  레트로 핏 공식 깃허브 : https://github.com/square/retrofit
 *
 *  주요 코드 설명 (POST 방식)
 * {@link retro.winitech.com.retrofitapi.MainActivity}              서버로 통신을 위한 객체 생성 및 파라메터를 구성하고 실제 통신을 수행 하는부분
 * {@link retro.winitech.com.retrofitapi.callBack.retroCallBack}    서버와 통신 후 데이터 처리를 담은 부분
 * {@link retro.winitech.com.retrofitapi.retroIF.TestRetroIF}       클라이언트에서 서버로 전달되는 IF설정 부분
 * {@link retro.winitech.com.retrofitapi.retroModel.dataModel}      서버에서 받아오는 데이터를 저장하기 위한 데이터 모델 (서버와 같은 형식이여야 함)
 *
 * 실행결과 -> error 로그 값 확인
 * 추가 기능 사용을 위한 API소개 :
 *  - https://square.github.io/retrofit/
 *  - https://square.github.io/retrofit/2.x/retrofit/
 * */


public class MainActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    Context mContext;
    ApplicationRetro mApplication;

    //서버와 통신을 위한 retrofit 객체
    private Retrofit retrofit_list;     //다중 리스트용 GET
    private Retrofit retrofit_single;   //단일 오브젝트용 GET
    private Retrofit retrofit_post;     //JSON 방식의 POST

    //retrofit 객체와 연결되는 IF (GET용 retro는 같은 IF에 구현함, 연결하는 IF는 retro와 1:1 매칭이되어야함)
    private RetroInterface mRetroInterface;
    private RetroInterface mRetroInterface2;
    //POST 방식의 IF
    private TestRetroIF mTestRetroIF;

    //retrofit을 이용해 서버로 전달하기 위한 변수
    private Call<List<Contributor.data>> mCallList;
    private Call<Contributor> mCallModel;
    private Call<dataModel.data> mCallJsonArray;
    private Call<List<dataModel.dataArray>> mCallListJsonArray;

    //서버로 보내기위한 데이터를 담을 JSON obj
    private JsonObject jsonObject;

    Button btn_pKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mApplication = (ApplicationRetro) getApplication();
        btn_pKey = findViewById(R.id.btn_pKey);
        btn_pKey.setOnClickListener(this);
        /**
         * GET방식의 예제
         *  - 단일 과 다중 리스트의 방식 구현
         * */

        //retro 객체에 대한 URL및 retro 서비스 연결
        retrofit_list = new Retrofit.Builder().baseUrl(RetroInterface.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofit_single = new Retrofit.Builder().baseUrl(RetroInterface.API_URL2).addConverterFactory(GsonConverterFactory.create()).build();

        //해당 객체를 해당 인터페이스에 연결하여 IF 객체를 생성
        mRetroInterface = retrofit_list.create(RetroInterface.class);
        mRetroInterface2 = retrofit_single.create(RetroInterface.class);

        //서버로 전달하기 위한 변수에 IF에 존재하는 함수 콜 (이때 방식 또는 변수를 IF에 전달하여 객체에 담는다)
        mCallList = mRetroInterface.getList();
        mCallModel = mRetroInterface2.getData();

        //retofit을 이용하여 서버로 전달 및 CallBack함수 구현
        //해당 콜백들은 API 공부를 위해 보기 편하도록 따로 빼둠
        //실제 사용시는 같은 페이지에 두고 화면을 구현하는것이 좋음
//        mCallList.enqueue(new retroCallBack().mRetrofitCallback);
//        mCallModel.enqueue(new retroCallBack().mContributorCallback);


        /**
         * 암호화 테스트
         * */
//        new RSACipher(mApplication).encrypted("Test Message");
//        new AESCipher(mApplication).encrypted("Test Message");

        /**
         * 실제 사용을 자주 하게되는 POST 방식 예제
         *  - {@link JsonObject} 형식의 파라메타 전달
         *  - {@link com.google.gson.JsonArray} 형식으로 콜백을 받음
         * */

        //retro객체 생성
        //okHttp3를 사용하기 위한 객체 생성
        //okHttp3를 사용하는 이유는 header 및 body 등 옵션변경을 위해 사용 (개별 client 등록)
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
//        retrofit_post = APIClientPost.getClient();
        retrofit_post = new Retrofit.Builder().baseUrl(TestRetroIF.API_URL4).addConverterFactory(GsonConverterFactory.create(gson)).build();

        //서버로 전달을 위한 IF 에 retro 객체 등록
        mTestRetroIF = retrofit_post.create(TestRetroIF.class);

        //서버에 전송할 값 생성
        jsonObject = new JsonObject();

        JsonArray jsonArray = new JsonArray();

        JsonObject obj = new JsonObject();
        obj.addProperty("SEARCH_IDNTFCCD","테스트");
        obj.addProperty("USER_ID","winitest");

        jsonArray.add(obj);

        //JsonObject 내에에 JsonObject가 있는 형식
//        jsonObject.addProperty("SERVICENM","smof_policeDetailDisappearService");
//        jsonObject.add("PARM",jsonArray);

        jsonObject.addProperty("SERVICE_NM","Vl0000");
        jsonObject.addProperty("message", "TEST");

        //IF에 있는 getData2 함수에 jsonObject 를 매개변수로 전달
//        mCallJsonArray = mTestRetroIF.getData2(jsonObject);
//        mCallJsonArray.enqueue(new retroCallBack().mDatamodelCallback);     //POST 형식 콜백 (기본사용)

//        mCallListJsonArray = mTestRetroIF.getData3("{\"SERVICE_NM\":\"Vl0000\",\"message\":\"TEST\"}");
        mCallListJsonArray = mTestRetroIF.getData3(jsonObject.toString());
        mCallListJsonArray.enqueue(new retroCallBack(mApplication).mDatamodelListCallback);     //POST 형식 콜백 (리스트콜백시)
    }

    public void pushEncMsg(){
        //서버에 전송할 값 생성
        jsonObject = null;
        jsonObject = new JsonObject();

        RSACipher rsaCipher  = new RSACipher(mApplication);
        AESCipher aesCipher  = new AESCipher(mApplication);

        byte[] eKEY = rsaCipher.encrypted(mApplication.getAesKey().getEncoded());
        byte[] eMSG = aesCipher.encrypted("Test Message RSA");

        jsonObject.addProperty("SERVICE_NM","Vl0001");
        jsonObject.addProperty("key",Base64.encodeToString(eKEY, Base64.URL_SAFE|Base64.NO_PADDING|Base64.NO_WRAP));
        jsonObject.addProperty("message", Base64.encodeToString(eMSG, Base64.URL_SAFE|Base64.NO_PADDING|Base64.NO_WRAP));

        mCallListJsonArray = mTestRetroIF.getData3(jsonObject.toString());
        mCallListJsonArray.enqueue(new retroCallBack(mApplication).mDatamodelListCallback2);     //POST 형식 콜백 (리스트콜백시)
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pKey:
                pushEncMsg();
                break;
        }
    }
}
