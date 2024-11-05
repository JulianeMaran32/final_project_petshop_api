package br.com.juhmaran.petshop_api.core.customize.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Deserializador personalizado para objetos LocalDate.
 * <p>
 * Este deserializador converte uma string no formato "dd/MM/yyyy" em um objeto LocalDate.
 * </p>
 *
 * @author Juliane Maran
 */
public class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Deserializa uma string no formato "dd/MM/yyyy" em um objeto LocalDate.
     *
     * @param p       o parser JSON
     * @param context o contexto de desserialização
     * @return o objeto LocalDate desserializado
     * @throws IOException      se ocorrer um erro de I/O
     * @throws JacksonException se ocorrer um erro de desserialização
     */
    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext context) throws IOException, JacksonException {
        return LocalDate.parse(p.getText(), formatter);
    }

}