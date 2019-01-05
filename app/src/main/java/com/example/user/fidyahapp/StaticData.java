package com.example.user.fidyahapp;

/*store constant variable, to make it easy to get access*/
public class StaticData {
    public static String FIREBASE_DATABASE_URL = "https://app-fidyah-1.firebaseio.com/";
    public static boolean isAdmin = false;

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }
}
