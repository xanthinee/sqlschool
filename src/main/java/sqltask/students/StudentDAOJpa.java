package sqltask.students;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Profile("hibernate")
public class StudentDAOJpa implements StudentDAO {

    @PersistenceContext
    private final EntityManager em;
    public StudentDAOJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public void updateGroupIdByStudId(Student student, int groupID) {
        student.setGroupId(groupID);
        em.merge(student);
    }

    @Override
    public Student getById(int id) {
        return em.find(Student.class, id);
    }

    @Override
    public List<Student> getAll() {
        return em.createNamedQuery("student.getAll", Student.class).getResultList();
    }

    @Override
    public void deleteAll() {
        for (Student student : getAll()) {
            em.remove(student);
        }
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        Student toRemove = em.find(Student.class, id);
        em.remove(toRemove);
    }

    @Transactional
    @Override
    public void saveAll(List<Student> list) {
        for (Student student : list) {
            em.persist(student);
        }
    }

    @Transactional
    @Override
    public void save(Student entity) {
        em.persist(entity);
    }
}
