package sqltask.courses;

import sqltask.helpers.CustomFileReader;
import sqltask.students.Student;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class CourseUtils {

    private CourseUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Course> makeCoursesList(String fileName) {

        CustomFileReader fileCon = new CustomFileReader();
        Stream<String> courses = fileCon.readFile(fileName);
        CoursesParser cp = new CoursesParser();
        return courses.map(cp::parse).toList();
    }

    public static String printCoursesTable(List<Course> courses) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("COURSES:");
        for (Course course : courses) {
            sj.add(String.format("%5d. ",course.getId())
                    + String.format("%-12s: ",course.getName())
                    + course.getDescription());
        }
        return sj.toString();
    }

    public static String printCoursesOfStud(List<Course> courses) {
        StringJoiner sjCourses = new StringJoiner(System.lineSeparator());
        sjCourses.add("Entered STUDENT has next COURSES: ");
        int index = 1;
        for (Course course : courses) {
            sjCourses.add(index + ". " + course.getName());
            index++;
        }
        return sjCourses.toString();
    }

    public static String infoToPrint(List<Course> coursesOfStudent, List<Course> availableForStudentCourses) {
        int numOfCourses = coursesOfStudent.size();
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        if (numOfCourses != 0) {
            sj.add("You can give next COURSES to STUDENT1:");
            int index = 1;
            for (Course courseName : availableForStudentCourses) {
                sj.add(index + ". " + courseName.getName());
                index++;
            }
        } else {
            sj.add("Chosen STUDENT has no any COURSES");
            sj.add("You can give next COURSES to STUDENT2:");
            int index = 1;
            for (Course courseName : availableForStudentCourses) {
                sj.add(index + ". " + courseName.getName());
                index++;
            }
            return sj.toString();
        }
        return sj.toString();
    }

    public static String printMembers(List<Student> students) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("STUDENTS which have this COURSE: ");
        for (Student student : students) {
            sj.add(String.format("%-5d|", student.getStudentId())
                    + String.format("%-5d|", student.getGroupId())
                    + String.format("%-10s ", student.getName())
                    + String.format("%s", student.getSurname()));
        }
        return sj.toString();
    }
}
