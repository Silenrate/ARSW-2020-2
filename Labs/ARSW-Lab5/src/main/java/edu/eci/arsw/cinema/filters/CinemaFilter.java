package edu.eci.arsw.cinema.filters;

import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.services.CinemaException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CinemaFilter {

    public List<CinemaFunction> filtrar(List<CinemaFunction> list, String filtro) throws CinemaException;

}
