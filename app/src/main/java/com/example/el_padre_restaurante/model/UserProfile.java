package com.example.el_padre_restaurante.model;

public class UserProfile {
    private static int id;
    private static String username;
    private static boolean golden;
    private static int type;
    private static boolean loggedIn = false;

    public static int getId() {return id;}

    public static void setId(int id) {UserProfile.id = id;}

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserProfile.username = username;
    }

    public static boolean isGolden() {
        return golden;
    }

    public static void setGolden(boolean golden) {
        UserProfile.golden = golden;
    }

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        UserProfile.type = type;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        UserProfile.loggedIn = loggedIn;
    }
}
