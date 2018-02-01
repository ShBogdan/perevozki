package work.to.time.gpsservice.net.response;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by User on 020 20.01.18.
 */

public class MessageModel implements Comparable<MessageModel> {

    private String message;
    private String ukr;
    private Integer date;

    public MessageModel(String message, String ukr, Integer date) {
        this.message = message;
        this.ukr = ukr;
        this.date = date;
    }

    public MessageModel(String message) {
        init(message);
    }

    public String getMessage() {
        return message;
    }

    public String getUkr() {
        return ukr;
    }

    public Integer getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageModel that = (MessageModel) o;

        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (ukr != null ? !ukr.equals(that.ukr) : that.ukr != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (ukr != null ? ukr.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@NonNull MessageModel messageModel) {
        return date.compareTo(messageModel.getDate());
    }

    private void init(String message) {
        String[] arr = StringUtils.split(message, "|||");
        this.message = arr[0];
//        this.ukr = arr[1];
//        this.date = Integer.getInteger(arr[2]);
    }

    public String backToString(){
        return message + "<a href=" + ukr +  ">" + date;
    }

}

//      existing.add(
//              StringUtils.substringBetween(value, "", "<a") + "|||" +
//              StringUtils.substringBetween(value, "<a href=", ">") + "|||" +
//              new Date().getTime());