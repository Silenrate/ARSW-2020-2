package edu.eci.arsw.cinema.services;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface CinemaServicesInterface {
    public void addNewCinema(Cinema c) throws CinemaException;

    /**
     * @param name cinema's name
     * @return the cinema of the given name created by the given author
     * @throws CinemaException
     */
    public Cinema getCinemaByName(String name) throws CinemaException;

    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException;

    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) throws CinemaException;

    public Set<Cinema> getAllCinemas() throws CinemaException;

    public List<CinemaFunction> getFilteredFunctions(String cinema, String date, String filtro) throws CinemaException;


    public CinemaFunction getFunctionByMovieName(String nombre, String fecha, String nombrePelicula) throws CinemaException;

    public void addNewFunction(String nombreDelCine, CinemaFunction nuevaFuncion) throws CinemaException;

    public void updateFunction(String nombreDelCine, CinemaFunction funcionactualizada) throws CinemaException;

    public void deleteFunction(String nombreDelCine, CinemaFunction funcion) throws CinemaException;
}
