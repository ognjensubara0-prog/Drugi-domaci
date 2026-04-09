package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserProfileService {

    @Inject
    EntityManager em;

    @Transactional
    public UserProfile addProfile(UserProfile profile) {
        return em.merge(profile);
    }

    public List<UserProfile> getAllProfiles() {
        return em.createNamedQuery(UserProfile.GET_ALL_PROFILES, UserProfile.class).getResultList();
    }

    public UserProfile getProfileById(Long id) {
        return em.find(UserProfile.class, id);
    }

    
    public List<UserProfile> findByPhone(String phone) {
        return em.createQuery("SELECT p FROM UserProfile p WHERE p.phone = :phone", UserProfile.class)
                 .setParameter("phone", phone)
                 .getResultList();
    }
}