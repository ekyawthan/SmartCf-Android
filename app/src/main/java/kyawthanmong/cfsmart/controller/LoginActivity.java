package kyawthanmong.cfsmart.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.RequestParams;
import com.rengwuxian.materialedittext.MaterialEditText;
import net.steamcrafted.loadtoast.LoadToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kyawthanmong.cfsmart.Alarm.AlarmBroadcastReceiver;
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
            setWeeklyAlarm(
                    Settings.sharedInstance(LoginActivity.this).getAlarmDay(),
                    Settings.sharedInstance(LoginActivity.this).getAlarmHour(),
                    Settings.sharedInstance(LoginActivity.this).getAlarmMin() );
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

            finish();

          }
        });


      }
    }
  }



  private void setWeeklyAlarm(int day, int hour, int minute) {

    Calendar calendar = Calendar.getInstance();
    Log.i("week day", String.valueOf(calendar.get(Calendar.DAY_OF_WEEK))
    );
    if (calendar.get(Calendar.DAY_OF_WEEK) == day) {
      if (calendar.get(Calendar.HOUR) > hour) {
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.MINUTE, 0);
      }else {
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.MINUTE, 0);

      }
    }
    else if(calendar.get(Calendar.DAY_OF_WEEK) > day){
      calendar.set(Calendar.DAY_OF_WEEK, day);
      calendar.add(Calendar.WEEK_OF_YEAR, 1);
      calendar.set(Calendar.HOUR, hour);
      calendar.set(Calendar.MINUTE, minute);
      calendar.set(Calendar.SECOND, 0);

    }

    else {
      calendar.set(Calendar.DAY_OF_WEEK, day);
      calendar.set(Calendar.HOUR, hour);
      calendar.set(Calendar.MINUTE, minute);
      calendar.set(Calendar.SECOND, 0);
    }


    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Log.i("setting alarm: ", format.format(calendar.getTime()));
    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    Intent myIntent = new Intent(LoginActivity.this, AlarmBroadcastReceiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(LoginActivity.this, 0, myIntent, 0);

    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
            alarmManager.INTERVAL_DAY * 7, pendingIntent);


  }


}
