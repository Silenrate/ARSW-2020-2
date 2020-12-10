package edu.eci.arsw.cinema.test;


import com.google.gson.Gson;
import edu.eci.arsw.cinema.CinemaAPIApplication;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.services.CinemaServicesInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CinemaAPIApplication.class})
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CinemaServicesInterface services;

    private final Gson gson = new Gson();

    @Test
    public void shouldGetAllCinemas() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/cinemas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldGetACinemaByName() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/cinemas/cinemaX")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldNotGetAnNonexistentCinemaByName() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/cinemas/cineplus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotGetTheFunctionsOfANonexistentCinemaByDate() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/cinemas/cineplus/2018-12-18")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotGetTheFunctionsOfANonexistentDateInACinema() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/cinemas/cinemaX/1999-12-18")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotGetTheFunctionsOfABadFormatDate() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/cinemas/cinemaX/1999_12_18")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetTheFunctionsOfACinemaOnADay() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/cinemas/cinemaX/2018-12-18")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldNotGetANonExistentFunction() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/cinemas/cinemaX/1999-12-18 15:30/Transformers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotGetAFunctionOfANonExistentCinema() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/cinemas/cineplus/2018-12-18 15:30/Transformers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotGetASpecificFunctionWithABadFormatDate() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/cinemas/cinemaX/2018-12-18_15:30/SuperHeroes Movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetASpecificFunctionOfACinema() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/cinemas/cinemaX/2018-12-18 15:30/SuperHeroesMovie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldNotCreateANewCinemaFunctionOnANonexistentCinema() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/cinemas/cineplus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(getCinemaFunction("Toy Story", "Infantil")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotCreateABadCinemaFunction() throws Exception {
        CinemaFunction cinemaFunction = getCinemaFunction("Toy Story", "Infantil");
        mvc.perform(
                MockMvcRequestBuilders.post("/cinemas/cinemaX")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(cinemaFunction.getMovie()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotCreateAnAlreadyExistingCinemaFunction() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/cinemas/cinemaX")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(getCinemaFunction("SuperHeroesMovie", "Action")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldCreateANewCinemaFunction() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/cinemas/cinemaX")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(getCinemaFunction("Toy Story", "Infantil")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldNotUpdateACinemaFunctionOfANonexistentCinema() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.put("/cinemas/cineplus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(getCinemaFunction("Toy Story", "Infantil")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotUpdateABadCinemaFunction() throws Exception {
        CinemaFunction cinemaFunction = getCinemaFunction("Toy Story", "Infantil");
        mvc.perform(
                MockMvcRequestBuilders.put("/cinemas/cinemaX")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(cinemaFunction.getMovie()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateACinemaFunction() throws Exception {
        CinemaFunction cinemaFunction = services.getFunctionByMovieName("cinemaX","2018-12-18 15:30","SuperHeroesMovie");
        cinemaFunction.buyTicket(0,0);
        mvc.perform(
                MockMvcRequestBuilders.put("/cinemas/cinemaX")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(cinemaFunction))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    private CinemaFunction getCinemaFunction(String name, String gender) {
        Movie movie = new Movie(name, gender);
        return new CinemaFunction(movie, "2018-12-18 15:30");
    }


}
