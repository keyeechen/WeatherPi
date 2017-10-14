package weatherpi.keyu.com.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
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
    private static Handler uiHandler = new Handler();
    public NetTasker(Context context){
        this.mContext = context;
    }

    public static interface  CallBack {
        public void doAfterTask(String result);
    }

    public void visitNet(final String strUrl, final CallBack calllBack){
        if(isNetOK()){//网络OK
            Utils.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection conn = null;
                    try{
                        URL url  =new URL(strUrl);
                        conn = (HttpURLConnection)url.openConnection();
                        if(conn.getResponseCode() == 200){//成功返回数据
                            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            final StringBuffer sb = new StringBuffer();
                            String str = null;
                            while((str = reader.readLine()) != null){
                                sb.append(str);
                            }
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    calllBack.doAfterTask(sb.toString());
                                }
                            });
                        }
                        else{//返回null
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    calllBack.doAfterTask(null);
                                }
                            });
                        }

                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                    finally {
                        if (conn != null) {
                            conn.disconnect();
                        }
                    }

                }
            });
        }
        else {
            Toast.makeText(mContext, "请检查网络！", Toast.LENGTH_SHORT).show();
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    calllBack.doAfterTask(null);
                }
            });
        }
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
