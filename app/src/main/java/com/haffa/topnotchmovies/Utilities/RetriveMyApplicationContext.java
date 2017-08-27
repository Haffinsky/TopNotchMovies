package com.haffa.topnotchmovies.Utilities;

import android.app.Application;
import android.content.Context;

/**
 * Created by Rafal on 4/5/2017.
 */

public class RetriveMyApplicationContext extends Application {

    private static RetriveMyApplicationContext mRetriveMyApplicationContext;

    public static RetriveMyApplicationContext getInstance() {

        return mRetriveMyApplicationContext;
    }

    public static Context getAppContext() {

        return mRetriveMyApplicationContext.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRetriveMyApplicationContext = this;
    }
}