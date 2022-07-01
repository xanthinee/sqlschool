package sqltask.application.methods.comparison;

import java.util.List;
import java.util.StringJoiner;

public class CompareGroupsMethods {

    public String resultOfComparison(List<String> withEqualSize, List<String> withFewerSize) {

        StringBuilder sb = new StringBuilder("");
        if (withFewerSize.isEmpty()) {
            sb.append("There no group with FEWER amount of students.");
        } else {
            sb.append("Groups with FEWER amount of students: ");
            StringJoiner sj = new StringJoiner("; ");
            for (String names : withFewerSize) {
                sj.add(names);
            }
        }
        sb.append(System.lineSeparator());

        if (withEqualSize.isEmpty()) {
            sb.append("There no group with EQUAL amount of students.");
        } else {
            sb.append("Groups with EQUAL amount of students: ");
            StringJoiner sj = new StringJoiner("; ");
            for (String names : withEqualSize) {
                sj.add(names);
            }
            sb.append(sj);
        }
        return sb.toString();
    }
}
