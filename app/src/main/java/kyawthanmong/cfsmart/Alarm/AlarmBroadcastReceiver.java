package kyawthanmong.cfsmart.Alarm;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import kyawthanmong.cfsmart.R;
import kyawthanmong.cfsmart.adapter.Survey;
import kyawthanmong.cfsmart.controller.NotificationDismissActivity;
import kyawthanmong.cfsmart.controller.SurveyActivity;


public class AlarmBroadcastReceiver extends BroadcastReceiver {

  private String TAG = AlarmBroadcastReceiver.class.getCanonicalName();

  public AlarmBroadcastReceiver() {

  }

  @Override public void onReceive(Context context, Intent intent) {
    Log.i(TAG, "received");

    if (new Survey(context).isTodaySurveyNotTakenYet()) {
      int notificationId = 1001;
      PendingIntent snoozeSurvey = NotificationDismissActivity.getDismissPendingIntent(notificationId, context);
      PendingIntent takeSurvey = PendingIntent.getActivity(context, 0, new Intent(context, SurveyActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);

      NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
      builder.setPriority(NotificationCompat.PRIORITY_MAX)
              .setDefaults(NotificationCompat.DEFAULT_ALL)
              .setSmallIcon(R.drawable.check)
              .setContentTitle("Cf Smart")
              .setAutoCancel(false)

              .setContentText("Time to take a survey")
              .addAction(R.drawable.survey, "Take Now", takeSurvey)
              .addAction(R.drawable.time, "Snooze 30 minutes", snoozeSurvey);
      NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification noti = builder.build();
        noti.flags = Notification.FLAG_ONGOING_EVENT;
      manager.notify(notificationId, noti);


    }
  }
}
