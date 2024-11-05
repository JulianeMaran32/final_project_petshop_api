package br.com.juhmaran.petshop_api.core.customize.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Serializador personalizado para objetos LocalDate.
 * Este serializador converte uma data no formato "dd/MM/yyyy".
 *
 * @author Juliane Maran
 */
public class CustomLocalDateSerializer extends JsonSerializer<LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Serializa um objeto LocalDate para uma string no formato "dd/MM/yyyy".
     *
     * @param value       o valor da data a ser serializado
     * @param gen         o gerador JSON usado para escrever a saída JSON
     * @param serializers o provedor de serializadores
     * @throws IOException se ocorrer um erro de entrada/saída
     */
    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(formatter));
    }
}