package br.com.juhmaran.petshop_api.core.customize.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializador personalizado para objetos LocalDateTime.
 * Este serializador converte uma data e hora no formato "dd/MM/yyyy HH:mm:ss".
 *
 * @author Juliane Maran
 */
public class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Serializa um objeto LocalDateTime para uma string no formato "dd/MM/yyyy HH:mm:ss".
     *
     * @param value       o valor da data e hora a ser serializado
     * @param gen         o gerador JSON usado para escrever a saída JSON
     * @param serializers o provedor de serializadores
     * @throws IOException se ocorrer um erro de entrada/saída
     */
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(formatter));
    }

}
