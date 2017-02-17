package work.to.time.gpsservice.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public final class PermissionsUtils {
    public static class Location {
        private final static String[] LOCATION_PERMISSIONS = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_SMS
        };

        public static boolean isDenied(Activity activity) {
            return ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED;
        }

        public static boolean isNeedRequest(Activity activity) {
            return !ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_SMS);
        }

        public static void request(Activity activity, int requestCode) {
            ActivityCompat.requestPermissions(activity, LOCATION_PERMISSIONS, requestCode);
        }

        public static boolean isGrantedResult(int[] grantResults) {
            for (int grandResult : grantResults) {
                if (grandResult != PackageManager.PERMISSION_GRANTED) return false;
            }
            return true;
        }
    }

}
