package StateClasses;


import com.sun.jdi.event.ExceptionEvent;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class LoggedUser {


    public static boolean loggedIn() {
        return userPreferences.getBoolean("loggedIn", false);
    }


    static Preferences userPreferences = Preferences.userRoot();

    public static void initialize() {
        userPreferences.putBoolean("loggedIn", false);
    }

    public static void logOut() throws BackingStoreException {
        userPreferences.clear();
        userPreferences.putBoolean("loggedIn", false);
    }

    public static int getId() {
        return userPreferences.getInt("id", 0);
    }

    public static String getStatus() {
        return userPreferences.get("status", null);
    }

    public static String getName() {
        return userPreferences.get("name", null);
    }


}

