package br.com.juhmaran.petshop_api.api.appointment.entities;

import br.com.juhmaran.petshop_api.api.pet.entities.PetEntity;
import br.com.juhmaran.petshop_api.core.auditable.Auditable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "appointments")
public class AppointmentEntity extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonSerialize
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false, foreignKey = @ForeignKey(name = "FK_appointment_pet"))
    private PetEntity pet;

    @Column(name = "date", nullable = false, columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "time", nullable = false, columnDefinition = "TIME")
    private LocalTime time;

    @Column(name = "veterinarian", nullable = false)
    private String veterinarian;

    @Column(name = "description")
    private String description;

}
