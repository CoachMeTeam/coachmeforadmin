package com.coachme.coachmeforadmin.model;

import javax.validation.constraints.NotNull;

public class User {

    private int id;
    @NotNull
    private String name;
    @NotNull
    private String firstName;
    private String goal;
    private boolean loggedOnAMachine;

    public User() {
    }

    public User(String name, String firstName, String goal, boolean loggedOnAMachine) {
        this.name = name;
        this.firstName = firstName;
        this.goal = goal;
        this.loggedOnAMachine = loggedOnAMachine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public boolean isLoggedOnAMachine() {
        return loggedOnAMachine;
    }

    public void setLoggedOnAMachine(boolean loggedOnAMachine) {
        this.loggedOnAMachine = loggedOnAMachine;
    }
}
