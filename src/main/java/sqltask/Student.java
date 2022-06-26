package sqltask;

import lombok.Data;

@Data
public class Student {

    private int studentId;
    private Integer groupId;
    private String name;
    private String surname;


    public Student(int studentId, Integer groupId, String name, String surname) {
        this.studentId = studentId;
        this.groupId = groupId;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("student ID: " + studentId + " | ");
        sb.append("group ID: " + groupId + " | ");
        sb.append("name: " + String.format("%-9s", name) + " | ");
        sb.append("surname: " + String.format("%-14s", surname));
        return sb.toString();
    }
}
