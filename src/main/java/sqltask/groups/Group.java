package sqltask.groups;

import javax.persistence.*;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "group_name")
    private String name;

    public Group(){}
    public Group(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(System.lineSeparator());
        sb.append("Group ID: ").append(id).append(" | ");
        sb.append("Name: ").append(name);
        return sb.toString();
    }
}
