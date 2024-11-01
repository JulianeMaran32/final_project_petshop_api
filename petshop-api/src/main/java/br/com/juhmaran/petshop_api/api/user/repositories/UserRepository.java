package br.com.juhmaran.petshop_api.api.user.repositories;

import br.com.juhmaran.petshop_api.api.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<UserEntity> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    boolean existsByCpfAndIdNot(String cpf, Long id);


    @Modifying
    @Query("UPDATE UserEntity u SET u.failedLoginAttempts = ?1 WHERE u.email = ?2")
    void updateFailedLoginAttempts(int failedAttempts, String email);

    @Modifying
    @Query("UPDATE UserEntity u SET u.accountNonLocked = ?1 WHERE u.email = ?2")
    void updateAccountNonLocked(boolean accountNonLocked, String email);

    @Query(value = "CALL search_users(:name, :cpf, :email, :phone)", nativeQuery = true)
    List<UserEntity> searchUsers(@Param("name") String name,
                                 @Param("cpf") String cpf,
                                 @Param("email") String email,
                                 @Param("phone") String phone);

}