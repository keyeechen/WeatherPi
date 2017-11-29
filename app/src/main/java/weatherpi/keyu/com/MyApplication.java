package weatherpi.keyu.com;

import android.app.Application;

import java.util.List;

import weatherpi.keyu.com.db.DBManager;
import weatherpi.keyu.com.entity.CityInfo;
import weatherpi.keyu.com.utils.Utils;
import weatherpi.keyu.com.utils.Constant;


/**
 * Created by focus on 2017/11/22.
 */

public class MyApplication extends Application {
    List<CityInfo> cityInfos;
    MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        loadCityInfos();
    }

    public Application getInstance() {
        return myApplication;
    }


    private void loadCityInfos() {
        Utils.executorService.execute(new Runnable() {
            @Override
            public void run() {
                cityInfos = DBManager.getCityInfo(myApplication, Constant.DB_NAME);
                Utils.log(getPackageName(), cityInfos.toString());
            }
        });
    }

    public List<CityInfo> getCityInfos() {
        return cityInfos;
    }

}
