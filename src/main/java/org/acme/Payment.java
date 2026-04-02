package org.acme;

import jakarta.persistence.*;

@Entity
@Table(name = "payment")
@NamedQuery(name = Payment.GET_ALL_PAYMENTS, query = "Select p from Payment p")
public class Payment {
    public static final String GET_ALL_PAYMENTS = "GetAllPayments";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pay_seq")
    @SequenceGenerator(name = "pay_seq", sequenceName = "pay_seq", allocationSize = 1)
    private Long id;

    private Double amount;
    private String payment_date;
    private String method;

    // USLOV 1: Druga @OneToOne relacija (Payment -> Reservation)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public Payment() {
    }

    // GETTERI I SETTERI (Jedan ispod drugog)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}