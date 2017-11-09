package utils;

public class NumberUtils {
    private NumberUtils(){}
    public static int toInteger(String number, int fallback){
        int result = fallback;
        try {
            result = Integer.valueOf(number);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return result;
    }
    public static float toFloat(String number, float fallback){
        float result = fallback;
        try {
            result = Float.valueOf(number);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return result;
    }
}
