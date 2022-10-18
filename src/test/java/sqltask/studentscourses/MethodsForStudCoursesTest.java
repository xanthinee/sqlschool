package sqltask.studentscourses;

import sqltask.courses.*;

class MethodsForStudCoursesTest {

    CourseUtils testObj = new CourseUtils();

//    @Test
//    void printStudCourseTable_whenNoLines() {
//        List<StudentCourse> testList = List.of();
//        String expected = "STUDENTS's COURSES";
//        assertEquals(expected, testObj.printStudCourseTable(testList));
//    }
//
//    @Test
//    void printStudCourseTable_whenOneLine() {
//        StudentCourse testSC = new StudentCourse(1,1,1);
//        List<StudentCourse> testList = List.of(testSC);
//        String expected = "STUDENTS's COURSES\n" +
//                "1     | 1     | 1";
//        assertEquals(expected, testObj.printStudCourseTable(testList));
//    }
//
//    @Test
//    void printStudCourseTable_whenSeveralLines() {
//        StudentCourse testSc = new StudentCourse(1,1,1);
//        StudentCourse testSc1 = new StudentCourse(1,1,1);
//        List<StudentCourse> testList = List.of(testSc, testSc1);
//        String expected = "STUDENTS's COURSES\n" +
//                "1     | 1     | 1\n" +
//                "1     | 1     | 1";
//        assertEquals(expected, testObj.printStudCourseTable(testList));
//    }
//
//    @Test
//    void printStudCourseTable_whenDifferentSizesOfIndexes() {
//        StudentCourse testSc = new StudentCourse(1,10,100);
//        StudentCourse testSc1 = new StudentCourse(100,1,10);
//        StudentCourse testSc2 = new StudentCourse(10,100,1);
//        List<StudentCourse> testList = List.of(testSc, testSc1, testSc2);
//        String expected = "STUDENTS's COURSES\n" +
//                "1     | 10    | 100\n" +
//                "100   | 1     | 10\n" +
//                "10    | 100   | 1";
//        assertEquals(expected, testObj.printStudCourseTable(testList));
//    }
}