package com.example.promanager;

import android.app.Application;
import android.util.Log;

//((Global)this.getApplication()).setMyDB(myDB)

public class GlobalVar extends Application {
    public Query db;
    public void setLocalQuery(Query query) {this.db=query;}
    public Query getLocalQuery() {return this.db;}
}
