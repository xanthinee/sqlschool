package sqltask.helpers;

import java.util.StringJoiner;

public class Utils {

    public static String repeat(char ch, int repeats) {
        return String.valueOf(ch).repeat(Math.max(0, repeats));
    }
    public static String stringCentre(String str, int stringLength) {
        int emptySpaces = stringLength / 2;
        StringJoiner sj = new StringJoiner("");
        sj.add(repeat(' ', emptySpaces) + str + repeat(' ', emptySpaces));
        return sj.toString();
    }
}
