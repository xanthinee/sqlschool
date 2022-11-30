package sqltask.applicationmenu;

import java.util.*;

public interface DAO<T> {

    T getById(int id);
    List<T> getAll();
    void deleteAll();
    void deleteById(int id);
    void saveAll(List<T> list);
    void save(T entity);
}
