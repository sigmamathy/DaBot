package utils;

import java.awt.*;

public class ColorHelper {

    private ColorHelper(){}

    public static Color returnColor(String s){
        switch (s.toLowerCase()){
            case "red" -> {
                return Color.RED;
            }
            case "cyan" -> {
                return Color.CYAN;
            }
            case "white" -> {
                return Color.WHITE;
            }
            case "black" -> {
                return Color.BLACK;
            }
            case "blue" -> {
                return Color.BLUE;
            }
            case "green" -> {
                return Color.GREEN;
            }
            case "gray" -> {
                return Color.GRAY;
            }
            case "magenta" -> {
                return Color.MAGENTA;
            }
            case "pink" -> {
                return Color.PINK;
            }
            case "orange" -> {
                return Color.ORANGE;
            }
            default -> {
                return null;
            }
        }
    }
}
