package sqltask.courses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sqltask.students.Student;

import javax.persistence.*;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id.equals(course.id) && name.equals(course.name) && description.equals(course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
