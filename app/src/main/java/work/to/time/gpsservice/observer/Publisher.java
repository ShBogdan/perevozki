package work.to.time.gpsservice.observer;

public interface Publisher<S extends Subscriber> {

    void subscribe(S subscriber);

    void unsubscribe(S subscriber);

    boolean isSubscribe(S subscriber);

}
