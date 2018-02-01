package work.to.time.gpsservice.net.response;

import java.io.Serializable;
import java.util.List;

public class RouteModel extends BaseResponse implements Serializable {

    public List<?> errors;

    public List<Rotes> data;

    public RouteModel(List<Rotes> data) {
        this.data = data;
    }

    public class Rotes {
        Integer id = 0;
        List<ActiveOrders.Order> orders;
        String status;
        String statusName;
        Boolean current;
        Integer suitableCount;
        String auto;
        List<?> categories;
        String fromAddress;
        String toAddress;
        String fromCity;
        String toCity;
        String latitudeStart;
        String longitudeStart;
        List<?> nonZeroWaypoints;
        List<?> activeWaybills;
        String latitudeEnd;
        String longitudeEnd;
        String timeStartFrom;
        String timeStartTo;
        String timeEndFrom;
        String timeEndTo;
        String dateStartFrom;
        String dateStartTo;
        String dateEndFrom;
        String dateEndTo;
        String loadType;
        Boolean track;
        String createdAt;
        String updatedAt;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public void setCurrent(Boolean current) {
            this.current = current;
        }

        public void setSuitableCount(Integer suitableCount) {
            this.suitableCount = suitableCount;
        }

        public void setAuto(String auto) {
            this.auto = auto;
        }

        public void setCategories(List<?> categories) {
            this.categories = categories;
        }

        public void setFromAddress(String fromAddress) {
            this.fromAddress = fromAddress;
        }

        public void setToAddress(String toAddress) {
            this.toAddress = toAddress;
        }

        public void setFromCity(String fromCity) {
            this.fromCity = fromCity;
        }

        public void setToCity(String toCity) {
            this.toCity = toCity;
        }

        public void setLatitudeStart(String latitudeStart) {
            this.latitudeStart = latitudeStart;
        }

        public void setLongitudeStart(String longitudeStart) {
            this.longitudeStart = longitudeStart;
        }

        public void setNonZeroWaypoints(List<?> nonZeroWaypoints) {
            this.nonZeroWaypoints = nonZeroWaypoints;
        }

        public void setActiveWaybills(List<?> activeWaybills) {
            this.activeWaybills = activeWaybills;
        }

        public void setLatitudeEnd(String latitudeEnd) {
            this.latitudeEnd = latitudeEnd;
        }

        public void setLongitudeEnd(String longitudeEnd) {
            this.longitudeEnd = longitudeEnd;
        }

        public void setTimeStartFrom(String timeStartFrom) {
            this.timeStartFrom = timeStartFrom;
        }

        public void setTimeStartTo(String timeStartTo) {
            this.timeStartTo = timeStartTo;
        }

        public void setTimeEndFrom(String timeEndFrom) {
            this.timeEndFrom = timeEndFrom;
        }

        public void setTimeEndTo(String timeEndTo) {
            this.timeEndTo = timeEndTo;
        }

        public void setDateStartFrom(String dateStartFrom) {
            this.dateStartFrom = dateStartFrom;
        }

        public void setDateStartTo(String dateStartTo) {
            this.dateStartTo = dateStartTo;
        }

        public void setDateEndFrom(String dateEndFrom) {
            this.dateEndFrom = dateEndFrom;
        }

        public void setDateEndTo(String dateEndTo) {
            this.dateEndTo = dateEndTo;
        }

        public void setLoadType(String loadType) {
            this.loadType = loadType;
        }

        public void setTrack(Boolean track) {
            this.track = track;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getId() {
            return id;
        }

        public String getStatusName() {
            return statusName;
        }

        public Boolean getCurrent() {
            return current;
        }

        public Integer getSuitableCount() {
            return suitableCount;
        }

        public String getAuto() {
            return auto;
        }

        public List<?> getCategories() {
            return categories;
        }

        public String getFromAddress() {
            return fromAddress;
        }

        public String getToAddress() {
            return toAddress;
        }

        public String getFromCity() {
            return fromCity;
        }

        public String getToCity() {
            return toCity;
        }

        public String getLatitudeStart() {
            return latitudeStart;
        }

        public String getLongitudeStart() {
            return longitudeStart;
        }

        public List<?> getNonZeroWaypoints() {
            return nonZeroWaypoints;
        }

        public List<?> getActiveWaybills() {
            return activeWaybills;
        }

        public String getLatitudeEnd() {
            return latitudeEnd;
        }

        public String getLongitudeEnd() {
            return longitudeEnd;
        }

        public String getTimeStartFrom() {
            return timeStartFrom;
        }

        public String getTimeStartTo() {
            return timeStartTo;
        }

        public String getTimeEndFrom() {
            return timeEndFrom;
        }

        public String getTimeEndTo() {
            return timeEndTo;
        }

        public String getDateStartFrom() {
            return dateStartFrom;
        }

        public String getDateStartTo() {
            return dateStartTo;
        }

        public String getDateEndFrom() {
            return dateEndFrom;
        }

        public String getDateEndTo() {
            return dateEndTo;
        }

        public String getLoadType() {
            return loadType;
        }

        public Boolean getTrack() {
            return track;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public List<ActiveOrders.Order> getOrders() {
            return orders;
        }

        public void setOrders(List<ActiveOrders.Order> orders) {
            this.orders = orders;
        }

        @Override
        public String toString() {
            return "Rotes{" +
                    "id=" + id +
                    ", statusName='" + statusName + '\'' +
                    ", orders='" + orders + '\'' +
                    ", current=" + current +
                    ", suitableCount=" + suitableCount +
                    ", auto='" + auto + '\'' +
                    ", categories=" + categories +
                    ", fromAddress='" + fromAddress + '\'' +
                    ", toAddress='" + toAddress + '\'' +
                    ", fromCity='" + fromCity + '\'' +
                    ", toCity='" + toCity + '\'' +
                    ", latitudeStart='" + latitudeStart + '\'' +
                    ", longitudeStart='" + longitudeStart + '\'' +
                    ", nonZeroWaypoints=" + nonZeroWaypoints +
                    ", activeWaybills=" + activeWaybills +
                    ", latitudeEnd='" + latitudeEnd + '\'' +
                    ", longitudeEnd='" + longitudeEnd + '\'' +
                    ", timeStartFrom='" + timeStartFrom + '\'' +
                    ", timeStartTo='" + timeStartTo + '\'' +
                    ", timeEndFrom='" + timeEndFrom + '\'' +
                    ", timeEndTo='" + timeEndTo + '\'' +
                    ", dateStartFrom='" + dateStartFrom + '\'' +
                    ", dateStartTo='" + dateStartTo + '\'' +
                    ", dateEndFrom='" + dateEndFrom + '\'' +
                    ", dateEndTo='" + dateEndTo + '\'' +
                    ", loadType='" + loadType + '\'' +
                    ", track=" + track +
                    ", createdAt=" + createdAt +
                    ", updatedAt=" + updatedAt +
                    '}';
        }
    }

    private Integer perPage;
    private Integer totalCount;
    private Integer pageCount;
    private Boolean verified;
    private Integer userId;
    private String role;

    public void setErrors(List<?> errors) {
        this.errors = errors;
    }

    public void setData(List<Rotes> data) {
        this.data = data;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RouteModel{" +
                "errors=" + errors +
                ", data=" + data +
                ", perPage=" + perPage +
                ", totalCount=" + totalCount +
                ", pageCount=" + pageCount +
                ", verified=" + verified +
                ", userId=" + userId +
                '}';
    }
}
