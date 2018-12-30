package com.example.administrator.hkbookcyh;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class
DBManager extends SQLiteOpenHelper{

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE Item (id INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER, month INTEGER, day INTEGER,won INTEGER, kind INTEGER)";
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertItem(Integer year, Integer month, Integer day, Integer won, Integer kind){
        SQLiteDatabase db = getReadableDatabase();
        String query = "Insert into Item values(null,"+ year +","+ month +","+ day +" , " + won  +"," + kind + ")";
        Log.d("cyhh",year.toString() + " " + month.toString() + " " + day.toString() + " " + won.toString() + " " + kind.toString() );

        db.execSQL(query);

    }

    public void deleteItem(Integer key){
        SQLiteDatabase db = getReadableDatabase();
        String query = "delete from Item where id = "+ key +";";
        db.execSQL(query);
    }

    public ArrayList<Item> getItemList(Integer year, Integer month, Integer day){
        ArrayList<Item> items = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "select * from Item where year = "+ year +" and month = "+ month +" and day = " + day +";";
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){
            Integer id = cursor.getInt(0);
            Integer y = cursor.getInt(1);
            Integer m = cursor.getInt(2);
            Integer d = cursor.getInt(3);
            Integer won = cursor.getInt(4);
            Integer kind = cursor.getInt(5);

            Item temp = new Item(id,y,m,d,won,kind);
            items.add(temp);
        }
        cursor.close();

        return items;
    }

}
