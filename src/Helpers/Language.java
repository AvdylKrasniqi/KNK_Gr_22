package Helpers;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {
    private static ResourceBundle bundle;
    private static String lang;

    public static void translate(String language) {
        Locale locale;
        if (language.equalsIgnoreCase("english")) {
            locale = new Locale("en", "UK");
            lang= "english";
        }
        else {
            locale = new Locale("xk", "AL");
            lang = "albanian";

        }
        bundle = ResourceBundle.getBundle("Languages.strings", locale);


    }

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static String returnLanguage() {
        return lang;
    }

}

