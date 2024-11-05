package br.com.juhmaran.petshop_api.api.appointment;

import br.com.juhmaran.petshop_api.api.common.enums.RoleType;
import br.com.juhmaran.petshop_api.api.pet.entities.PetEntity;
import br.com.juhmaran.petshop_api.api.pet.repositories.PetRepository;
import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import br.com.juhmaran.petshop_api.api.user.repositories.UserRepository;
import br.com.juhmaran.petshop_api.api.user.utils.UserUtil;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopBadRequestException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopForbiddenException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopNotFoundException;
import br.com.juhmaran.petshop_api.core.security.role.entities.RoleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    @Override
    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {
        try {
            log.debug("Iniciando criação de agendamento para o pet ID: {}", appointmentRequest.getPetId());

            if (appointmentRequest.getPetId() == null) {
                throw new PetShopBadRequestException("O campo petId é obrigatório");
            }

            PetEntity pet = petRepository.findById(appointmentRequest.getPetId())
                    .orElseThrow(() -> new PetShopNotFoundException("Pet não encontrado."));

            UserEntity user = userRepository.findById(pet.getUser().getId())
                    .orElseThrow(() -> new PetShopNotFoundException("Usuário não encontrado"));

            Authentication authentication = UserUtil.getAuthentication();
            UserEntity authenticatedUser = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new PetShopNotFoundException("Usuário autenticado não encontrado."));
            UserUtil.userNotPermission(authenticatedUser, user.getId(), "Usuário não tem permissão para criar agendamento.");

            AppointmentEntity appointment = AppointmentMapper.INSTANCE.toAppointment(appointmentRequest);
            appointment.setPet(pet);
            appointment.setCreatedBy(user.getName());
            appointment.setCreatedByRole("System");
            appointment.setCreatedDate(LocalDateTime.now());

            AppointmentEntity savedAppointment = appointmentRepository.save(appointment);
            log.info("Agendamento criado com sucesso para o pet ID: {}", appointmentRequest.getPetId());
            return AppointmentMapper.INSTANCE.toAppointmentResponse(savedAppointment);
        } catch (PetShopNotFoundException | PetShopForbiddenException e) {
            log.error("Erro ao criar agendamento: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro genérico ao criar agendamento", e);
            throw new PetShopBadRequestException("Erro ao criar agendamento");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentResponse> listAppointments() {
        Authentication authentication = UserUtil.getAuthentication();
        UserEntity authenticatedUser = getAuthenticatedUser(authentication);

        List<AppointmentEntity> appointments;

        if (hasAdminRole(authenticatedUser)) {
            appointments = appointmentRepository.findAll();
        } else {
            appointments = appointmentRepository.findByPet_UserId(authenticatedUser.getId());
        }

        return appointments.stream()
                .map(AppointmentMapper.INSTANCE::toAppointmentResponse)
                .collect(Collectors.toList());
    }

    private UserEntity getAuthenticatedUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new PetShopNotFoundException("Usuário autenticado não encontrado."));
    }

    private boolean hasAdminRole(UserEntity user) {
        return user.getRoles().stream()
                .map(RoleEntity::getName)
                .anyMatch(role -> role == RoleType.ADMIN || role == RoleType.SUPER_ADMIN);
    }

    // todo: implementar o método updateAppointment
    // todo: implementar o método deleteAppointment

    // todo: implementar o método listAppointmentsByUser
    // todo: implementar o método listAppointmentsByPet
    // todo: implementar o método listAppointmentsByDate
    // todo: implementar o método listAppointmentsByStatus

}
