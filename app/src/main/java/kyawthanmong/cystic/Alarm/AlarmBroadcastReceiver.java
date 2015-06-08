package kyawthanmong.cystic.Alarm;




import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;
import android.util.Log;
import kyawthanmong.cystic.adapter.Settings;
import kyawthanmong.cystic.controller.MainActivity;

public class AlarmBroadcastReceiver extends BroadcastReceiver
{

    private String TAG = AlarmBroadcastReceiver.class.getCanonicalName();
    private Settings settings;
    public AlarmBroadcastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i(TAG, "received");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Intent i = new Intent(context, MainActivity.TriggeredActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1,
            i, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() ,
            pendingIntent);


    }
}
