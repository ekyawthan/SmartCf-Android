package kyawthanmong.cystic.controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;

import net.steamcrafted.loadtoast.LoadToast;

import kyawthanmong.cystic.AppUtils;
import kyawthanmong.cystic.R;
import kyawthanmong.cystic.adapter.Settings;
import kyawthanmong.cystic.delegate.InterfaceLogin;
import kyawthanmong.cystic.network.POSTLogin;


public class LoginActivity
        extends ActionBarActivity
        implements InterfaceLogin

{

    private MaterialEditText userId;
    private Settings settings;
    private LoadToast loadToast;
    String id = "1000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.settings = new Settings(this);
        this.loadToast = new LoadToast(this);
        shouldSetupView();

    }


    private void shouldSetupView() {

        userId = (MaterialEditText) findViewById(R.id.userId);


    }


    public void didClickOnSignIn(View view)
    {

        id = userId.getText().toString();

        if (id != "")
        {
            if (AppUtils.isOnline(getApplicationContext())){
                new POSTLogin(this, id, this);
                loadToast.setText("Sign In ......");
                loadToast.show();
        }

        }

    }

    @Override
    public void didLoginSucceess() {
        settings.setUserId(id);
        settings.setUserLoginStatus(true);
        loadToast.success();
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }

    @Override
    public void didLoginFail() {
        loadToast.error();

    }
}
