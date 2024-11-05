package br.com.juhmaran.petshop_api.api.appointment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentMapper {

    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pet", ignore = true)
    AppointmentEntity toAppointment(AppointmentRequest appointmentRequest);

    @Mapping(source = "pet.id", target = "petId")
    AppointmentResponse toAppointmentResponse(AppointmentEntity appointment);

}
