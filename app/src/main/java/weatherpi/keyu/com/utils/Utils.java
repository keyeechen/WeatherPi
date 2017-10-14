package weatherpi.keyu.com.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/10/12.
 */

public class Utils {
    public static ExecutorService executorService = Executors.newCachedThreadPool();
    public static final boolean DEBUGGING = true;

    public static boolean isNetOK(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null){
            return networkInfo.isAvailable();
        }
        return false;
    }

    public static void log(String flag, String msg){
        if(DEBUGGING){
            Log.i(flag, msg);
        }
    }

    public static String getWeekday() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        return dateFormat.format(date);
    }

    public static boolean judgeDay(){
        Date date =new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(dateFormat.format(date));
        return  hour >= 6 && hour <= 18 ? true : false;
    }

    public static int getWindForce(String srcWindForce){
        int windForce = -1;
        if(srcWindForce != null){
            StringBuffer sb =new StringBuffer();
            for(int i = 0; i < srcWindForce.length(); i++){
              char  c = srcWindForce.charAt(i);
                if(c >= '0' && c <= '9') sb.append(c);
            }
            if(sb.length() > 0) windForce = Integer.valueOf(sb.toString());
        }
        return windForce;
    }
}
