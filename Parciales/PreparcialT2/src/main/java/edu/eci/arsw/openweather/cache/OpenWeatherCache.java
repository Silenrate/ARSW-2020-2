package edu.eci.arsw.openweather.cache;

import edu.eci.arsw.openweather.model.WeatherByCity;
import edu.eci.arsw.openweather.services.OpenWeatherException;
import org.springframework.stereotype.Service;

/**
 * Interfaz para servicio de cache de la aplicación Open Weather
 */
@Service
public interface OpenWeatherCache {
    /**
     * Obtiene la información del clima de un lugar especifico
     *
     * @param city El nombre del lugar a buscar
     * @return Información del clima
     * @throws OpenWeatherException - Cuando no existe esa información en cache
     */
    WeatherByCity getWeatherFromCity(String city) throws OpenWeatherException;

    /**
     * Agrega al cache la información del clima de un lugar
     *
     * @param city          El nombre del lugar
     * @param weatherByCity La información del clima de un lugar
     * @throws OpenWeatherException - Cuando ya existe esa información en cache
     */
    void putWeatherOfCityInCache(String city, WeatherByCity weatherByCity) throws OpenWeatherException;

    /**
     * Elimina la información del clima de un lugar
     *
     * @param city El nombre del lugar
     * @throws OpenWeatherException - Cuando no existe ese pais en el cache
     */
    void cleanCaseOfCache(String city) throws OpenWeatherException;

    /**
     * Averigua si existe la información del clima de un lugar en el cache
     *
     * @param city El nombre del lugar
     * @return El valor booleano que determina si existe la información del clima de un lugar en el cache
     * @throws OpenWeatherException - Si hubo un error al validar la existencia de esa información en cache
     */
    boolean isWeatherInCache(String city) throws OpenWeatherException;

}
