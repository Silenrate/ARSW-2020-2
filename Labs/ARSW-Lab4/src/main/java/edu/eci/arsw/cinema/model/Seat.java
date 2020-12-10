/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.model;

import java.util.Objects;

/**
 * @author cristian
 */
public class Seat {

    private int row;
    private int col;

    public Seat() {
    }

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
    }


    public int getRow() {
        return row;
    }


    public void setRow(int row) {
        this.row = row;
    }


    public int getCol() {
        return col;
    }


    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return row == seat.row &&
                col == seat.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
