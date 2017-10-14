package weatherpi.keyu.com;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import weatherpi.keyu.com.entity.WeatherInfo;
import weatherpi.keyu.com.utils.Constant;
import weatherpi.keyu.com.utils.NetTasker;
import weatherpi.keyu.com.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private ImageView title_update_btn;
    private SharedPreferences sp;
    private String cityCode;
    private WeatherInfo weatherInfo;
    private TextView city;
    private TextView time;
    private TextView humidity;
    private TextView pm_data;
    private TextView pm2_5_quality;
    private TextView week_today;
    private TextView temperature;
    private TextView climate;
    private TextView wind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        city = (TextView)findViewById(R.id.city);
        time = (TextView)findViewById(R.id.time);
        humidity = (TextView)findViewById(R.id.humidity);
        pm_data = (TextView)findViewById(R.id.pm_data);
        pm2_5_quality = (TextView)findViewById(R.id.pm2_5_quality);
        week_today = (TextView)findViewById(R.id.week_today);
        temperature = (TextView)findViewById(R.id.temperature);
        climate = (TextView)findViewById(R.id.climate);
        wind = (TextView)findViewById(R.id.wind);
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
        weatherInfo = new WeatherInfo();

    }

    private void getWeahterInfo(final String stringUrl){
        new NetTasker(this).visitNet(stringUrl, new NetTasker.CallBack() {
            @Override
            public void doAfterTask(String result) {
                if(result == null) return;//未返回预期结果
                try{
                    XmlPullParserFactory  factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(new StringReader(result));
                    int eventType = xpp.getEventType();
                    boolean dayFlag = false;//进入读取白天天气信息的标志
                    while(eventType != XmlPullParser.END_DOCUMENT){
                        if(eventType == XmlPullParser.START_TAG){
                            String tag = xpp.getName();
                            if("city".equals(tag)){
                                weatherInfo.setCity(xpp.nextText());
                            }
                            else if("wendu".equals(tag)){
                                weatherInfo.setTemperature(xpp.nextText());
                            }
                            else if("fengli".equals(tag)){
                                weatherInfo.setWindForce(xpp.nextText());
                            }
                            else if("shidu".equals(tag)){
                                weatherInfo.setWetness(xpp.nextText());
                            }
                            else if("pm25".equals(tag)){
                                weatherInfo.setPm25(xpp.nextText());
                            }
                            else if("updatetime".equals(tag)){
                                weatherInfo.setUpdateTime(xpp.nextText());
                            }
                            else if("quality".equals(tag)){
                                weatherInfo.setAirQuality(xpp.nextText());
                            }
                            else if("high".equals(tag)){
                                weatherInfo.setHighTemper(xpp.nextText());
                            }
                            else if("low".equals(tag)){
                                weatherInfo.setLowTemper(xpp.nextText());
                            }
                            else if("day".equals(tag)){
                                dayFlag = true;
                            }
                            else if("type".equals(tag)){
                                if(dayFlag){
                                    weatherInfo.setWeatherDay(xpp.nextText());
                                }
                                else {
                                    weatherInfo.setWeatherNight(xpp.nextText());
                                }
                            }

                        }
                        else if(eventType == XmlPullParser.END_TAG){
                            String tag = xpp.getName();
                            if("day".equals(tag)) dayFlag = false;//进入读取夜晚信息区域
                            else if("weather".equals(tag)){
                                break;//第一天的天气读取结束
                            }
                        }
                        eventType = xpp.next();//进入下一个标签
                    }
                    //更新天气信息
                    city.setText(weatherInfo.getCity());
                    time.setText(weatherInfo.getUpdateTime());
                    humidity.setText(weatherInfo.getWetness());
                    pm_data.setText(weatherInfo.getPm25());
                    pm2_5_quality.setText(weatherInfo.getAirQuality());
                    week_today.setText(String.format(getString(R.string.week_today), Utils.getWeekday()));
                    climate.setText(weatherInfo.getWeatherDay());



                }
                catch(Exception e){
                    System.out.println(e);
                }

            }
        });

    }
}
