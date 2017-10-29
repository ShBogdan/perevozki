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
import work.to.time.gpsservice.net.response.ArchiveOrders;
import work.to.time.gpsservice.net.response.AuthModel;
import work.to.time.gpsservice.net.response.CoordResponse;
import work.to.time.gpsservice.observer.net.NetSubscriber;
import work.to.time.gpsservice.utils.MyLog;


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
    public void sendCoord(String longitude, String latitude, String timestamp, String token) {
        api.sendCoord(longitude, latitude, timestamp, "Bearer " + token).enqueue(new Callback<CoordResponse>() {
            @Override
            public void onResponse(Call<CoordResponse> call, Response<CoordResponse> response) {
                notifySuccess(REQUEST_SEND_COORD, response.body().data.get(0));
                MyLog.show("Server return: " + "longitude:" + response.body().data.get(0).getLatitude() + " latitude " + response.body().data.get(0).getLongitude());
            }

            @Override
            public void onFailure(Call<CoordResponse> call, Throwable t) {
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
    public void suitableOrders(String id, String token) {

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
