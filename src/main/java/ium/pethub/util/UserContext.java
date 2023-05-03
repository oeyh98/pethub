package ium.pethub.util;

public class UserContext {
    public static ThreadLocal<TokenUserData> userData = new ThreadLocal<>();

    public static void remove() {
        if (userData != null)
            userData.remove();
    }
}
