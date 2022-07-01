package sqltask.application.methods.comparison;

import java.util.List;
import java.util.StringJoiner;

public class CompareGroupsMethods {

    public String resultOfComparison(List<String> withEqualSize, List<String> withFewerSize) {

        StringJoiner sj = new StringJoiner("");
        if (withFewerSize.isEmpty()) {
            sj.add("There no group with FEWER amount of students.");
        } else {
            sj.add("Groups with FEWER amount of students: ");
            for (String names : withFewerSize) {
                sj.add(names + "; ");
            }
        }
        sj.add(System.lineSeparator());

        if (withEqualSize.isEmpty()) {
            sj.add("There no group with EQUAL amount of students.");
        } else {
            sj.add("Groups with EQUAL amount of students: ");
            for (String names : withEqualSize) {
                sj.add(names + "; ");
            }
        }
        return sj.toString();
    }
}
