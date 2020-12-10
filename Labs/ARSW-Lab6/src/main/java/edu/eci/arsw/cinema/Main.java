//package edu.eci.arsw.cinema;
//
//import edu.eci.arsw.cinema.model.Cinema;
//import edu.eci.arsw.cinema.model.CinemaFunction;
//import edu.eci.arsw.cinema.model.Movie;
//import edu.eci.arsw.cinema.services.CinemaException;
//import edu.eci.arsw.cinema.services.CinemaServices;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.util.ArrayList;
//
//public class Main {
//
//    public static void main(String[] args) throws CinemaException {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        CinemaServices cinemaServices = applicationContext.getBean(CinemaServices.class);
//        Cinema cinema = crearCinema("CineColombia");
//        cinemaServices.addNewCinema(cinema);
//        System.out.println(cinemaServices.getAllCinemas());
//        System.out.println("-------------------------------------------------------------------------------");
//        System.out.println(cinemaServices.getCinemaByName("CineColombia"));
//        System.out.println("-------------------------------------------------------------------------------");
//        for (int i = 0; i < 4; i++) {
//            cinemaServices.buyTicket(0, i, "CineColombia", "2020-08-27 14:30", "Transformers");
//        }
//        System.out.println(cinemaServices.getCinemaByName("CineColombia"));
//        System.out.println("-------------------------------------------------------------------------------");
//        System.out.println(cinemaServices.getFunctionsbyCinemaAndDate("cinemaX", "2018-12-18 15:30"));
//        System.out.println("-------------------------------------------------------------------------------");
//        //System.out.println(cinemaServices.getFilteredFunctions("CineColombia", "2020-08-27 14:30", "81"));
//        System.out.println(cinemaServices.getFilteredFunctions("CineColombia", "2020-08-27 14:30", "Action"));
//    }
//
//    private static Cinema crearCinema(String name) {
//        Movie movie = new Movie("Transformers", "Action");
//        Movie movie1 = new Movie("En busca de la felicidad", "Drama");
//        Movie movie2 = new Movie("Siempre a tu lado", "Drama");
//        ArrayList<CinemaFunction> functions = new ArrayList<>();
//        CinemaFunction cinemaFunction = new CinemaFunction(movie, "2020-08-27 14:30");
//        CinemaFunction cinemaFunction1 = new CinemaFunction(movie1, "2020-08-27 14:30");
//        CinemaFunction cinemaFunction2 = new CinemaFunction(movie2, "2020-08-27 14:30");
//        functions.add(cinemaFunction);
//        functions.add(cinemaFunction1);
//        functions.add(cinemaFunction2);
//        return new Cinema(name, functions);
//    }
//}
