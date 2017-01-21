package com.coachme.coachmeforadmin.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coachme.coachmeforadmin.database.helpers.MachineDatabaseHelper;
import com.coachme.coachmeforadmin.database.helpers.ReservationDatabaseHelper;
import com.coachme.coachmeforadmin.database.helpers.UserDatabaseHelper;
import com.coachme.coachmeforadmin.utils.ConfigPropertiesReader;

public class SQLiteManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = ConfigPropertiesReader.getConfig().getProperty("database_name");
    private static final int DATABASE_VERSION = 1;
    private static SQLiteManager sInstance;

    public static synchronized SQLiteManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SQLiteManager(context);
        }
        return sInstance;
    }

    private SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserDatabaseHelper.CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(MachineDatabaseHelper.CREATE_TABLE_MACHINE);
        sqLiteDatabase.execSQL(ReservationDatabaseHelper.CREATE_TABLE_RESERVATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        onCreate(sqLiteDatabase);
    }
}
