package weatherpi.keyu.com.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/11/1.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public MyDatabaseHelper(Context context, String dbName, SQLiteDatabase.CursorFactory  cursorFactory, int version){
        super(context, dbName, cursorFactory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
