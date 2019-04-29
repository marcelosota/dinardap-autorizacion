
package ec.gob.dinardap.autorizacion.pojo.token;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidarTokenResponse {

    @SerializedName("validateResponse")
    @Expose
    private ValidateResponse validateResponse;

    public ValidateResponse getValidateResponse() {
        return validateResponse;
    }

    public void setValidateResponse(ValidateResponse validateResponse) {
        this.validateResponse = validateResponse;
    }

}
