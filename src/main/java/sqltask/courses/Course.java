package sqltask.courses;

import lombok.Data;

@Data
public class Course {

    private Integer id;
    private String name;
    private String description;

    public Course(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
