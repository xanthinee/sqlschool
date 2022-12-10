package sqltask.students;

import javax.persistence.*;

@Entity(name = "student")
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

    public Student() {}
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


    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
