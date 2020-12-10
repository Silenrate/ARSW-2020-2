/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.CinemaModelException;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author cristian
 */
@Service("inMemory")
public class InMemoryCinemaPersistence implements CinemaPersitence {

    private final ConcurrentHashMap<String, Cinema> cinemas = new ConcurrentHashMap<>();

    public InMemoryCinemaPersistence() {
        //load stub data
        String functionDate = "2018-12-18 15:30";
        CopyOnWriteArrayList<CinemaFunction> functions = new CopyOnWriteArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroesMovie", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("TheNight", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c = new Cinema("cinemaX", functions);

        Cinema procinal = crearNuevoCinema("Procinal");
        Cinema cineColombia = crearNuevoCinema("CineColombia");
        cinemas.put("Procinal", procinal);
        cinemas.put("CineColombia", cineColombia);
        cinemas.put("cinemaX", c);


    }

    @Override
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaPersistenceException {
        Cinema cine = getCinema(cinema);
        if (date == null || movieName == null) {
            throw new CinemaPersistenceException("La fecha y el nombre de la película no pueden ser nulos");
        }
        try {
            CinemaFunction function = cine.getFunctionByNameAndDate(movieName, date);
            function.buyTicket(row, col);
        } catch (CinemaModelException e) {
            throw new CinemaPersistenceException(e.getMessage(), e);
        }
    }

    @Override
    public List<CinemaFunction> getFunctionsByCinemaAndDate(String cinema, String date) throws CinemaPersistenceException {
        Cinema cine = getCinema(cinema);
        if (date == null) {
            throw new CinemaPersistenceException("La fecha para buscar no puede ser nula");
        }
        try {
            return cine.getFunctionsByDate(date);
        } catch (CinemaModelException e) {
            throw new CinemaPersistenceException(e.getMessage(), e);
        }
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (c == null) throw new CinemaPersistenceException("El cine a guardar no puede ser nulo");
        if (cinemas.containsKey(c.getName())) {
            throw new CinemaPersistenceException("The given cinema already exists: " + c.getName());
        } else {
            cinemas.put(c.getName(), c);
        }
    }

    @Override
    public Cinema getCinema(String name) throws CinemaPersistenceException {
        if (name == null) throw new CinemaPersistenceException("El nombre del cine no puede ser nulo");
        Cinema cinema = cinemas.get(name);
        if (cinema == null) throw new CinemaPersistenceException("No existe el cine " + name);
        return cinema;
    }

    @Override
    public Set<Cinema> getAllCinemas() throws CinemaPersistenceException {
        Set<Cinema> conjunto = new HashSet(cinemas.values());
        return conjunto;
    }

    @Override
    public CinemaFunction getFunctionByMovieName(String nombre, String fecha, String nombrePelicula) throws CinemaPersistenceException {
        Cinema cine = getCinema(nombre);
        if (fecha == null) {
            throw new CinemaPersistenceException("La fecha para buscar no puede ser nula.");
        }
        if (nombrePelicula == null) {
            throw new CinemaPersistenceException("Esa película no existe.");
        }
        try {
            return cine.getFunctionByMovieName(fecha, nombrePelicula);
        } catch (CinemaModelException e) {
            throw new CinemaPersistenceException(e.getMessage(), e);
        }
    }

    @Override
    public void addNewFunction(String nombreDelCine, CinemaFunction nuevaFuncion) throws CinemaPersistenceException {
        Cinema cine = getCinema(nombreDelCine);
        if (nuevaFuncion == null) {
            throw new CinemaPersistenceException("La nueva función agregada no puede ser nula.");
        }
        try {
            cine.addNewFunction(nuevaFuncion);
        } catch (CinemaModelException e) {
            throw new CinemaPersistenceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateFunction(String nombreDelCine, CinemaFunction funcionactualizada) throws CinemaPersistenceException {
        Cinema cine = getCinema(nombreDelCine);
        if (funcionactualizada == null) {
            throw new CinemaPersistenceException("La función que va a actualizar no puede ser nula.");
        }
        try {
            cine.updateFunction(funcionactualizada);
        } catch (CinemaModelException e) {
            throw new CinemaPersistenceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteFunction(String nombreDelCine, CinemaFunction funcion) throws CinemaPersistenceException {
        Cinema cine = getCinema(nombreDelCine);
        if (funcion == null) {
            throw new CinemaPersistenceException("La función que va a actualizar no puede ser nula.");
        }
        try {
            cine.deleteFunction(funcion);
        } catch (CinemaModelException e) {
            throw new CinemaPersistenceException(e.getMessage(), e);
        }
    }


    private static Cinema crearNuevoCinema(String name) {
        Movie movie = new Movie("Transformers", "Action");
        Movie movie1 = new Movie("Matrix", "Fiction");
        Movie movie2 = new Movie("Titanic", "Action");
        CopyOnWriteArrayList<CinemaFunction> functions = new CopyOnWriteArrayList<>();
        CinemaFunction cinemaFunction = new CinemaFunction(movie, "2020-08-27 14:30");
        CinemaFunction cinemaFunction1 = new CinemaFunction(movie1, "2020-09-27 14:30");
        CinemaFunction cinemaFunction2 = new CinemaFunction(movie2, "2020-09-27 15:30");
        functions.add(cinemaFunction);
        functions.add(cinemaFunction1);
        functions.add(cinemaFunction2);
        return new Cinema(name, functions);
    }

}
