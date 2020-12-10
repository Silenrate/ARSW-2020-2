/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.model;

import java.util.*;

/**
 * @author cristian
 */
public class CinemaFunction {

    public static final int NUMBER_OF_COLUMNS = 7;
    public static final int NUMBER_OF_ROWS = 12;
    private Movie movie;
    private List<List<Boolean>> seats = new ArrayList<>();
    private String date;
    private int emptySeats;

    public CinemaFunction() {
    }

    public CinemaFunction(Movie movie, String date) {
        this.movie = movie;
        this.date = date;
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            List<Boolean> row = new ArrayList<>(Arrays.asList(new Boolean[NUMBER_OF_COLUMNS]));
            Collections.fill(row, Boolean.TRUE);
            this.seats.add(row);
        }
        this.emptySeats = NUMBER_OF_COLUMNS * NUMBER_OF_ROWS;
    }

    public void buyTicket(int row, int col) throws CinemaModelException {
        if (row < 0 || col < 0 || row >= NUMBER_OF_ROWS || col >= NUMBER_OF_COLUMNS) {
            throw new CinemaModelException("No existe asiento en esas coordenadas");
        }
        if (seats.get(row).get(col).equals(true)) {
            seats.get(row).set(col, Boolean.FALSE);
            emptySeats--;
        } else {
            throw new CinemaModelException("Asiento ocupado");
        }
    }

    public int getEmptySeats() {
        return emptySeats;
    }

    public List<List<Boolean>> getSeats() {
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
        return "date='" + date + "'" + ", movie=" + movie + ", seats=" + seats;
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
