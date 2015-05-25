package kyawthanmong.cystic.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.widget.Toast;



public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private static int counter = 0;


    public AlarmBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service1 = new Intent(context, AlarmService.class);
        context.startService(service1);

        Toast.makeText(context, "Alarm Received", Toast.LENGTH_LONG).show();
        counter = counter + 1;
        Log.i("Current counter ", String.valueOf(counter));

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (counter == 5) {
            int alarmInt = intent.getExtras().getInt("alarmId");
            PendingIntent alarmIntend = PendingIntent.getBroadcast(context, alarmInt,
                    new Intent(context, AlarmBroadcastReceiver.class),
                    PendingIntent.FLAG_CANCEL_CURRENT
            );
            manager.cancel(alarmIntend);
            Toast.makeText(context, "Alarm Cancelled after 5", Toast.LENGTH_LONG).show();

//            Log.i("Send email", "");
//
//            String[] TO = {"ekyawthan@gmail.com"};
//            String[] CC = {"ekyawthan@gmail.com"};
//            Intent emailIntent = new Intent(Intent.ACTION_SEND);
//            emailIntent.setData(Uri.parse("mailto:"));
//            emailIntent.setType("text/plain");
//
//
//            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//            emailIntent.putExtra(Intent.EXTRA_CC, CC);
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
//            emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
//
//            try {
//                context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//
//            } catch (android.content.ActivityNotFoundException ex) {
//                Toast.makeText(context,
//                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
//            }
        }


    }
}
