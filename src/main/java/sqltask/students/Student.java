package sqltask.students;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sqltask.courses.Course;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "student")
@NoArgsConstructor
@Getter
@Setter
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "first_name")
    private String name;
    @Column(name = "second_name")
    private String surname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "students_courses",
    joinColumns = @JoinColumn(name = "student_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id"))
    Set<Course> coursesOfStud;

    public Student(Integer studentId, Integer groupId, String name, String surname) {
        this.studentId = studentId;
        this.groupId = groupId;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("student ID: ").append(studentId).append(" | ");
        sb.append("group ID: ").append(groupId).append(" | ");
        sb.append("name: ").append(String.format("%-9s", name).trim()).append(" | ");
        sb.append("surname: ").append(String.format("%-14s", surname));
        return sb.toString();
    }
}
