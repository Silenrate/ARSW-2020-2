package edu.eci.arsw.cinema.test;

import edu.eci.arsw.cinema.CinemaAPIApplication;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author cristian
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CinemaAPIApplication.class})
public class InMemoryPersistenceTest {

    private InMemoryCinemaPersistence ipct;

    @Before
    public void setUp() {
        ipct = new InMemoryCinemaPersistence();
    }

    @Test
    public void shouldSaveANewCinema() throws CinemaPersistenceException {
        Cinema c = getCinema("Movies Bogotá", 2);
        ipct.saveCinema(c);
        assertEquals(c, ipct.getCinema(c.getName()));
    }


    @Test
    public void shouldNotSaveAnExistingCinema() {
        Cinema c = getCinema("Movies Chia", 2);
        try {
            ipct.saveCinema(c);
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        }
        Cinema c2 = getCinema("Movies Chia", 3);
        try {
            ipct.saveCinema(c2);
            fail("An exception was expected after saving a second cinema with the same name");
        } catch (CinemaPersistenceException ex) {
            assertEquals("The given cinema already exists: Movies Chia", ex.getMessage());
        }
    }

    @Test
    public void shouldNotSaveANullCinema() {
        try {
            ipct.saveCinema(null);
            fail("An exception was expected after saving a second cinema with the same name");
        } catch (CinemaPersistenceException ex) {
            assertEquals("El cine a guardar no puede ser nulo", ex.getMessage());
        }
    }

    @Test
    public void shouldConsultAllCinemas() throws CinemaPersistenceException {
        Set<Cinema> functions = ipct.getAllCinemas();
        //Al menos debe estar el cine cargado por defecto en el constructor
        assertTrue(functions.size() >= 1);
    }

    @Test
    public void shouldNotConsultANonExistingCinema() {
        try {
            ipct.getCinema("Porncinal");
            fail("Debió fallar al consultar un cine que no existe");
        } catch (CinemaPersistenceException e) {
            assertEquals("No existe el cine Porncinal", e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultANullCinema() {
        try {
            ipct.getCinema(null);
            fail("Debió fallar al consultar un cine nulo");
        } catch (CinemaPersistenceException e) {
            assertEquals("El nombre del cine no puede ser nulo", e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultTheFunctionsOfANonExistingCinema() {
        try {
            ipct.getFunctionsByCinemaAndDate("Porncinal", "2018-12-18 15:30");
            fail("Debió fallar al buscar las funciones de un cine que no existe");
        } catch (CinemaPersistenceException e) {
            assertEquals("No existe el cine Porncinal", e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultTheFunctionsOfACinemaWithoutFunctionsOnThatDate() {
        Cinema c = getCinema("CinePeru", 4);
        try {
            ipct.saveCinema(c);
            ipct.getFunctionsByCinemaAndDate("CinePeru", "2019-12-18 15:30");
            fail("Debió fallar al buscar las funciones de un cine en una fecha sin funciones");
        } catch (CinemaPersistenceException e) {
            assertEquals("No hay funciones programadas a esa hora en el cine CinePeru", e.getMessage());
        }
    }

    @Test
    public void shouldNotConsultTheFunctionsOfACinemaWithANullDate() {
        Cinema c = getCinema("CineEcuador", 3);
        try {
            ipct.saveCinema(c);
            ipct.getFunctionsByCinemaAndDate("CineEcuador", null);
            fail("Debió fallar al buscar las funciones de un cine con la fecha nula");
        } catch (CinemaPersistenceException e) {
            assertEquals("La fecha para buscar no puede ser nula", e.getMessage());
        }
    }

    @Test
    public void shouldConsultTheFunctionsOfACinemaOnCertainDate() throws Exception {
        String functionDate = "2018-12-18 15:30";
        Cinema c = getCinema("CinePeru", 4);
        List<CinemaFunction> functions = c.getFunctionsByDate(functionDate);
        ipct.saveCinema(c);
        List<CinemaFunction> functionsRegistered = ipct.getFunctionsByCinemaAndDate("CinePeru", functionDate);
        assertEquals(functions, functionsRegistered);
    }

    @Test
    public void shouldNotBuyTheTicketOfAMovieWithANullParameter() {
        Cinema c = getCinema("CineChile", 1);
        try {
            ipct.saveCinema(c);
        } catch (CinemaPersistenceException e) {
            fail("No debería fallar al guardar este cine");
        }
        try {
            ipct.buyTicket(2, 2, null, "2018-12-18 15:30", "SuperHeroes Movie 1");
            fail("Debió fallar al comprar el boleto de un cine que no existe");
        } catch (CinemaPersistenceException e) {
            assertEquals("El nombre del cine no puede ser nulo", e.getMessage());
        }
        try {
            ipct.buyTicket(2, 2, "CineChile", null, "SuperHeroes Movie 1");
            fail("Debió fallar al comprar un boleto con la fecha nula");
        } catch (CinemaPersistenceException e) {
            assertEquals("La fecha y el nombre de la película no pueden ser nulos", e.getMessage());
        }
        try {
            ipct.buyTicket(2, 2, "CineChile", "2018-12-18 15:30", null);
            fail("Debió fallar al comprar un boleto con el nombre de la película nulo");
        } catch (CinemaPersistenceException e) {
            assertEquals("La fecha y el nombre de la película no pueden ser nulos", e.getMessage());
        }
        try {
            ipct.buyTicket(2, 2, "CineChile", null, null);
            fail("Debió fallar al comprar un boleto con la fecha y el nombre de la película nulos");
        } catch (CinemaPersistenceException e) {
            assertEquals("La fecha y el nombre de la película no pueden ser nulos", e.getMessage());
        }
    }

    @Test
    public void shouldNotBuyTheTicketInANonExistingSeat() {
        Cinema c = getCinema("CineArgentina", 2);
        try {
            ipct.saveCinema(c);
        } catch (CinemaPersistenceException e) {
            fail("No debería fallar al guardar este cine");
        }
        try {
            ipct.buyTicket(100, 5, "CineArgentina", "2018-12-18 15:30", "SuperHeroes Movie 1");
            fail("Debió fallar al tener una coordenada inexistente");
        } catch (CinemaPersistenceException e) {
            assertEquals("No existe asiento en esas coordenadas", e.getMessage());
        }
        try {
            ipct.buyTicket(5, 100, "CineArgentina", "2018-12-18 15:30", "SuperHeroes Movie 1");
            fail("Debió fallar al tener una coordenada inexistente");
        } catch (CinemaPersistenceException e) {
            assertEquals("No existe asiento en esas coordenadas", e.getMessage());
        }
        try {
            ipct.buyTicket(100, 100, "CineArgentina", "2018-12-18 15:30", "SuperHeroes Movie 1");
            fail("Debió fallar al tener dos coordenadas inexistentes");
        } catch (CinemaPersistenceException e) {
            assertEquals("No existe asiento en esas coordenadas", e.getMessage());
        }
        try {
            ipct.buyTicket(-1, 5, "CineArgentina", "2018-12-18 15:30", "SuperHeroes Movie 1");
            fail("Debió fallar al tener una coordenada inexistente");
        } catch (CinemaPersistenceException e) {
            assertEquals("No existe asiento en esas coordenadas", e.getMessage());
        }
        try {
            ipct.buyTicket(5, -1, "CineArgentina", "2018-12-18 15:30", "SuperHeroes Movie 1");
            fail("Debió fallar al tener una coordenada inexistente");
        } catch (CinemaPersistenceException e) {
            assertEquals("No existe asiento en esas coordenadas", e.getMessage());
        }
        try {
            ipct.buyTicket(-1, -1, "CineArgentina", "2018-12-18 15:30", "SuperHeroes Movie 1");
            fail("Debió fallar al tener dos coordenadas inexistentes");
        } catch (CinemaPersistenceException e) {
            assertEquals("No existe asiento en esas coordenadas", e.getMessage());
        }
    }

    @Test
    public void shouldNotBuyTheTicketOnAOccupiedSeat() {
        Cinema c = getCinema("CineBrasil", 1);
        try {
            ipct.saveCinema(c);
        } catch (CinemaPersistenceException e) {
            fail("No debería fallar al guardar este cine");
        }
        try {
            ipct.buyTicket(1, 1, "CineBrasil", "2018-12-18 15:30", "SuperHeroes Movie 1");
            ipct.buyTicket(1, 1, "CineBrasil", "2018-12-18 15:30", "SuperHeroes Movie 1");
            fail("Debió fallar al intentar comprar un boleto en un asiento ocupado");
        } catch (CinemaPersistenceException e) {
            assertEquals("Asiento ocupado", e.getMessage());
        }
    }

    @Test
    public void shouldNotBuyTheTicketOnANonExistingCinema() {
        try {
            ipct.buyTicket(1, 1, "CineY", "2018-12-18 15:30", "SuperHeroes Movie 1");
            fail("Debió fallar al intentar comprar un boleto en un asiento ocupado");
        } catch (CinemaPersistenceException e) {
            assertEquals("No existe el cine CineY", e.getMessage());
        }
    }

    @Test
    public void shouldBuyTheTicket() throws Exception {
        Cinema c = getCinema("CineW", 1);
        ipct.saveCinema(c);
        Cinema cinema = ipct.getCinema("CineW");
        List<CinemaFunction> functions = cinema.getFunctionsByDate("2018-12-18 15:30");
        CinemaFunction function = functions.get(0);
        int emptySeatsBefore = function.getEmptySeats();
        ipct.buyTicket(1, 1, "CineW", "2018-12-18 15:30", "SuperHeroes Movie 1");
        int emptySeatsAfter = function.getEmptySeats();
        assertEquals(emptySeatsBefore, emptySeatsAfter + 1);
    }

    private Cinema getCinema(String name, int amountOfCinemaFunctions) {
        String functionDate = "2018-12-18 15:30";
        CopyOnWriteArrayList<CinemaFunction> functions = new CopyOnWriteArrayList<>();
        for (int i = 0; i < amountOfCinemaFunctions; i++) {
            CinemaFunction function = new CinemaFunction(new Movie("SuperHeroes Movie " + (i + 1), "Action"), functionDate);
            functions.add(function);
        }
        return new Cinema(name, functions);
    }
}
