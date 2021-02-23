package edu.eci.arsw.openweather;

import com.google.gson.Gson;
import edu.eci.arsw.openweather.controllers.OpenWeatherController;
import edu.eci.arsw.openweather.model.WeatherByCity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OpenWeatherController.class)
public class OpenWeatherControllerTest {
    @Autowired
    private MockMvc mvc;

    private final Gson gson = new Gson();

    @Test
    public void shouldGetWeatherInfoByCityName() throws Exception {
        MvcResult result = mvc.perform(get("/weather/Bogota")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        WeatherByCity weather = gson.fromJson(json, WeatherByCity.class);
        assertEquals("CO", weather.getCountry());
    }

    @Test
    public void shouldNotGetWeatherInfoByCityNameIfDoNotExist() throws Exception {
        MvcResult result = mvc.perform(get("/weather/aaaaaaaaaaaaa")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        assertEquals("No existen resultados con ese nombre", bodyResult);
    }
}
