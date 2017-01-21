package com.coachme.coachmeforadmin;

import android.app.Application;
import android.content.Context;

public class CoachMeForAdminApp extends Application {
    private static CoachMeForAdminApp instance;

    public static CoachMeForAdminApp getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
