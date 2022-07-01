package sqltask.application;

import lombok.Data;

@Data
public class MenuTable {

    int code;
    String name;
    String methodName;

    public MenuTable(int code, String name, String methodName) {
        this.code = code;
        this.name = name;
        this.methodName = methodName;
    }
}
