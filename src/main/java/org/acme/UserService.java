package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import io.quarkus.scheduler.Scheduled;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    EntityManager em;

    @Transactional
    public User addUser(User user) {
        // Postavljamo vezu sa obe strane za profil i rezervacije (Uslov 4)
        if (user.getUserProfile() != null) {
            user.getUserProfile().setUser(user);
        }
        if (user.getReservations() != null) {
            user.getReservations().forEach(r -> r.setUser(user));
        }
        return em.merge(user);
    }

    public List<User> getAllUsers() {
        return em.createNamedQuery(User.GET_ALL_USERS, User.class).getResultList();
    }

    public User getUserById(Long id) {
        return em.find(User.class, id);
    }

    // Dodata metoda za pretragu po imenu (potrebna za @QueryParam)
    public List<User> findByName(String name) {
        return em.createQuery("SELECT u FROM User u WHERE u.name LIKE :name", User.class)
                 .setParameter("name", "%" + name + "%")
                 .getResultList();
    }

    // USLOV 6: Smisleno iskoristiti Quarkus @Scheduler
    @Scheduled(every = "1h")
    void checkSystemActivity() {
        // Loguje broj trenutnih korisnika u konzolu svakih sat vremena
        Long count = em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
        System.out.println("[SCHEDULER] Trenutno korisnika u bazi: " + count);
    }
}