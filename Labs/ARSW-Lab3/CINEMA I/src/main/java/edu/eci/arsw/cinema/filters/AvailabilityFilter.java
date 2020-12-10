package edu.eci.arsw.cinema.filters;

import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.services.CinemaException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("availability")
public class AvailabilityFilter implements CinemaFilter {

    public AvailabilityFilter() {
    }

    @Override
    public List<CinemaFunction> filtrar(List<CinemaFunction> list, String filtro) throws CinemaException {
        if (list == null || filtro == null) {
            throw new CinemaException("La lista de funciones ni el filtro pueden ser nulos");
        }
        if (list.size() == 0) throw new CinemaException("La lista de funciones está vacía");
        int x;
        try {
            x = Integer.parseInt(filtro);
        } catch (NumberFormatException ex) {
            throw new CinemaException("Parámetro invalido. Ingrese un numero entero");
        }
        if (x < 0) throw new CinemaException("El valor del filtro no puede ser un número negativo");
        List<CinemaFunction> listaFiltrada = new ArrayList<>();
        for (CinemaFunction f : list) {
            if (f.getEmptySeats() > x) {
                listaFiltrada.add(f);
            }
        }
        if (listaFiltrada.size() == 0) {
            throw new CinemaException("No existen funciones con ese numero de asientos disponibles.");
        }
        return listaFiltrada;
    }
}
