package edu.eci.arsw.cinema.filters;

import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.services.CinemaException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("gender")
public class GenderFilter implements CinemaFilter {

    public GenderFilter() {
    }

    @Override
    public List<CinemaFunction> filtrar(List<CinemaFunction> list, String filtro) throws CinemaException {
        if (list == null || filtro == null) {
            throw new CinemaException("La lista de funciones ni el filtro pueden ser nulos");
        }
        if (list.size() == 0) throw new CinemaException("La lista de funciones está vacía");
        List<CinemaFunction> listaFiltrada = new ArrayList<>();
        for (CinemaFunction f : list) {
            Movie movie = f.getMovie();
            if (movie.getGenre().equals(filtro)) {
                listaFiltrada.add(f);
            }
        }
        if (listaFiltrada.size() == 0) {
            throw new CinemaException("No existen funciones con ese genero");
        }
        return listaFiltrada;
    }
}
