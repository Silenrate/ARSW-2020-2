import edu.eci.arsw.cinema.filters.GenderFilter;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.services.CinemaException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GenderFilterTest implements FilterTest {

    private GenderFilter genderFilter;

    @Before
    public void setUp() {
        genderFilter = new GenderFilter();
    }

    @Test
    public void shouldNotFilterByGenderWithANullParameter() {
        try {
            genderFilter.filtrar(null, null);
            fail("Debió fallar por usar un filtro nulo");
        } catch (CinemaException e) {
            assertEquals("La lista de funciones ni el filtro pueden ser nulos", e.getMessage());
        }
        try {
            genderFilter.filtrar(null, "Action");
            fail("Debió fallar por usar una lista nula");
        } catch (CinemaException e) {
            assertEquals("La lista de funciones ni el filtro pueden ser nulos", e.getMessage());
        }
        List<CinemaFunction> functions = getFunctions(3);
        try {
            genderFilter.filtrar(functions, null);
            fail("Debió fallar por usar un filtro nulo");
        } catch (CinemaException e) {
            assertEquals("La lista de funciones ni el filtro pueden ser nulos", e.getMessage());
        }
    }

    @Test
    public void shouldNotFilterByGenderWithAnEmptyListOfFunctions() {
        List<CinemaFunction> functions = new ArrayList<>();
        try {
            genderFilter.filtrar(functions, "Horror");
            fail("Debió fallar por usar una lista vacía");
        } catch (CinemaException e) {
            assertEquals("La lista de funciones está vacía", e.getMessage());
        }
    }

    @Test
    public void shouldNotFilterByGenderWithANonExistingGender() {
        List<CinemaFunction> functions = getFunctions(1);
        try {
            genderFilter.filtrar(functions, "Galleta");
            fail("Debió fallar por usar un filtro que no es un genero");
        } catch (CinemaException e) {
            assertEquals("No existen funciones con ese genero", e.getMessage());
        }
    }

    @Test
    public void shouldFilterByGender() throws CinemaException {
        int numberOfFunctions = 5;
        String gender = "Action"; //Debe ser Acción o Drama (En inglés)
        List<CinemaFunction> functions = getFunctions(numberOfFunctions);
        List<CinemaFunction> cinemaFilteredFunctions = genderFilter.filtrar(functions, gender);
        for (CinemaFunction cinemaFunction : cinemaFilteredFunctions) {
            Movie movie = cinemaFunction.getMovie();
            if (!movie.getGenre().equals(gender)) {
                fail("Ninguna función debería tener un género diferente a " + gender);
            }
        }
        assertEquals(numberOfFunctions, 2 * cinemaFilteredFunctions.size(), 1); //El delta de 1 es por si el número de funciones es impar, si es par puede ser 0.
    }
}
