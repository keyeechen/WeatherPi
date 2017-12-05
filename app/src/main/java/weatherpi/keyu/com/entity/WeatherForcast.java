package weatherpi.keyu.com.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by focus on 2017/12/4.
 */

public class WeatherForcast implements Parcelable {
    private String weekDay;
    private String date;
    private String airQuality;
    private String lowTemper;//最低温度
    private String highTemper;//最高温度
    private String weatherDay;//白天天气概况
    private String dayWindForce;//白天风力
    private String dayWindDirection;//白天风向
    private String weatherNight;//晚上天气概况
    private String nightWindForce;//晚上风力
    private String nightWindDirection;//晚上风向

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(String airQuality) {
        this.airQuality = airQuality;
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

    public String getWeatherDay() {
        return weatherDay;
    }

    public void setWeatherDay(String weatherDay) {
        this.weatherDay = weatherDay;
    }

    public String getDayWindForce() {
        return dayWindForce;
    }

    public void setDayWindForce(String dayWindForce) {
        this.dayWindForce = dayWindForce;
    }

    public String getDayWindDirection() {
        return dayWindDirection;
    }

    public void setDayWindDirection(String dayWindDirection) {
        this.dayWindDirection = dayWindDirection;
    }

    public String getWeatherNight() {
        return weatherNight;
    }

    public void setWeatherNight(String weatherNight) {
        this.weatherNight = weatherNight;
    }

    public String getNightWindForce() {
        return nightWindForce;
    }

    public void setNightWindForce(String nightWindForce) {
        this.nightWindForce = nightWindForce;
    }

    public String getNightWindDirection() {
        return nightWindDirection;
    }

    public void setNightWindDirection(String nightWindDirection) {
        this.nightWindDirection = nightWindDirection;
    }



    public WeatherForcast() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.weekDay);
        dest.writeString(this.date);
        dest.writeString(this.airQuality);
        dest.writeString(this.lowTemper);
        dest.writeString(this.highTemper);
        dest.writeString(this.weatherDay);
        dest.writeString(this.dayWindForce);
        dest.writeString(this.dayWindDirection);
        dest.writeString(this.weatherNight);
        dest.writeString(this.nightWindForce);
        dest.writeString(this.nightWindDirection);
    }

    protected WeatherForcast(Parcel in) {
        this.weekDay = in.readString();
        this.date = in.readString();
        this.airQuality = in.readString();
        this.lowTemper = in.readString();
        this.highTemper = in.readString();
        this.weatherDay = in.readString();
        this.dayWindForce = in.readString();
        this.dayWindDirection = in.readString();
        this.weatherNight = in.readString();
        this.nightWindForce = in.readString();
        this.nightWindDirection = in.readString();
    }

    public static final Creator<WeatherForcast> CREATOR = new Creator<WeatherForcast>() {
        @Override
        public WeatherForcast createFromParcel(Parcel source) {
            return new WeatherForcast(source);
        }

        @Override
        public WeatherForcast[] newArray(int size) {
            return new WeatherForcast[size];
        }
    };
}
