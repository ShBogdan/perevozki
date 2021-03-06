package work.to.time.gpsservice.net;


import work.to.time.gpsservice.observer.Publisher;
import work.to.time.gpsservice.observer.net.NetSubscriber;

public interface Net extends Publisher<NetSubscriber> {

    void authorize(String username, String password, String deviceId);

    void sendCoord(String longitude, String latitude, String timestamp, String routeId, String header);

    void activeOrders(String deviceId, String token);

    void archiveOrders(String deviceId, String token);

    void suitableOrders(String id, String token);

    void activeRoutes(String deviceId, String token);

    void archiveRoutes(String deviceId, String token);

    void suitableRoutes(String id, String token);

}
