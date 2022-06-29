package sqltask.courses;

import sqltask.helpers.customFileReader;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class CourseMethods {

    public List<Course> makeCoursesList(String fileName) {

        customFileReader fileCon = new customFileReader();
        Stream<String> courses = fileCon.readFile(fileName);
        CoursesParser cp = new CoursesParser();
        return courses.map(cp::parse).toList();
    }

    public String printCoursesTable(List<Course> courses) {

        StringJoiner sj = new StringJoiner("");
        sj.add("COURSES:");
        sj.add(System.lineSeparator());
        for (Course course : courses) {
            sj.add(course.getId() + ". " + course.getName().trim()
                    + ": " + course.getDescription().trim() + System.lineSeparator());
        }
        return sj.toString();
    }
}
