package weatherpi.keyu.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;
import java.util.HashMap;


import weatherpi.keyu.com.entity.CityInfo;
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
    private ImageView pm2_5_img;
    private HashMap<String, Integer> weahterImgs;
    private ImageView weather_img;
    private ImageView title_city_manager;
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
        pm2_5_img = (ImageView)findViewById(R.id.pm2_5_img);
        weather_img = (ImageView)findViewById(R.id.weather_img);
        title_city_manager = (ImageView)findViewById(R.id.title_city_manager);
        sp = getSharedPreferences("wheather", MODE_PRIVATE);
        cityCode = sp.getString("cityCode", "101010100");
        final String stringUrl = Constant.WEATHER_URL+cityCode;
        title_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.log(getLocalClassName(), stringUrl);
                getWeahterInfo(stringUrl);
            }
        });
        title_city_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SelectActivity.class);
                startActivityForResult(i, Constant.CHOOSE_CITY_REQUEST_CODE);
            }
        });
        initWeatherMap();
        weatherInfo = new WeatherInfo();
        getWeahterInfo(stringUrl);

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
                    boolean windRead = false;//是否已经读了风力
                    while(eventType != XmlPullParser.END_DOCUMENT){
                        if(eventType == XmlPullParser.START_TAG){
                            String tag = xpp.getName();
                            if("city".equals(tag)){
                                weatherInfo.setCity(xpp.nextText());
                            }
                            else if("wendu".equals(tag)){
                                weatherInfo.setTemperature(xpp.nextText());
                            }
                            else if("fengli".equals(tag) && !windRead){
                                windRead = true;
                                weatherInfo.setWindForce(Utils.getWindForce(xpp.nextText()));
                            }
                            else if("shidu".equals(tag)){
                                weatherInfo.setWetness(xpp.nextText());
                            }
                            else if("pm25".equals(tag)){
                                weatherInfo.setPm25(Integer.valueOf(xpp.nextText()));
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
                    updateWeatherUI();

                }
                catch(Exception e){
                    System.out.println(e);
                }


            }
        });

    }

    private void updateWeatherUI(){
        city.setText(weatherInfo.getCity());
        time.setText(String.format(getString(R.string.temp_update_time), weatherInfo.getUpdateTime()));
        humidity.setText(String.format(getString(R.string.wetness), weatherInfo.getWetness()));
        pm_data.setText(String.valueOf(weatherInfo.getPm25()));
        pm2_5_quality.setText(weatherInfo.getAirQuality());
        week_today.setText(String.format(getString(R.string.week_today), Utils.getWeekday()));
        String lowTemper = getString(R.string.unknown);
        String highTemper = getString(R.string.unknown);
        if(weatherInfo.getLowTemper() != null){
            lowTemper = weatherInfo.getLowTemper().substring(weatherInfo.getLowTemper().indexOf(' ')+1);
        }
        if(weatherInfo.getHighTemper() != null){
            highTemper = weatherInfo.getHighTemper().substring(weatherInfo.getHighTemper().indexOf(' ')+1);
        }
        temperature.setText(String.format(getString(R.string.temp_range), lowTemper, highTemper));
        String weatherType = null;
        if(Utils.judgeDay()){
            weatherType = weatherInfo.getWeatherDay();
        }
        else{
            weatherType = weatherInfo.getWeatherNight();
        }
        climate.setText(weatherType);
        if(weahterImgs.get(weatherType) != null){
            weather_img.setImageResource(weahterImgs.get(weatherType));
        }

        int windForceInd = weatherInfo.getWindForce();
        String[] windForces = getResources().getStringArray(R.array.wind_forces);
        if(windForceInd != -1 && windForceInd < windForces.length){
            wind.setText(windForces[windForceInd]);
        }
        else{
            wind.setText(R.string.unknown);
        }
        int pm25 = weatherInfo.getPm25();
        if(pm25 <= 50){
            pm2_5_img.setImageResource(R.drawable.biz_plugin_weather_0_50);
        }
        else if(pm25 <= 100){
            pm2_5_img.setImageResource(R.drawable.biz_plugin_weather_51_100);
        }
        else if(pm25 <= 150){
            pm2_5_img.setImageResource(R.drawable.biz_plugin_weather_101_150);
        }
        else if(pm25 <= 200){
            pm2_5_img.setImageResource(R.drawable.biz_plugin_weather_151_200);
        }
        else if(pm25 <= 300){
            pm2_5_img.setImageResource(R.drawable.biz_plugin_weather_201_300);
        }
        else{
            pm2_5_img.setImageResource(R.drawable.biz_plugin_weather_greater_300
            );
        }


    }

    private void initWeatherMap(){
        weahterImgs = new HashMap<>();
        String[]  weatherTypes = getResources().getStringArray(R.array.weather_types);
        weahterImgs.put(weatherTypes[0], R.drawable.biz_plugin_weather_baoxue);
        weahterImgs.put(weatherTypes[1], R.drawable.biz_plugin_weather_baoyu);
        weahterImgs.put(weatherTypes[2], R.drawable.biz_plugin_weather_dabaoyu);
        weahterImgs.put(weatherTypes[3], R.drawable.biz_plugin_weather_daxue);
        weahterImgs.put(weatherTypes[4], R.drawable.biz_plugin_weather_dayu);
        weahterImgs.put(weatherTypes[5], R.drawable.biz_plugin_weather_duoyun);
        weahterImgs.put(weatherTypes[6], R.drawable.biz_plugin_weather_leizhenyu);
        weahterImgs.put(weatherTypes[7], R.drawable.biz_plugin_weather_leizhenyubingbao);
        weahterImgs.put(weatherTypes[8], R.drawable.biz_plugin_weather_qing);
        weahterImgs.put(weatherTypes[9], R.drawable.biz_plugin_weather_yin);
        weahterImgs.put(weatherTypes[10], R.drawable.biz_plugin_weather_shachenbao);
        weahterImgs.put(weatherTypes[11], R.drawable.biz_plugin_weather_tedabaoyu);
        weahterImgs.put(weatherTypes[12], R.drawable.biz_plugin_weather_xiaoxue);
        weahterImgs.put(weatherTypes[13], R.drawable.biz_plugin_weather_xiaoyu);
        weahterImgs.put(weatherTypes[14], R.drawable.biz_plugin_weather_yujiaxue);
        weahterImgs.put(weatherTypes[15], R.drawable.biz_plugin_weather_zhenxue);
        weahterImgs.put(weatherTypes[16], R.drawable.biz_plugin_weather_zhenyu);
        weahterImgs.put(weatherTypes[17], R.drawable.biz_plugin_weather_zhenxue);
        weahterImgs.put(weatherTypes[18], R.drawable.biz_plugin_weather_zhongyu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Utils.log(getLocalClassName(), "onActivityResult");
           if(requestCode == Constant.CHOOSE_CITY_REQUEST_CODE){
               if(resultCode == Constant.CHOOSE_CITY_RESULT_CODE){
                   CityInfo cityInfo = data.getParcelableExtra(Constant.CUR_CITY);
                   String url = Constant.WEATHER_URL + cityInfo.getNumber();
                   getWeahterInfo(url);
               }
           }
    }
}
