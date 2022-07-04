package sqltask.applicationmenu;

import java.sql.SQLException;

public interface Action {

     Object doAction() throws SQLException;
}
