package br.com.juhmaran.petshop_api.api.appointment;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(AppointmentRequest appointmentRequest);

    List<AppointmentResponse> listAppointments();

}
