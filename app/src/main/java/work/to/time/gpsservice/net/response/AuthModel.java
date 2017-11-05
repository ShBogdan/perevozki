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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthModel authModel = (AuthModel) o;

        if (token != null ? !token.equals(authModel.token) : authModel.token != null) return false;
        if (error != null ? !error.equals(authModel.error) : authModel.error != null) return false;
        if (verified != null ? !verified.equals(authModel.verified) : authModel.verified != null)
            return false;
        return userId != null ? userId.equals(authModel.userId) : authModel.userId == null;
    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (verified != null ? verified.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthModel{" +
                "token='" + token + '\'' +
                ", error='" + error + '\'' +
                ", verified=" + verified +
                ", userId=" + userId +
                '}';
    }

}
