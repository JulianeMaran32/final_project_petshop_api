package br.com.juhmaran.petshop_api.api.delivery.repositories;

import br.com.juhmaran.petshop_api.api.delivery.dto.UserDeliveryInfoView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Define o metodo para executar a consulta SQL na view
 */
public interface UserDeliveryInfoRepository extends JpaRepository<UserDeliveryInfoView, Long> {

    @Query(value = "SELECT u.id AS user_id, u.name AS user_name, u.email AS user_email, u.phone AS user_phone, a.street, a.complement, a.unit, a.neighborhood, a.city, a.state, a.country, a.zip_code " +
            "FROM users u LEFT JOIN addresses a ON u.id = a.user_id", nativeQuery = true)
    List<UserDeliveryInfoView> findAllUserDeliveryInfo();

    @Query(value = "SELECT u.id AS user_id, u.name AS user_name, u.email AS user_email, u.phone AS user_phone, a.street, a.complement, a.unit, a.neighborhood, a.city, a.state, a.country, a.zip_code " +
            "FROM users u LEFT JOIN addresses a ON u.id = a.user_id WHERE u.name LIKE %:name%", nativeQuery = true)
    List<UserDeliveryInfoView> findByName(@Param("name") String name);

    @Query(value = "SELECT u.id AS user_id, u.name AS user_name, u.email AS user_email, u.phone AS user_phone, a.street, a.complement, a.unit, a.neighborhood, a.city, a.state, a.country, a.zip_code " +
            "FROM users u LEFT JOIN addresses a ON u.id = a.user_id WHERE u.phone LIKE %:phone%", nativeQuery = true)
    List<UserDeliveryInfoView> findByPhone(@Param("phone") String phone);

    @Query(value = "SELECT u.id AS user_id, u.name AS user_name, u.email AS user_email, u.phone AS user_phone, a.street, a.complement, a.unit, a.neighborhood, a.city, a.state, a.country, a.zip_code " +
            "FROM users u LEFT JOIN addresses a ON u.id = a.user_id WHERE a.zip_code LIKE %:zipCode%", nativeQuery = true)
    List<UserDeliveryInfoView> findByZipCode(@Param("zipCode") String zipCode);

}
