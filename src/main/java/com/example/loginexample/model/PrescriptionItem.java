package com.example.loginexample.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "prescription_items")
public class PrescriptionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medicament_id", referencedColumnName = "id")
    private Medicament medicament;

    @NotNull
    private Float quantity;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "items_daytimes",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "daytime_id"))
    private Set<TimeOfDay> timesOfDay = new HashSet<>();

    private LocalDate administrationEndDate;

    @NotNull
    private Integer daysBetweenAdministrations;

    public PrescriptionItem(Medicament medicament, Float quantity, LocalDate administrationEndDate, Integer daysBetweenAdministrations) {
        this.medicament = medicament;
        this.quantity = quantity;
        this.administrationEndDate = administrationEndDate;
        this.daysBetweenAdministrations = daysBetweenAdministrations;
    }
}
