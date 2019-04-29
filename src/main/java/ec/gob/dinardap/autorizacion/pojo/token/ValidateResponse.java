
package ec.gob.dinardap.autorizacion.pojo.token;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidateResponse {

    @SerializedName("return")
    @Expose
    private Return _return;

    public Return getReturn() {
        return _return;
    }

    public void setReturn(Return _return) {
        this._return = _return;
    }

}
