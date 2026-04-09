package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ReservationService {

    @Inject
    EntityManager em;

    @Transactional
    public Reservation addReservation(Reservation reservation) {
        
        if (reservation.getPayment() != null) {
            reservation.getPayment().setReservation(reservation);
        }
        return em.merge(reservation);
    }

    public List<Reservation> getAllReservations() {
        return em.createNamedQuery(Reservation.GET_ALL_RESERVATIONS, Reservation.class).getResultList();
    }

    public Reservation getReservationById(Long id) {
        return em.find(Reservation.class, id);
    }

    
    public List<Reservation> findByStatus(String status) {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.status = :status", Reservation.class)
                 .setParameter("status", status)
                 .getResultList();
    }
}