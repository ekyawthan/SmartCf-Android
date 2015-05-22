package kyawthanmong.cystic.controller;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import kyawthanmong.cystic.AppUtils;
import kyawthanmong.cystic.R;
import kyawthanmong.cystic.adapter.Settings;


public class MainActivity extends ActionBarActivity {

    private Button                  surveyButton, logOut;
    private Settings                settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.settings = new Settings(this);
        if (!settings.isUserLogin())
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        if (getSupportActionBar() != null){
            getSupportActionBar().setElevation(0);

        }
        setTitle("");
        shouldSetUpView();
        surveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtils.isMondayYet(getApplicationContext())) {
                    startActivity(new Intent(getApplicationContext(), SurveyActivity.class));
                }

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setUserLoginStatus(false);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }


    private void shouldSetUpView(){
        surveyButton        = (Button) findViewById(R.id.buttonSurveyXml);
        logOut              = (Button) findViewById(R.id.logOutButton);
        surveyButton.setVisibility(View.INVISIBLE);

        if (AppUtils.isMondayYet(this)) {
            surveyButton.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onBackPressed() {
        return;
    }





    private void shouldSetupAlermOnMonday(){
    }

}
