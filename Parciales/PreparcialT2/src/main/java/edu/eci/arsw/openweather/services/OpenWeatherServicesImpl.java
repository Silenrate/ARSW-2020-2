package edu.eci.arsw.openweather.services;

import edu.eci.arsw.openweather.cache.OpenWeatherCache;
import edu.eci.arsw.openweather.connection.HttpConnectionService;
import edu.eci.arsw.openweather.connection.OpenWeatherConnectionException;
import edu.eci.arsw.openweather.model.WeatherByCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Clase que implementa los servicios de la aplicación Open Weather
 */
@Service
public class OpenWeatherServicesImpl implements OpenWeatherServices {
    @Autowired
    private HttpConnectionService httpConnectionService;

    @Autowired
    private OpenWeatherCache openWeatherCache;

    /**
     * Obtiene los datos del clima de un lugar específico
     *
     * @param name Nombre del lugar
     * @return Información del clima en ese lugar
     * @throws OpenWeatherException Cuando el lugar no existe
     */
    @Override
    public WeatherByCity getWeatherByCityName(String name) throws OpenWeatherException {
        WeatherByCity weather;
        try {
            if (openWeatherCache.isWeatherInCache(name)) {
                weather = openWeatherCache.getWeatherFromCity(name);
            } else {
                weather = httpConnectionService.getWeatherByCityName(name);
                weather.setTime(LocalDateTime.now());
                openWeatherCache.putWeatherOfCityInCache(name, weather);
            }
        } catch (OpenWeatherConnectionException e) {
            throw new OpenWeatherException(e.getMessage(), e);
        }
        return weather;
    }
}
