package kyawthanmong.cystic.adapter;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by owais on 4/15/15.
 */
public class Settings {


    private SharedPreferences pref ;
    private SharedPreferences.Editor editor;

    public Settings(Context context){
        pref = context.getSharedPreferences("com.owaisnaeem", Context.MODE_PRIVATE);
        editor = pref.edit();
    }


    public  void setUserLoginStatus(boolean status){

        this.editor.putBoolean("loginStatus", status).commit();
    }

    public boolean isUserLogin(){
        return pref.getBoolean("loginStatus", false);
    }

    public void setUserId(String id){
        this.editor.putString("userId", id).commit();
    }


    public String getUserId(){
        return pref.getString("userId", "");
    }

    public  void setAlarmLimit(int limit) {this.editor.putInt("alarmLimit", 0).commit();}
    public int getAlarmLimit(){return pref.getInt("alarmLimit", 1);}
}