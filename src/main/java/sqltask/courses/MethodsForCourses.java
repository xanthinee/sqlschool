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
            sj.add(String.format("%5d. ",course.getId())
                    + String.format("%-12s: ",course.getName())
                    + course.getDescription());
        }
        return sj.toString();
    }

    public String printCoursesOfStud(List<String> courses) {
        StringJoiner sjCourses = new StringJoiner(System.lineSeparator());
        sjCourses.add("Entered STUDENT has next COURSES: ");
        int index = 1;
        for (String course : courses) {
            sjCourses.add(index + ". " + course);
            index++;
        }
        return sjCourses.toString();
    }

    public String infoToPrint(List<String> coursesOfStudent, List<String> availableForStudentCourses) {
        int numOfCourses = coursesOfStudent.size();
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        if (numOfCourses != 0) {
            sj.add(printCoursesOfStud(coursesOfStudent));
            sj.add("You can give next COURSES to STUDENT:");
            int index = 1;
            for (String courseName : availableForStudentCourses) {
                sj.add(index + ". " + courseName);
                index++;
            }
        } else {
            sj.add("Chosen STUDENT has no any COURSES");
            sj.add("You can give next COURSES to STUDENT:");
            int index = 1;
            for (String courseName : availableForStudentCourses) {
                sj.add(index + ". " + courseName);
                index++;
            }
            return sj.toString();
        }
        return sj.toString();
    }
}
