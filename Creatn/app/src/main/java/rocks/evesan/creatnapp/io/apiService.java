package rocks.evesan.creatnapp.io;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rocks.evesan.creatnapp.domain.History;
import rocks.evesan.creatnapp.domain.searchData;

/**
 * Created by evesan on 8/21/16.
 */
public interface apiService {

    @GET("/")
    Call <List<History>> getAllHistories();

    @Multipart
    @POST("history")
    Call <History> postHistory(@Part MultipartBody.Part h, @Part MultipartBody.Part la, @Part MultipartBody.Part lo, @Part MultipartBody.Part f);

    @POST("history/like")
    Call <History> likeHistory(@Body History h);

    @POST("history/search")
    Call <List<History>>  seacrhHistories(@Body searchData d);
}
