package sqltask.applicationmenu;

import java.sql.Connection;

public interface Menu {

    String getMenuText();
    String doAction(Connection connection);
}
