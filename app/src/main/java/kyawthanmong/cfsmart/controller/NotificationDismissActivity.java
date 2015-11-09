package kyawthanmong.cfsmart.controller;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;

import kyawthanmong.cfsmart.Alarm.RepeatAlarmBroadcastReceiver;
import kyawthanmong.cfsmart.R;
import kyawthanmong.cfsmart.adapter.Settings;

public class NotificationDismissActivity extends AppCompatActivity {
    public static String NOTIFICATION_ID = "Notification_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(getIntent().getIntExtra(NOTIFICATION_ID, -1));

        // notify 30 min later
        int currentDelayCounter = Settings.sharedInstance(this).getDelayCounter();
        if (currentDelayCounter < 5) {
            currentDelayCounter += 1;
            Settings.sharedInstance(this).setDelayCounter(currentDelayCounter);

            Log.i("Current delay :", String.valueOf(currentDelayCounter));

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, new Intent(this, RepeatAlarmBroadcastReceiver.class), PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager repeatAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Calendar rightNow = Calendar.getInstance();
            rightNow.add(Calendar.SECOND, 30);
            repeatAlarmManager.set(AlarmManager.RTC_WAKEUP, rightNow.getTimeInMillis(), pendingIntent);

        }else {
            Settings.sharedInstance(this).setDelayCounter(0);
        }

        // kill activity
        finish();
    }

    public static PendingIntent getDismissPendingIntent(int notificationId, Context context){

        Intent intent = new Intent(context, NotificationDismissActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(NOTIFICATION_ID, notificationId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;

    }

}
