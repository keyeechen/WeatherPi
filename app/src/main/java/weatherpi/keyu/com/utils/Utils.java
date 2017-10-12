package weatherpi.keyu.com.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

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
}
