package kyawthanmong.cfsmart.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.loopj.android.http.RequestParams;
import com.rengwuxian.materialedittext.MaterialEditText;
import net.steamcrafted.loadtoast.LoadToast;
import kyawthanmong.cfsmart.AppUtils;
import kyawthanmong.cfsmart.R;
import kyawthanmong.cfsmart.adapter.Settings;
import kyawthanmong.cfsmart.delegate.CompleteHandler;
import kyawthanmong.cfsmart.delegate.InterfaceLogin;
import kyawthanmong.cfsmart.network.POSTLogin;
import kyawthanmong.cfsmart.network.User;

public class LoginActivity
    extends AppCompatActivity


{

  private MaterialEditText userId;
  private Settings settings;
  private LoadToast loadToast;
  String id = "1000";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    this.settings = new Settings(this);
    this.loadToast = new LoadToast(this);
    setTitle("Smart-CF");
    shouldSetupView();
  }

  private void shouldSetupView() {

    userId = (MaterialEditText) findViewById(R.id.userId);
  }

  public void didClickOnSignIn(View view) {

    id = userId.getText().toString();

    if (id != "") {
      if (AppUtils.isOnline(getApplicationContext())) {
        loadToast.setText("Sign In ......");
        loadToast.show();
        User.loginInBackgroung(id, new CompleteHandler() {
          @Override
          public void onFailed() {
            loadToast.error();

          }

          @Override
          public void onSuccessed() {
            settings.setUserId(id);
            settings.setUserLoginStatus(true);
            loadToast.success();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();

          }
        });


      }
    }
  }


}
