package utils;

public class MathHelper {

    public static boolean isInteger(String s) throws NumberFormatException{
        double value = Double.parseDouble(s);
        return value == Math.round(value);
    }

    public static String getTime(long milliseconds){
        int hour = (int) Math.floor(milliseconds / 3600000.0);
        int min = (int) Math.floor(milliseconds / 60000.0) - hour * 60;
        int sec = (int) Math.floor(milliseconds / 1000.0) - hour * 3600 - min * 60;
        String second = sec >= 10 ? String.valueOf(sec) : "0" + sec;
        if (hour == 0) return min + ":" + second;
        String minute = min >= 10 ? String.valueOf(min) : "0" + min;
        return hour + ":" + minute + ":" + second;
    }
}
