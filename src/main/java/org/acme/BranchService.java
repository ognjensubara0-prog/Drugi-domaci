package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BranchService {

    @Inject
    EntityManager em;

    @Transactional
    public Branch addBranch(Branch branch) {
        // Osiguravamo da svaka rezervacija u listi zna kojoj poslovnici pripada
        if (branch.getReservations() != null) {
            branch.getReservations().forEach(r -> r.setBranch(branch));
        }
        return em.merge(branch);
    }

    public List<Branch> getAllBranches() {
        return em.createNamedQuery(Branch.GET_ALL_BRANCHES, Branch.class).getResultList();
    }

    public Branch getBranchById(Long id) {
        return em.find(Branch.class, id);
    }

    public List<Branch> findByCity(String city) {
        return em.createQuery("SELECT b FROM Branch b WHERE b.city = :city", Branch.class)
                 .setParameter("city", city)
                 .getResultList();
    }
}