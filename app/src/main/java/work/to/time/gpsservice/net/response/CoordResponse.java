package work.to.time.gpsservice.net.response;

import java.util.List;

public class CoordResponse extends BaseResponse {

    public List<MyData> data;
    public List<String> errors;

    public CoordResponse(List<MyData> data, List<String> errors) {
        this.data = data;
        this.errors = errors;
    }

    public class MyData {
        private String longitude;
        private String latitude;
        private String created_at;
        private String timestamp;
        private int user_id;
        private int id;

        public String getLongitude() {
            return longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getCreated_at() {
            return created_at;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getId() {
            return id;
        }

        public List<String> getErrorMassage() {
            return errors;
        }
    }

}


