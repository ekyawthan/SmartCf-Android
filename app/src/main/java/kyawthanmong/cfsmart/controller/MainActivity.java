package kyawthanmong.cfsmart.controller;


import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import android.widget.Button;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.github.jjobes.slidedaytimepicker.SlideDayTimeListener;
import com.github.jjobes.slidedaytimepicker.SlideDayTimePicker;

import io.fabric.sdk.android.Fabric;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import kyawthanmong.cfsmart.Alarm.AlarmBroadcastReceiver;
import kyawthanmong.cfsmart.AppUtils;
import kyawthanmong.cfsmart.R;
import kyawthanmong.cfsmart.adapter.Settings;
import kyawthanmong.cfsmart.adapter.Survey;

public class MainActivity
        extends AppCompatActivity

{

    private Button surveyButton, logOut;
    private TextView textViewSurveyAvailable;
    private Settings settings;
    private PendingIntent pendingIntent;

    private String TAG = "MAIN";

    private Button alarmResetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        this.settings = new Settings(this);

        Log.i(TAG, String.valueOf(settings.getUserId()));
        if (new Survey(this).isSurveyAvailable()) {
            Log.i(TAG, "survey available");
        }
        if (!settings.isUserLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
        setTitle("CF Smart");

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
                settings.reset();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        if (settings.isUserLogin()) {
            //setWeeklyAlarm();
           // setWeeklyAlarm(Settings., 0, 30);
           // resetAlarmTime();

        }

        alarmResetButton = (Button) findViewById(R.id.settingButton);
        if (alarmResetButton != null) {
            alarmResetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resetAlarmTime();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        shouldSetUpView();
    }


    private void shouldSetUpView() {
        surveyButton = (Button) findViewById(R.id.buttonSurveyXml);
        logOut = (Button) findViewById(R.id.logOutButton);
        textViewSurveyAvailable = (TextView) findViewById(R.id.textViewSurvey);
        surveyButton.setVisibility(View.INVISIBLE);
        textViewSurveyAvailable.setVisibility(View.INVISIBLE);
        Survey survey = new Survey(this);

        if (survey.isSurveyAvailable()) {
            surveyButton.setVisibility(View.VISIBLE);
        } else {
            textViewSurveyAvailable.setVisibility(View.VISIBLE);
        }
    }

    private void setWeeklyAlarm(int day, int hour, int minute) {

        Calendar calendar = Calendar.getInstance();
        Log.i("week day", String.valueOf(calendar.get(Calendar.DAY_OF_WEEK))
        );
        Log.i("hour : ---->", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
        if (calendar.get(Calendar.DAY_OF_WEEK) == day) {
            if (calendar.get(Calendar.HOUR_OF_DAY) > hour) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                calendar.set(Calendar.DAY_OF_WEEK, day);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                //calendar.set(Calendar.MINUTE, 0);
            }else {
                calendar.set(Calendar.DAY_OF_WEEK, day);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                //calendar.set(Calendar.SECOND, 0);


            }
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK) > day){
            calendar.set(Calendar.DAY_OF_WEEK, day);
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            //calendar.set(Calendar.SECOND, 0);

        }

        else {
            calendar.set(Calendar.DAY_OF_WEEK, day);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
        }

        calendar.set(Calendar.SECOND, 0);



        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.i("setting alarm: ", format.format(calendar.getTime()));
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(MainActivity.this, AlarmBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                alarmManager.INTERVAL_DAY * 7, pendingIntent);


    }

    @Override
    public void onBackPressed() {
        return;
    }


    private void resetAlarmTime() {
        new SlideDayTimePicker.Builder(getSupportFragmentManager())
                .setListener(new SlideDayTimeListener() {
                    @Override
                    public void onDayTimeSet(int day, int hour, int minute) {
                       // hour = hour - 12 ;
                        Settings.sharedInstance(MainActivity.this).setAlarmDay(day);
                        Settings.sharedInstance(MainActivity.this).setAlarmHour(hour);
                        Settings.sharedInstance(MainActivity.this).setAlarmMin(minute);
                        Log.i(TAG, " day : " + day);
                        Log.i(TAG, " hour : " + hour);
                        Log.i(TAG, " minute : " + minute);
                        setWeeklyAlarm(day, hour, minute);


                    }
                })
                .setInitialDay(Settings.sharedInstance(this).getAlarmDay())
                .setInitialHour(Settings.sharedInstance(this).getAlarmHour() + 12)
                .setInitialMinute(Settings.sharedInstance(this).getAlarmMin())
                .build()
                .show();
    }


}
