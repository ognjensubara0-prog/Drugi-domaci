package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PaymentService {

    @Inject
    EntityManager em;

    @Transactional
    public Payment addPayment(Payment payment) {
        return em.merge(payment);
    }

    public List<Payment> getAllPayments() {
        return em.createNamedQuery(Payment.GET_ALL_PAYMENTS, Payment.class).getResultList();
    }

    public Payment getPaymentById(Long id) {
        return em.find(Payment.class, id);
    }

    
    public List<Payment> findByMethod(String method) {
        return em.createQuery("SELECT p FROM Payment p WHERE p.method = :method", Payment.class)
                 .setParameter("method", method)
                 .getResultList();
    }
}