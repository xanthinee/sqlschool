package sqltask;

public interface TestDAOInterface {

    void save_shouldSaveOnlyOneLine();
    void saveAll_shouldSaveSeveralRows();
    void deleteAll_shouldRetrieveZeroRows();
    void getAll_sizesShouldBeEqual();
    void getById_shouldRetrieveExactEntity();
    void deleteById_shouldCountZeroRows();
}
