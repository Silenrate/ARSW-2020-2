/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.model;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author cristian
 */
public class CinemaFunction {

    public static final int NUMBER_OF_COLUMNS = 7;
    public static final int NUMBER_OF_ROWS = 12;
    private Movie movie;
    private CopyOnWriteArrayList<CopyOnWriteArrayList<Boolean>> seats = new CopyOnWriteArrayList<>();
    private String date;
    private int emptySeats;

    public CinemaFunction() {
    }

    public CinemaFunction(Movie movie, String date) {
        this.movie = movie;
        this.date = date;
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            CopyOnWriteArrayList<Boolean> row = new CopyOnWriteArrayList<>(Arrays.asList(new Boolean[NUMBER_OF_ROWS]));
            Collections.fill(row, Boolean.TRUE);
            this.seats.add(row);
        }
        //Para poner un asiento ocupado
        //seats.get(0).set(0, Boolean.FALSE);
        this.emptySeats = NUMBER_OF_COLUMNS * NUMBER_OF_ROWS;
    }


    public void buyTicket(int row, int col) throws CinemaModelException {
        if (row < 0 || col < 0 || row >= NUMBER_OF_ROWS || col >= NUMBER_OF_COLUMNS) {
            throw new CinemaModelException(String.format("No existe asiento en las coordenadas (%d,%d)", row, col));
        }
        if (seats.get(col).get(row).equals(true)) {
            seats.get(col).set(row, Boolean.FALSE);
            emptySeats--;
        } else {
            throw new CinemaModelException("Asiento ocupado");
        }
    }

    public void updateEmptySeats() {
        int newEmptySeats = 0;
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                newEmptySeats++;
            }
        }
        emptySeats = newEmptySeats;
    }


    public boolean verificarFuncion(CinemaFunction cinemaFunction) {
        return (cinemaFunction.getMovie().equals(movie) && cinemaFunction.getDate().substring(0, 9).equals(date.substring(0, 9)));
    }

    public int getEmptySeats() {
        return emptySeats;
    }

    public CopyOnWriteArrayList<CopyOnWriteArrayList<Boolean>> getSeats() {
        return this.seats;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "date='" + date + "'" + ", movie=" + movie + ", seats=" + seats + "\n emptySeats= " + emptySeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CinemaFunction that = (CinemaFunction) o;
        return Objects.equals(movie, that.movie) &&
                Objects.equals(seats, that.seats) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, seats, date);
    }


}
