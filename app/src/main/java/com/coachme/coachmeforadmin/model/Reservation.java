package com.coachme.coachmeforadmin.model;

import javax.validation.constraints.NotNull;

public class Reservation {
    private int id;
    @NotNull
    private String reservationDate;
    @NotNull
    private int duration;
    @NotNull
    private int machineId;
    @NotNull
    private int userId;

    public Reservation() {
    }

    public Reservation(String reservationDate, int duration, int machineId, int userId) {
        this.reservationDate = reservationDate;
        this.duration = duration;
        this.machineId = machineId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
