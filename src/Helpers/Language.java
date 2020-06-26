package Helpers;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {
    private static ResourceBundle bundle;
    private static String language="english";

    public static void translate(String language) {
        if (language.equalsIgnoreCase("english")) {
            Locale locale = new Locale("en", "UK");
            language="english";
            bundle = ResourceBundle.getBundle("Languages.strings", locale);
        }
        if (language.equalsIgnoreCase("shqip")) {
            Locale locale = new Locale("xk", "AL");
            language="albanian";
            bundle = ResourceBundle.getBundle("Languages.strings", locale);

        }



    }

    public static ResourceBundle getBundle() {
       return bundle;
    }
    public static String returnLanguage()
    {
        return language;
    }

}

