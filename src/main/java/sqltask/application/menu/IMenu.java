package sqltask.application.menu;

import java.sql.Connection;

public interface IMenu {

    String getMenuText();
    String doAction(Connection connection);
}
