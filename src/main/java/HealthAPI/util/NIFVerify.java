package HealthAPI.util;

public class NIFVerify {

    public static boolean verifyNif(int NIF) {
        int checkSum = 0;
        String NIFString = Integer.toString(NIF);
        for (int i = 0; i < NIFString.length() - 1; i++) {
            checkSum += (NIFString.charAt(i) - '0') * (NIFString.length() - i);
        }
        int checkDigit = 11 - (checkSum % 11);
        if (checkDigit >= 10) checkDigit = 0;
        return checkDigit == NIFString.charAt(NIFString.length() - 1) - '0';
    }

}
