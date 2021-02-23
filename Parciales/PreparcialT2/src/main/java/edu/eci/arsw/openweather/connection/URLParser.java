package edu.eci.arsw.openweather.connection;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Interfaz para codificar string como URL
 */
public interface URLParser {
    /**
     * Codifica como URL una cadena de texto
     *
     * @param cadena cadena de texto a codificar
     * @return cadena de texto codificada
     */
    default String getStringInUrl(String cadena) throws OpenWeatherConnectionException {
        String encodedQuery;
        try {
            encodedQuery = URLEncoder.encode(cadena, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new OpenWeatherConnectionException("Error al convertir " + cadena, ex.getCause());
        }
        return encodedQuery.replace("+", "%20");
    }
}