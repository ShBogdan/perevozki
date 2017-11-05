package work.to.time.gpsservice.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import work.to.time.gpsservice.utils.Constants;
import work.to.time.gpsservice.utils.MyLog;


public class SmsListener extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle pudsBundle = intent.getExtras();
        Object[] pdus = (Object[]) pudsBundle.get("pdus");
        SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);
        if (messages.getMessageBody().contains("start")) {
            MyLog.show(messages.getMessageBody());
            MyLog.show(messages.getDisplayOriginatingAddress());

            Intent locationService = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            locationService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(locationService);

            Intent startGpsTracker = new Intent(context.getApplicationContext(), GPSTracker.class);
            startGpsTracker.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            context.startService(startGpsTracker);
        }

        if (messages.getMessageBody().contains("stop")) {
            MyLog.show(messages.getMessageBody());
            MyLog.show(messages.getDisplayOriginatingAddress());
            Intent stopGpsTracker = new Intent(context.getApplicationContext(), GPSTracker.class);
            stopGpsTracker.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
            context.startService(stopGpsTracker);

        }

        if (SMS_RECEIVED.equals(intent.getAction()) || BOOT_COMPLETED.equals(intent.getAction())) {
            Toast.makeText(context.getApplicationContext(), "Perevozki SMS получено", Toast.LENGTH_LONG).show();
            MyLog.show("SMS ПОЛУЧИЛ");
        }
    }

}
