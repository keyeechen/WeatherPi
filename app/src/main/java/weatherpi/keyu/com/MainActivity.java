package weatherpi.keyu.com;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import weatherpi.keyu.com.utils.Constant;
import weatherpi.keyu.com.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private ImageView title_update_btn;
    SharedPreferences sp;
    String cityCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        title_update_btn = (ImageView)findViewById(R.id.title_update_btn);
        sp = getSharedPreferences("wheather", MODE_PRIVATE);
        title_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityCode = sp.getString("cityCode", "101010100");
                String stringUrl = Constant.WEATHER_URL+cityCode;
                Utils.log(getLocalClassName(), stringUrl);
                getWeahterInfo(stringUrl);
            }
        });


    }

    private void getWeahterInfo(final String stringUrl){
        if(Utils.isNetOK(this)){
            Utils.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection conn = null;
                    try{
                        URL url = new URL(stringUrl);
                        conn = (HttpURLConnection)url.openConnection();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuffer sb = new StringBuffer();
                        String line;
                        while((line = bufferedReader.readLine()) != null){
                            sb.append(line);
                        }
                        Log.i(getLocalClassName(), sb.toString());

                    }
                    catch(Exception e){

                    }
                    finally {
                        if(conn != null){
                            conn.disconnect();
                        }
                    }
                }
            });

        }
        else{
            Toast.makeText(this, "请检查网络！", Toast.LENGTH_SHORT).show();
        }

    }
}
