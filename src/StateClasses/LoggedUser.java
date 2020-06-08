package StateClasses;


public class LoggedUser {

    public enum Status {
        Waiter, Admin
    }

    public static int id;
    public static String emri;
    public static Status status;
    public static boolean loggedIn;


    public static void setUser(int id, String emri, Status status) {
        LoggedUser.id = id;
        LoggedUser.emri = emri;
        LoggedUser.status = status;
        LoggedUser.loggedIn = true;
    }

    public static void logout() {
        LoggedUser.id = -1;
        LoggedUser.emri = null;
        LoggedUser.status = Status.Waiter;
        LoggedUser.loggedIn = false;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static boolean isWaiter() {
        return (loggedIn && LoggedUser.status == Status.Waiter);
    }

    public static boolean isAdmin() {
        return (loggedIn && LoggedUser.status == Status.Admin);
    }
}
