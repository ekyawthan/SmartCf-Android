package kyawthanmong.cystic.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import kyawthanmong.cystic.controller.MainActivity;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public AlarmBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service1 = new Intent(context, AlarmService.class);
        context.startService(service1);

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
