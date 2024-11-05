package br.com.juhmaran.petshop_api.core.customize.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializador personalizado para objetos LocalDateTime.
 * <p>
 * Este deserializador converte strings no formato "dd/MM/yyyy HH:mm" em objetos LocalDateTime.
 * </p>
 *
 * @author Juliane Maran
 */
public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Deserializa uma string em um objeto LocalDateTime.
     *
     * @param p       o parser JSON
     * @param context o contexto de desserialização
     * @return o objeto LocalDateTime desserializado
     * @throws IOException      se ocorrer um erro de I/O
     * @throws JacksonException se ocorrer um erro de desserialização
     */
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext context) throws IOException, JacksonException {
        return LocalDateTime.parse(p.getText(), formatter);
    }

}