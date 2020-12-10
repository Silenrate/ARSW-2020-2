import edu.eci.arsw.cinema.filters.AvailabilityFilter;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.services.CinemaException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AvailabilityFilterTest implements FilterTest{

    private AvailabilityFilter availabilityFilter;

    @Before
    public void setUp() {
        availabilityFilter = new AvailabilityFilter();
    }

    @Test
    public void shouldNotFilterByAvailabilityWithANonNumberFilter() {
        List<CinemaFunction> functions = getFunctions(1);
        try {
            availabilityFilter.filtrar(functions, "Action");
            fail("Debió fallar por usar un filtro que no es un número");
        } catch (CinemaException e) {
            assertEquals("Parámetro invalido. Ingrese un numero entero", e.getMessage());
        }
    }

    @Test
    public void shouldNotFilterByAvailabilityWithANullParameter() {
        try {
            availabilityFilter.filtrar(null, null);
            fail("Debió fallar por usar un filtro nulo");
        } catch (CinemaException e) {
            assertEquals("La lista de funciones ni el filtro pueden ser nulos", e.getMessage());
        }
        try {
            availabilityFilter.filtrar(null, "2");
            fail("Debió fallar por usar una lista nula");
        } catch (CinemaException e) {
            assertEquals("La lista de funciones ni el filtro pueden ser nulos", e.getMessage());
        }
        List<CinemaFunction> functions = getFunctions(3);
        try {
            availabilityFilter.filtrar(functions, null);
            fail("Debió fallar por usar un filtro nulo");
        } catch (CinemaException e) {
            assertEquals("La lista de funciones ni el filtro pueden ser nulos", e.getMessage());
        }
    }

    @Test
    public void shouldNotFilterByAvailabilityWithANegativeNumber() {
        List<CinemaFunction> functions = getFunctions(1);
        try {
            availabilityFilter.filtrar(functions, "-1");
            fail("Debió fallar por usar un filtro negativo");
        } catch (CinemaException e) {
            assertEquals("El valor del filtro no puede ser un número negativo", e.getMessage());
        }
    }

    @Test
    public void shouldNotFilterByAvailabilityWithANumberGreaterThanTheCinemaMaximumCapacity() {
        List<CinemaFunction> functions = getFunctions(2);
        try {
            availabilityFilter.filtrar(functions, "1000");
            fail("Debió fallar por usar un filtro muy grande");
        } catch (CinemaException e) {
            assertEquals("No existen funciones con ese numero de asientos disponibles.", e.getMessage());
        }
    }

    @Test
    public void shouldNotFilterByAvailabilityWithAnEmptyListOfFunctions() {
        List<CinemaFunction> functions = new ArrayList<>();
        try {
            availabilityFilter.filtrar(functions, "5");
            fail("Debió fallar por usar una lista vacía");
        } catch (CinemaException e) {
            assertEquals("La lista de funciones está vacía", e.getMessage());
        }
    }

    @Test
    public void shouldFilterByAvailability() throws Exception {
        int numberOfFunctions = 5; //Debe ser mayor que 1
        List<CinemaFunction> functions = getFunctions(numberOfFunctions);
        CinemaFunction function1 = functions.get(0);
        for (int i = 0; i < CinemaFunction.NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < CinemaFunction.NUMBER_OF_COLUMNS; j++) {
                function1.buyTicket(i, j);
            }
        }
        List<CinemaFunction> cinemaFilteredFunctions = availabilityFilter.filtrar(functions, "1");
        for (CinemaFunction cinemaFunction : cinemaFilteredFunctions) {
            if (function1.equals(cinemaFunction)) {
                fail("La primera función no debería estar en la lista filtrada por capacidad");
            }
        }
        assertEquals(numberOfFunctions, cinemaFilteredFunctions.size() + 1);
    }
}
