package work.to.time.gpsservice.net;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import work.to.time.gpsservice.net.response.ArchiveOrders;
import work.to.time.gpsservice.net.response.AuthModel;
import work.to.time.gpsservice.net.response.CoordResponse;

public interface Api {
    @FormUrlEncoded
    @POST("/api/auth")
    Call<AuthModel> authorize(@Field("login") String username,
                              @Field("password") String password,
                              @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @POST("/api/track")
    Call<CoordResponse> sendCoord(@Field("longitude") String longitude,
                                  @Field("latitude") String latitude,
                                  @Field("timestamp") String timestamp,
                                  @Header("auth") String header);

    @FormUrlEncoded
    @POST("/api/orders/active")
    Call<ResponseBody> activeOrders(@Field("deviceId") String deviceId,
                                    @Header("auth") String header);
    @FormUrlEncoded
    @POST("/api/orders/archive")
    Call<ArchiveOrders> archiveOrders(@Field("deviceId") String deviceId,
                                      @Header("auth") String header);

    @FormUrlEncoded
    @POST("/api/orders/suitable")
    Call<ResponseBody> suitableOrders(@Field("deviceId") String deviceId,
                                     @Header("auth") String header);


    @FormUrlEncoded
    @POST("/api/track")
    Call<ResponseBody> testCoord(@Field("longitude") String longitude,
                                 @Field("latitude") String latitude,
                                 @Field("timestamp") String timestamp,
                                 @Header("auth") String header);
}

