package Helpers;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {
    private static ResourceBundle bundle;

    public static void translate(String language) {
        if (language.equalsIgnoreCase("english")) {
            Locale locale = new Locale("en", "UK");
            bundle = ResourceBundle.getBundle("Languages.strings", locale);
        }
        if (language.equalsIgnoreCase("shqip")) {
            Locale locale = new Locale("xk", "AL");
            bundle = ResourceBundle.getBundle("Languages.strings", locale);

        }



    }

    public static ResourceBundle getBundle() {
       return bundle;
    }
}

