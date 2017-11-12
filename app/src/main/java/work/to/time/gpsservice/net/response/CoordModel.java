package work.to.time.gpsservice.net.response;

public class CoordModel extends BaseResponse {

    private String longitude;
    private String latitude;
    private String timestamp;
    private int userId;
    private int user_id;
    private int routeId;
    private String created_at;
    private int id;
    private Boolean verified;

    public CoordModel(String longitude,
                      String latitude,
                      String timestamp,
                      int userId,
                      int user_id,
                      int routeId,
                      String created_at,
                      int id,
                      Boolean verified) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
        this.userId = userId;
        this.user_id = user_id;
        this.routeId = routeId;
        this.created_at = created_at;
        this.id = id;
        this.verified = verified;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getRouteId() {
        return routeId;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getId() {
        return id;
    }

    public Boolean getVerified() {
        return verified;
    }

    @Override
    public String toString() {
        return "CoordModel{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", userId=" + userId +
                ", user_id=" + user_id +
                ", routeId=" + routeId +
                ", created_at='" + created_at + '\'' +
                ", id=" + id +
                ", verified=" + verified +
                '}';
    }
}




