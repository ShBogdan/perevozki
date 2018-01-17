package work.to.time.gpsservice;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import work.to.time.gpsservice.service.GPSTracker;
import work.to.time.gpsservice.utils.Constants;
import work.to.time.gpsservice.utils.MyLog;
import work.to.time.gpsservice.utils.PermissionsUtils;
import work.to.time.gpsservice.utils.SharedUtils;

public class LoginActivity extends FragmentActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    boolean doNotChangeSwitch = true;
    BroadcastReceiver receiver = null;

    @Bind(R.id.text_is_watching)
    TextView tvIsWatching;
    @Bind(R.id.switchGPS)
    Switch mSwitchGPS;
    //    @Bind(R.id.ic_notifications_1)
//    ImageView ivNotify_1;

    @Bind(R.id.ic_notifications_2)
    ImageView ivNotify_2;
    @Bind(R.id.nr_notifications_2)
    TextView tvNotify_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.show("LoginActivity");
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

//        String uri = "@drawable/ic_notifications";
//        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
//        Drawable res = getResources().getDrawable(imageResource);
//        ivNotify_1.setImageDrawable(res);
//
        String uri1 = "@drawable/ic_notifications";
        int imageResource1 = getResources().getIdentifier(uri1, null, getPackageName());
        Drawable res1 = getResources().getDrawable(imageResource1);
        ivNotify_2.setImageDrawable(res1);
        if(!SharedUtils.getFcmMessage(getApplicationContext()).equals("emptyString")){
            tvNotify_2.setText("1");
            ivNotify_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), SharedUtils.getFcmMessage(getApplicationContext()), Toast.LENGTH_LONG).show();
                }
            });
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (SharedUtils.getAccessToken(getApplicationContext()) == null) {
            AuthFragment authFragment = new AuthFragment();
            fragmentTransaction.add(R.id.fragment, authFragment);
            fragmentTransaction.commit();
        } else {
            MenuFragment menuFragment = new MenuFragment();
            fragmentTransaction.add(R.id.fragment, menuFragment);
            fragmentTransaction.commit();
        }

//        int PERMISSION_ALL = 1;
//        String[] PERMISSIONS = {Manifest.permission.READ_SMS};
//
//        if(!hasPermissions(this, PERMISSIONS)){
//            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
//        }

        switchListener();
    }

    @Override
    protected void onResume() {
        MyLog.show("onResume");
        super.onResume();
        broadcastReceiver(true);
        statusCheck();
    }

    @Override
    protected void onPause() {
        MyLog.show("onPause");
        super.onPause();
        broadcastReceiver(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        app.getNetManager().unsubscribe(this);

        //stopService(new Intent(getApplicationContext(), GPSTracker.class));
    }

    public void statusCheck() {
        doNotChangeSwitch = false;

        if (isLocationByNet()) {
            tvIsWatching.setText("Сервис для отслеживания: Сеть оператора");
            tvIsWatching.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark));
        }
        if (isGPSActive()) {
            tvIsWatching.setText("Сервис для отслеживания: GPS");
            tvIsWatching.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark));
        }


        if (!isLocationByNet() && !isGPSActive()) {
            tvIsWatching.setText("Нет доступных источников слежения");
            tvIsWatching.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
        }


        if (isMyServiceRunning(GPSTracker.class)) {
            mSwitchGPS.setChecked(true);
            mSwitchGPS.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark));
        } else {
            mSwitchGPS.setChecked(false);
            mSwitchGPS.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
        }

        doNotChangeSwitch = true;

//        MyLog.show("isNetworkAvailable: " + isNetworkAvailable());
//        MyLog.show("isGPSActive: " + isGPSActive());
//        MyLog.show("isLocationByNet: " + isLocationByNet());
//        MyLog.show("isLocationEnabled: " + isLocationEnabled(this));
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("На устройстве отключены все источники слежения. Включить?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void displayLocationSettingsRequest(Context context) {
        MyLog.show("displayLocationSettingsRequest");
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                MyLog.show("setResultCallback");

                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
//                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(LoginActivity.this, 33);
                        } catch (IntentSender.SendIntentException e) {
//                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    //Соединение передача данных
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private boolean isGPSActive() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    //Отслеживание через сеть
    private boolean isLocationByNet() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private void stopService() {
        //если просто остановить сервис(stopService()) то не будет возможности закрыть выполняющиеся потоки
        Intent i = new Intent(getApplicationContext(), GPSTracker.class);
        i.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        startService(i);
    }

    private void startService() {
        Intent foregroundService = new Intent(getBaseContext(), GPSTracker.class);
        foregroundService.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(foregroundService);
    }

    private void switchListener() {
        mSwitchGPS.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (SharedUtils.getAccessToken(getApplicationContext()) == null) {
                    Toast.makeText(getApplication(), "Вы не авторизированы", Toast.LENGTH_LONG).show();
                    mSwitchGPS.setChecked(false);
                    mSwitchGPS.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
                    return;
                }
                if (doNotChangeSwitch) {
                    if (isChecked) {
                        if (isLocationEnabled(getApplication())) {
                            startService();
                        } else {
                            buildAlertMessageNoGps();
                        }
                        if (isMyServiceRunning(GPSTracker.class)) {
                            mSwitchGPS.setChecked(true);
                            mSwitchGPS.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark));
                        } else {
                            mSwitchGPS.setChecked(false);
                            mSwitchGPS.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
                        }
                    }
                    if (!isChecked) {
                        if (isMyServiceRunning(GPSTracker.class)) {
                            stopService();
                            mSwitchGPS.setChecked(false);
                            mSwitchGPS.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
                        }
                    }
                }
            }
        });
    }

    public static boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        new android.app.AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(null)
                .setMessage("Завершить сессию? Слежение будет остановлено.")
                .setPositiveButton("Нет", null)
                .setNegativeButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedUtils.removeRecord(getApplicationContext());
                        if (isMyServiceRunning(GPSTracker.class)) {
                            stopService();
                        }
                        finish();
                    }
                }).show();
    }

    private void broadcastReceiver(boolean register) {

        if (register) {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    statusCheck();
                }
            };

            IntentFilter filter = new IntentFilter();
            filter.addAction("SERVICE_DISABLED");
            registerReceiver(receiver, filter);
        }
        if (!register) {
            if (null != receiver) {
                ///Должно быть в OnPause
                unregisterReceiver(receiver);
            }
        }
    }

    private void checkPermission() {
        if (PermissionsUtils.Location.isDenied(this)) {
            if (PermissionsUtils.Location.isNeedRequest(this)) {
                PermissionsUtils.Location.request(this, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}

