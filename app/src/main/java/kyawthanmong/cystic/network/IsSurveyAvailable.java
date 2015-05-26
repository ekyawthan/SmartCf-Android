package kyawthanmong.cystic.network;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import kyawthanmong.cystic.adapter.Constant;
import kyawthanmong.cystic.adapter.RestClientAdapter;
import kyawthanmong.cystic.adapter.Settings;
import kyawthanmong.cystic.adapter.model.ModelSurvey;
import kyawthanmong.cystic.delegate.InterfaceIsSurveyAvailable;

/**
 * Created by kyawthan on 4/20/15.
 */
public class IsSurveyAvailable {


    private Settings settings;
    private Context context;
    private InterfaceIsSurveyAvailable delegate;
    private String TAG = IsSurveyAvailable.class.getSimpleName();

    public IsSurveyAvailable(Context context, InterfaceIsSurveyAvailable interfaceIsSurveyAvailable) {
        this.context = context;
        this.delegate = interfaceIsSurveyAvailable;
        this.settings = new Settings(context);
        shouldSerializeData();
    }

    private void shouldSerializeData() {
        RequestParams params = new RequestParams();
        params.add("id", settings.getUserId());
        shouldCheckServerIfSurveyAvaible(params);
    }

    private void shouldCheckServerIfSurveyAvaible(RequestParams params) {
        RestClientAdapter.post(Constant.SURVEYAVAILABLE_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, new String(responseBody));
                ModelSurvey modelSurvey = new ModelSurvey();
                Gson gson = new Gson();
                modelSurvey = gson.fromJson(new String(responseBody), ModelSurvey.class);
                if (modelSurvey.successId == 1) {
                    delegate.issurveyavaible(true);
                } else {
                    delegate.issurveyavaible(false);
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}