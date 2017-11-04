package weatherpi.keyu.com.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/11/1.
 */

public class DBManager {

    public static SQLiteDatabase getDatabase(Context mContext, String dbName) throws  IOException{
        String dbPath = mContext.getDatabasePath("").getPath();
        File file =  new File(dbPath);
        if(!file.exists()){
            file.mkdir();
        }
        InputStream inputStream = mContext.getAssets().open(dbName);
        BufferedInputStream  bufferedInputStream = new BufferedInputStream(inputStream);
        String dbFullPath = dbPath + File.separator + dbName;
        FileOutputStream fileOutputStream = new FileOutputStream(dbFullPath);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        byte[] buf = new byte[1024];
        int len = 0;
        while(bufferedInputStream.read(buf, 0, len) != -1){
             bufferedOutputStream.write(buf, 0, len);
        }
        bufferedOutputStream.close();
        fileOutputStream.close();
        bufferedInputStream.close();
        inputStream.close();
        return SQLiteDatabase.openOrCreateDatabase(dbFullPath, null, null);
    }


}
