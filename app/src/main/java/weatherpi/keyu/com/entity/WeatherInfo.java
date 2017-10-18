package weatherpi.keyu.com.entity;

/**
 * Created by Administrator on 2017/10/12.
 */

public class WeatherInfo {
    private String  city;//城市
    private String  updateTime;//更新时间
    private String  wetness;//湿度
    private String temperature;//温度
    private int  pm25;//pm2.5
    private String  airQuality;//空气质量
    private String  dayInfo;//今天的信息
    private String  lowTemper;//最低温度
    private String  highTemper;//最高温度
    private String  weatherDay;//白天天气概况
    private String  weatherNight;//晚上天气概况
    private int  windForce;//风力

    public String getWeatherDay() {
        return weatherDay;
    }

    public void setWeatherDay(String weatherDay) {
        this.weatherDay = weatherDay;
    }

    public String getWeatherNight() {
        return weatherNight;
    }

    public void setWeatherNight(String weatherNight) {
        this.weatherNight = weatherNight;
    }



    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getWetness() {
        return wetness;
    }

    public void setWetness(String wetness) {
        this.wetness = wetness;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }

    public String getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(String airQuality) {
        this.airQuality = airQuality;
    }

    public String getDayInfo() {
        return dayInfo;
    }

    public void setDayInfo(String dayInfo) {
        this.dayInfo = dayInfo;
    }

    public String getLowTemper() {
        return lowTemper;
    }

    public void setLowTemper(String lowTemper) {
        this.lowTemper = lowTemper;
    }

    public String getHighTemper() {
        return highTemper;
    }

    public void setHighTemper(String highTemper) {
        this.highTemper = highTemper;
    }



    public int getWindForce() {
        return windForce;
    }

    public void setWindForce(int windForce) {
        this.windForce = windForce;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

}
