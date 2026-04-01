package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    EntityManager em;

    // Metoda za dodavanje User-a (ujedno spašava i UserProfile zbog CascadeType.ALL)
    @Transactional
    public User addUser(User user) {
        // Postavljamo vezu sa obe strane da bi Hibernate znao čiji je profil
        if (user.getUserProfile() != null) {
            user.getUserProfile().setUser(user);
        }
        return em.merge(user);
    }

    // Metoda koja koristi NamedQuery iz User klase
    public List<User> getAllUsers() {
        return em.createNamedQuery(User.GET_ALL_USERS, User.class)
                 .getResultList();
    }

    // Metoda za pronalaženje jednog korisnika po ID-u
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }
}