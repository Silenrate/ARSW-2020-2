package edu.eci.arsw.openweather;

import edu.eci.arsw.openweather.model.WeatherByCity;
import edu.eci.arsw.openweather.services.OpenWeatherException;
import edu.eci.arsw.openweather.services.OpenWeatherServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Clase de pruebas para aplicación Open Weather Map.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenWeatherServicesTest {

    @Autowired
    OpenWeatherServices services;

    @Test
    public void shouldGetWeatherInfoByCityName() throws OpenWeatherException {
        WeatherByCity response = services.getWeatherByCityName("Bogota");
        assertNotNull(response);
        assertEquals("CO", response.getCountry());
    }

    @Test
    public void shouldNotGetWeatherInfoByCityNameIfDoNotExist() {
        try {
            services.getWeatherByCityName("aaaaaaaaa");
            fail("Debió fallar por consultar la información de un lugar que no existe");
        } catch (OpenWeatherException e) {
            assertEquals("No existen resultados con ese nombre", e.getMessage());
        }
    }
}
