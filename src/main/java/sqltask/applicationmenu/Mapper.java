package sqltask.applicationmenu;


import java.sql.ResultSet;
import java.sql.PreparedStatement;

public interface Mapper<T> {

    T mapToEntity(ResultSet rs);
    void mapToRow(PreparedStatement ps, T entity);
}