package org.acme;

import jakarta.persistence.*;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_seq")
    @SequenceGenerator(name = "car_seq", sequenceName = "car_seq", allocationSize = 1)
    private Long id;

    private String brand;
    private String model;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch currentBranch;

    public Car() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public Branch getCurrentBranch() { return currentBranch; }
    public void setCurrentBranch(Branch currentBranch) { this.currentBranch = currentBranch; }
}