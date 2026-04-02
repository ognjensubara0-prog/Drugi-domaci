package org.acme;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "branch")
@NamedQuery(name = Branch.GET_ALL_BRANCHES, query = "Select b from Branch b")
public class Branch {
    public static final String GET_ALL_BRANCHES = "GetAllBranches";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_seq")
    @SequenceGenerator(name = "branch_seq", sequenceName = "branch_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String city;
    private String address;

    // USLOV 2: Druga @OneToMany relacija (Branch -> Reservation)
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    public Branch() {
    }

    // GETTERI I SETTERI (Jedan ispod drugog)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}