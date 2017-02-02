package com.coachme.coachmeforadmin;

import android.app.Application;
import android.content.Context;

import com.coachme.coachmeforadmin.database.helpers.MachineDatabaseHelper;
import com.coachme.coachmeforadmin.database.helpers.ReservationDatabaseHelper;

public class CoachMeForAdminApp extends Application {
    private static CoachMeForAdminApp instance;
    private static ReservationDatabaseHelper reservationDatabaseHelper;
    private static MachineDatabaseHelper machineDatabaseHelper;

    @Override
    public void onCreate() {
        instance = this;
        reservationDatabaseHelper = new ReservationDatabaseHelper();
        machineDatabaseHelper = new MachineDatabaseHelper();
        super.onCreate();
    }

    public static CoachMeForAdminApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
        // or return instance.getApplicationContext();
    }

    public static ReservationDatabaseHelper getReservationDatabaseHelper() {
        return reservationDatabaseHelper;
    }

    public static void setReservationDatabaseHelper(ReservationDatabaseHelper reservationDatabaseHelper) {
        CoachMeForAdminApp.reservationDatabaseHelper = reservationDatabaseHelper;
    }

    public static MachineDatabaseHelper getMachineDatabaseHelper() {
        return machineDatabaseHelper;
    }

    public static void setMachineDatabaseHelper(MachineDatabaseHelper machineDatabaseHelper) {
        CoachMeForAdminApp.machineDatabaseHelper = machineDatabaseHelper;
    }
}
