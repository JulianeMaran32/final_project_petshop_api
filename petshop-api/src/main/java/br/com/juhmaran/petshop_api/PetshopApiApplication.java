package br.com.juhmaran.petshop_api;

import br.com.juhmaran.petshop_api.api.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableGlobalAuthentication
@EnableCaching(proxyTargetClass = true)
@EnableFeignClients(basePackages = "br.com.juhmaran.petshop_api.api.address.clients", defaultConfiguration = FeignConfig.class)
@SpringBootApplication
public class PetshopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetshopApiApplication.class, args);
    }

}
