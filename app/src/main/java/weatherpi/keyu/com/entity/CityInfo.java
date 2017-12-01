package weatherpi.keyu.com.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/11/1.
 */

public class CityInfo implements Parcelable {


    private int _id;
    private String province;
    private String city;
    private String number;
    private String allpy;
    private String allfirstpy;
    private String firstpy;

    @Override
    public String toString() {
        return "CityInfo{" +
                "_id=" + _id +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", number='" + number + '\'' +
                ", allpy='" + allpy + '\'' +
                ", allfirstpy='" + allfirstpy + '\'' +
                ", firstpy='" + firstpy + '\'' +
                '}';
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAllpy() {
        return allpy;
    }

    public void setAllpy(String allpy) {
        this.allpy = allpy;
    }

    public String getAllfirstpy() {
        return allfirstpy;
    }

    public void setAllfirstpy(String allfirstpy) {
        this.allfirstpy = allfirstpy;
    }

    public String getFirstpy() {
        return firstpy;
    }

    public void setFirstpy(String firstpy) {
        this.firstpy = firstpy;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.number);
        dest.writeString(this.allpy);
        dest.writeString(this.allfirstpy);
        dest.writeString(this.firstpy);
    }

    public CityInfo() {
    }

    protected CityInfo(Parcel in) {
        this._id = in.readInt();
        this.province = in.readString();
        this.city = in.readString();
        this.number = in.readString();
        this.allpy = in.readString();
        this.allfirstpy = in.readString();
        this.firstpy = in.readString();
    }

    public static final Parcelable.Creator<CityInfo> CREATOR = new Parcelable.Creator<CityInfo>() {
        @Override
        public CityInfo createFromParcel(Parcel source) {
            return new CityInfo(source);
        }

        @Override
        public CityInfo[] newArray(int size) {
            return new CityInfo[size];
        }
    };
}
