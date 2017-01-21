package com.coachme.coachmeforadmin.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coachme.coachmeforadmin.CoachMeForAdminApp;
import com.coachme.coachmeforadmin.database.SQLiteManager;
import com.coachme.coachmeforadmin.model.Machine;
import com.coachme.coachmeforadmin.utils.errors.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MachineDatabaseHelper {
    public static final String TABLE_NAME = "machine";
    public static final String KEY_ID = "id";
    public static final String KEY__MACHINE_NAME = "machineName";
    public static final String KEY__MACHINE_TYPE = "machineType";
    public static final String KEY_AVAILABLE = "available";
    public static final String KEY_USED_BY_A_TABLET = "usedByATablet";
    public static final String CREATE_TABLE_MACHINE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            " (" +
            " " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + KEY__MACHINE_NAME + " VARCHAR(250) NOT NULL," +
            " " + KEY__MACHINE_TYPE + " VARCHAR(250) NOT NULL," +
            " " + KEY_AVAILABLE + " INTEGER NOT NULL CHECK (" + KEY_AVAILABLE + " IN (0,1))," +
            " " + KEY_USED_BY_A_TABLET + " INTEGER NOT NULL CHECK (" + KEY_USED_BY_A_TABLET + " IN (0,1))" +
            ");";

    private SQLiteManager sqLiteManager; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    public MachineDatabaseHelper() {
        sqLiteManager = SQLiteManager.getInstance(CoachMeForAdminApp.getContext());
        open();
    }

    public void open() {
        db = sqLiteManager.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long addMachine(Machine machine) {
        ContentValues values = new ContentValues();
        values.put(KEY__MACHINE_NAME, machine.getMachineName());
        values.put(KEY__MACHINE_TYPE, machine.getMachineType());
        values.put(KEY_AVAILABLE, true);
        values.put(KEY_USED_BY_A_TABLET, machine.isUsedByATablet());

        return db.insert(TABLE_NAME, null, values);
    }

    public int updateMachine(int id, Machine machine) {
        ContentValues values = new ContentValues();
        values.put(KEY__MACHINE_NAME, machine.getMachineName());
        values.put(KEY__MACHINE_TYPE, machine.getMachineType());
        values.put(KEY_AVAILABLE, machine.isAvailable());
        values.put(KEY_USED_BY_A_TABLET, machine.isUsedByATablet());

        String where = KEY_ID + " = ?";
        String[] whereArgs = {id + ""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteMachine(Machine machine) {
        String where = KEY_ID + " = ?";
        String[] whereArgs = {machine.getId() + ""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Machine getMachine(int id) throws NotFoundException {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + id, null);
        if (cursor.moveToFirst()) {
            Machine machine = new Machine(cursor.getString(cursor.getColumnIndex(KEY__MACHINE_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY__MACHINE_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(KEY_AVAILABLE)) == 1,
                    cursor.getInt(cursor.getColumnIndex(KEY_USED_BY_A_TABLET)) == 1);
            machine.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));

            cursor.close();

            return machine;
        }
        cursor.close();

        throw new NotFoundException("Resource not found", "The machine resource with the id " + id + " does not exist");
    }

    public List<Machine> getMachines() {
        List<Machine> machines = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            Machine machine = new Machine(cursor.getString(cursor.getColumnIndex(KEY__MACHINE_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY__MACHINE_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(KEY_AVAILABLE)) == 1,
                    cursor.getInt(cursor.getColumnIndex(KEY_USED_BY_A_TABLET)) == 1);
            machine.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            machines.add(machine);
        }
        cursor.close();

        return machines;
    }
}
