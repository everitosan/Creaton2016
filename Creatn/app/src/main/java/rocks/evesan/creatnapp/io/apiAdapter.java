package rocks.evesan.creatnapp.io;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by evesan on 8/21/16.
 */
public class apiAdapter {

    private static apiService API_SERVICE;
    private static Retrofit retrofit;

    public static apiService getApiService() {
        if (API_SERVICE == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);


            OkHttpClient KeyHeader = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request request = original.newBuilder()
                                    .method(original.method(), original.body())
                                    .build();

                            return chain.proceed(request);
                        }}).build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(apiConstants.URLBASE)
                    //.client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(KeyHeader)
                    .build();
            API_SERVICE = retrofit.create(apiService.class);
        }
        return  API_SERVICE;

    }

    public static Retrofit retrofit() {
        return retrofit;
    }


}
