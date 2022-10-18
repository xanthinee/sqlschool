package sqltask.students;

import lombok.Data;

@Data
public class Student {

    private Integer studentId;
    private Integer groupId;
    private String name;
    private String surname;


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
