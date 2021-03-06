package kyawthanmong.cfsmart.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created by owais on 4/15/15.
 */
public class Settings {

  private SharedPreferences pref;
  private SharedPreferences.Editor editor;
  //

  public static Settings sharedInstance(Context context){
    return  new Settings(context);
  }

  public Settings(Context context) {
    pref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    editor = pref.edit();
  }

  public void setUserLoginStatus(boolean status) {

    this.editor.putBoolean("loginStatus", status).commit();
  }

  public boolean isUserLogin() {
    return pref.getBoolean("loginStatus", false);
  }

  public void setUserId(String id) {
    this.editor.putString("userId", id).commit();
  }

  public String getUserId() {
    return pref.getString("userId", "");
  }

  public void setAlarmLimit(int limit) {
    this.editor.putInt("alarmLimit", 0).commit();
  }

  public int getAlarmLimit() {
    return pref.getInt("alarmLimit", 1);
  }

  public void setSurveyTakenStatus(boolean b) {
    this.editor.putBoolean("survey", b).commit();
  }

  public boolean getSurveyTakenStatus() {
    return pref.getBoolean("survey", false);
  }

  public void setSurveyAvailableStatus(boolean b) {
    this.editor.putBoolean("avaible", b).commit();
  }

  public boolean getSurveyAvailableStatus() {
    return pref.getBoolean("avaible", false);
  }

  public void setDelayCounter(int counter) {
    this.editor.putInt("delayCounter", counter).commit();
  }

  public int getDelayCounter() {
    return pref.getInt("delayCounter", 0);
  }

  public void setLastSurveyTakenDate(String date) {
    this.editor.putString("lastSurveyDate", date).commit();
  }

  public void setAlarmHour(int hour) {this.editor.putInt("alarmHour", hour);}
  public void setAlarmDay (int day) {this.editor.putInt("day", day);}
  public void setAlarmMin(int min) {this.editor.putInt("minute",min);}

  public int getAlarmHour() { return this.pref.getInt("alarmHour", 0);}
  public int getAlarmDay() { return pref.getInt("day", 2);}
  public int getAlarmMin() { return  pref.getInt("minute", 30);}

  public String getLastSurveyTakenDate() {
    return pref.getString("lastSurveyDate", "2015-06-04 21:40:46");
  }

  public void reset() {
    editor.clear().commit();
  }
}