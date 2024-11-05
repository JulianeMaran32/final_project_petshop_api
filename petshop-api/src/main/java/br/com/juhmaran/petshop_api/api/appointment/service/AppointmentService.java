package br.com.juhmaran.petshop_api.api.appointment.service;

import br.com.juhmaran.petshop_api.api.appointment.dto.AppointmentRequest;
import br.com.juhmaran.petshop_api.api.appointment.dto.AppointmentResponse;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(AppointmentRequest appointmentRequest);

    List<AppointmentResponse> listAppointments();

}
