package sqltask.groups;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Profile("hibernate")
public class GroupDAOJpa implements GroupDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Group> compareGroups(int groupId) {
        return null;
    }

    @Override
    public Group getById(int id) {
        return em.find(Group.class, id);
    }

    @Override
    public List<Group> getAll() {
        return em.createNamedQuery("group.getAll", Group.class).getResultList();
    }

    @Override
    public void deleteAll() {
        for (Group group : getAll()) {
            em.remove(group);
        }
    }

    @Override
    public void deleteById(int id) {
        Group toRemove = em.find(Group.class, id);
        em.remove(toRemove);
    }

    @Transactional
    @Override
    public void saveAll(List<Group> list) {
        for(Group group : list) {
            em.persist(group);
        }
    }

    @Transactional
    @Override
    public void save(Group entity) {
        em.persist(entity);
    }
}
