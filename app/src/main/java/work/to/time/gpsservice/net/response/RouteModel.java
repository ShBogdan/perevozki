package work.to.time.gpsservice.net.response;

import java.util.List;

public class RouteModel extends BaseResponse {

    public List<Rotes> data;

    public RouteModel(List<Rotes> data) {
        this.data = data;
    }

    public class Rotes {
        private String id;
        private String auto;
        private String ategories;
        private String fromAddress;
        private String toAddress;
        private String fromCity;
        private String toCity;
        private String latitudeStart;
        private String longitudeStart;
        private String nonZeroWaypoints;
        private String orders;
        private String latitudeEnd;
        private String longitudeEnd;
        private String timeStartFrom;
        private String timeStartTo;
        private String timeEndFrom;
        private String timeEndTo;
        private String dateStartFrom;
        private String dateStartTo;
        private String dateEndFrom;
        private String dateEndTo;
        private String loadType;
        private String track;
        private String createdAt;
        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuto() {
            return auto;
        }

        public void setAuto(String auto) {
            this.auto = auto;
        }

        public String getAtegories() {
            return ategories;
        }

        public void setAtegories(String ategories) {
            this.ategories = ategories;
        }

        public String getFromAddress() {
            return fromAddress;
        }

        public void setFromAddress(String fromAddress) {
            this.fromAddress = fromAddress;
        }

        public String getToAddress() {
            return toAddress;
        }

        public void setToAddress(String toAddress) {
            this.toAddress = toAddress;
        }

        public String getFromCity() {
            return fromCity;
        }

        public void setFromCity(String fromCity) {
            this.fromCity = fromCity;
        }

        public String getToCity() {
            return toCity;
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

        public String getNonZeroWaypoints() {
            return nonZeroWaypoints;
        }

        public void setNonZeroWaypoints(String nonZeroWaypoints) {
            this.nonZeroWaypoints = nonZeroWaypoints;
        }

        public String getOrders() {
            return orders;
        }

        public void setOrders(String orders) {
            this.orders = orders;
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

        public String getTimeStartFrom() {
            return timeStartFrom;
        }

        public void setTimeStartFrom(String timeStartFrom) {
            this.timeStartFrom = timeStartFrom;
        }

        public String getTimeStartTo() {
            return timeStartTo;
        }

        public void setTimeStartTo(String timeStartTo) {
            this.timeStartTo = timeStartTo;
        }

        public String getTimeEndFrom() {
            return timeEndFrom;
        }

        public void setTimeEndFrom(String timeEndFrom) {
            this.timeEndFrom = timeEndFrom;
        }

        public String getTimeEndTo() {
            return timeEndTo;
        }

        public void setTimeEndTo(String timeEndTo) {
            this.timeEndTo = timeEndTo;
        }

        public String getDateStartFrom() {
            return dateStartFrom;
        }

        public void setDateStartFrom(String dateStartFrom) {
            this.dateStartFrom = dateStartFrom;
        }

        public String getDateStartTo() {
            return dateStartTo;
        }

        public void setDateStartTo(String dateStartTo) {
            this.dateStartTo = dateStartTo;
        }

        public String getDateEndFrom() {
            return dateEndFrom;
        }

        public void setDateEndFrom(String dateEndFrom) {
            this.dateEndFrom = dateEndFrom;
        }

        public String getDateEndTo() {
            return dateEndTo;
        }

        public void setDateEndTo(String dateEndTo) {
            this.dateEndTo = dateEndTo;
        }

        public String getLoadType() {
            return loadType;
        }

        public void setLoadType(String loadType) {
            this.loadType = loadType;
        }

        public String getTrack() {
            return track;
        }

        public void setTrack(String track) {
            this.track = track;
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
    }



}
