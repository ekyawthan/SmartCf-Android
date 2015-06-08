package kyawthanmong.cystic.adapter;

import android.content.Context;
import android.util.Log;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import kyawthanmong.cystic.AppUtils;

/*;
 * Created by kyawthan on 6/1/15.
 */
public class Survey implements Serializable{
  private Settings settings;
  private Context context;
  SimpleDateFormat format;
  private String TAG = Survey.class.getCanonicalName();

  public Survey(Context context){
    this.context = context;
    this.settings = new Settings(context);
    this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


  }

  public boolean isSurveyAvailable() {
    if (AppUtils.isOnline(context)){
      Log.i(TAG, "online");
      if (AppUtils.isMondayYet(context)){
        Log.i(TAG, "Its Monday");
        if(isTodaySurveyNotTakenYet()){

          return true;
        }
      }
    }

    return false;
  }

  public boolean isTodaySurveyNotTakenYet() {

    if(isTimeDifferentMoreThanOneDay(settings.getLastSurveyTakenDate())){
      return true;
    }

    return false;
  }

  private boolean isTimeDifferentMoreThanOneDay(String lastSurveyDate) {
    Date lastSurvey = null, currentDate = null;
    try {
      lastSurvey = format.parse(lastSurveyDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    Calendar calendar = Calendar.getInstance();

    currentDate = calendar.getTime();
    if ((currentDate != null ) && (lastSurveyDate != null)){
      Log.i(TAG, String.valueOf(currentDate.getTime() - lastSurvey.getTime()));
      if((currentDate.getTime() - lastSurvey.getTime()) > 66400000){
        Log.i(TAG, "yes its more than one");
        return true;
      }

    }


    return false;
  }

  public void resetOnSurveyToken (){
    settings.setDelayCounter(0);
    Calendar calendar = Calendar.getInstance();

    settings.setLastSurveyTakenDate(format.format(calendar.getTime()));
  }

  public void settingsOnSnooz(){
    int counter = settings.getDelayCounter();
    settings.setDelayCounter(counter + 1);
  }



}
