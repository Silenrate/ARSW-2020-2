package edu.eci.arsw.openweather.connection;

import edu.eci.arsw.openweather.model.WeatherByCity;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Interfaz de conexión con otras aplicaciones para obtener datos del clima
 */
@Service
public interface HttpConnectionService {
    double DELTA_FROM_KELVIN_T0_CENTIGRADE = 273.15;

    /**
     * Obtiene los datos del clima de un lugar específico por medio de una conexión con otra app
     *
     * @param name Nombre del lugar
     * @return Información del clima en ese lugar
     * @throws OpenWeatherConnectionException Cuando el lugar no existe
     */
    WeatherByCity getWeatherByCityName(String name) throws OpenWeatherConnectionException;

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

    /**
     * Convierte el objeto JSON recibido en la información del clima necesaria
     *
     * @param obj Objeto Json recibido por la conexión
     * @return Información del clima del objeto Json
     * @throws JSONException Cuando hubo un error al convertir el objeto
     */
    default WeatherByCity getWeatherByCity(JSONObject obj) throws JSONException {
        JSONObject main = obj.getJSONObject("main");
        JSONObject coordinates = obj.getJSONObject("coord");
        String city = getStringOfJsonObject(obj, "name");
        String country = getStringOfJsonObject(obj.getJSONObject("sys"), "country");
        String image = getStringFromJsonArrayElementInJsonObject(0, "weather", obj, "icon");
        double temperature = getDoubleOfJsonObject(main, "temp", DELTA_FROM_KELVIN_T0_CENTIGRADE);
        double thermalSensation = getDoubleOfJsonObject(main, "feels_like", DELTA_FROM_KELVIN_T0_CENTIGRADE);
        double windSpeed = getDoubleOfJsonObject(obj.getJSONObject("wind"), "speed", 0);
        double longitude = getDoubleOfJsonObject(coordinates, "lon", 0);
        double latitude = getDoubleOfJsonObject(coordinates, "lat", 0);
        int pressure = main.getInt("pressure");
        int humidity = main.getInt("humidity");
        WeatherByCity weather = new WeatherByCity(city, country, temperature, thermalSensation, windSpeed, pressure, humidity);
        weather.setImage(image);
        weather.setLatitude(latitude);
        weather.setLongitude(longitude);
        return weather;
    }

    /**
     * Obtiene un valor de tipo string de un elemento de un JsonArray dentro de un JsonObject
     *
     * @param index       Indice del elemento en el array
     * @param arrayValue  Nombre del atributo array dentro del Json Object
     * @param jsonObject  Objeto Json sobre el cual se realiza la consulta
     * @param stringValue Nombre del atributo string que se va a extraer
     * @return Valor del string del elemento del JsonArray del Json Object
     */
    default String getStringFromJsonArrayElementInJsonObject(int index, String arrayValue, JSONObject jsonObject, String stringValue) {
        JSONObject object = jsonObject.getJSONArray(arrayValue).getJSONObject(index);
        return getStringOfJsonObject(object, stringValue);
    }

    /**
     * Obtiene un valor double redondeado de un Json Object
     *
     * @param jsonObject Objeto sobre el cual se va a extraer el valor
     * @param value      Nombre del atributo double que se va a extraer
     * @param delta      Valor a restar del numero obtenido
     * @return Valor double redondeado a dos cifras del atributo del Json Object
     */
    default double getDoubleOfJsonObject(JSONObject jsonObject, String value, double delta) {
        double returnedValue = jsonObject.getDouble(value) - delta;
        return Math.round(returnedValue * 10d) / 10d;
    }

    /**
     * Obtiene un valor string de un Json Object
     *
     * @param jsonObject Objeto sobre el cual se va a extraer el valor
     * @param value      Nombre del atributo string que se va a extraer
     * @return Valor del string del Json Object
     */
    default String getStringOfJsonObject(JSONObject jsonObject, String value) {
        return jsonObject.getString(value);
    }
}
