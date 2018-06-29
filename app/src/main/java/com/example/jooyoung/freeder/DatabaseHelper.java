package com.example.jooyoung.freeder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME ="restaurant";
    SQLiteDatabase db;

    private static final int DATA_BASE_VERSION = 18;

    //SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor;

    //table name
    public static final String CUSTOMER_TABLE = "user";

    //column name of cust_tab
    public static final String db_event_title ="title";
    public static final String db_event_date = "date";
    public static final String db_event_time = "time";
    public static final String db_event_location = "location";
    public static final String db_event_url = "url";
    public static final String db_event_category = "category";
    public static final String db_event_favorite = "favorite";
    public static final String db_event_id = "id";

    String CREATE_C_TABLE = "CREATE TABLE " + CUSTOMER_TABLE + "("
            + db_event_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + db_event_title + " TEXT NOT NULL,"
            + db_event_date + " TEXT NOT NULL," + db_event_time + " TEXT NOT NULL,"
            + db_event_location + " TEXT NOT NULL," + db_event_url + " TEXT NOT NULL," + db_event_category + " TEXT NOT NULL,"
            + db_event_favorite + " TEXT NOT NULL"+ ");";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_C_TABLE);
        Log.i("create table","성공");

    }

    public void insert(String title , String date , String time, String location , String url , String category){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO user VALUES(null, '" + title + "', '" + date + "', '" + time + "', '" + location + "', '"
                   + url + "', '" + category + "', 'true');");
        db.close();
    }

    public void delete(String title){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM user WHERE title = '" + title + "';");
        db.close();
    }

    public String select(){
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        cursor = db.rawQuery("SELECT * FROM user ",null);
        while(cursor.moveToNext()){
            result += cursor.getString(1) + "@" + cursor.getString(2) + "@"
                    + cursor.getString(3) + "@" + cursor.getString(4) + "@"
                    + cursor.getString(5) + "@" + cursor.getString(6) + "$";
        }
        cursor.close();
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
