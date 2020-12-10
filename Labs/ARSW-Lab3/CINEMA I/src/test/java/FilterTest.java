import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;

import java.util.ArrayList;
import java.util.List;

public interface FilterTest {

    default List<CinemaFunction> getFunctions(int amountOfCinemaFunctions) {
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions = new ArrayList<>();
        for (int i = 0; i < amountOfCinemaFunctions; i++) {
            String gender = (i % 2 == 0) ? "Action" : "Drama";
            CinemaFunction function = new CinemaFunction(new Movie("Movie" + (i + 1), gender), functionDate);
            functions.add(function);
        }
        return functions;
    }
}
