/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.services;

import edu.eci.arsw.cinema.filters.CinemaFilter;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author cristian
 */
@Service("cinemaServices")
public class CinemaServices {

    @Autowired
    @Qualifier("inMemory")
    CinemaPersitence cps = null;

    @Autowired
    @Qualifier("gender")
    CinemaFilter cinemaFilter = null;

    public void addNewCinema(Cinema c) throws CinemaException {
        try {
            cps.saveCinema(c);
        } catch (CinemaPersistenceException e) {
            throw new CinemaException(e.getMessage(), e);
        }
    }

    /**
     * @param name cinema's name
     * @return the cinema of the given name created by the given author
     * @throws CinemaException
     */
    public Cinema getCinemaByName(String name) throws CinemaException {
        try {
            return cps.getCinema(name);
        } catch (CinemaPersistenceException e) {
            throw new CinemaException(e.getMessage(), e);
        }
    }

    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException {
        try {
            cps.buyTicket(row, col, cinema, date, movieName);
        } catch (CinemaPersistenceException e) {
            throw new CinemaException(e.getMessage(), e);
        }
    }

    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) throws CinemaException {
        List<CinemaFunction> funciones;
        try {
            funciones = cps.getFunctionsByCinemaAndDate(cinema, date);
            return funciones;
        } catch (CinemaPersistenceException e) {
            throw new CinemaException(e.getMessage(), e);
        }
    }


    public Set<Cinema> getAllCinemas() throws CinemaException {
        try {
            return cps.getAllCinemas();
        } catch (CinemaPersistenceException e) {
            throw new CinemaException(e.getMessage(), e);
        }
    }

    public List<CinemaFunction> getFilteredFunctions(String cinema, String date, String filtro) throws CinemaException{
        try {
            List<CinemaFunction> funciones = cps.getFunctionsByCinemaAndDate(cinema, date);
            return cinemaFilter.filtrar(funciones, filtro);
        } catch (CinemaPersistenceException e) {
            throw new CinemaException(e.getMessage(), e);
        }

    }


}
