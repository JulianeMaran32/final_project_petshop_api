package br.com.juhmaran.petshop_api.api.address.repositories;

import br.com.juhmaran.petshop_api.api.address.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
