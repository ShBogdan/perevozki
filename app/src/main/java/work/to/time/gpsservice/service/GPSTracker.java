package work.to.time.gpsservice.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import work.to.time.gpsservice.LoginActivity;
import work.to.time.gpsservice.R;
import work.to.time.gpsservice.net.NetManager;
import work.to.time.gpsservice.net.response.CoordModel;
import work.to.time.gpsservice.observer.net.NetSubscriber;
import work.to.time.gpsservice.utils.Constants;
import work.to.time.gpsservice.utils.MyLog;
import work.to.time.gpsservice.utils.SharedUtils;

public class GPSTracker extends Service implements NetSubscriber,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{
    private static final String TAG = "MyLog";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final int FIVE_MINUTES = 1000 * 5 * 1; // высылаем на сервер каждые * минут
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    // Declaring a Location Manager
    protected LocationManager locationManager;
    Handler handler_proc = new Handler();
    NetManager manager;
    NotificationManager notificationManager;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    final Runnable runnable_proc = new Runnable() {
        public void run() {
            MyLog.show("tread work");
            if(mGoogleApiClient.isConnected()) {
                startLocationUpdates();
                handleNewLocation();}

            if(!String.valueOf(getCurrentLongitude()).equals("0.0")){
                manager.sendCoord(String.valueOf(getCurrentLongitude()), String.valueOf(getCurrentLatitude()), getTimeStamp(),SharedUtils.getAccessToken(getApplicationContext()));
                Toast.makeText(getBaseContext(), String.valueOf(getCurrentLongitude()) + " " + String.valueOf(getCurrentLatitude()), Toast.LENGTH_SHORT).show();
                MyLog.show("Send to server: " + String.valueOf(getCurrentLongitude()) + " " + String.valueOf(getCurrentLatitude()));
            }
            MyLog.show("current position: " + String.valueOf(getCurrentLongitude()) + " " + String.valueOf(getCurrentLatitude()));

            //Сохраненные данные. После отсылки удаляются в onNetSuccess
            SharedPreferences sp = getBaseContext().getSharedPreferences("position", Context.MODE_PRIVATE);
            Map<String,?> arrPosition = sp.getAll();
            for(Map.Entry<String,?> entry : arrPosition.entrySet()){
                String point[] = entry.getValue().toString().split("&");
                String timeStamp = entry.getKey();
                String longitude = point[0];
                String latitude  = point[1];
                String token     = point[2];
                manager.sendCoord(longitude, latitude, timeStamp, token);
            }
            //


            handler_proc.postDelayed(runnable_proc, FIVE_MINUTES);

//            if (!isNetworkAvailable()){
//                MyLog.show("Сеть не доступна");
//
//                Toast.makeText(getBaseContext(), "Сеть не доступна", Toast.LENGTH_SHORT).show();
//            }
        }
    };

    public double getCurrentLatitude() {
        return currentLatitude ;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.show("onCreate");

        manager = new NetManager();
        manager.subscribe(this);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (checkPlayServices()) {
            buildGoogleApiClient();
            createLocationRequest();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyLog.show("onStartCommand");
        mGoogleApiClient.connect();
        if (intent == null || intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            MyLog.show("STARTFOREGROUND_ACTION");
            Intent startActivity = new Intent(getApplicationContext(), LoginActivity.class);
            PendingIntent pendingActivity = PendingIntent.getActivity(getApplicationContext(), 0, startActivity, 0);
            Intent stopIntent = new Intent(getApplicationContext(), GPSTracker.class);
            stopIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
            PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, stopIntent, 0);
            Notification n = new Notification.Builder(this)
                    .setContentTitle("Перевозки")
                    .setContentText("Сервис работает")
                    .setSmallIcon(R.drawable.ic_wheel)
//                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingActivity)
                    .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Остановить", pendingIntent)
                    .setOngoing(true)
                    .setAutoCancel(false).build();
//            notificationManager.notify(123, n);
            startForeground(123,n);


        }
        if(intent != null){
            if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
                MyLog.show("STOPFOREGROUND_ACTION");

                stopTracker();
                stopSelf();

                return START_NOT_STICKY;
            }
        }

        handler_proc.post(runnable_proc);
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        MyLog.show("onDestroy");
        super.onDestroy();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    //NetSubscriber
    @Override
    public void onNetSuccess(int requestId, Object data) {
        if (requestId == NetManager.REQUEST_SEND_COORD) {
            MyLog.show("onNetSuccess");
            CoordModel response = (CoordModel) data;
            SharedUtils.setUid(getApplicationContext(), response.getId());

            //удаляем переданные из SP
            SharedPreferences sp = getBaseContext().getSharedPreferences("position", Context.MODE_PRIVATE);
            sp.edit().remove(response.getTimestamp()).apply();
            //
        }
    }

    @Override
    public void onNetError(int requestId, String error) {
        MyLog.show("onNetError");

        MyLog.show("Сохранить: " +String.valueOf(getCurrentLongitude())+" "+String.valueOf(getCurrentLatitude())+" "+getTimeStamp()+" "+SharedUtils.getAccessToken(getApplicationContext()));

        //Сохраняем не переданные
        SharedPreferences sp = getApplication().getSharedPreferences("position", Context.MODE_PRIVATE);
        sp.edit()
                .putString(getTimeStamp(),
                        String.valueOf(getCurrentLongitude())
                        +"&"+String.valueOf(getCurrentLatitude())
                        +"&"+SharedUtils.getAccessToken(getApplicationContext()))
                .apply();
        //
    }

    @Override
    public void onNetConnectionError(int requestId) {
        MyLog.show("onNetConnectionError");

    }

    @Override
    public void onConnected(Bundle bundle) {
//        MyLog.show("onConnected");
//        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if (location == null) {
//            startLocationUpdates();
//        }
    }

    private void handleNewLocation() {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(location!=null){
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void stopTracker(){
        handler_proc.removeCallbacksAndMessages(null);
//        notificationManager.cancelAll();
        stopForeground(true);
        mGoogleApiClient.disconnect();
//        stopLocationUpdates();
        sendBroadcast(new Intent("SERVICE_DISABLED"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(null, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
            }

            return false;
        }

        return true;
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
//        if(mGoogleApiClient.isConnected())
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int arg0) {
//        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    private String getTimeStamp(){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        String timestampISO = df.format(new Date());
        return timestampISO;
    }
}


