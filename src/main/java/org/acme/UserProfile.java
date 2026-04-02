package org.acme;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "user_profile")
@NamedQuery(name = UserProfile.GET_ALL_PROFILES, query = "Select p from UserProfile p")
public class UserProfile {
    public static final String GET_ALL_PROFILES = "GetAllProfiles";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_seq")
    @SequenceGenerator(name = "user_profile_seq", sequenceName = "user_profile_seq", allocationSize = 1)
    private Long id;

    private String address;
    private String phone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public UserProfile() {
    }

    // --- GETTERI I SETTERI ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}