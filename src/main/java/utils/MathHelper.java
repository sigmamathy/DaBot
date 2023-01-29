package utils;

public class MathHelper {

    public static boolean isInteger(String s) throws NumberFormatException{
        double value = Double.parseDouble(s);
        return value == Math.round(value);
    }

    public static String getTime(long milliseconds){
        int minute = (int) Math.floor(milliseconds / 60000.0);
        int sec = (int) Math.floor(milliseconds / 1000.0) - minute * 60;
        String second = sec >= 10 ? String.valueOf(sec) : "0" + sec;
        return minute + ":" + second;
    }
}
