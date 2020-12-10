/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.model;

import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author cristian
 */
public class Cinema {

    private String name;
    private List<CinemaFunction> functions;


    public Cinema() {
    }

    public Cinema(String name, List<CinemaFunction> functions) {
        this.name = name;
        this.functions = functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CinemaFunction> getFunctions() {
        return this.functions;
    }

    public void setSchedule(List<CinemaFunction> functions) {
        this.functions = functions;
    }

    public CinemaFunction getFunctionByNameAndDate(String name, String date) throws CinemaModelException {
        CinemaFunction cinemaFunction = null;
        for (CinemaFunction f : functions) {
            if (f.getMovie().getName().equals(name) && f.getDate().equals(date)) {
                cinemaFunction = f;
                break;
            }
        }
        if (cinemaFunction == null) {
            throw new CinemaModelException("Esa función no existe en este cinema");
        }
        return cinemaFunction;
    }

    public List<CinemaFunction> getFunctionsByDate(String date) throws CinemaModelException {
        List<CinemaFunction> lista = new ArrayList<>();
        for (CinemaFunction f : functions) {
            if (f.getDate().equals(date)) {
                lista.add(f);
            }
        }
        if (lista.size() == 0) {
            throw new CinemaModelException("No hay funciones programadas a esa hora en el cine "+name);
        }
        return lista;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' + ", functions=" + functions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(name, cinema.name) &&
                Objects.equals(functions, cinema.functions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, functions);
    }
}
