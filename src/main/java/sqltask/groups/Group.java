package sqltask.groups;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "groups")
@NamedQuery(name = "group.compareGroup", query = "select g from Group g left outer join student s on g.id = s.groupId where g.id <>?1 group by g.id, g.name having count(s.id) <= ( select count(s2.id) from student s2 where s2.groupId = ?2)")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer id;

    @Column(name = "group_name")
    private String name;

    public Group(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(System.lineSeparator());
        sb.append("Group ID: ").append(id).append(" | ");
        sb.append("Name: ").append(name);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id.equals(group.id) && name.equals(group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
