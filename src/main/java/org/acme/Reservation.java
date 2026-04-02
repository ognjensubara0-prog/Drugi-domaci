package org.acme;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "reservation")
@NamedQuery(name = Reservation.GET_ALL_RESERVATIONS, query = "Select r from Reservation r")
public class Reservation {
    public static final String GET_ALL_RESERVATIONS = "GetAllReservations";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "res_seq")
    @SequenceGenerator(name = "res_seq", sequenceName = "res_seq", allocationSize = 1)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Payment payment;

    public Reservation() {
    }

    // --- GETTERI I SETTERI ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}