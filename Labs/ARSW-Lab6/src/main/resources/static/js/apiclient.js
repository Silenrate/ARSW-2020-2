api = (function () {


    function getFunctionsByCinema(cinema_name, callback) {
        //http://localhost:8080/cinemas/{nombre}
        $.getJSON("http://localhost:8080/cinemas/" + cinema_name, function (data) {
            callback(data);
        });
    }

    function getFunctionsByCinemaAndDate(cinema_name, fdate, callback) {
        //http://localhost:8080/cinemas/{nombre}/{fecha}
        $.getJSON("http://localhost:8080/cinemas/" + cinema_name + "/" + fdate, function (data) {
            callback(data);
        });
    }

    function getFunctionByNameAndDate(cinema_name, fdate, movie_name, callback) {
        //http://localhost:8080/cinemas//{nombre}/{fecha}/{nombrePelicula}
        $.getJSON("http://localhost:8080/cinemas/" + cinema_name + "/" + fdate + "/" + movie_name, function (data) {
            callback(data);
        });
    }

    function modifyFunction(nombreDelCine, nuevaFuncion, callback) {
        var cinemaFunction = JSON.stringify(nuevaFuncion);

        const promise = new Promise((resolve, reject) => {
            $.ajax({
                url: "http://localhost:8080/cinemas/" + nombreDelCine,
                type: 'PUT',
                data: cinemaFunction,
                contentType: "application/json"
            }).done(function () {
                resolve('SUCCESS');

            }).fail(function (msg) {
                reject('FAIL');
            });
        });

        promise
            .then(res => {
                callback();
            })
            .catch(error => {
                alert(error);
            });

    }

    function createFunction(nombreDelCine, nuevaFuncion, callback){
        var cinemaFunction = JSON.stringify(nuevaFuncion);

        const promise = new Promise((resolve, reject) => {
            $.ajax({
                url: "http://localhost:8080/cinemas/" + nombreDelCine,
                type: 'POST',
                data: cinemaFunction,
                contentType: "application/json"
            }).done(function () {
                resolve('SUCCESS');

            }).fail(function (msg) {
                reject('FAIL');
            });
        });

        promise
            .then(res => {
                callback();
            })
            .catch(error => {
                alert(error);
            });
    }

    function deleteFunction(nombreDelCine, nuevaFuncion, callback){
        var cinemaFunction = JSON.stringify(nuevaFuncion);

        const promise = new Promise((resolve, reject) => {
            $.ajax({
                url: "http://localhost:8080/cinemas/" + nombreDelCine,
                type: 'DELETE',
                data: cinemaFunction,
                contentType: "application/json"
            }).done(function () {
                resolve('SUCCESS');

            }).fail(function (msg) {
                reject('FAIL');
            });
        });

        promise
            .then(res => {
                callback();
            })
            .catch(error => {
                alert(error);
            });
    }

    return {
        getFunctionsByCinema: getFunctionsByCinema,
        getFunctionsByCinemaAndDate: getFunctionsByCinemaAndDate,
        getFunctionByNameAndDate: getFunctionByNameAndDate,
        modifyFunction: modifyFunction,
        createFunction: createFunction,
        deleteFunction: deleteFunction
    }

})();

