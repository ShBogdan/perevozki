package work.to.time.gpsservice.net;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import work.to.time.gpsservice.net.response.ActiveOrders;
import work.to.time.gpsservice.net.response.ArchiveOrders;
import work.to.time.gpsservice.net.response.AuthModel;
import work.to.time.gpsservice.net.response.CoordModel;
import work.to.time.gpsservice.net.response.RouteModel;

public interface Api {

    @FormUrlEncoded
    @POST("/api/auth")
    Call<AuthModel> authorize(@Field("login") String username,
                              @Field("password") String password,
                              @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @POST("/api/auth/verify-status")
    Call<AuthModel> verify(@Header("auth") String header);

    @FormUrlEncoded
    @POST("/api/track/create")
    Call<CoordModel> sendCoord(@Field("longitude") String longitude,
                               @Field("latitude") String latitude,
                               @Field("timestamp") String timestamp,
                               @Field("routeId") String routeId,
                               @Header("auth") String header);

    @FormUrlEncoded
    @POST("/api/track")
    Call<ResponseBody> testCoord(@Field("longitude") String longitude,
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
    @POST("/api/routes/active")
    Call<RouteModel> activeRoutes(@Field("deviceId") String deviceId,
                                  @Header("auth") String header,
                                  @Header("Language") String language
    );

    @FormUrlEncoded
    @POST("/api/routes/archive")
    Call<ArchiveOrders> archiveRoutes(@Field("deviceId") String deviceId,
                                      @Header("auth") String header);

    @GET("/api/routes/suitable/{id}")
    Call<ActiveOrders> suitableRoutes(@Path("id") String id,
                                            @Header("auth") String header,
                                            @Header("Language") String language

    );

}
