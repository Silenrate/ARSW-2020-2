package edu.eci.arsw.openweather.connection;

import edu.eci.arsw.openweather.model.WeatherByCity;
import org.springframework.stereotype.Service;

/**
 * Interfaz de conexión con otras aplicaciones para obtener datos del clima
 */
@Service
public interface HttpConnectionService {
    /**
     * Obtiene los datos del clima de un lugar específico por medio de una conexión con otra app
     *
     * @param name Nombre del lugar
     * @return Información del clima en ese lugar
     * @throws OpenWeatherConnectionException Cuando el lugar no existe
     */
    WeatherByCity getWeatherByCityName(String name) throws OpenWeatherConnectionException;
}
