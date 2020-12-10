app = (function () {

    let cine;
    let fecha;
    let data;
    let modulo = "js/apiclient.js";
    let stompClient = null;
    let dataFunction;
    let seats = [[true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true], [true, true, true, true, true, true, true, true, true, true, true, true]];
    let horaPelicula;
    let firstBuy = false;
    let concatenacion;

    class Seat {
        constructor(row, col) {
            this.row = row;
            this.col = col;
        }
    }

    function buyTicket() {
        var row = $("#row").val();
        var column = $("#column").val();
        console.info("buying ticket at row: " + row + " col: " + column);
        verifyAvailability(row, column);
    }

    var connectAndSubscribe = function (identificador, callback) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);

        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/app/buyticket.' + identificador, function (message) {
                callback(message);
            });
        });

        stompClient.connect({}, function (frame) {
                    console.log('Connected: ' + frame);
                    stompClient.subscribe('/topic/buyticket.' + identificador, function (message) {
                        callback(message);
                    });
                });
    };

    function recibirEvento(evento) {
        var theObject = JSON.parse(evento.body);
        llenarAsiento(theObject.row, theObject.col);

    }

    var verifyAvailability = function (row, col) {
        var st = new Seat(row, col);
        var seats = dataFunction.seats;

        if (seats[row][col] === true) {
            seats[row][col] = false;
            Swal.fire(
                'Enhorabuena',
                'Tiquete comprado',
                'success'
            )
            stompClient.send("/app/buyticket." + concatenacion, {}, JSON.stringify(st));

        } else {
            console.info("Ticket not available");
        }

    };


    function getMousePosition() {
        if (dataFunction == null) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'No has seleccionado ninguna función!',
            })
        } else {
            if (!firstBuy) {
                firstBuy = true;
                Swal.fire(
                    'Ya puedes comprar tiquetes',
                    'Selecciona los que deseas comprar',
                    'success'
                )
            }
            $('#canvas').click(function (e) {
                var rect = canvas.getBoundingClientRect();
                var x = e.clientX - rect.left;
                var y = e.clientY - rect.top;
                validarSiEsUnAsiento(x, y);
            });
        }

    };

    function validarSiEsUnAsiento(x, y) {
        var c = document.getElementById("canvas");
        var ctx = c.getContext("2d");
        const pixel = ctx.getImageData(x, y, 1, 1).data;
        if (!(pixel[0] == 0 && pixel[1] == 153 && pixel[2] == 0 && pixel[3] == 255)) { //Si a lo que doy click no es verde
            if (pixel[0] == 255 && pixel[1] == 0 && pixel[2] == 0) {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Este asiento ya está ocupado',
                })
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Debes hacer click en un asiento!!',
                })
            }
        } else {

            calcularAsiento(x, y);
        }
    }

    function calcularAsiento(x, y) {
        var row;
        var col;
        var limiteX1;
        var limiteY1;
        var limiteX2;
        var limiteY2
        for (var i = 0; i < 12; i++) {
            for (var j = 0; j < 7; j++) {
                limiteX1 = i * 55 + 25;
                limiteY1 = j * 55 + 120;
                limiteX2 = limiteX1 + 40;
                limiteY2 = limiteY1 + 40;
                if (x >= limiteX1 && x <= limiteX2 && y >= limiteY1 && y <= limiteY2) {
                    row = j;
                    col = i;
                }
            }
        }
        verifyAvailability(row, col);
    }

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
        concatenacion = cine + "." + fecha + "." + nombrePelicula;
        connectAndSubscribe(concatenacion, recibirEvento);
    }

    function llenarAsiento(fila, columna) {
        var c = document.getElementById("canvas");
        var ctx = c.getContext("2d");
        ctx.fillStyle = "#ff0000";
        ctx.fillRect((columna) * 55 + 25, (fila) * 55 + 120, 40, 40);
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
                atx.fillStyle = "#009900";
                if (asientos[j][i] == false) {
                    atx.fillStyle = "#ff0000";
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

    function deleteFunction() {
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
        deleteFunction: deleteFunction,
        buyTicket: buyTicket,
        getMousePosition: getMousePosition
    }

})();
