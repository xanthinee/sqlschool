package sqltask.studentscourses;

import sqltask.students.Student;

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

    public String printMembers(List<Student> students) {

        StringJoiner sj = new StringJoiner(System.lineSeparator());
        sj.add("STUDENTS which have this COURSE: ");
        for (Student student : students) {
            sj.add(String.format("%-5d|", student.getStudentId())
                    + String.format("%-5d|", student.getGroupId())
                    + String.format("%-10s|", student.getName())
                    + String.format("%1s", student.getSurname()));
        }
        return "abc";
    }
}
