package br.com.juhmaran.petshop_api.core.customize.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializador personalizado para objetos LocalTime.
 * Este serializador converte uma hora no formato "HH:mm:ss".
 *
 * @author Juliane Maran
 */
public class CustomLocalTimeSerializer extends JsonSerializer<LocalTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Serializa um objeto LocalTime para uma string no formato "HH:mm:ss".
     *
     * @param value       o valor da hora a ser serializado
     * @param gen         o gerador JSON usado para escrever a saída JSON
     * @param serializers o provedor de serializadores
     * @throws IOException se ocorrer um erro de entrada/saída
     */
    @Override
    public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(formatter));
    }

}