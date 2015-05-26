package kyawthanmong.cystic.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import kyawthanmong.cystic.AppUtils;
import kyawthanmong.cystic.adapter.Settings;

public class FiveTimeAlarmBroadcastReceiver extends BroadcastReceiver {
    private Settings settings ;
    public FiveTimeAlarmBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (AppUtils.isMondayYet(context)) {
            Intent service1 = new Intent(context, AlarmService.class);
            context.startService(service1);

        }

        settings = new Settings(context);
        settings.setAlarmLimit(settings.getAlarmLimit() + 1);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (settings.getAlarmLimit() == 5) {
            settings.setAlarmLimit(0);

            int alarmInt = intent.getExtras().getInt("fiveTimesAlarm");

            PendingIntent alarmIntend = PendingIntent.getBroadcast(context, alarmInt,
                    new Intent(context, FiveTimeAlarmBroadcastReceiver.class),
                    PendingIntent.FLAG_CANCEL_CURRENT

            );
            manager.cancel(alarmIntend);

        }

    }
}
