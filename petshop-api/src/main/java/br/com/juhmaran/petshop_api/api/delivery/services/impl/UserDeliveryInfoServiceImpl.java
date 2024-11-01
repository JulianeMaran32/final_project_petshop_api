package br.com.juhmaran.petshop_api.api.delivery.services.impl;

import br.com.juhmaran.petshop_api.api.delivery.dto.UserDeliveryInfoView;
import br.com.juhmaran.petshop_api.api.delivery.repositories.UserDeliveryInfoRepository;
import br.com.juhmaran.petshop_api.api.delivery.services.UserDeliveryInfoService;
import br.com.juhmaran.petshop_api.core.exceptions.runtimes.PetShopInternalServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Define o serviço para executar a consulta SQL na view
 *
 * @author Juliane Maran
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDeliveryInfoServiceImpl implements UserDeliveryInfoService {

    private final UserDeliveryInfoRepository userDeliveryInfoRepository;

    @Override
    public List<UserDeliveryInfoView> findUserDeliveryInfo(String name, String phone, String zipCode) {
        try {
            return getUserDeliveryInfos(name, phone, zipCode);
        } catch (Exception e) {
            log.error("Erro ao buscar informações de entrega", e);
            throw new PetShopInternalServerErrorException("Erro ao buscar informações de entrega", e);
        }
    }

    private List<UserDeliveryInfoView> getUserDeliveryInfos(String name, String phone, String zipCode) {
        if (name != null) {
            return findByUserName(name);
        }
        if (phone != null) {
            return findByPhone(phone);
        }
        if (zipCode != null) {
            return findByZipCode(zipCode);
        }
        log.info("Buscando todas as informações de entrega");
        return userDeliveryInfoRepository.findAllUserDeliveryInfo();
    }

    private List<UserDeliveryInfoView> findByZipCode(String zipCode) {
        log.info("Buscando informações de entrega por CEP: {}", zipCode);
        return userDeliveryInfoRepository.findByZipCode(zipCode);
    }

    private List<UserDeliveryInfoView> findByPhone(String phone) {
        log.info("Buscando informações de entrega por telefone: {}", phone);
        return userDeliveryInfoRepository.findByPhone(phone);
    }

    private List<UserDeliveryInfoView> findByUserName(String name) {
        log.info("Buscando informações de entrega por nome: {}", name);
        return userDeliveryInfoRepository.findByName(name);
    }
}