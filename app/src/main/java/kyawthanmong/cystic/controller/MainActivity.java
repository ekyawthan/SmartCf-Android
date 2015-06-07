package kyawthanmong.cystic.controller;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
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

        if(new Survey(this).isSurveyAvailable()){
            Log.i(TAG, "survey available");
        }
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
            @Override public void onClick(View v) {
                if (AppUtils.isMondayYet(getApplicationContext())) {
                    startActivity(new Intent(getApplicationContext(), SurveyActivity.class));
                }
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                settings.setUserLoginStatus(false);
                settings.setSurveyTakenStatus(false);
                settings.setSurveyAvailableStatus(false);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        if(settings.isUserLogin()){
            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            setAlarm(4, manager, this);
        }



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
      else {
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

    // Nortification Helper Class

    public static class TriggeredActivity extends Activity {

        private Settings settings;

        @Override protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog);
            //Button dismiss = (Button) findViewById(R.id.dismiss);
            //Button snooze = (Button) findViewById(R.id.snooze);
            final Context context = this;
            this.settings = new Settings(this);
            Window wind;
            wind = this.getWindow();
            wind.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            wind.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            wind.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


            showCallbacks(context);



        }

        private void showCallbacks(final Context context) {

                    Uri notification = RingtoneManager.getDefaultUri(
                        RingtoneManager.TYPE_ALARM);
                    final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();

            new MaterialDialog.Builder(this).title("Cf-Smart Survey")
                .content("How are You  Feeling")
                .positiveText("Take Survey")

                .autoDismiss(false)

                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override public void onDismiss(DialogInterface dialog) {
                        r.stop();
                        finish();
                    }
                })
                .negativeText("Snooze")

                .callback(new MaterialDialog.ButtonCallback() {
                    @Override public void onPositive(MaterialDialog dialog) {
                        startActivity(new Intent(context, SurveyActivity.class));
                        r.stop();
                        finish();
                    }

                    @Override public void onNegative(MaterialDialog dialog) {
                        if(settings.getDelayCounter()<6){
                            new Survey(context).settingsOnSnooz();
                            setAlarm(10, ((AlarmManager) getSystemService(ALARM_SERVICE)), context);

                        }{
                            settings.setDelayCounter(0);
                            // set email
                        }

                        r.stop();
                        finish();
                    }
                })
                .show();
        }


    }

    public static final void setAlarm(int seconds, AlarmManager alarmManager,
        Context context) {
        // create the pending intent
        Intent intent = new Intent(context, TriggeredActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
            intent, PendingIntent.FLAG_ONE_SHOT);
        // get the alarm manager, and scedule an alarm that triggers my
        // activity
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
            + seconds * 1000, pendingIntent);
        Toast.makeText(context, "Timer set to " + seconds + " seconds.",
            Toast.LENGTH_SHORT).show();
    }





}
