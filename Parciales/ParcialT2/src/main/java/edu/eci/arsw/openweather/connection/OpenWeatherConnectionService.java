package edu.eci.arsw.openweather.connection;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.eci.arsw.openweather.model.WeatherByCity;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Clase que implementa la conexión con la API de OpenWeather por medio de HTTP
 */
@Service
public class OpenWeatherConnectionService implements HttpConnectionService {

    private final String url;
    private final String appId;

    /**
     * Constructor de la conexión por HTTP
     */
    public OpenWeatherConnectionService() {
        url = "http://api.openweathermap.org/data/2.5/weather?q=";
        appId = "0ebb279a5949cccfa1ffcd7c2f326dd1";
    }

    /**
     * Obtiene los datos del clima de un lugar específico por medio de una conexión con otra app
     *
     * @param name Nombre del lugar
     * @return Información del clima en ese lugar
     * @throws OpenWeatherConnectionException Cuando el lugar no existe
     */
    @Override
    public WeatherByCity getWeatherByCityName(String name) throws OpenWeatherConnectionException {
        HttpResponse<JsonNode> response;
        try {
            response = Unirest.get(url + getStringInUrl(name) + "&appid=" + appId).asJson();
        } catch (UnirestException e) {
            throw new OpenWeatherConnectionException("Error de conexión con OpenWeather", e);
        }
        if (response == null) throw new OpenWeatherConnectionException("Error de conexión con OpenWeather");
        JSONObject obj = response.getBody().getObject();
        WeatherByCity weather;
        try {
            weather = getWeatherByCity(obj);
        } catch (JSONException e) {
            throw new OpenWeatherConnectionException("No existen resultados con ese nombre", e);
        }
        return weather;
    }
}
