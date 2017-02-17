package work.to.time.gpsservice.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import work.to.time.gpsservice.R;
import work.to.time.gpsservice.utils.Constants;
import work.to.time.gpsservice.utils.MyLog;

public class OnBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context.getApplicationContext(),"Broadcast", Toast.LENGTH_LONG).show();
        MyLog.show("GET_SERVICE");
//        //Bogdan
//        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
//            Toast toast = Toast.makeText(context.getApplicationContext(),
//                    context.getResources().getString(R.string.your_message), Toast.LENGTH_LONG);
//            toast.show();
//            MyLog.show(context.getResources().getString(R.string.your_message));
//        }
    }
}
