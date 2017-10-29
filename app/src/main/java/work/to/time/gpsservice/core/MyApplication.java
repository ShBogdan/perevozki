package work.to.time.gpsservice.core;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import work.to.time.gpsservice.net.NetManager;
import work.to.time.gpsservice.utils.MyLog;

public class MyApplication extends MultiDexApplication {

    NetManager netManager;

    private boolean activityVisible;

    public boolean isActivityVisible() {
        return activityVisible;
    }

    public void activityResumed() {
        activityVisible = true;
    }

    public void activityPaused() {
        activityVisible = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        netManager = new NetManager();

    }

    public NetManager getNetManager() {
        return netManager;
    }

    public void setNetManager(NetManager netManager) {
        this.netManager = netManager;
    }

}
