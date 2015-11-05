package kyawthanmong.cfsmart.network;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import kyawthanmong.cfsmart.adapter.RestClientAdapter;
import kyawthanmong.cfsmart.adapter.Settings;
import kyawthanmong.cfsmart.delegate.CompleteHandler;

/**
 * Created by kyawthan on 11/4/15.
 */
public class User {

    public User(){

    }

    public static  void loginInBackgroung(String user_id, final CompleteHandler completeHandler){

        RestClientAdapter.getWithoutParam("/user/" + user_id + "/",  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    completeHandler.onSuccessed();
                } else {
                    completeHandler.onFailed();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                completeHandler.onFailed();

            }
        });

    }

    public static void PostSurveyInBackgroud(ArrayList<Integer> answerList, Context context , final CompleteHandler completeHandler) {

        RequestParams params = new RequestParams();
        for(int i= 1; i<= 14; i++){
            params.put("question" + i, answerList.get(i - 1));
        }
        params.put("author", Settings.sharedInstance(context).getUserId());
        params.put("delay_counter", Settings.sharedInstance(context).getDelayCounter());


        RestClientAdapter.post("survey/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 201) {
                    completeHandler.onSuccessed();
                }else {
                    completeHandler.onFailed();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                completeHandler.onFailed();


            }
        });

    }
}


