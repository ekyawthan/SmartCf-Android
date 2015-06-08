package kyawthanmong.cfsmart.adapter.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kyawthan on 5/21/15.
 */
public class LoginResponseModel implements Serializable {
    //{"success":1,"message":"Login successful!"}

    @SerializedName("success")
    public int loginCode;
    @SerializedName("message")
    public String loginMessage;
}
