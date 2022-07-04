package sqltask.courses;

import java.util.List;
import java.util.StringJoiner;

public class MethodsForCourses {

    public String printCoursesTable(List<Course> courses) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("COURSES:");
        for (Course course : courses) {
            sj.add(String.format("%5d. ",course.getId())
                    + String.format("%12s: ",course.getName())
                    + course.getDescription());
        }
        return sj.toString();
    }
}
