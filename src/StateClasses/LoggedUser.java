package StateClasses;

public class LoggedUser {
    public static int id;
    public static String emri;
    public static boolean status = false;
    public static boolean loggedIn = false;
    /*
    * status(kamarier) = 0
    * status(admin) = 1
    * */
    public static void setUser(int id, String emri, boolean status){
        LoggedUser.id = id;
        LoggedUser.emri = emri;
        LoggedUser.status = status;
        LoggedUser.loggedIn = true;
    }
    public static void logout(){
        LoggedUser.id = -1;
        LoggedUser.emri = null;
        LoggedUser.status = false;
        LoggedUser.loggedIn = false;
    }
    public static boolean isAdmin(){
        return LoggedUser.status;
    }
}
