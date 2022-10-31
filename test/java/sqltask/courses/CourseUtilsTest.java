package sqltask.courses;

import org.junit.jupiter.api.Test;
import sqltask.students.Student;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CourseUtilsTest {

    @Test
    void makeCourseList_whenDataIsPresence_shouldMakeListOfCourses() {

        Course testCourse = new Course(null, "Mathematics", "mathematical science with geometry features");
        Course testCourse1 = new Course(null, "Computer science", "Study of computation and automation");
        Course testCourse2 = new Course(null, "Foreign language", "Study of English language");
        List<Course> testList = List.of(testCourse, testCourse1, testCourse2);
        assertEquals(testList, CourseUtils.makeCoursesList("testdata/coursestest.txt"));
    }

    @Test
    void printCoursesOfStud_whenHasCourses_shouldPrintCourses() {

        List<Course> courses = new ArrayList<>();
        Course testCourse1 = new Course(1, "course", "description");
        Course testCourse2 = new Course(2, "course", "description");
        Course testCourse3 = new Course(3, "course", "description");
        courses.add(testCourse1);
        courses.add(testCourse2);
        courses.add(testCourse3);
        String result = """
                Entered STUDENT has next COURSES:\s
                1. course
                2. course
                3. course""";
        assertEquals(result, CourseUtils.printCoursesOfStud(courses));
    }

    @Test
    void infoToPrint_whenStudentHasCourses_shouldPrintAllExceptOfAlreadyUsedCourses() {

        List<Course> allCourses = CourseUtils.makeCoursesList("testdata/coursestest.txt");
        List<Course> avbCourses = new ArrayList<>();
        Course course = new Course(1, "Computer science", "description");
        Course course2 = new Course(2, "Foreign language", "description");
        avbCourses.add(course);
        avbCourses.add(course2);

        String result = """
                You can give next COURSES to STUDENT:
                1. Computer science
                2. Foreign language""";
        assertEquals(result, CourseUtils.infoToPrint(allCourses, avbCourses));
    }

    @Test
    void infoToPrint_whenStudentHasNoCourses_ShouldPrintAllCourses() {

        List<Course> allCourses = CourseUtils.makeCoursesList("testdata/coursestest.txt");
        List<Course> avbCourses = new ArrayList<>();
        Course course = new Course(1, "Mathematics", "description");
        Course course1 = new Course(2, "Computer science", "description");
        Course course2 = new Course(3, "Foreign language", "description");
        avbCourses.add(course);
        avbCourses.add(course1);
        avbCourses.add(course2);
        String result = """
                You can give next COURSES to STUDENT:
                1. Mathematics
                2. Computer science
                3. Foreign language""";
        assertEquals(result, CourseUtils.infoToPrint(allCourses, avbCourses));
    }

    @Test
    void printMembers_whenMembersArePresence_shouldPrintThem() {

        List<Student> students = new ArrayList<>();
        Student student = new Student(1,1,"A", "B");
        Student student1 = new Student(1,1,"A", "B");
        students.add(student);
        students.add(student1);
        String result = """
                STUDENTS which have this COURSE:
                1    |1    |A          B
                1    |1    |A          B""";
        assertEquals(result, CourseUtils.printMembers(students));
    }
}