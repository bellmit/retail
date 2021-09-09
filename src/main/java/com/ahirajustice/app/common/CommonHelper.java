package com.ahirajustice.app.common;

public class CommonHelper {

    public static boolean isStringUpperCase(String str) {
        char[] charArray = str.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            if (!Character.isUpperCase(charArray[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean containsSpecialCharactersAndNumbers(String str) {
        String specialCharactersAndNumbers = "!@#$%&*()'+,-./:;<=>?[]^_`{|}0123456789";

        for (int i=0; i < str.length() ; i++)
        {
            char ch = str.charAt(i);
            if(specialCharactersAndNumbers.contains(Character.toString(ch))) {
                return true;
            }
        }

        return false;
    }

}
