package kyawthanmong.cfsmart.adapter.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kyawthan on 4/21/15.
 */
public class ModelSurvey implements Serializable{

    @SerializedName("success")
    public int successId;
    @SerializedName("message")
    public String message;
}