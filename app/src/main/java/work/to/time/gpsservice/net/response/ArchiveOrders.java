package work.to.time.gpsservice.net.response;

import java.util.List;

public class ArchiveOrders {

    public List<Order> data;

    public ArchiveOrders(List<Order> data) {
        this.data = data;
    }

    public class Order {
        private String id;
        private String user;
        private String fromCity;
        private String toCity;
        private String latitudeStart;
        private String longitudeStart;
        private String fromAddress;
        private String latitudeEnd;
        private String longitudeEnd;
        private String toAddress;
        private String goodtypes;
        private String timeFrom;
        private String timeTo;
        private String evaluated;
        private String cost;
        private String costType;
        private String pays;
        private String description;
        private String loadType;
        private String loadRadius;
        private String recipientName;
        private String recipientPhone;
        private String suitableCount;
        private String status;
        private Waybill waybill;
        private String routeId;
        private String wantTrack;
        private String createdAt;
        private String updatedAt;

        public Order(Waybill waybill) {
            this.waybill = waybill;
        }

        public class Waybill {
            private String id;
            private String status;
            private String driverSigned;
            private String senderSigned;
            private String recipientSigned;
            private String createdAt;
        }


        public String getUser() {
            return user;
        }

        public String getFromCity() {
            return fromCity;
        }

        public String getToCity() {
            return toCity;
        }

    }

}
