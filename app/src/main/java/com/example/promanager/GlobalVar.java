package com.example.promanager;

import android.app.Application;
import android.util.Log;

//((Global)this.getApplication()).setMyDB(myDB)

public class GlobalVar extends Application {
    public Query db;
    public void setLocalQuery(Query query) {this.db=query;}
    public Query getLocalQuery() {return this.db;}

    public String userId = "none";
    public void setUserId(String id){this.userId=id;}
    public String getUserId(){return this.userId;}

    public String callback;

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
