package br.com.juhmaran.petshop_api;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(
        info = @Info(
                title = "Petshop API",
                version = "1.0",
                description = "API para gerenciamento de petshops"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Servidor local"
                )
        },
        externalDocs = @ExternalDocumentation(
                description = "Documentação do projeto",
                url = ""
        ),
        tags = {
                @Tag(name = "Auth", description = "Operações relacionadas a autenticação do usuário"),
                @Tag(name = "User", description = "Operações relacionadas a usuários")
        }
)
@EnableFeignClients
@SpringBootApplication
public class PetshopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetshopApiApplication.class, args);
    }

}
