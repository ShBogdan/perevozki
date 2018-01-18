package work.to.time.gpsservice.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.LinkedHashMap;
import java.util.Map;

import work.to.time.gpsservice.LoginActivity;
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
            if (Boolean.parseBoolean(remoteMessages.get("startTracing"))) {
                Intent locationService = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                locationService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(locationService);

                Intent startGpsTracker = new Intent(getApplicationContext(), GPSTracker.class);
                startGpsTracker.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                startService(startGpsTracker);
            } else {
                Intent stopGpsTracker = new Intent(getApplicationContext(), GPSTracker.class);
                stopGpsTracker.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                getBaseContext().startService(stopGpsTracker);
            }
        }

        if (remoteMessages.containsKey("notification")) {
            sendNotification(remoteMessages.get("notification"));
        }

        if (remoteMessages.containsKey("innerNotification")) {
            SharedUtils.setFcmMessage(this, remoteMessages.get("innerNotification"));
            sendBroadcast(new Intent("MESSAGE_UPDATED"));
        }

    }


    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
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

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
