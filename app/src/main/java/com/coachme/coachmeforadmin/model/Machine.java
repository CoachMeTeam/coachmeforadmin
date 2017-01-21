package com.coachme.coachmeforadmin.model;

import javax.validation.constraints.NotNull;

public class Machine {
    private int id;
    @NotNull
    private String machineName;
    @NotNull
    private String machineType;
    private boolean available;
    private boolean usedByATablet;

    public Machine() {
    }

    public Machine(String machineName, String machineType, boolean available, boolean usedByATablet) {
        this.machineName = machineName;
        this.machineType = machineType;
        this.available = available;
        this.usedByATablet = usedByATablet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isUsedByATablet() {
        return usedByATablet;
    }

    public void setUsedByATablet(boolean usedByATablet) {
        this.usedByATablet = usedByATablet;
    }
}
