package br.com.juhmaran.petshop_api.api.appointment.repositories;

import br.com.juhmaran.petshop_api.api.appointment.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    List<AppointmentEntity> findByPet_UserId(Long userId);

}
