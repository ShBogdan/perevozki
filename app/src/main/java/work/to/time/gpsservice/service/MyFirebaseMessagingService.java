package work.to.time.gpsservice.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.LinkedHashMap;
import java.util.Map;

import work.to.time.gpsservice.LoginActivity;
import work.to.time.gpsservice.MessageActivity;
import work.to.time.gpsservice.R;
import work.to.time.gpsservice.utils.Constants;
import work.to.time.gpsservice.utils.MyLog;
import work.to.time.gpsservice.utils.SharedUtils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    {
        MyLog.show(("MyFirebaseMessagingService"));
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> remoteMessages = new LinkedHashMap<>();

        if (remoteMessage.getData().size() > 0) {
            remoteMessages = remoteMessage.getData();

        }

        if (remoteMessages.containsKey("startTracing")) {
            if (SharedUtils.getAccessToken(getApplicationContext()) != null) {
                if (Boolean.parseBoolean(remoteMessages.get("startTracing"))) {
                    if (!isLocationEnabled(getApplicationContext())) {
                        Intent locationService = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        locationService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(locationService);
                    }
                    Intent startGpsTracker = new Intent(getApplicationContext(), GPSTracker.class);
                    startGpsTracker.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                    startService(startGpsTracker);
                } else {
                    Intent stopGpsTracker = new Intent(getApplicationContext(), GPSTracker.class);
                    stopGpsTracker.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                    getBaseContext().startService(stopGpsTracker);
                }
                sendBroadcast(new Intent("GPS_STATUS_UPDATED"));
            }
        }

        if (remoteMessages.containsKey("notification")) {
            Intent intent = new Intent(this, MessageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("GET_NEW_MESSAGE_FOR_ACTIVITY", remoteMessages.get("notification"));
            startActivity(intent);
            sendNotification(remoteMessages.get("notification"), 0);
        }

        if (remoteMessages.containsKey("innerNotification")) {
            SharedUtils.setFcmMessages(this, remoteMessages.get("innerNotification"));
            sendBroadcast(new Intent("MESSAGE_UPDATED"));
            sendNotification("Есть не прочитанные сообщания", 1);
        }

        PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
        //isScreenOn deprecated and can be change to isInteractive
        boolean isScreenOn = pm.isScreenOn();
        if(!isScreenOn)
        {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock");
            wl.acquire(10000);
            PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");

            wl_cpu.acquire(10000);
        }

    }

    private void sendNotification(String messageBody, Integer code) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("GET_NEW_MESSAGE", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, code /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_wheel)
                .setContentTitle("Perevozki notification")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(code /* ID of notification */, notificationBuilder.build());
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

}
