package sqltask.courses;

import lombok.Data;
import java.util.*;

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

    public Course(Map<String, Object> map) {
        this.id = (Integer) map.get("course_id");
        this.name = (String) map.get("course_name");
        this.description = (String) map.get("course_description");
    }
}
