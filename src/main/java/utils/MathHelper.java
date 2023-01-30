package utils;

public class MathHelper {

    public static String getTime(long milliseconds){
        int hour = (int) Math.floor(milliseconds / 3600000.0);
        int min = (int) Math.floor(milliseconds / 60000.0) - hour * 60;
        int sec = (int) Math.floor(milliseconds / 1000.0) - hour * 3600 - min * 60;
        String second = sec >= 10 ? String.valueOf(sec) : "0" + sec;
        if (hour == 0) return min + ":" + second;
        String minute = min >= 10 ? String.valueOf(min) : "0" + min;
        return hour + ":" + minute + ":" + second;
    }

    public static long timeToLong(String str)
    {
        int[] colon_places = new int[2];
        int j = 0;
        for (int i = 0; i < str.length() && j < 2; i++) {
            if (str.charAt(i) == ':')
                colon_places[j++] = i;
        }
        try {
            if (j == 0)
                return Long.parseLong(str) * 1000;
            long x = Long.parseLong(str.substring(0, colon_places[0]));
            switch (j) {
                case 1 -> {
                    long sec = Long.parseLong(str.substring(colon_places[0] + 1));
                    return x * 60000 + sec * 1000;
                }
                case 2 -> {
                    long min = Long.parseLong(str.substring(colon_places[0] + 1, colon_places[1]));
                    long sec = Long.parseLong(str.substring(colon_places[1] + 1));
                    return x * 3600000 + min * 60000 + sec * 1000;
                }
                default -> {
                    return -1;
                }
            }
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
