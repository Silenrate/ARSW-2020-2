package edu.eci.arsw.openweather.controllers;

import edu.eci.arsw.openweather.services.OpenWeatherException;
import edu.eci.arsw.openweather.services.OpenWeatherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST de la aplicación Open Weather
 */
@RestController
@RequestMapping(value = "/weather")
public class OpenWeatherController {
    @Autowired
    OpenWeatherServices services;

    /**
     * Obtiene la información del clima de un lugar específico
     *
     * @param name Nombre del lugar
     * @return Entidad de respuesta con la información del clima del lugar o con el mensaje de error en caso de algún fallo
     */
    @GetMapping(value = "/{name}")
    public ResponseEntity<?> getWeatherByCityName(@PathVariable String name) {
        try {
            return new ResponseEntity<>(services.getWeatherByCityName(name), HttpStatus.ACCEPTED);
        } catch (OpenWeatherException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
