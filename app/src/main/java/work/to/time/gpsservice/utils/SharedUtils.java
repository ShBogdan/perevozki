package work.to.time.gpsservice.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SharedUtils {
    private static final String NAME = "perevozki";
    private static final String USER_LOGGED_IN = "userLoggedIn";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String DEVICE_ID = "deviceId";
    private static final String UID = "uid";
    private static final String VERIFY = "verify";
    private static final String FCM_MESSAGES = "fcmMessages";


    private static SharedPreferences getSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    private static void save(@NonNull Context context, @NonNull String key, boolean value) {
        getSharedPreferences(context)
                .edit()
                .putBoolean(key, value)
                .apply();
    }

    private static void save(@NonNull Context context, @NonNull String key, String value) {
        getSharedPreferences(context)
                .edit()
                .putString(key, value)
                .apply();
    }

    private static void save(Context context, String key, long value) {
        getSharedPreferences(context)
                .edit()
                .putLong(key, value)
                .apply();
    }

    public static void setUserLoggedIn(@NonNull Context context, boolean value) {
        save(context, USER_LOGGED_IN, value);
    }

    public static boolean isUserLoggedIn(@NonNull Context context) {
        return getSharedPreferences(context).getBoolean(USER_LOGGED_IN, false);
    }

    public static void setAccessToken(@NonNull Context context, String value) {
        save(context, ACCESS_TOKEN, value);
    }

    public static void setVerify(@NonNull Context context, String value) {
        save(context, VERIFY, value);
    }

    @Nullable
    public static String getAccessToken(@NonNull Context context) {
        return getSharedPreferences(context).getString(ACCESS_TOKEN, null);
    }

    @Nullable
    public static String getAccessDeviceId(@NonNull Context context) {
        return getSharedPreferences(context).getString(DEVICE_ID, null);
    }

    @Nullable
    public static String getVerify(@NonNull Context context) {
        return getSharedPreferences(context).getString(VERIFY, null);
    }

    public static void setAccessDeviceId(@NonNull Context context, String value) {
        save(context, DEVICE_ID, value);
    }

    public static void setFcmMessage(@NonNull Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences("MESSAGES", Context.MODE_PRIVATE);
        Set<String> existing = sp.getStringSet(FCM_MESSAGES, new HashSet<String>());
        existing.add(value);
        sp.edit()
                .clear()
                .putStringSet(FCM_MESSAGES, existing)
                .apply();
    }

    public static void updateFcmMessage(@NonNull Context context, List<String> value) {
        SharedPreferences sp = context.getSharedPreferences("MESSAGES", Context.MODE_PRIVATE);
        sp.edit().remove(FCM_MESSAGES).apply();
        Set<String> existing = new HashSet<String>(value);
        sp.edit()
                .clear()
                .putStringSet(FCM_MESSAGES, existing)
                .apply();
    }

    @Nullable
    public static Set<String> getFcmMessage(@NonNull Context context) {
        SharedPreferences sp = context.getSharedPreferences("MESSAGES", Context.MODE_PRIVATE);
        return sp.getStringSet(FCM_MESSAGES, new HashSet<String>());
    }

    public static void setUid(@NonNull Context context, int value) {
        save(context, UID, value);
    }

    @Nullable
    public static String getUid(@NonNull Context context) {
        return getSharedPreferences(context).getString(UID, null);
    }

    public static void clear(Context context) {
        getSharedPreferences(context).edit().clear().commit();
    }

    public static void removeRecord(Context context) {
        getSharedPreferences(context).edit().remove(ACCESS_TOKEN).commit();
    }

}
