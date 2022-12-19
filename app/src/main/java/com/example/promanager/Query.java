package com.example.promanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class Query extends SQLiteOpenHelper {
    public Query(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //------------------ Vũ Đức Dũng - 20127473 -----------------------

    //Truy vấn không trả kết quả
    public void queryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    //Truy vấn trả kết quả
    public Cursor getData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
