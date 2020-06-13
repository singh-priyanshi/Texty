package com.skncoe.androidtextrecognition;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {
   private static String DB_Name="history.db";
   private static String TB_Name="Records";
   private  static String col="record";

    public database(Context context) {
        super(context,DB_Name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TB_Name+" (Hid integer primary key autoincrement ,record Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("Drop table if exists "+TB_Name);
    onCreate(db);
    }
    boolean insertdata(String record)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col,record);
        long result=db.insert(TB_Name,null,contentValues);
        if (result==-1)return false;
        else
            return true;
    }
    Cursor getalldata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TB_Name,null);
        return cursor;
    }
}
