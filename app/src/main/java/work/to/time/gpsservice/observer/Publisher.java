package work.to.time.gpsservice.observer;

/**
 * Created by Admin on 28.10.2016.
 */

public interface Publisher<S extends Subscriber> {
    void subscribe(S subscriber);

    void unsubscribe(S subscriber);

    boolean isSubscribe(S subscriber);
}
