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
        // Ako uz rezervaciju šaljemo i plaćanje (Payment), povezujemo ih
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

    // Metoda za pretragu po statusu (npr. PENDING, CONFIRMED) za @QueryParam
    public List<Reservation> findByStatus(String status) {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.status = :status", Reservation.class)
                 .setParameter("status", status)
                 .getResultList();
    }
}