package work.to.time.gpsservice.net.response;

import java.io.Serializable;
import java.util.List;

public class RouteSuitableModel extends BaseResponse implements Serializable {

    public List<?> errors;

    public List<RotesSuitable> data;

    public RouteSuitableModel(List<RotesSuitable> data) {
        this.data = data;
    }

    public class RotesSuitable {
        Integer id;
        String statusName;
        String user;
        String fromCity;
        String toCity;
        String latitudeStart;
        String longitudeStart;
        String fromAddress;
        String latitudeEnd;
        String longitudeEnd;
        String toAddress;
        String goodtypes;
        String timeFrom;
        String timeTo;
        String dateFrom;
        String dateTo;
        String cost;
        String costType;
        String pays;
        String description;
        String loadType;
        String loadRadius;
        String recipientName;
        String recipientPhone;
        String suitableCount;
        String status;
        String waybill;
        String wantTrack;
        String createdAt;
        String updatedAt;
        String displayContent;

        @Override
        public String toString() {
            return "RotesSuitable{" +
                    "id=" + id +
                    ", statusName='" + statusName + '\'' +
                    ", user='" + user + '\'' +
                    ", fromCity='" + fromCity + '\'' +
                    ", toCity='" + toCity + '\'' +
                    ", latitudeStart='" + latitudeStart + '\'' +
                    ", longitudeStart='" + longitudeStart + '\'' +
                    ", fromAddress='" + fromAddress + '\'' +
                    ", latitudeEnd='" + latitudeEnd + '\'' +
                    ", longitudeEnd='" + longitudeEnd + '\'' +
                    ", toAddress='" + toAddress + '\'' +
                    ", goodtypes='" + goodtypes + '\'' +
                    ", timeFrom='" + timeFrom + '\'' +
                    ", timeTo='" + timeTo + '\'' +
                    ", dateFrom='" + dateFrom + '\'' +
                    ", dateTo='" + dateTo + '\'' +
                    ", cost='" + cost + '\'' +
                    ", costType='" + costType + '\'' +
                    ", pays='" + pays + '\'' +
                    ", description='" + description + '\'' +
                    ", loadType='" + loadType + '\'' +
                    ", loadRadius='" + loadRadius + '\'' +
                    ", recipientName=" + recipientName +
                    ", recipientPhone='" + recipientPhone + '\'' +
                    ", suitableCount=" + suitableCount +
                    ", status='" + status + '\'' +
                    ", waybill='" + waybill + '\'' +
                    ", wantTrack='" + wantTrack + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", updatedAt='" + updatedAt + '\'' +
                    ", displayContent='" + displayContent + '\'' +
                    '}';
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
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

        public String getGoodtypes() {
            return goodtypes;
        }

        public void setGoodtypes(String goodtypes) {
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

        public String getDateFrom() {
            return dateFrom;
        }

        public void setDateFrom(String dateFrom) {
            this.dateFrom = dateFrom;
        }

        public String getDateTo() {
            return dateTo;
        }

        public void setDateTo(String dateTo) {
            this.dateTo = dateTo;
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

        public String getWaybill() {
            return waybill;
        }

        public void setWaybill(String waybill) {
            this.waybill = waybill;
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

        public String getDisplayContent() {
            return displayContent;
        }

        public void setDisplayContent(String displayContent) {
            this.displayContent = displayContent;
        }
    }

    private Integer perPage;
    private Integer totalCount;
    private Integer pageCount;
    private Boolean verified;
    private Integer userId;

    public void setErrors(List<?> errors) {
        this.errors = errors;
    }

    public void setData(List<RotesSuitable> data) {
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
