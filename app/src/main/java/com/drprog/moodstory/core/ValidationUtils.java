package com.drprog.moodstory.core;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by donchenko_r on 19.01.2016.
 */
public final class ValidationUtils {
    /**
     * @param _email - entered email by user
     * @return - true in case if correct email, else false
     */
    public static boolean isValidEmail(final String _email) {
        return !TextUtils.isEmpty(_email) && Patterns.EMAIL_ADDRESS.matcher(_email).matches();
    }

    /**
     * @param _password - entered password by user
     * @param minLength
     * @return - true in case if the password is not empty and more then 4 symbols
     */
    public static boolean isValidPassword(final String _password, int minLength) {
        return !TextUtils.isEmpty(_password) && _password.length() >= minLength;
    }

    public static boolean isEmptyField(final String _field) {
        return TextUtils.isEmpty(_field);
    }

    /**
     * @param _userName - username to validate. this string cannot be empty
     * @return - true in case if field is not empty
     */
    public static boolean isValidUserName(final String _userName) {
        return !TextUtils.isEmpty(_userName);
    }

    /**
     * @param _phone - entered phone number by user
     * @return - true in case if correct phone number, else false
     */
    public static boolean isValidPhone(final String _phone) {
        return !TextUtils.isEmpty(_phone) && Patterns.PHONE.matcher(_phone).matches();
    }
}
