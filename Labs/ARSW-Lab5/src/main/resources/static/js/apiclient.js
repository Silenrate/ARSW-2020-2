api = (function () {


    function getFunctionsByCinema(cinema_name, callback) {
        //http://localhost:8080/cinemas/{nombre}
        $.getJSON("http://localhost:8080/cinemas/" + cinema_name, function (data) {
            callback(data);
        });
    }

    function getFunctionsByCinemaAndDate(cinema_name, fdate, callback) {
        //http://localhost:8080/cinemas/{nombre}/{fecha}
        $.getJSON("http://localhost:8080/cinemas/" + cinema_name +"/"+ fdate, function (data) {
            callback(data);
        });
    }

    function getFunctionByNameAndDate(cinema_name, fdate, movie_name, callback) {
        //http://localhost:8080/cinemas//{nombre}/{fecha}/{nombrePelicula}
        $.getJSON("http://localhost:8080/cinemas/"+cinema_name+"/"+ fdate+"/"+movie_name, function (data) {
            callback(data);
        });
    }

    return {
        getFunctionsByCinema: getFunctionsByCinema,
        getFunctionsByCinemaAndDate: getFunctionsByCinemaAndDate,
        getFunctionByNameAndDate: getFunctionByNameAndDate
    }

})();

