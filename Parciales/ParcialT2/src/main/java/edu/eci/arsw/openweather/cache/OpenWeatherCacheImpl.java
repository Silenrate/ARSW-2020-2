package edu.eci.arsw.openweather.cache;

import edu.eci.arsw.openweather.model.WeatherByCity;
import edu.eci.arsw.openweather.services.OpenWeatherException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Clase que implementa el sistema de cache de la aplicación Open Weather
 */
@Service
public class OpenWeatherCacheImpl implements OpenWeatherCache {
    private static final long MINUTES_IN_CACHE = 5;
    private final ConcurrentHashMap<String, WeatherByCity> cache = new ConcurrentHashMap<>();

    /**
     * Obtiene la información del clima de un lugar especifico
     *
     * @param city El nombre del lugar a buscar
     * @return Un string de los casos de covid19
     * @throws OpenWeatherException - Cuando no existe esa información en cache
     */
    @Override
    public WeatherByCity getWeatherFromCity(String city) throws OpenWeatherException {
        WeatherByCity weather = cache.get(city);
        if (city == null) {
            throw new OpenWeatherException("No se encuentra ese caso en cache");
        }
        return weather;
    }

    /**
     * Agrega al cache la información del clima de un lugar
     *
     * @param city          El nombre del lugar
     * @param weatherByCity La información del clima de un lugar
     * @throws OpenWeatherException - Cuando ya existe esa información en cache
     */
    @Override
    public void putWeatherOfCityInCache(String city, WeatherByCity weatherByCity) throws OpenWeatherException {
        if (isWeatherInCache(city)) {
            throw new OpenWeatherException("Esa información ya esta en cache");
        }
        cache.put(city, weatherByCity);
    }

    /**
     * Elimina la información del clima de un lugar
     *
     * @param city El nombre del lugar
     * @throws OpenWeatherException - Cuando no existe esa información en el cache
     */
    @Override
    public void cleanCaseOfCache(String city) throws OpenWeatherException {
        cache.remove(city);
    }

    /**
     * Averigua si existe la información del clima de un lugar en el cache
     *
     * @param city El nombre del lugar
     * @return El valor booleano que determina si existe la información del clima de un lugar en el cache
     * @throws OpenWeatherException - Si hubo un error al validar la existencia de esa información en cache
     */
    @Override
    public boolean isWeatherInCache(String city) throws OpenWeatherException {
        WeatherByCity caso = cache.get(city);
        boolean isInCache = true;
        if (caso == null) {
            isInCache = false;
        } else if (LocalDateTime.now().isAfter(caso.getTime().plusMinutes(MINUTES_IN_CACHE))) {
            cleanCaseOfCache(city);
            isInCache = false;
        }
        return isInCache;
    }
}
