package kyawthanmong.cfsmart.network;

import android.app.ProgressDialog;
import android.content.Context;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.apache.http.Header;

import kyawthanmong.cfsmart.adapter.RestClientAdapter;
import kyawthanmong.cfsmart.adapter.Settings;
import kyawthanmong.cfsmart.delegate.InterfaceLogin;

/**
 * Created by kyawthan on 4/15/15.
 */
public class POSTLogin {

    private InterfaceLogin delegate ;
    private Settings settings;
    private String TAG = POSTLogin.class.getName();
    private String user_id ;
    private ProgressDialog progressDialog;
    private Context mContext;


    public POSTLogin(Context context,String id, InterfaceLogin interfaceLogin)
    {
        this.delegate = interfaceLogin;
        this.user_id = id;
        this.mContext= context;
        this.settings = new Settings(context);
        settings.setUserId(id);
        shouldRequestingLogin(id);


    }

    private void shouldRequestingLogin(String id) {
        RequestParams params = new RequestParams();
        params.add("id", id);
        shouldContactServer(params);
    }

    private void shouldContactServer(RequestParams params) {

        RestClientAdapter.getWithoutParam("/user/" + user_id + "/", new AsyncHttpResponseHandler() {
            @Override public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, String.valueOf(statusCode));
                if (statusCode == 200){
                    Settings.sharedInstance(mContext).setUserLoginStatus(true);
                    delegate.didLoginSucceess();
                }else {
                    delegate.didLoginFail();
                }

            }

            @Override public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                Throwable error) {
                delegate.didLoginFail();

            }
        });


       
    }


}
