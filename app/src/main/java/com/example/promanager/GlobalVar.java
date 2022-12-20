package com.example.promanager;

import android.app.Application;
import android.util.Log;

//((Global)this.getApplication()).setMyDB(myDB)

public class GlobalVar extends Application {
    public int mssv;
    public void setMssv(int ms) {mssv=ms;}
    public int getMssv() {return mssv;}
    public Query db;
    public void setLocalQuery(Query query) {
        Log.e("GlobalVar", "Set up db");
        this.db=query;
        Log.e("GlobalVar", db.toString());
    }
    public Query getLocalQuery() {
        Log.e("GlobalVar", this.db.toString());
        return this.db;}
}
