package sqltask.studentscourses;

import java.util.List;
import java.util.StringJoiner;

public class MethodsForStudCourses {

    public String printStudCourseTable(List<StudentCourse> stdCrs) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("STUDENTS's COURSES");
        for(StudentCourse sc : stdCrs) {
            int studID = sc.getStudentID();
            int courseID = sc.getCourseID();
            sj.add(String.format("%-5d", sc.getRowID()) + " | "
            + String.format("%-5d", studID) + " | "
            + String.format("%1d",courseID));
        }
        return sj.toString();
    }
}
