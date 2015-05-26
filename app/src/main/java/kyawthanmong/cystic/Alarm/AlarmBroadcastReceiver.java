package kyawthanmong.cystic.Alarm;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import kyawthanmong.cystic.adapter.Settings;

import kyawthanmong.cystic.delegate.AlarmDelegate;


public class AlarmBroadcastReceiver extends BroadcastReceiver
{

    private Settings settings;
    private AlarmDelegate delegate;

    public AlarmBroadcastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent)
    {

        Calendar calendar = Calendar.getInstance();

        Intent myIntent = new Intent(context, FiveTimeAlarmBroadcastReceiver.class);
        myIntent.putExtra("fiveTimesAlarm", 1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 30, pendingIntent);


    }
}
