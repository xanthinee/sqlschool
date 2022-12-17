package sqltask.courses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sqltask.students.Student;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer id;
    @Column(name = "course_name")
    private String name;
    @Column(name = "course_description")
    private String description;

    @ManyToMany(mappedBy = "coursesOfStud")
    Set<Student> studentSet;

    public Course(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
