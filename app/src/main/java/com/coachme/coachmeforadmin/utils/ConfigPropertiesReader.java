package com.coachme.coachmeforadmin.utils;


import com.coachme.coachmeforadmin.CoachMeForAdminApp;

import java.util.Properties;

public class ConfigPropertiesReader {
    public static Properties getConfig() {
        try {
            Properties properties = new Properties();
            properties.load(CoachMeForAdminApp.getContext().getAssets().open("app.properties"));
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
