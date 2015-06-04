package kyawthanmong.cystic.controller;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import kyawthanmong.cystic.Alarm.AlarmBroadcastReceiver;
import kyawthanmong.cystic.AppUtils;
import kyawthanmong.cystic.R;
import kyawthanmong.cystic.adapter.Settings;
import kyawthanmong.cystic.adapter.Survey;


public class MainActivity extends ActionBarActivity

{

    private Button                  surveyButton, logOut;
    private TextView                textViewSurveyAvailable;
    private Settings                settings;
    private PendingIntent           pendingIntent;

    private String TAG = "MAIN";


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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = format.format(calendar.getTime());
        Log.i(TAG, currentTime);

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
                settings.setSurveyTakenStatus(false);
                settings.setSurveyAvailableStatus(false);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });


        setWeeklyAlarm();

//        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
//        r.play();
//        r.stop();
    }


    @Override
    protected void onResume() {
        super.onResume();
        shouldSetUpView();
    }

    private void shouldSetUpView(){
        surveyButton                    = (Button) findViewById(R.id.buttonSurveyXml);
        logOut                          = (Button) findViewById(R.id.logOutButton);
        textViewSurveyAvailable         = (TextView) findViewById(R.id.textViewSurvey);
        surveyButton.setVisibility(View.INVISIBLE);
        textViewSurveyAvailable.setVisibility(View.INVISIBLE);
      Survey survey = new Survey(this);

        if (survey.isSurveyAvailable()){
          surveyButton.setVisibility(View.VISIBLE);
        }

        if (AppUtils.isMondayYet(this)) {
            if(settings.getSurveyAvailableStatus()){
                surveyButton.setVisibility(View.VISIBLE);
            } else {
                textViewSurveyAvailable.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            textViewSurveyAvailable.setVisibility(View.VISIBLE);
        }

    }


    private void setWeeklyAlarm(){
        Calendar monday = Calendar.getInstance();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, AlarmBroadcastReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, monday.getTimeInMillis(),alarmManager.INTERVAL_DAY * 7,pendingIntent);

    }

    @Override
    public void onBackPressed() {
        return;
    }



}
