package kyawthanmong.cystic.Alarm;




import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;
import kyawthanmong.cystic.adapter.Settings;

public class AlarmBroadcastReceiver extends BroadcastReceiver
{

    private Settings settings;
    public AlarmBroadcastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent)
    {



        //settings = new Settings(context);
        //if (settings.getSurveyAvailableStatus()){
        //    Calendar calendar = Calendar.getInstance();
        //
        //    Intent myIntent = new Intent(context, FiveTimeAlarmBroadcastReceiver.class);
        //    myIntent.putExtra("fiveTimesAlarm", 1);
        //    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);
        //
        //    AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        //    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 30, pendingIntent);
        //
        //}




    }
}
