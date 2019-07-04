package retro.winitech.com.retrofitapi.retroIF;

import java.util.List;

import retro.winitech.com.retrofitapi.retroModel.Contributor;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetroInterface {
    public static final String API_URL = "https://api.github.com/repos/square/retrofit/";
    public static final String API_URL2 = "https://api.github.com";

    @GET("contributors")
    Call<List<Contributor.data>> getList();

    @GET("/")
    Call<Contributor> getData();
}
