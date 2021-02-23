package edu.eci.arsw.api.primesrepo;

import com.google.gson.Gson;
import edu.eci.arsw.api.primesrepo.model.FoundPrime;
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
@SpringBootTest(classes = {PrimesRepoApplication.class})
@AutoConfigureMockMvc
public class PrimesRepoApplicationTests {

    @Autowired
    private MockMvc mvc;

    private final Gson gson = new Gson();

    @Test
    public void shouldGetAllPrimes() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/primes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldGetAPrime() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/primes/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldNotGetAPrime() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/primes/20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateANewPrime() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/primes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(new FoundPrime("wal", "5")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldNotCreateAWrongPrime() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/primes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson("7"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotCreateAExistingPrime() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/primes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(new FoundPrime("wal", "2")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

}
