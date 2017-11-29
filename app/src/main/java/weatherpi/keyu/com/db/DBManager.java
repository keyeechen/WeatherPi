package weatherpi.keyu.com.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import weatherpi.keyu.com.entity.CityInfo;

import static weatherpi.keyu.com.utils.Constant.*;

/**
 * Created by Administrator on 2017/11/1.
 */

public class DBManager {

    public static SQLiteDatabase getDatabase(Context mContext, String dbName){
        String dbDirPath = "/data" + Environment.getDataDirectory() + File.separator + mContext.getPackageName() + File.separator + DB_DIR + File.separator;
        String dbPath = dbDirPath + DB_NAME;
         File dbFile = new File(dbPath);
         if(!dbFile.exists()){//数据库文件不存在则为其创建目录
             File dbDir = new File(dbDirPath);
             if(!dbDir.exists()){
                 dbDir.mkdirs();
             }
             try {
                 InputStream inputStream = mContext.getAssets().open(dbName);
                 BufferedInputStream bufferedInputStream =new BufferedInputStream(inputStream);
                 FileOutputStream fileOutputStream = new FileOutputStream(dbPath);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                 byte buf[] = new byte[1024];
                 int len;
                 while((len = bufferedInputStream.read(buf)) != -1){
                     bufferedOutputStream.write(buf, 0, len);
                     bufferedOutputStream.flush();
                 }

             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
         return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }

    public static List<CityInfo>    getCityInfo(Context mContext, String dbName) {
        List<CityInfo> cityInfos = new ArrayList<>();
        SQLiteDatabase database = getDatabase(mContext, dbName);
        Cursor cursor = database.query(DB_TABLE_NAME, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            CityInfo cityInfo = new CityInfo();
            cityInfo.setProvince(cursor.getString(cursor.getColumnIndex(DB_TABLE_COLUMN_PROVINCE)));
            cityInfo.setCity(cursor.getString(cursor.getColumnIndex(DB_TABLE_COLUMN_CITY)));
            cityInfo.setNumber(cursor.getString(cursor.getColumnIndex(DB_TABLE_COLUMN_NUMBER)));
            cityInfo.setAllpy(cursor.getString(cursor.getColumnIndex(DB_TABLE_COLUMN_ALLPY)));
            cityInfo.setAllfirstpy(cursor.getString(cursor.getColumnIndex(DB_TABLE_COLUMN_ALLFIRSTPY)));
            cityInfo.setFirstpy(cursor.getString(cursor.getColumnIndex(DB_TABLE_COLUMN_FIRSTPY)));
            cityInfos.add(cityInfo);
        }
        cursor.close();
        database.close();
        return cityInfos;
    }

}
