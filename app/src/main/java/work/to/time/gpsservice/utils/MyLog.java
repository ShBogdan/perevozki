package work.to.time.gpsservice.utils;


import android.util.Log;

public class MyLog {
    final private static String LOG_TAG = "MyLog";

    public static void show(String massage){
        Log.d(LOG_TAG, massage);
    }
}
