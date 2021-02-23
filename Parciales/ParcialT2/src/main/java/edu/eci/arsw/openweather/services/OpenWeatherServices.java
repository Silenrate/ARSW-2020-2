package edu.eci.arsw.openweather.services;

import edu.eci.arsw.openweather.model.WeatherByCity;
import org.springframework.stereotype.Service;

/**
 * Interfaz de Servicios de la aplicación Open Weather
 */
@Service
public interface OpenWeatherServices {
    /**
     * Obtiene los datos del clima de un lugar específico
     *
     * @param name Nombre del lugar
     * @return Información del clima en ese lugar
     * @throws OpenWeatherException Cuando el lugar no existe
     */
    WeatherByCity getWeatherByCityName(String name) throws OpenWeatherException;
}
