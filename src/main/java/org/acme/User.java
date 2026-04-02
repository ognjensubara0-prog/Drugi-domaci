package org.acme;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
@NamedQuery(name = User.GET_ALL_USERS, query = "Select u from User u")
public class User {
    public static final String GET_ALL_USERS = "GetAllUsers";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String email;
    private String password;

    // USLOV 1: Prva @OneToOne relacija (User -> UserProfile)
    // Postavljamo LAZY fetch prema tvom zahtjevu
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile userProfile;

    // USLOV 2: Prva @OneToMany relacija (User -> Reservation)
    // Dodajemo CascadeType.ALL da bi mogao dodati User-a i njegove rezervacije odjednom (Uslov 4)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    public User() {
    }

    // --- GETTERI I SETTERI ---

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        // Ključno: Osiguravamo dvosmjernu vezu ako profil nije null
        if (userProfile != null) {
            userProfile.setUser(this);
        }
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
        // Ključno: Povezujemo svaku rezervaciju sa ovim userom
        if (reservations != null) {
            for (Reservation r : reservations) {
                r.setUser(this);
            }
        }
    }
}