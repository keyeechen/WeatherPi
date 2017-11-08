package weatherpi.keyu.com.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import weatherpi.keyu.com.entity.CityInfo;
import weatherpi.keyu.com.utils.Utils;

/**
 * Created by Administrator on 2017/11/1.
 */

public class DBManager {

    public static SQLiteDatabase getDatabase(Context mContext, String dbName){
        try {
            String dbPath = "/data" + Environment.getDataDirectory() + File.separator + mContext.getPackageName() + File.separator + "database" + File.separator + dbName;
            Utils.log(mContext.getPackageName(), "dbPath = " + dbPath);
            File file =  new File(dbPath);
            if(!file.exists()){//数据库不存在则拷贝
                String dbDir = "/data" + Environment.getDataDirectory() + File.separator + mContext.getPackageName() + File.separator + "database" + File.separator;
                File dir = new File(dbDir);
                if(!dir.exists()){//创建数据库文件的父目录
                    dir.mkdirs();
                }
                InputStream inputStream = mContext.getAssets().open(dbName);
                //BufferedInputStream  bufferedInputStream = new BufferedInputStream(inputStream);
                FileOutputStream fileOutputStream = new FileOutputStream(dbPath);
                //BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                byte[] buf = new byte[1024];
                int len = 0;
                while(inputStream.read(buf, 0, len) != -1){
                    fileOutputStream.write(buf, 0, len);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
                inputStream.close();
                //bufferedInputStream.close();
                //inputStream.close();
            }
            return SQLiteDatabase.openOrCreateDatabase(dbPath, null, null);

        }
        catch(IOException ioe){
            System.out.println(ioe);
        }
        return null;
    }

    public static List<CityInfo>    getCityInfo(Context mContext, String dbName, int limit){
        SQLiteDatabase db = DBManager.getDatabase(mContext, dbName);
        Utils.log(mContext.getPackageName(), db.getPath());
        List<CityInfo> citys = new ArrayList<>();
        if(db != null){
            Cursor cursor = db.query("city", null, null, null, null, null, null, limit+"");
            while(!cursor.isAfterLast()){
                CityInfo city = new CityInfo();
                city.setCity(cursor.getString(cursor.getColumnIndex("city")));
                city.setNum(cursor.getString(cursor.getColumnIndex("num")));
                citys.add(city);
                cursor.moveToNext();
            }
        }

        return citys;
    }

}
