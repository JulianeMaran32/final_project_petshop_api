package br.com.juhmaran.petshop_api.api.address.services;

import br.com.juhmaran.petshop_api.api.address.clients.ViaCepClient;
import br.com.juhmaran.petshop_api.api.address.dtos.AddressRequest;
import br.com.juhmaran.petshop_api.api.address.dtos.AddressResponse;
import br.com.juhmaran.petshop_api.api.address.dtos.ViaCepResponse;
import br.com.juhmaran.petshop_api.api.address.entities.AddressEntity;
import br.com.juhmaran.petshop_api.api.address.mapping.AddressMapper;
import br.com.juhmaran.petshop_api.api.address.repositories.AddressRepository;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final ViaCepClient viaCepClient;
    private final AddressRepository addressRepository;

    public AddressResponse getAddressByCep(String cep) {
        log.info("Iniciando busca de endereço pelo CEP: {}", cep);
        try {
            ViaCepResponse viaCepResponse = viaCepClient.getAddressByCep(cep);
            AddressResponse response = AddressMapper.INSTANCE.viaCepToAddressResponse(viaCepResponse);
            log.info("Endereço encontrado para o CEP: {}", cep);
            return response;
        } catch (Exception e) {
            log.error("Erro ao buscar endereço pelo CEP: {}", cep, e);
            throw new PetShopNotFoundException("Endereço não encontrado para o CEP: " + cep);
        }
    }

    public List<AddressResponse> getAddressByDetails(String uf, String city, String street) {
        log.info("Iniciando busca de endereço pelos detalhes: UF={}, Cidade={}, Rua={}", uf, city, street);
        try {
            List<ViaCepResponse> viaCepResponses = viaCepClient.getAddressByDetails(uf, city, street);
            List<AddressResponse> responses = viaCepResponses.stream()
                    .map(AddressMapper.INSTANCE::viaCepToAddressResponse).toList();
            log.info("Endereços encontrados para os detalhes fornecidos.");
            return responses;
        } catch (Exception e) {
            log.error("Erro ao buscar endereço pelos detalhes: UF={}, Cidade={}, Rua={}", uf, city, street, e);
            throw new PetShopNotFoundException("Endereço não encontrado para os detalhes fornecidos.");
        }
    }

    public AddressResponse createAddress(AddressRequest addressRequestDTO) {
        log.info("Iniciando criação de novo endereço");
        try {
            AddressEntity address = AddressMapper.INSTANCE.requestToAddress(addressRequestDTO);
            AddressEntity savedAddress = addressRepository.save(address);
            AddressResponse response = AddressMapper.INSTANCE.addressToAddressResponse(savedAddress);
            log.info("Endereço criado com sucesso: {}", response.getId());
            return response;
        } catch (Exception e) {
            log.error("Erro ao criar endereço", e);
            throw new PetShopInternalServerErrorException("Erro ao criar endereço");
        }
    }

    public AddressResponse updateAddress(Long id, AddressRequest addressRequestDTO) {
        log.info("Iniciando atualização de endereço com ID: {}", id);
        try {
            Optional<AddressEntity> optionalAddress = addressRepository.findById(id);
            if (optionalAddress.isPresent()) {
                AddressEntity address = optionalAddress.get();
                AddressMapper.INSTANCE.updateAddressFromRequest(addressRequestDTO, address);
                AddressEntity updatedAddress = addressRepository.save(address);
                AddressResponse response = AddressMapper.INSTANCE.addressToAddressResponse(updatedAddress);
                log.info("Endereço atualizado com sucesso: {}", id);
                return response;
            }
            log.warn("Endereço não encontrado para o ID: {}", id);
            throw new PetShopNotFoundException("Endereço não encontrado.");
        } catch (Exception e) {
            log.error("Erro ao atualizar endereço com ID: {}", id, e);
            throw new PetShopInternalServerErrorException("Erro ao atualizar endereço");
        }
    }

    public void deleteAddress(Long id) {
        log.info("Iniciando exclusão de endereço com ID: {}", id);
        try {
            addressRepository.deleteById(id);
            log.info("Endereço com ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao deletar endereço com ID: {}", id, e);
            throw new PetShopInternalServerErrorException("Erro ao deletar endereço");
        }
    }

}
