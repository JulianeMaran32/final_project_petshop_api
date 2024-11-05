package br.com.juhmaran.petshop_api.core.customize.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializador personalizado para objetos LocalTime.
 * <p>
 * Este deserializador converte uma string no formato "HH:mm" em um objeto LocalTime.
 * </p>
 *
 * @author Juliane Maran
 */
public class CustomLocalTimeDeserializer extends JsonDeserializer<LocalTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Deserializa uma string no formato "HH:mm" em um objeto LocalTime.
     *
     * @param p       o parser JSON
     * @param context o contexto de desserialização
     * @return o objeto LocalTime desserializado
     * @throws IOException      se ocorrer um erro de I/O
     * @throws JacksonException se ocorrer um erro de desserialização
     */
    @Override
    public LocalTime deserialize(JsonParser p, DeserializationContext context) throws IOException, JacksonException {
        return LocalTime.parse(p.getText(), formatter);
    }

}