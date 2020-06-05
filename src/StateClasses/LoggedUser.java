package StateClasses;


public class LoggedUser {

    public  enum Status {
        Kamarier, Admin
    }
    
    public static int id;
    public static String emri;
    //    public static boolean status = false;
    public static Status status = Status.Kamarier;
    public static boolean loggedIn = false;


    public static void setUser(int id, String emri, Status status) {
        LoggedUser.id = id;
        LoggedUser.emri = emri;
        LoggedUser.status = status;
        LoggedUser.loggedIn = true;
    }

    public static void logout() {
        LoggedUser.id = -1;
        LoggedUser.emri = null;
        LoggedUser.status = Status.Kamarier;
        LoggedUser.loggedIn = false;
    }

    public static boolean isAdmin() {
        return (LoggedUser.status == Status.Admin);
    }
}
