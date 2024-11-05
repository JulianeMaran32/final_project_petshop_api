package br.com.juhmaran.petshop_api;

import br.com.juhmaran.petshop_api.api.config.FeignConfig;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;

@OpenAPIDefinition(
        info = @Info(
                title = "Petshop API", version = "1.0",
                description = "API para gerenciamento de petshops.",
                contact = @Contact(
                        name = "Juliane Maran",
                        email = "juliane.vmaran@gmail.com")
        ),
        externalDocs = @ExternalDocumentation(
                description = "Documentação da API",
                url = "http://localhost:8080/api/swagger-ui/index.html#/"),
        servers = {
                @Server(url = "https://petshop-web-api-a5aeanc2g5gzgzer.eastus2-01.azurewebsites.net/", description = "Produção"),
                @Server(url = "http://localhost:8080/api", description = "Local")}
)
@EnableGlobalAuthentication
@EnableCaching(proxyTargetClass = true)
@EnableFeignClients(basePackages = "br.com.juhmaran.petshop_api.api.address.clients", defaultConfiguration = FeignConfig.class)
@SpringBootApplication
public class PetshopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetshopApiApplication.class, args);
    }

}
