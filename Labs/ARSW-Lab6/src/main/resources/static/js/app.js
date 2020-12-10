app = (function () {

    let cine;
    let fecha;
    let data;
    let modulo = "js/apiclient.js";
    let dataFunction;
    let seats = [[true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true]];
    let horaPelicula;

    function cambiarNombre() {
        cine = nuevoNombre;
    }

    function cambiarFecha() {
        fecha = nuevaFecha;
    }

    function getFunctionsByCinemaAndDate() {
        cine = $("#nombre").val();
        fecha = $("#fecha").val();
        $.getScript(modulo, function () {
            api.getFunctionsByCinemaAndDate(cine, fecha, cambiarDatos);
        });
    }

    function cambiarDatos(datosAPIMock) {
        data = datosAPIMock;
        var mapData = data.map(function (val) {
            var hora = val.date.substring(11, 16);
            return {movieName: val.movie.name, genre: val.movie.genre, hour: hora};
        })
        actualizarData(mapData);
    }

    function actualizarData(datos) {
        clearBoard();
        $("#moviename").text("Availability of: ");
        $("#nombreCine").text("Cinema selected:");
        if (datos.length != 0) {
            $("#nombreCine").text("Cinema selected: " + cine);
        }
        var tabla = $("table");
        var body = $("tbody");
        if (body != null) {
            body.remove();
        }
        tabla.append("<tbody>");
        var tblBody = $("tbody");
        datos.map(function (movie) {
            var onclick = "app.getDetallesDeUnaFuncion(\"" + movie.movieName + "\",\"" + fecha + "\",\"" + movie.hour + "\")";
            var boton = "<input type='button' class='show' value='Open Seats' onclick=" + onclick + "></input>";
            var fila = '<tr><td>' + movie.movieName + '</td><td>' + movie.genre + '</td><td>' + movie.hour + '</td><td>' + boton + '</tr>';
            tblBody.append(fila);
        })
        tabla.append(tblBody);
        tabla.append("</tbody>");
    }


    function getDetallesDeUnaFuncion(nombrePelicula, fecha, hora) {
        cine = $("#nombre").val();
        var date = fecha.concat(" ", hora);
        $("#moviename").text("Availability of: " + nombrePelicula);
        $.getScript(modulo, function () {
            api.getFunctionByNameAndDate(cine, date, nombrePelicula, actualizarCanvas);
        });
    }

    function actualizarCanvas(datosAPIMock) {
        clearBoard();
        dataFunction = datosAPIMock;
        horaPelicula = datosAPIMock.date;
        var asientos = datosAPIMock.seats;
        var c = document.getElementById("canvas");
        var ctx = c.getContext("2d");

        ctx.fillStyle = "#1d52ea";
        ctx.fillRect(125, 20, 450, 50);
        ctx.beginPath();
        var a = document.getElementById("canvas");

        var atx = a.getContext("2d");
        for (var i = 0; i < asientos[0].length; i++) {
            for (var j = 0; j < asientos.length; j++) {
                atx.fillStyle = "#b0acac";
                if (asientos[j][i] == false) {
                    atx.fillStyle = "#e22727";
                }
                atx.fillRect(i * 55 + 25, j * 55 + 120, 40, 40);
            }
        }
    }

    function clearBoard() {
        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.beginPath();
    }


    function modifyFunction() {
        var hour = $("#hora").val();
        cine = $("#nombre").val();
        var cinemaFunction = {
            "movie": dataFunction.movie,
            "seats": dataFunction.seats,
            "date": fecha + " " + hour
        };
        $.getScript(modulo, function () {
            api.modifyFunction(cine, cinemaFunction, actualizarPagina);
        });
    }

    function actualizarPagina() {
        clearBoard();
        getFunctionsByCinemaAndDate();
        Swal.fire(
            'Buen trabajo!',
            'Operación exitosa',
            'success'
        )
    }

    function crearLabelsEInputs() {
        clearBoard();
        clearInput();

        var admin = $("#admin");
        var labelMovieName = '<label id="labelMovieName" for="nombre">Movie name:</label>'
        var labelMovieGenre = '<label id="labelMovieGenre" for="nombre">Movie genre:</label>'
        var movieName = '<input type="text" id="nombrePelicula" name="nombrePelicula" placeholder="Movie name">';
        var genre = '<input type="text" id="genre" name="genre" placeholder="Genre">';
        var br1 = '<br id="br1">';
        var br2 = '<br id="br2">';

        admin.append(labelMovieName);
        admin.append(movieName);
        admin.append(br1);
        admin.append(labelMovieGenre);
        admin.append(genre);
        admin.append(br2);

    }

    function clearInput() {
        var labelMovieNameID = $("#labelMovieName");
        var labelMovieGenreID = $("#labelMovieGenre");
        var nombrePeliculaID = $("#nombrePelicula");
        var genreID = $("#genre");
        var br1ID = $("#br1");
        var br2ID = $("#br2");

        if (br1ID != null) {
            br1ID.remove();
        }

        if (br2ID != null) {
            br2ID.remove();
        }

        if (labelMovieNameID != null) {
            labelMovieNameID.remove();
        }

        if (labelMovieGenreID != null) {
            labelMovieGenreID.remove();
        }

        if (nombrePeliculaID != null) {
            nombrePeliculaID.remove();
        }

        if (genreID != null) {
            genreID.remove();
        }
    }

    function saveOrUpdate() {
        var nombrePeliculaID = $("#nombrePelicula").val();
        var genreID = $("#genre").val();
        var hourID = $("#hora").val();

        if (nombrePeliculaID != '' && genreID != '' && hourID != '') {
            createFunction();
        } else if (nombrePeliculaID == null && genreID == null && hourID != null) {
            modifyFunction();
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Algo salió mal. Intentalo de nuevo!',
            })
        }

    }


    function createFunction() {
        var hour = $("#hora").val();
        cine = $("#nombre").val();
        var nombrePeliculaID = $("#nombrePelicula").val();
        var genreID = $("#genre").val();
        var cinemaFunction = {
            "movie": {"name": nombrePeliculaID, "genre": genreID},
            "seats": seats,
            "date": fecha + " " + hour
        };
        $.getScript(modulo, function () {
            api.createFunction(cine, cinemaFunction, actualizarPagina);
        });
        clearInput();
    }

    function deleteFunction(){
        cine = $("#nombre").val();
        var cinemaFunction = {
            "movie": dataFunction.movie,
            "seats": dataFunction.seats,
            "date": horaPelicula
        };
        $.getScript(modulo, function () {
            api.deleteFunction(cine, cinemaFunction, actualizarPagina);
        });
    }


    return {
        cambiarNombre: cambiarNombre,
        cambiarFecha: cambiarFecha,
        getFunctionsByCinemaAndDate: getFunctionsByCinemaAndDate,
        getDetallesDeUnaFuncion: getDetallesDeUnaFuncion,
        crearLabelsEInputs: crearLabelsEInputs,
        saveOrUpdate: saveOrUpdate,
        deleteFunction: deleteFunction
    }

})();
