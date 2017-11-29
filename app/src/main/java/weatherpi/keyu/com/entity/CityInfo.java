package weatherpi.keyu.com.entity;

/**
 * Created by Administrator on 2017/11/1.
 */

public class CityInfo {


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
}
