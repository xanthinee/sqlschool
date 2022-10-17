package sqltask.applicationmenu;

import java.util.*;

public interface DAO<T> {

    T getById(int id);
    List<T> getAll();
//    void save(T entity);
    void deleteAll();
    void deleteById(int id);

}
