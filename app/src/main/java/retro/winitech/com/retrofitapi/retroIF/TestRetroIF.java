package retro.winitech.com.retrofitapi.retroIF;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retro.winitech.com.retrofitapi.retroModel.dataModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface TestRetroIF {
    public static final String API_URL3 = "http://192.168.0.80:9401/TEST/";
    public static final String API_URL4 = "http://192.168.15.145:8080/avl/";


    /**
     * 서버에 전송되는 헤더 설정
     * */
    @Headers({
            "Content-Type: text/plain;charset=utf-8"
    })


    /**
     * @author ysk5898
     * @param jsonObject
     * Client에서 서버에 POST 방식으로 데이터를 전달함 해당 파라메타에 값을 넣어주면된다
     * */
    @POST("test1.do/")
    Call<dataModel.data> getData2(@Body JsonObject jsonObject);


    /**
     * @author ysk5898
     * @param request
     * 서버에서 string 형식으로 데이터를 받는경우
     * */
    @POST("scall")
    Call<List<dataModel.dataArray>> getData3(@Body String request);
}
