package ru.atom.lecture08.websocket.dao;

import org.springframework.stereotype.Repository;
import ru.atom.lecture08.websocket.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Transactional
@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    private Set<String> loginsOnline = new HashSet<>();

    public User getByLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> userCriteria = cb.createQuery(User.class);
        Root<User> userRoot = userCriteria.from(User.class);
        userCriteria.select(userRoot);
        userCriteria.where(cb.equal(userRoot.get("login"), login));
        List<User> list = em.createQuery(userCriteria).getResultList();
        if (list.size() == 0)
            return null;
        else {
            return list.get(0);
        }
    }

    public String getUsersOnline() {
        return loginsOnline.stream()
                .reduce("", (e1,e2) -> e1 + "\n" + e2);
    }


    public void refresh(User user) {
        em.merge(user);
    }

    public void save(User user) {
        em.persist(user);
    }

    public void setLoggedIn(User user) {
        loginsOnline.add(user.getLogin());
        refresh(user.setOnline((short)1));
    }

    public void setLoggedOut(User user) {
        loginsOnline.remove(user.getLogin());
        refresh(user.setOnline((short)0));
    }
}
