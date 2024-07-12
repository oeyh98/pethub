package ium.pethub.util;

public class AuthConstants {
    public static final String BEARER_PREFIX = "Bearer ";
    public final static String VIEW_INDEX = "index";
    public final static String SIGNIN_URL = "signinUrl";
    public final static String USER = "user";
    public final static String USERID = "userId";
    public final static String ROLE = "role";
    public final static String EMAIL = "email";
    // 조훈창 - 수정
    // NICKNAME CONSTANT 추가
    public final static String NICKNAME = "nickname";
    public final static String NEW_PASSWORD = "newPassword";


    public final static String PASSWORD = "password";
    public final static String CALL_NUMBER = "callNumber";
    public final static String NAME = "name";
    public final static String PROFILE = "profile";
    public final static String ACCESS_TOKEN = "ACCESS_TOKEN";
    public final static String REFRESH_TOKEN = "REFRESH_TOKEN";
    public static final String TOKEN_REGEX = "\\.";
    public static final Integer ACCESS_EXPIRE = 15 * 1000 * 60 * 60 * 24; // 1일
    public static final Integer REFRESH_EXPIRE = 15 * 1000 * 60 * 60 * 24; // 15일
    public static final long PASSWORD_RESET_TOKEN_VALID_TIME = 1000 * 60 * 10; // 10분
}