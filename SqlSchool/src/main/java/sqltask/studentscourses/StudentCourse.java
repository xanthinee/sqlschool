package sqltask.studentscourses;

import lombok.Data;
@Data
public class StudentCourse {

    int rowID;
    int studentID;
    int courseID;

    public StudentCourse(int rowID, int studentID, int courseID) {
        this.rowID = rowID;
        this.studentID = studentID;
        this.courseID = courseID;
    }
}
