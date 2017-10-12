package weatherpi.keyu.com.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/10/12.
 */

public class NetTasker {
    private Context mContext;
    public NetTasker(Context context){
        this.mContext = context;
    }

    public static interface  CallBack {
        public void doAfterTask(String str);
    }

    public void visitNet(String strUrl, CallBack calllBack){
        String result = null;
        if(isNetOK()){
            HttpURLConnection conn;
            try{
                URL url  =new URL(strUrl);
                conn = (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String str = null;
                while((str = reader.readLine()) != null){
                    sb.append(str);
                }
                result = sb.toString();

            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        else {
            Toast.makeText(mContext, "请检查网络！", Toast.LENGTH_SHORT).show();
        }
        calllBack.doAfterTask(result);
    }

    private boolean isNetOK(){
        ConnectivityManager connectivityManager =(ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null){
            return networkInfo.isAvailable();
        }
        return false;
    }

}
