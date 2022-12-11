package sqltask.courses;


import javax.persistence.*;

@Entity
@Table(name = "courses")
@NamedQueries({
        @NamedQuery(name = "course.getAll", query = "select c from Course c")
})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "course_name")
    private String name;
    @Column(name = "course_description")
    private String description;

    public Course(){}
    public Course(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
