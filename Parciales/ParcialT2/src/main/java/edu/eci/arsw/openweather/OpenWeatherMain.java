package edu.eci.arsw.openweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Clase main de la aplicación Open Weather Map
 */
@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.openweather"})
public class OpenWeatherMain {
    /**
     * Método que ejecuta la aplicación
     *
     * @param args Argumentos para iniciar la aplicación
     */
    public static void main(String[] args) {
        SpringApplication.run(OpenWeatherMain.class, args);
    }
}
