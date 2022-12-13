package sqltask.groups;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Profile("jpa")
public class GroupDAOJpa implements GroupDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Group> compareGroups(int groupId) {
        return em.createNamedQuery("group.compareGroup", Group.class)
                .setParameter(1, groupId)
                .setParameter(2, groupId)
                .getResultList();
    }

    @Override
    public Group getById(int id) {
        return em.find(Group.class, id);
    }

    @Override
    public List<Group> getAll() {
        return em.createQuery("select g from Group g", Group.class).getResultList();
    }

    @Transactional
    @Override
    public void deleteAll() {
        for (Group group : getAll()) {
            em.remove(group);
        }
    }

    @Transactional
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

    @Override
    public void save(Group entity) {
        em.persist(entity);
    }
}
