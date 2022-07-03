package sqltask.application.methods.courseaddition;

import java.util.List;
import java.util.StringJoiner;

public class CourseAdditionMethods {

    public String printCoursesOfStud(List<String> courses) {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("Entered STUDENT has next COURSES: ");
        int index = 1;
        for (String course : courses) {
            sj.add(index + ". " + course);
            index++;
        }
        return sj.toString();
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
