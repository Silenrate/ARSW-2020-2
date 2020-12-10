package edu.eci.arsw.cinema.services;

/**
 *
 * @author cristian
 */
public class CinemaException extends Exception{

    public CinemaException(String message) {
        super(message);
    }

    public CinemaException(String message, Throwable cause) {
        super(message, cause);
    }

}
