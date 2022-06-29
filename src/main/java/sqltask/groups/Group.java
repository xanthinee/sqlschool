package sqltask.groups;

import lombok.Data;
import java.util.Random;

@Data
public class Group {

    private int id;
    private String name;
    Random rd;

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
