package work.to.time.gpsservice.net;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import work.to.time.gpsservice.BuildConfig;
import work.to.time.gpsservice.net.response.ActiveOrders;
import work.to.time.gpsservice.net.response.ArchiveOrders;
import work.to.time.gpsservice.net.response.AuthModel;
import work.to.time.gpsservice.net.response.CoordModel;
import work.to.time.gpsservice.net.response.RouteModel;
import work.to.time.gpsservice.observer.net.NetSubscriber;
import work.to.time.gpsservice.utils.MyLog;
import work.to.time.gpsservice.utils.SharedUtils;


public class NetManager implements Net {

    private Retrofit retrofit;
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private Api api;
    private final List<NetSubscriber> subscribers = new ArrayList<>();
    public static final int REQUEST_AUTH = 11;
    public static final int REQUEST_SEND_COORD = 22;
    public static final int REQUEST_ACTIVE_ORDERS = 33;
    public static final int REQUEST_ARCHIVE_ORDERS = 44;
    public static final int REQUEST_SUITABLE_ORDERS = 55;
    public static final int REQUEST_ACTIVE_ROUTES = 66;
    public static final int REQUEST_ARCHIVE_ROUTES = 77;
    public static final int REQUEST_SUITABLE_ROUTES = 88;

    public NetManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    @Override
    public void authorize(String login, String pass, String deviceId) {
        api.authorize(login, pass, deviceId).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                notifySuccess(REQUEST_AUTH, response.body());
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                notifyError(REQUEST_AUTH, t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void sendCoord(String longitude, String latitude, String timestamp, String routeId, String token) {
        api.sendCoord(longitude, latitude, timestamp, routeId,"Bearer " + token).enqueue(new Callback<CoordModel>() {
            @Override
            public void onResponse(Call<CoordModel> call, Response<CoordModel> response) {
                MyLog.show("response.body()" + response.body().toString());
                notifySuccess(REQUEST_SEND_COORD, response.body());
                MyLog.show("Server return: " + "longitude:" + response.body().getLatitude() + " latitude " + response.body().getLongitude());
            }

            @Override
            public void onFailure(Call<CoordModel> call, Throwable t) {
                notifyError(REQUEST_SEND_COORD, t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void activeOrders(String deviceId, String token) {
        api.activeOrders(deviceId, "Bearer " + token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                MyLog.show("response.body()" + response.body().toString());
                try {
                    notifySuccess(REQUEST_ACTIVE_ORDERS, response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    MyLog.show(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                notifyError(REQUEST_ACTIVE_ORDERS, t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void activeRoutes(String deviceId, String token) {
        api.activeRoutes(deviceId, "Bearer " + token, "en").enqueue(new Callback<RouteModel>() {
            @Override
            public void onResponse(Call<RouteModel> call, Response<RouteModel> response) {
                notifySuccess(REQUEST_ACTIVE_ROUTES, response.body());
            }

            @Override
            public void onFailure(Call<RouteModel> call, Throwable t) {
                notifyError(REQUEST_ACTIVE_ROUTES, t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void archiveOrders(String deviceId, String token) {
        api.archiveOrders(deviceId, "Bearer " + token).enqueue(new Callback<ArchiveOrders>() {
            @Override
            public void onResponse(Call<ArchiveOrders> call, Response<ArchiveOrders> response) {
                notifySuccess(REQUEST_ARCHIVE_ORDERS, response.body());
            }

            @Override
            public void onFailure(Call<ArchiveOrders> call, Throwable t) {
                notifyError(REQUEST_ARCHIVE_ORDERS, t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void archiveRoutes(String deviceId, String token) {
        api.archiveRoutes(deviceId, "Bearer " + token).enqueue(new Callback<ArchiveOrders>() {
            @Override
            public void onResponse(Call<ArchiveOrders> call, Response<ArchiveOrders> response) {
                notifySuccess(REQUEST_ARCHIVE_ROUTES, response.body());
            }

            @Override
            public void onFailure(Call<ArchiveOrders> call, Throwable t) {
                notifyError(REQUEST_ARCHIVE_ROUTES, t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void suitableOrders(String deviceId, String token) {
        api.suitableOrders(deviceId, "Bearer " + token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               notifySuccess(REQUEST_SUITABLE_ORDERS, response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                notifyError(REQUEST_SUITABLE_ORDERS, t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void suitableRoutes(String suitableRoutesId, String token) {
        api.suitableRoutes(suitableRoutesId, "Bearer " + token, "en").enqueue(new Callback<ActiveOrders>() {
            @Override
            public void onResponse(Call<ActiveOrders> call, Response<ActiveOrders> response) {
                notifySuccess(REQUEST_SUITABLE_ROUTES, response.body());
            }

            @Override
            public void onFailure(Call<ActiveOrders> call, Throwable t) {
                notifyError(REQUEST_SUITABLE_ROUTES, t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void subscribe(NetSubscriber subscriber) {
        if (!subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
        }
    }

    @Override
    public void unsubscribe(NetSubscriber subscriber) {
        if (subscribers.contains(subscriber)) {
            subscribers.remove(subscriber);
        }
    }

    @Override
    public boolean isSubscribe(NetSubscriber subscriber) {
        return false;
    }

    private void notifySuccess(final int requestId, final Object data) {
        for (NetSubscriber subscriber : subscribers) {
            subscriber.onNetSuccess(requestId, data);
        }
    }

    private void notifyError(final int requestId, final String error) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                for (NetSubscriber subscriber : subscribers) {
                    subscriber.onNetError(requestId, error);
                }
            }
        });
    }

}
