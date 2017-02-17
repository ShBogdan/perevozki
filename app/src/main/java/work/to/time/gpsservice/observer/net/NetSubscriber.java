package work.to.time.gpsservice.observer.net;


import work.to.time.gpsservice.observer.Subscriber;

public interface NetSubscriber extends Subscriber {
    void onNetSuccess(int requestId, Object data);

    void onNetError(int requestId, String error);

    void onNetConnectionError(int requestId);
}
