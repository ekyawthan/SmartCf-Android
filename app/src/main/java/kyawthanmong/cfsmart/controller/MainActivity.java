package kyawthanmong.cfsmart.controller;


import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import android.widget.Button;
import android.widget.TextView;

import com.github.jjobes.slidedaytimepicker.SlideDayTimeListener;
import com.github.jjobes.slidedaytimepicker.SlideDayTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        if (getSupportActionBar() != null) {
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
                settings.reset();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        if (settings.isUserLogin()) {
            //setWeeklyAlarm();
            setWeeklyAlarm(2, 0, 30);
            resetAlarmTime();

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
        if (calendar.get(Calendar.DAY_OF_WEEK) == day) {
            if (calendar.get(Calendar.HOUR) > hour) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
            }
        } else {
            calendar.set(Calendar.DAY_OF_WEEK, day);
            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.MINUTE, minute);
        }


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
                        Log.i(TAG, String.valueOf(day) + " " + String.valueOf(hour));
                        hour = hour - 12 ;
                        setWeeklyAlarm(day, hour, minute);


                    }
                })
                .setInitialDay(2)
                .setInitialHour(12)
                .setInitialMinute(30)
                .build()
                .show();
    }


}
