package work.to.time.gpsservice.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import work.to.time.gpsservice.utils.MyLog;

public class SmsListener extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context.getApplicationContext(),"SMS", Toast.LENGTH_LONG).show();
        MyLog.show("SMS");
        if (intent.getAction().equals(SMS_RECEIVED) || intent.getAction().equals(BOOT_COMPLETED)) {
            Toast.makeText(context.getApplicationContext(),"SMS ПОЛУЧИЛ", Toast.LENGTH_LONG).show();
            MyLog.show("SMS ПОЛУЧИЛ");
        }
    }
}
