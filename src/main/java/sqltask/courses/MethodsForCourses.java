package sqltask.courses;

import sqltask.helpers.CustomFileReader;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class MethodsForCourses {

    public List<Course> makeCoursesList(String fileName) {

        CustomFileReader fileCon = new CustomFileReader();
        Stream<String> courses = fileCon.readFile(fileName);
        CoursesParser cp = new CoursesParser();
        return courses.map(cp::parse).toList();
    }

    public String printCoursesTable(List<Course> courses) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("COURSES:");
        for (Course course : courses) {
            sj.add(course.getId() + ". " + course.getName()
                    + ": " + course.getDescription().trim());
        }
        return sj.toString();
    }
}
