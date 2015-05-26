package kyawthanmong.cystic;

import android.content.Context;
import android.net.ConnectivityManager;

import java.util.Calendar;


/**
 * Created by kyawthan on 5/20/15.
 */
public class AppUtils {


    public AppUtils(){}

    public static String getVersionName(){

        return BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE;

    }
    
    public static boolean isOnline(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if( cm.getActiveNetworkInfo() == null ) {
            return false;

        }

        return cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static boolean isMondayYet(Context context){
        Calendar calendar = Calendar.getInstance();

       return (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY);
    }
}

