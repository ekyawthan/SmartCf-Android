package kyawthanmong.cystic.network;



import android.content.Context;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import kyawthanmong.cystic.adapter.Constant;
import kyawthanmong.cystic.adapter.RestClientAdapter;
import kyawthanmong.cystic.adapter.Settings;
import kyawthanmong.cystic.delegate.InterfacePostServery;

/**
 * Created by kyawthan on 4/15/15.
 */
public class POSTSurvey {

    private InterfacePostServery delegate;
    private Settings settings;
    private String choices, answer, delayCount;

    private Context mContext;

    public POSTSurvey(  Context context, String choices, String answer, String delayCount, InterfacePostServery delegate )
    {
        this.delegate = delegate;
        this.mContext = context;
        this.settings = new Settings(context);
        this.choices = choices;
        this.answer = answer;
        this.delayCount = delayCount;
        shouldPostServey();
    }

    private void shouldPostServey() {
        RequestParams params = new RequestParams();
        params.put("id", settings.getUserId());
        params.add("delaycount", this.delayCount);
        params.add("choices", this.choices);
        params.add("answers", this.answer);

        shouldMakeServerRequest(params);
    }

    private void shouldMakeServerRequest(RequestParams params) {
        RestClientAdapter.post(Constant.SURVEY_POST, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                delegate.didSucessedPostServey();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegate.didFailedPostServer();

            }
        });
    }



}
