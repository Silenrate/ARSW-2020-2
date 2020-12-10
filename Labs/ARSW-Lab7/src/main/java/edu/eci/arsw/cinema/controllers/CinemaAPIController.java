/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;

import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.services.CinemaException;
import edu.eci.arsw.cinema.services.CinemaServicesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author cristian
 */
@RestController
@RequestMapping(value = "/cinemas")
public class CinemaAPIController {

    @Autowired
    @Qualifier("cinemaServices")
    private CinemaServicesInterface cinemaServicesInterface = null;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllCinemas() {
        try {
            return new ResponseEntity<>(cinemaServicesInterface.getAllCinemas(), HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/{nombre}", method = RequestMethod.GET)
    public ResponseEntity<?> getCinemaByName(@PathVariable String nombre) {
        try {
            return new ResponseEntity<>(cinemaServicesInterface.getCinemaByName(nombre), HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().equals("El nombre del cine no puede ser nulo")) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }

        }
    }

    @RequestMapping(value = "/{nombre}/{fecha}", method = RequestMethod.GET)
    public ResponseEntity<?> getCinemaByNameAndDate(@PathVariable String nombre, @PathVariable String fecha) {
        String dateFormat = "\\d{4}-\\d{1,2}-\\d{1,2}";
        if (!Pattern.matches(dateFormat, fecha)) {
            return new ResponseEntity<>("La fecha debe ser escrita en formato 'yyyy-MM-dd'", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(cinemaServicesInterface.getFunctionsbyCinemaAndDate(nombre, fecha), HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().equals("No existe el cine " + nombre) || (ex.getMessage().equals("No hay funciones programadas a esa hora en el cine " + nombre))) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/{nombre}/{fecha}/{nombrePelicula}", method = RequestMethod.GET)
    public ResponseEntity<?> getFunctionByMovieName(@PathVariable String nombre, @PathVariable String fecha, @PathVariable String nombrePelicula) {
        String dateWithHourFormat = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}";
        if (!Pattern.matches(dateWithHourFormat, fecha)) {
            return new ResponseEntity<>("La fecha debe ser escrita en formato 'yyyy-MM-dd HH:mm'", HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(cinemaServicesInterface.getFunctionByMovieName(nombre, fecha, nombrePelicula), HttpStatus.ACCEPTED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().equals("No existe esa película a esa hora.") || ex.getMessage().equals("No existe el cine " + nombre)) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/{nombreDelCine}", method = RequestMethod.POST)
    public ResponseEntity<?> addNewFunction(@PathVariable String nombreDelCine, @RequestBody CinemaFunction nuevaFuncion) {
        if (nuevaFuncion.getMovie() == null || nuevaFuncion.getDate() == null) {
            return new ResponseEntity<>("JSON Bad Format", HttpStatus.BAD_REQUEST);
        }
        try {
            cinemaServicesInterface.addNewFunction(nombreDelCine, nuevaFuncion);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().equals("No existe el cine " + nombreDelCine)) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            } else if (ex.getMessage().equals("Ya existe esa función")) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/{nombreDelCine}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFunction(@PathVariable String nombreDelCine, @RequestBody CinemaFunction funcionactualizada) {
        if (funcionactualizada.getMovie() == null || funcionactualizada.getDate() == null) {
            return new ResponseEntity<>("JSON Bad Format", HttpStatus.BAD_REQUEST);
        }
        try {
            cinemaServicesInterface.updateFunction(nombreDelCine, funcionactualizada);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().equals("No existe el cine " + nombreDelCine)) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/{nombreDelCine}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFunction(@PathVariable String nombreDelCine, @RequestBody CinemaFunction funcionactualizada) {
        if (funcionactualizada.getMovie() == null || funcionactualizada.getDate() == null) {
            return new ResponseEntity<>("JSON Bad Format", HttpStatus.BAD_REQUEST);
        }
        try {
            cinemaServicesInterface.deleteFunction(nombreDelCine, funcionactualizada);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().equals("No existe el cine " + nombreDelCine)) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
            }
        }
    }

}
