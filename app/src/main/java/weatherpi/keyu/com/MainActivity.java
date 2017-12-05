package weatherpi.keyu.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaku.wcv.WeatherChartView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import weatherpi.keyu.com.entity.CityInfo;
import weatherpi.keyu.com.entity.WeatherForcast;
import weatherpi.keyu.com.entity.WeatherInfo;
import weatherpi.keyu.com.utils.Constant;
import weatherpi.keyu.com.utils.NetTasker;
import weatherpi.keyu.com.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private ImageView title_update_btn;
    private SharedPreferences sp;
    private String cityCode;
    private WeatherInfo todayWeatherInfo;
    private TextView city;
    private TextView time;
    private TextView humidity;
    private TextView pm_data;
    private TextView pm2_5_quality;
    private TextView week_today;
    private TextView temperature;
    private TextView climate;
    private TextView wind;
    private TextView title_city_name;
    private ImageView pm2_5_img;
    private HashMap<String, Integer> weahterImgs;
    private ImageView weather_img;
    private ImageView title_city_manager;
    private String cityUrl;
    private TextView tv_day1_title;
    private TextView tv_day1_date;
    private TextView tv_day1_air_quality;
    private TextView tv_day1_weather;
    private ImageView iv_day1_weather;

    private TextView tv_day2_title;
    private TextView tv_day2_date;
    private TextView tv_day2_air_quality;
    private TextView tv_day2_weather;
    private ImageView iv_day2_weather;

    private TextView tv_day3_title;
    private TextView tv_day3_date;
    private TextView tv_day3_air_quality;
    private TextView tv_day3_weather;
    private ImageView iv_day3_weather;

    private TextView tv_day4_title;
    private TextView tv_day4_date;
    private TextView tv_day4_air_quality;
    private TextView tv_day4_weather;
    private ImageView iv_day4_weather;

    private TextView tv_day5_title;
    private TextView tv_day5_date;
    private TextView tv_day5_air_quality;
    private TextView tv_day5_weather;
    private ImageView iv_day5_weather;

    private TextView tv_day6_title;
    private TextView tv_day6_date;
    private TextView tv_day6_air_quality;
    private TextView tv_day6_weather;
    private ImageView iv_day6_weather;
    private List<WeatherForcast> weatherForcasts;
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
        title_city_name = (TextView) findViewById(R.id.title_city_name);
        sp = getSharedPreferences("wheather", MODE_PRIVATE);
        cityCode = sp.getString(Constant.CUR_CITY, Constant.DEFAULT_CITY_CODE);
        cityUrl = Constant.WEATHER_URL + cityCode;
        title_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.log(getLocalClassName(), cityUrl);
                cityUrl = Constant.WEATHER_URL + cityCode;
                getWeatherInfo(cityUrl);
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
        todayWeatherInfo = new WeatherInfo();
        weatherForcasts = new ArrayList<>();
        WeatherChartView chartView = (WeatherChartView) findViewById(R.id.line_char);
        // set day
        chartView.setTempDay(new int[]{14, 15, 16, 17, 9, 9});
        // set night
        chartView.setTempNight(new int[]{7, 5, 9, 10, 3, 2});
        chartView.invalidate();

        tv_day1_title = (TextView) findViewById(R.id.tv_day1_title);
        tv_day1_date = (TextView) findViewById(R.id.tv_day1_date);
        tv_day1_air_quality = (TextView) findViewById(R.id.tv_day1_air_quality);
        tv_day1_weather = (TextView) findViewById(R.id.tv_day1_weather);
        iv_day1_weather = (ImageView) findViewById(R.id.iv_day1_weather);

        tv_day2_title = (TextView) findViewById(R.id.tv_day2_title);
        tv_day2_date = (TextView) findViewById(R.id.tv_day2_date);
        tv_day2_air_quality = (TextView) findViewById(R.id.tv_day2_air_quality);
        tv_day2_weather = (TextView) findViewById(R.id.tv_day2_weather);
        iv_day2_weather = (ImageView) findViewById(R.id.iv_day2_weather);

        tv_day3_title = (TextView) findViewById(R.id.tv_day3_title);
        tv_day3_date = (TextView) findViewById(R.id.tv_day3_date);
        tv_day3_air_quality = (TextView) findViewById(R.id.tv_day3_air_quality);
        tv_day3_weather = (TextView) findViewById(R.id.tv_day3_weather);
        iv_day3_weather = (ImageView) findViewById(R.id.iv_day3_weather);

        tv_day4_title = (TextView) findViewById(R.id.tv_day4_title);
        tv_day4_date = (TextView) findViewById(R.id.tv_day4_date);
        tv_day4_air_quality = (TextView) findViewById(R.id.tv_day4_air_quality);
        tv_day4_weather = (TextView) findViewById(R.id.tv_day4_weather);
        iv_day4_weather = (ImageView) findViewById(R.id.iv_day4_weather);

        tv_day5_title = (TextView) findViewById(R.id.tv_day5_title);
        tv_day5_date = (TextView) findViewById(R.id.tv_day5_date);
        tv_day5_air_quality = (TextView) findViewById(R.id.tv_day5_air_quality);
        tv_day5_weather = (TextView) findViewById(R.id.tv_day5_weather);
        iv_day5_weather = (ImageView) findViewById(R.id.iv_day5_weather);

        tv_day6_title = (TextView) findViewById(R.id.tv_day6_title);
        tv_day6_date = (TextView) findViewById(R.id.tv_day6_date);
        tv_day6_air_quality = (TextView) findViewById(R.id.tv_day6_air_quality);
        tv_day6_weather = (TextView) findViewById(R.id.tv_day6_weather);
        iv_day6_weather = (ImageView) findViewById(R.id.iv_day6_weather);
        getWeatherInfo(cityUrl);


    }

    private void getWeatherInfo(final String stringUrl){
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
                    boolean windDirectionRead = false;//是否已经读了风向
                    int dayCnt = 0;//预测的日期，是第几天
                    WeatherForcast weatherForcast = new WeatherForcast();
                    while(eventType != XmlPullParser.END_DOCUMENT){
                        if(eventType == XmlPullParser.START_TAG){
                            String tag = xpp.getName();
                            if("city".equals(tag)){
                                todayWeatherInfo.setCity(xpp.nextText());
                            }
                            else if("wendu".equals(tag)){
                                todayWeatherInfo.setTemperature(xpp.nextText());
                            }
                            else if("fengli".equals(tag) && !windRead){
                                windRead = true;
                                todayWeatherInfo.setWindForce(Utils.getWindForce(xpp.nextText()));
                            } else if ("fengxiang".equals(tag) && !windDirectionRead) {
                                windDirectionRead = true;
                                todayWeatherInfo.setWindForce(Utils.getWindForce(xpp.nextText()));
                            } else if ("shidu".equals(tag)) {
                                todayWeatherInfo.setWetness(xpp.nextText());
                            } else if ("pm25".equals(tag)) {
                                todayWeatherInfo.setPm25(Integer.valueOf(xpp.nextText()));
                            } else if ("updatetime".equals(tag)) {
                                todayWeatherInfo.setUpdateTime(xpp.nextText());
                            } else if ("quality".equals(tag)) {
                                todayWeatherInfo.setAirQuality(xpp.nextText());
                            } else if ("yesterday".equals(tag)) {//昨天天气
                                weatherForcast = new WeatherForcast();
                            } else if ("date_1".equals(tag)) {
                                weatherForcast.setDate(xpp.nextText());
                            } else if ("high_1".equals(tag)) {
                                weatherForcast.setHighTemper(xpp.nextText());
                            } else if ("low_1".equals(tag)) {
                                weatherForcast.setLowTemper(xpp.nextText());
                            } else if ("day_1".equals(tag)) {
                                dayFlag = true;
                            } else if ("night_1".equals(tag)) {
                                dayFlag = false;
                            } else if ("type_1".equals(tag)) {
                                if (dayFlag) {
                                    weatherForcast.setWeatherDay(xpp.nextText());
                                } else {
                                    weatherForcast.setWeatherNight(xpp.nextText());
                                }
                            } else if ("fx_1".equals(tag)) {
                                if (dayFlag) {
                                    weatherForcast.setDayWindDirection(xpp.nextText());
                                } else {
                                    weatherForcast.setNightWindDirection(xpp.nextText());
                                }
                            } else if ("fl_1".equals(tag)) {
                                if (dayFlag) {
                                    weatherForcast.setDayWindForce(xpp.nextText());
                                } else {
                                    weatherForcast.setNightWindForce(xpp.nextText());
                                }
                            } else if ("weather".equals(tag)) {//今天及之后4天的天气
                                weatherForcast = new WeatherForcast();
                                dayCnt++;
                            } else if ("high".equals(tag)) {
                                String text = xpp.nextText();
                                if (dayCnt == 1) {//今天的信息
                                    todayWeatherInfo.setHighTemper(text);
                                }
                                weatherForcast.setHighTemper(text);
                            } else if ("low".equals(tag)) {
                                String text = xpp.nextText();
                                if (dayCnt == 1) {
                                    todayWeatherInfo.setLowTemper(text);
                                }
                                weatherForcast.setLowTemper(text);
                            } else if ("day".equals(tag)) {
                                dayFlag = true;
                            } else if ("night".equals(tag)) {
                                dayFlag = false;
                            } else if ("type".equals(tag)) {
                                String text = xpp.nextText();
                                if (dayFlag) {
                                    if (dayCnt == 1) {
                                        todayWeatherInfo.setWeatherDay(text);
                                    }
                                    weatherForcast.setWeatherDay(text);
                                } else {
                                    if (dayCnt == 1) {
                                        todayWeatherInfo.setWeatherNight(text);
                                    }
                                    weatherForcast.setWeatherNight(text);
                                }
                            } else if ("fengxiang".equals(tag)) {
                                String text = xpp.nextText();
                                if (dayCnt > 0) {
                                    if (dayFlag) {
                                        weatherForcast.setDayWindDirection(text);
                                    } else {
                                        weatherForcast.setNightWindDirection(text);
                                    }
                                }

                            } else if ("fengli".equals(tag)) {
                                if (dayCnt > 0) {
                                    if (dayFlag) {
                                        weatherForcast.setDayWindForce(xpp.nextText());
                                    } else {
                                        weatherForcast.setNightWindForce(xpp.nextText());
                                    }
                                }

                            }


                        }
                        else if(eventType == XmlPullParser.END_TAG){
                            String tag = xpp.getName();
                            if ("weather".equals(tag) || "yesterday".equals(tag)) {
                                weatherForcasts.add(weatherForcast);
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
        city.setText(todayWeatherInfo.getCity());
        time.setText(String.format(getString(R.string.temp_update_time), todayWeatherInfo.getUpdateTime()));
        humidity.setText(String.format(getString(R.string.wetness), todayWeatherInfo.getWetness()));
        pm_data.setText(String.valueOf(todayWeatherInfo.getPm25()));
        pm2_5_quality.setText(todayWeatherInfo.getAirQuality());
        week_today.setText(String.format(getString(R.string.week_today), Utils.getWeekday()));
        String lowTemper = getString(R.string.unknown);
        String highTemper = getString(R.string.unknown);
        if (todayWeatherInfo.getLowTemper() != null) {
            lowTemper = todayWeatherInfo.getLowTemper().substring(todayWeatherInfo.getLowTemper().indexOf(' ') + 1);
        }
        if (todayWeatherInfo.getHighTemper() != null) {
            highTemper = todayWeatherInfo.getHighTemper().substring(todayWeatherInfo.getHighTemper().indexOf(' ') + 1);
        }
        temperature.setText(String.format(getString(R.string.temp_range), lowTemper, highTemper));
        String weatherType = null;
        if(Utils.judgeDay()){
            weatherType = todayWeatherInfo.getWeatherDay();
        }
        else{
            weatherType = todayWeatherInfo.getWeatherNight();
        }
        climate.setText(weatherType);
        if(weahterImgs.get(weatherType) != null){
            weather_img.setImageResource(weahterImgs.get(weatherType));
        }

        int windForceInd = todayWeatherInfo.getWindForce();
        String[] windForces = getResources().getStringArray(R.array.wind_forces);
        if(windForceInd != -1 && windForceInd < windForces.length){
            wind.setText(windForces[windForceInd]);
        }
        else{
            wind.setText(R.string.unknown);
        }
        int pm25 = todayWeatherInfo.getPm25();
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
        
        int forcastSize = weatherForcasts.size();
        if(forcastSize > 5){
            WeatherForcast forcast = weatherForcasts.get(5);
            tv_day6_title.setText(Utils.certainWeekday(4));
            tv_day6_date.setText(Utils.certainDate(4));
            if(Utils.judgeDay()){
                tv_day6_weather.setText(forcast.getWeatherDay());
            }
            else{
                tv_day6_weather.setText(forcast.getWeatherNight());
            }
            if(Utils.judgeDay()){
                iv_day6_weather.setImageResource(weahterImgs.get(forcast.getWeatherDay()));
            }
            else{
                iv_day6_weather.setImageResource(weahterImgs.get(forcast.getWeatherNight()));
            }
        }
        if(forcastSize > 4){
            WeatherForcast forcast = weatherForcasts.get(4);
            tv_day5_title.setText(Utils.certainWeekday(3));
            tv_day5_date.setText(Utils.certainDate(3));
            if(Utils.judgeDay()){
                tv_day5_weather.setText(forcast.getWeatherDay());
            }
            else{
                tv_day5_weather.setText(forcast.getWeatherNight());
            }
            if(Utils.judgeDay()){
                iv_day5_weather.setImageResource(weahterImgs.get(forcast.getWeatherDay()));
            }
            else{
                iv_day5_weather.setImageResource(weahterImgs.get(forcast.getWeatherNight()));
            }
        }
        
        if(forcastSize > 3){
            WeatherForcast forcast = weatherForcasts.get(3);
            tv_day4_title.setText(Utils.certainWeekday(2));
            tv_day4_date.setText(Utils.certainDate(2));
            if(Utils.judgeDay()){
                tv_day4_weather.setText(forcast.getWeatherDay());
            }
            else{
                tv_day4_weather.setText(forcast.getWeatherNight());
            }
            if(Utils.judgeDay()){
                iv_day4_weather.setImageResource(weahterImgs.get(forcast.getWeatherDay()));
            }
            else{
                iv_day4_weather.setImageResource(weahterImgs.get(forcast.getWeatherNight()));
            }
        }
        
        if(forcastSize > 2){
            WeatherForcast forcast = weatherForcasts.get(2);
            tv_day3_title.setText(Utils.certainWeekday(1));
            tv_day3_date.setText(Utils.certainDate(1));
            if(Utils.judgeDay()){
                tv_day3_weather.setText(forcast.getWeatherDay());
            }
            else{
                tv_day3_weather.setText(forcast.getWeatherNight());
            }
            if(Utils.judgeDay()){
                iv_day3_weather.setImageResource(weahterImgs.get(forcast.getWeatherDay()));
            }
            else{
                iv_day3_weather.setImageResource(weahterImgs.get(forcast.getWeatherNight()));
            }
        }
        
        if(forcastSize > 1){
            WeatherForcast forcast = weatherForcasts.get(1);
            tv_day2_title.setText(Utils.certainWeekday(0));
            tv_day2_date.setText(Utils.certainDate(0));
            if(Utils.judgeDay()){
                tv_day2_weather.setText(forcast.getWeatherDay());
            }
            else{
                tv_day2_weather.setText(forcast.getWeatherNight());
            }
            if(Utils.judgeDay()){
                iv_day2_weather.setImageResource(weahterImgs.get(forcast.getWeatherDay()));
            }
            else{
                iv_day2_weather.setImageResource(weahterImgs.get(forcast.getWeatherNight()));
            }
        }

        if(forcastSize > 0){
            WeatherForcast forcast = weatherForcasts.get(0);
            tv_day1_title.setText(Utils.certainWeekday(-1));
            tv_day1_date.setText(Utils.certainDate(-1));
            if(Utils.judgeDay()){
                tv_day1_weather.setText(forcast.getWeatherDay());
            }
            else{
                tv_day1_weather.setText(forcast.getWeatherNight());
            }
            if(Utils.judgeDay()){
                iv_day1_weather.setImageResource(weahterImgs.get(forcast.getWeatherDay()));
            }
            else{
                iv_day1_weather.setImageResource(weahterImgs.get(forcast.getWeatherNight()));
            }
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
                   title_city_name.setText(cityInfo.getCity());
                   cityCode = cityInfo.getNumber();
                   getWeatherInfo(url);
               }
           }
    }
}
