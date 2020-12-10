/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author cristian
 */
@Service
public interface CinemaPersitence {

    /**
     * @param row       the row of the seat
     * @param col       the column of the seat
     * @param cinema    the cinema's name
     * @param date      the date of the function
     * @param movieName the name of the movie
     * @throws CinemaPersistenceException if the seat is occupied,
     *                                    or any other low-level persistence error occurs.
     */
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaPersistenceException;

    /**
     * @param cinema cinema's name
     * @param date   date
     * @return the list of the functions of the cinema in the given date
     */
    public List<CinemaFunction> getFunctionsByCinemaAndDate(String cinema, String date) throws CinemaPersistenceException;

    /**
     * @param cinema new cinema
     * @throws CinemaPersistenceException n if a cinema with the same name already exists
     */
    public void saveCinema(Cinema cinema) throws CinemaPersistenceException;

    /**
     * @param name name of the cinema
     * @return Cinema of the given name
     * @throws CinemaPersistenceException if there is no such cinema
     */
    public Cinema getCinema(String name) throws CinemaPersistenceException;

    /**
     * Devuelve todos los cinemas
     *
     * @return
     * @throws CinemaPersistenceException
     */
    public Set<Cinema> getAllCinemas() throws CinemaPersistenceException;


    public CinemaFunction getFunctionByMovieName(String nombre, String fecha, String nombrePelicula) throws CinemaPersistenceException;

    public void addNewFunction(String nombreDelCine, CinemaFunction nuevaFuncion) throws CinemaPersistenceException;

    public void updateFunction(String nombreDelCine, CinemaFunction funcionactualizada) throws CinemaPersistenceException;

    public void deleteFunction(String nombreDelCine, CinemaFunction funcion) throws CinemaPersistenceException;
}
