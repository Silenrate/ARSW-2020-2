/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author cristian
 */
public class Cinema {

    private String name;
    private CopyOnWriteArrayList<CinemaFunction> functions;


    public Cinema() {
    }

    public Cinema(String name, CopyOnWriteArrayList<CinemaFunction> functions) {
        this.name = name;
        this.functions = functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CopyOnWriteArrayList<CinemaFunction> getFunctions() {
        return this.functions;
    }

    public void setSchedule(CopyOnWriteArrayList<CinemaFunction> functions) {
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

    public List<CinemaFunction> getFunctionsByDateAndHour(String date) throws CinemaModelException {
        List<CinemaFunction> lista = new ArrayList<>();
        for (CinemaFunction f : functions) {
            if (f.getDate().equals(date)) {
                lista.add(f);
            }
        }
        if (lista.size() == 0) {
            throw new CinemaModelException("No hay funciones programadas a esa hora en el cine " + name);
        }
        return lista;
    }

    public List<CinemaFunction> getFunctionsByDate(String date) throws CinemaModelException {
        List<CinemaFunction> lista = new ArrayList<>();
        for (CinemaFunction f : functions) {
            if (f.getDate().contains(date)) {
                lista.add(f);
            }
        }
        if (lista.size() == 0) {
            throw new CinemaModelException("No hay funciones programadas a esa hora en el cine " + name);
        }
        return lista;
    }

    public CinemaFunction getFunctionByMovieName(String fecha, String nombrePelicula) throws CinemaModelException {
        CinemaFunction funcion = null;
        for (CinemaFunction f : functions) {
            if (f.getDate().equals(fecha) && f.getMovie().getName().equals(nombrePelicula)) {
                funcion = f;
                break;
            }
        }
        if (funcion == null) {
            throw new CinemaModelException("No existe esa película a esa hora.");
        }
        return funcion;
    }

    public void addNewFunction(CinemaFunction nuevaFuncion) throws CinemaModelException {
        nuevaFuncion.updateEmptySeats();
        for (CinemaFunction f : functions) {
            if (f.equals(nuevaFuncion)) {
                throw new CinemaModelException("Ya existe esa función");
            }
        }
        functions.add(nuevaFuncion);
    }

    public void updateFunction(CinemaFunction funcionactualizada) throws CinemaModelException {
        funcionactualizada.updateEmptySeats();
        boolean seActualizo = false;
        for (int i = 0; i < functions.size(); i++) {
            if (functions.get(i).getMovie().equals(funcionactualizada.getMovie()) && functions.get(i).getDate().equals(funcionactualizada.getDate())) {
                functions.set(i, funcionactualizada);
                seActualizo = true;
                break;
            }
        }
        if(!seActualizo){
            functions.add(funcionactualizada);
        }

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
