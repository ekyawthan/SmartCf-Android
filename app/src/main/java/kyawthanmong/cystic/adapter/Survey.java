package kyawthanmong.cystic.adapter;

import android.content.Context;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import kyawthanmong.cystic.AppUtils;

/**
 * Created by kyawthan on 6/1/15.
 */
public class Survey implements Serializable{
  private Settings settings;
  private Context context;
  SimpleDateFormat format;

  public Survey(Context context){
    this.context = context;
    this.settings = new Settings(context);
    this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


  }

  public boolean isSurveyAvailable() {
    if (AppUtils.isOnline(context)){
      if (AppUtils.isMondayYet(context)){
        if(isTodaySurveyNotTakenYet()){
          return true;
        }
      }
    }

    return false;
  }

  private boolean isTodaySurveyNotTakenYet() {

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
      if((currentDate.getTime() - lastSurvey.getTime()) > 76400000){
        return true;
      }

    }


    return false;
  }




}
