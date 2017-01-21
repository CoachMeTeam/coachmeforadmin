package com.coachme.coachmeforadmin.database.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coachme.coachmeforadmin.CoachMeForAdminApp;
import com.coachme.coachmeforadmin.database.SQLiteManager;
import com.coachme.coachmeforadmin.model.Reservation;
import com.coachme.coachmeforadmin.utils.errors.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ReservationDatabaseHelper {
    public static final String TABLE_NAME = "reservation";
    public static final String KEY_ID = "id";
    public static final String KEY_RESERVATION_DATE = "reservationDate";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_MACHINE_ID = "machineId";
    public static final String KEY_USER_ID = "userId";
    public static final String CREATE_TABLE_RESERVATION = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            " (" +
            " " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            " " + KEY_RESERVATION_DATE + " VARCHAR(250) NOT NULL," +
            " " + KEY_DURATION + " INTEGER NOT NULL," +
            " " + KEY_MACHINE_ID + " INTEGER NOT NULL," +
            " " + KEY_USER_ID + " INTEGER NOT NULL," +
            " " + "FOREIGN KEY (" + KEY_MACHINE_ID + ") REFERENCES " + MachineDatabaseHelper.TABLE_NAME + "(" + MachineDatabaseHelper.KEY_ID + ")," +
            " " + "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + UserDatabaseHelper.TABLE_NAME + "(" + UserDatabaseHelper.KEY_ID + ")" +
            ");";

    private SQLiteManager sqLiteManager; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    public ReservationDatabaseHelper() {
        sqLiteManager = SQLiteManager.getInstance(CoachMeForAdminApp.getContext());
        open();
    }

    public void open() {
        db = sqLiteManager.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long addReservation(Reservation reservation) {
        ContentValues values = new ContentValues();
        values.put(KEY_RESERVATION_DATE, reservation.getReservationDate());
        values.put(KEY_DURATION, reservation.getDuration());
        values.put(KEY_MACHINE_ID, reservation.getMachineId());
        values.put(KEY_USER_ID, reservation.getUserId());

        return db.insert(TABLE_NAME, null, values);
    }

    public int updateReservation(int id, Reservation reservation) {
        ContentValues values = new ContentValues();
        values.put(KEY_RESERVATION_DATE, reservation.getReservationDate());
        values.put(KEY_DURATION, reservation.getDuration());
        values.put(KEY_MACHINE_ID, reservation.getMachineId());
        values.put(KEY_USER_ID, reservation.getUserId());

        String where = KEY_ID + " = ?";
        String[] whereArgs = {id + ""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteReservation(Reservation reservation) {
        String where = KEY_ID + " = ?";
        String[] whereArgs = {reservation.getId() + ""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Reservation getReservation(int id) throws NotFoundException {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + id, null);
        if (cursor.moveToFirst()) {
            Reservation reservation = new Reservation(cursor.getString(cursor.getColumnIndex(KEY_RESERVATION_DATE)),
                    cursor.getInt(cursor.getColumnIndex(KEY_DURATION)),
                    cursor.getInt(cursor.getColumnIndex(KEY_MACHINE_ID)),
                    cursor.getInt(cursor.getColumnIndex(KEY_USER_ID)));
            reservation.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));

            cursor.close();

            return reservation;
        }
        cursor.close();

        throw new NotFoundException("Resource not found", "The reservation resource with the id " + id + " does not exist");
    }

    public List<Reservation> getReservations() {
        List<Reservation> reservations = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            Reservation reservation = new Reservation(cursor.getString(cursor.getColumnIndex(KEY_RESERVATION_DATE)),
                    cursor.getInt(cursor.getColumnIndex(KEY_DURATION)),
                    cursor.getInt(cursor.getColumnIndex(KEY_MACHINE_ID)),
                    cursor.getInt(cursor.getColumnIndex(KEY_USER_ID)));
            reservation.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            reservations.add(reservation);
        }
        cursor.close();

        return reservations;
    }
}
