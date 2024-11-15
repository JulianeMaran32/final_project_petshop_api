package br.com.juhmaran.petshop_api.api.appointment.controller;

import br.com.juhmaran.petshop_api.api.appointment.dto.AppointmentRequest;
import br.com.juhmaran.petshop_api.api.appointment.dto.AppointmentResponse;
import br.com.juhmaran.petshop_api.api.appointment.service.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "Operações relacionadas ao Agendamento de Consultas Veterinárias")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody @Valid AppointmentRequest appointmentRequest) {
        log.info("Criando um novo agendamento");
        AppointmentResponse appointmentResponse = appointmentService.createAppointment(appointmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentResponse);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> createAppointment() {
        log.info("Lista de agendamentos");
        List<AppointmentResponse> appointmentResponses = appointmentService.listAppointments();
        return ResponseEntity.status(HttpStatus.OK).body(appointmentResponses);
    }

}
