package com.coachme.coachmeforadmin.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coachme.coachmeforadmin.CoachMeForAdminApp;
import com.coachme.coachmeforadmin.database.SQLiteManager;
import com.coachme.coachmeforadmin.model.User;
import com.coachme.coachmeforadmin.utils.errors.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseHelper {
    public static final String TABLE_NAME = "user";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_FIRSTNAME = "firstName";
    public static final String KEY_GOAL = "goal";
    public static final String KEY_LOGGED_ON_A_MACHINE = "loggedOnAMachine";
    public static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            " (" +
            " " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + KEY_NAME + " VARCHAR(250) NOT NULL," +
            " " + KEY_FIRSTNAME + " VARCHAR(250) NOT NULL," +
            " " + KEY_GOAL + " VARCHAR(250) NOT NULL," +
            " " + KEY_LOGGED_ON_A_MACHINE + " INTEGER NOT NULL CHECK (" + KEY_LOGGED_ON_A_MACHINE + " IN (0,1))" +
            ");";

    private SQLiteManager sqLiteManager; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    public UserDatabaseHelper() {
        sqLiteManager = SQLiteManager.getInstance(CoachMeForAdminApp.getContext());
        open();
    }

    public void open() {
        db = sqLiteManager.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_FIRSTNAME, user.getFirstName());
        values.put(KEY_GOAL, user.getGoal());
        values.put(KEY_LOGGED_ON_A_MACHINE, user.isLoggedOnAMachine());

        return db.insert(TABLE_NAME, null, values);
    }

    public int updateUser(int id, User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_FIRSTNAME, user.getFirstName());
        values.put(KEY_GOAL, user.getGoal());
        values.put(KEY_LOGGED_ON_A_MACHINE, user.isLoggedOnAMachine());

        String where = KEY_ID + " = ?";
        String[] whereArgs = {id + ""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteUser(User user) {
        String where = KEY_ID + " = ?";
        String[] whereArgs = {user.getId() + ""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public User getUser(int id) throws NotFoundException {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + id, null);
        if (cursor.moveToFirst()) {
            User user = new User(cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_GOAL)),
                    cursor.getInt(cursor.getColumnIndex(KEY_GOAL)) == 1);
            user.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));

            cursor.close();

            return user;
        }
        cursor.close();

        throw new NotFoundException("Resource not found", "The user resource with the id " + id + " does not exist");
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            User user = new User(cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_GOAL)),
                    cursor.getInt(cursor.getColumnIndex(KEY_GOAL)) == 1);
            user.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            users.add(user);
        }
        cursor.close();

        return users;
    }
}
