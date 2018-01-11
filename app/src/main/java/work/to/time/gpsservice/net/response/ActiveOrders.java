package work.to.time.gpsservice.net.response;


import java.util.List;

public class ActiveOrders {

    public List<ActiveOrders.Order> data ;

    public ActiveOrders(List<ActiveOrders.Order> data) {
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
        private List<Goods> goodtypes;
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
        private String senderPhone;
        private String suitableCount;
        private String status;
        private ArchiveOrders.Order.Waybill waybill;
        private String routeId;
        private String wantTrack;
        private String createdAt;
        private String updatedAt;

        public Order(ArchiveOrders.Order.Waybill waybill) {
            this.waybill = waybill;
        }

        public String getSenderPhone() {
            return senderPhone;
        }

        public void setSenderPhone(String senderPhone) {
            this.senderPhone = senderPhone;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public void setFromCity(String fromCity) {
            this.fromCity = fromCity;
        }

        public void setToCity(String toCity) {
            this.toCity = toCity;
        }

        public String getLatitudeStart() {
            return latitudeStart;
        }

        public void setLatitudeStart(String latitudeStart) {
            this.latitudeStart = latitudeStart;
        }

        public String getLongitudeStart() {
            return longitudeStart;
        }

        public void setLongitudeStart(String longitudeStart) {
            this.longitudeStart = longitudeStart;
        }

        public String getFromAddress() {
            return fromAddress;
        }

        public void setFromAddress(String fromAddress) {
            this.fromAddress = fromAddress;
        }

        public String getLatitudeEnd() {
            return latitudeEnd;
        }

        public void setLatitudeEnd(String latitudeEnd) {
            this.latitudeEnd = latitudeEnd;
        }

        public String getLongitudeEnd() {
            return longitudeEnd;
        }

        public void setLongitudeEnd(String longitudeEnd) {
            this.longitudeEnd = longitudeEnd;
        }

        public String getToAddress() {
            return toAddress;
        }

        public void setToAddress(String toAddress) {
            this.toAddress = toAddress;
        }

        public List getGoodtypes() {
            return goodtypes;
        }

        public void setGoodtypes(List goodtypes) {
            this.goodtypes = goodtypes;
        }

        public String getTimeFrom() {
            return timeFrom;
        }

        public void setTimeFrom(String timeFrom) {
            this.timeFrom = timeFrom;
        }

        public String getTimeTo() {
            return timeTo;
        }

        public void setTimeTo(String timeTo) {
            this.timeTo = timeTo;
        }

        public String getEvaluated() {
            return evaluated;
        }

        public void setEvaluated(String evaluated) {
            this.evaluated = evaluated;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getCostType() {
            return costType;
        }

        public void setCostType(String costType) {
            this.costType = costType;
        }

        public String getPays() {
            return pays;
        }

        public void setPays(String pays) {
            this.pays = pays;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLoadType() {
            return loadType;
        }

        public void setLoadType(String loadType) {
            this.loadType = loadType;
        }

        public String getLoadRadius() {
            return loadRadius;
        }

        public void setLoadRadius(String loadRadius) {
            this.loadRadius = loadRadius;
        }

        public String getRecipientName() {
            return recipientName;
        }

        public void setRecipientName(String recipientName) {
            this.recipientName = recipientName;
        }

        public String getRecipientPhone() {
            return recipientPhone;
        }

        public void setRecipientPhone(String recipientPhone) {
            this.recipientPhone = recipientPhone;
        }

        public String getSuitableCount() {
            return suitableCount;
        }

        public void setSuitableCount(String suitableCount) {
            this.suitableCount = suitableCount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public ArchiveOrders.Order.Waybill getWaybill() {
            return waybill;
        }

        public void setWaybill(ArchiveOrders.Order.Waybill waybill) {
            this.waybill = waybill;
        }

        public String getRouteId() {
            return routeId;
        }

        public void setRouteId(String routeId) {
            this.routeId = routeId;
        }

        public String getWantTrack() {
            return wantTrack;
        }

        public void setWantTrack(String wantTrack) {
            this.wantTrack = wantTrack;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public class Waybill {
            private String id;
            private String status;
            private String driverSigned;
            private String senderSigned;
            private String recipientSigned;
            private String createdAt;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDriverSigned() {
                return driverSigned;
            }

            public void setDriverSigned(String driverSigned) {
                this.driverSigned = driverSigned;
            }

            public String getSenderSigned() {
                return senderSigned;
            }

            public void setSenderSigned(String senderSigned) {
                this.senderSigned = senderSigned;
            }

            public String getRecipientSigned() {
                return recipientSigned;
            }

            public void setRecipientSigned(String recipientSigned) {
                this.recipientSigned = recipientSigned;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }
        }

        public class Goods {
                       String id;
            String categoryId;
            String orderId;
            String name;
            String width;
            String height;
            String length;
            String weight;
            String partable;
            String quantity;
            String cold;
            String freezed;
            String animal;
            String inspection;
            String model;
            String evaluated;
            String description;

            public String getName() {
                return name;
            }

            @Override
            public String toString() {
                return name;
            }
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
