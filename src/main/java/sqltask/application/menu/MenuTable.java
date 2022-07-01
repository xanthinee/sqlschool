package sqltask.application.menu;

import lombok.Data;

@Data
public class MenuTable {

    int code;
    String name;

    public MenuTable(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
