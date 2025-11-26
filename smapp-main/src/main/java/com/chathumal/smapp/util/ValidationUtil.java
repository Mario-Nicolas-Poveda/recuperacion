package com.chathumal.smapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email.trim());

        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        return password.trim().length() >= 4;
    }

    public static boolean isValidMobile(String mobile) {
        if (mobile == null || mobile.trim().isEmpty()) {
            return false;
        }
        String mobileRegex = "^3\\d{9}$";
        return mobile.matches(mobileRegex);
    }
}
