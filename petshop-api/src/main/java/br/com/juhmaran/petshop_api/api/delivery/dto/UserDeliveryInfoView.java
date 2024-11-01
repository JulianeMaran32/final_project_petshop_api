package br.com.juhmaran.petshop_api.api.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Mapeia os campos da view user_delivery_info
 */
//@SqlResultSetMapping(
//        name = "UserDeliveryInfoMapping",
//        classes = @ConstructorResult(
//                targetClass = UserDeliveryInfo.class,
//                columns = {
//                        @ColumnResult(name = "user_id", type = Long.class),
//                        @ColumnResult(name = "user_name", type = String.class),
//                        @ColumnResult(name = "user_email", type = String.class),
//                        @ColumnResult(name = "user_phone", type = String.class),
//                        @ColumnResult(name = "street", type = String.class),
//                        @ColumnResult(name = "complement", type = String.class),
//                        @ColumnResult(name = "unit", type = String.class),
//                        @ColumnResult(name = "neighborhood", type = String.class),
//                        @ColumnResult(name = "city", type = String.class),
//                        @ColumnResult(name = "state", type = String.class),
//                        @ColumnResult(name = "country", type = String.class),
//                        @ColumnResult(name = "zip_code", type = String.class)
//                }
//        )
//)
@Entity
@Getter
@Setter
@AllArgsConstructor
@Schema(name = "UserDeliveryInfo", description = "Informações de entrega do usuário")
public class UserDeliveryInfoView {

    @Schema(description = "Identificador do usuário", example = "1")
    @Id
    private Long userId;

    @Schema(description = "Nome do usuário", example = "João da Silva")
    private String userName;

    @Schema(description = "E-mail do usuário", example = "")
    private String userEmail;

    @Schema(description = "Telefone do usuário", example = "")
    private String userPhone;

    @Schema(name = "street", description = "Logradouro", example = "Praça da Sé")
    private String street;

    @Schema(name = "neighborhood", description = "Bairro", example = "Sé")
    private String neighborhood;

    @Schema(name = "city", description = "Cidade", example = "São Paulo")
    private String city;

    @Schema(name = "complement", description = "Complemento", example = "lado ímpar")
    private String complement;

    @Schema(name = "unit", description = "Unidade", example = "Bloco A")
    private String unit;

    @Schema(name = "state", description = "Estado", example = "SP")
    private String state;

    @Schema(name = "country", description = "País", example = "Brasil")
    private String country;

    @Schema(name = "zipCode", description = "CEP", example = "01001-000")
    private String zipCode;

}
