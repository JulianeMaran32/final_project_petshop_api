package br.com.juhmaran.petshop_api.api.delivery.services;

import br.com.juhmaran.petshop_api.api.delivery.dto.UserDeliveryInfoView;

import java.util.List;

/**
 * Interface para o serviço de informações de entrega
 *
 * @author Julaine Maran
 */
public interface UserDeliveryInfoService {

    /**
     * Busca informações de entrega
     *
     * @param name    Nome do usuário
     * @param phone   Telefone do usuário
     * @param zipCode CEP do usuário
     * @return Lista de informações de entrega
     */
    List<UserDeliveryInfoView> findUserDeliveryInfo(String name, String phone, String zipCode);

}
