
package ec.gob.dinardap.autorizacion.pojo.token;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Return {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("authorizationContextToken")
    @Expose
    private String authorizationContextToken;
    @SerializedName("authorizedUser")
    @Expose
    private String authorizedUser;
    @SerializedName("errorMsg")
    @Expose
    private String errorMsg;
    @SerializedName("expiryTime")
    @Expose
    private String expiryTime;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("valid")
    @Expose
    private String valid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthorizationContextToken() {
        return authorizationContextToken;
    }

    public void setAuthorizationContextToken(String authorizationContextToken) {
        this.authorizationContextToken = authorizationContextToken;
    }

    public String getAuthorizedUser() {
        return authorizedUser;
    }

    public void setAuthorizedUser(String authorizedUser) {
        this.authorizedUser = authorizedUser;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

}
