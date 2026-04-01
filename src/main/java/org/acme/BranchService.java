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
    public Branch createBranch(Branch branch) {
        // Osnovna provjera kako bi izbjegli NullPointerException
        if (branch == null || branch.getName() == null) {
            return null; 
        }

        // Koristimo persist za novi entitet
        em.persist(branch);
        return branch;
    }

    public List<Branch> getAllBranches() {
        // Jednostavan JPQL upit za listanje svih poslovnica
        return em.createQuery("SELECT b FROM Branch b", Branch.class).getResultList();
    }

    public Branch getBranchById(Long id) {
        return em.find(Branch.class, id);
    }

    @Transactional
    public void deleteBranch(Long id) {
        Branch branch = getBranchById(id);
        if (branch != null) {
            em.remove(branch);
        }
    }
}