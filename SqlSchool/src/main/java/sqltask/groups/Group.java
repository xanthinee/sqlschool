package sqltask.groups;

import lombok.Data;

@Data
public class Group {

    private int id;
    private String name;

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
