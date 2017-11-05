package work.to.time.gpsservice.net.response;

public class AuthModel extends BaseResponse {

    private String token;
    private String error;
    private Boolean verified;
    private Integer userId;

    public AuthModel(String token, String error, Boolean verified, Integer userId) {
        this.token = token;
        this.error = error;
        this.verified = verified;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
