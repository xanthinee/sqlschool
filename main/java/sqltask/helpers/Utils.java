package sqltask.helpers;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    private static String repeat(char ch, int repeats) {
        return String.valueOf(ch).repeat(Math.max(0, repeats));
    }
    public static String stringCentre(String str, int stringLength) {
        int emptySpaces = stringLength / 2;
        return repeat(' ', emptySpaces) + str + repeat(' ', emptySpaces);
    }
}
