package kyawthanmong.cfsmart.network;



import android.content.Context;
import android.util.Log;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.Header;
import kyawthanmong.cfsmart.adapter.RestClientAdapter;
import kyawthanmong.cfsmart.adapter.Settings;
import kyawthanmong.cfsmart.delegate.InterfacePostServery;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyawthan on 4/15/15.
 */
public class POSTSurvey {

    private InterfacePostServery delegate;
    private Settings settings;
    private String TAG = POSTSurvey.class.getCanonicalName();



    public POSTSurvey(Context context, ArrayList<Integer> answerList,
        InterfacePostServery delegate) {
        this.settings = new Settings(context);
        this.delegate = delegate;
        shouldPostSurvey(answerList);

    }

    private void shouldPostSurvey(ArrayList<Integer> answerList) {
        JSONObject params = new JSONObject();

        try {
            params.put("author", settings.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("question1", answerList.get(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("question2", answerList.get(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("question3", answerList.get(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("question4", answerList.get(3));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("question5", answerList.get(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("question6", answerList.get(5));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("question7", answerList.get(6));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("question8", answerList.get(7));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("question9", answerList.get(8));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("question10", answerList.get(9));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("question11", answerList.get(10));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("question12", answerList.get(11));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            params.put("question13", answerList.get(12));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("question14", answerList.get(13));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("delay_counter", settings.getDelayCounter());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            StringEntity entity = new StringEntity(params.toString());
            shouldMakeServerRequest(entity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /*
        {
    "author": "1000",
    "question1": 1,
    "question2": 0,
    "question3": 1,
    "question4": 0,
    "question5": 0,
    "question6": 1,
    "question7": 0,
    "question8": 1,
    "question9": 0,
    "question10": 1,
    "question11": 1,
    "question12": 0,
    "delay_counter": 2
}
         */


        Log.i(TAG, params.toString());
    }

    private void shouldMakeServerRequest(StringEntity params) {
        RestClientAdapter.post("survey/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("status code ", String.valueOf(statusCode));
                Log.i(TAG, new String(responseBody));
                if (statusCode == 201){
                    Log.i(TAG, new String(responseBody));
                    delegate.didSucceedPostSurvey();
                }else {

                    delegate.didFailedPostServer();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "network failed");
                error.printStackTrace(System.out);
                delegate.didFailedPostServer();

            }
        });
    }



}
