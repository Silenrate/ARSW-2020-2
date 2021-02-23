package edu.eci.arsw.openweather.connection;

/**
 * Excepción Personalizada de la Aplicación Open Weather a nivel de la Conexión con la otra API
 */
public class OpenWeatherConnectionException extends Exception {
    /**
     * Constructor de la clase OpenWeatherConnectionException
     *
     * @param msg Mensaje de la Excepción
     */
    public OpenWeatherConnectionException(String msg) {
        super(msg);
    }

    /**
     * Constructor de la clase OpenWeatherConnectionException
     *
     * @param msg   Mensaje de la Excepción
     * @param cause Causa de la Excepción
     */
    public OpenWeatherConnectionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
