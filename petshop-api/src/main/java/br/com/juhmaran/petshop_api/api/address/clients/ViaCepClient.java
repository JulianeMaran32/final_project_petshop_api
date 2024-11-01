package br.com.juhmaran.petshop_api.api.address.clients;

import br.com.juhmaran.petshop_api.api.address.dtos.ViaCepResponse;
import br.com.juhmaran.petshop_api.api.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "${petshop.via-cep.name}", url = "${petshop.via-cep.url}", configuration = FeignConfig.class)
public interface ViaCepClient {

    @GetMapping("/{cep}/json/")
    ViaCepResponse getAddressByCep(@PathVariable("cep") String cep);

    @GetMapping("/{uf}/{city}/{street}/json/")
    List<ViaCepResponse> getAddressByDetails(@PathVariable("uf") String uf,
                                             @PathVariable("city") String city,
                                             @PathVariable("street") String street);

}
