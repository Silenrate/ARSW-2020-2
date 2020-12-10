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

/**
 * @author cristian
 */
@Service("inMemory")
public class InMemoryCinemaPersistence implements CinemaPersitence {

    private final Map<String, Cinema> cinemas = new HashMap<>();

    public InMemoryCinemaPersistence() {
        //load stub data
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c = new Cinema("cinemaX", functions);
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
        if (date == null) throw new CinemaPersistenceException("La fecha para buscar no puede ser nula");
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

}
