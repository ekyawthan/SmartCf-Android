package kyawthanmong.cystic.Alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import kyawthanmong.cystic.AppUtils;
import kyawthanmong.cystic.R;
import kyawthanmong.cystic.controller.MainActivity;
import kyawthanmong.cystic.controller.SurveyActivity;

public class AlarmService extends Service {

    NotificationManager notificationManager;
    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        notificationManager  = (NotificationManager)this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);


        if (AppUtils.isMondayYet(this.getApplicationContext())){
            startNotification();

        }else {
            if (notificationManager != null){
                notificationManager.cancel(1001);
            }
        }

    }

    private void startNotification(){

        Intent intentSurvey = new Intent(AlarmService.this, SurveyActivity.class);

        PendingIntent addMealPendingIntent = PendingIntent.getActivity(this, 0, intentSurvey, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentCancel = new Intent(AlarmService.this, MainActivity.class);
        PendingIntent addGlucosePendingIntent = PendingIntent.getActivity(this, 0, intentCancel, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true);

        builder.setContentTitle("Take Survey");
        builder.setContentText("How Are You Feeling?");
        builder.setSmallIcon(R.drawable.cf_smart);

        builder.addAction(R.drawable.ic_action_accept, "", addMealPendingIntent);
        builder.addAction(R.drawable.ic_action_discard, "", addGlucosePendingIntent);
        Notification notification = builder.setOngoing(true).build();
        notificationManager.notify(1001, notification);
    }



}
