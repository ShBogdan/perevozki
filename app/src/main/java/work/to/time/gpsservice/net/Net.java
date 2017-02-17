package work.to.time.gpsservice.net;


import work.to.time.gpsservice.observer.Publisher;
import work.to.time.gpsservice.observer.net.NetSubscriber;

public interface Net extends Publisher<NetSubscriber> {
    void authorize(String username,
                   String password);

    void sendCoord(String longitude,
                   String latitude, String timestamp, String header);


}
