package edu.eci.arsw.openweather.services;

/**
 * Excepción Personalizada de la Aplicación Open Weather
 */
public class OpenWeatherException extends Exception {
    /**
     * Constructor de la clase OpenWeatherException
     *
     * @param msg Mensaje de la Excepción
     */
    public OpenWeatherException(String msg) {
        super(msg);
    }

    /**
     * Constructor de la clase OpenWeatherException
     *
     * @param msg   Mensaje de la Excepción
     * @param cause Causa de la Excepción
     */
    public OpenWeatherException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
