app = (function () {

    var cine;
    var fecha;
    var data;
    var modulo = "js/apimock.js";

    function cambiarNombre() {
        cine = nuevoNombre;
    }

    function cambiarFecha() {
        fecha = nuevaFecha;
    }

    function getFunctionsByCinemaAndDate() {
        cine = $("#nombre").val();
        fecha = $("#fecha").val();
        $.getScript(modulo, function(){
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
        if(datos.length!=0){$("#nombreCine").text("Cinema selected: "+cine);}
        var tabla = $("table");
        var body = $("tbody");
        if (body != null) {
            body.remove();
        }
        tabla.append("<tbody>");
        var tblBody = $("tbody");
        datos.map(function (movie) {
            var onclick = "app.getDetallesDeUnaFuncion(\""+movie.movieName+"\",\""+fecha+"\",\""+movie.hour+"\")";
            var boton = "<input type='button' class='show' value='Open Seats' onclick=" + onclick + "></input>";
            var fila = '<tr><td>' + movie.movieName + '</td><td>' + movie.genre + '</td><td>' + movie.hour + '</td><td>' + boton + '</tr>';
            tblBody.append(fila);
        })
        tabla.append(tblBody);
        tabla.append("</tbody>");
    }


    function getDetallesDeUnaFuncion(nombrePelicula,fecha,hora) {
        cine = $("#nombre").val();
        var date = fecha.concat(" ",hora);
        $("#moviename").text("Availability of: "+nombrePelicula);
        $.getScript(modulo, function(){
            api.getFunctionByNameAndDate(cine,date,nombrePelicula,actualizarCanvas);
        });
    }

    function actualizarCanvas(datosAPIMock) {
        clearBoard();
        var asientos = datosAPIMock.seats;
        var c = document.getElementById("canvas");
        var ctx = c.getContext("2d");

        ctx.fillStyle = "#1d52ea";
        ctx.fillRect(125, 20 , 450, 50);
        ctx.beginPath();
        var a = document.getElementById("canvas");

        var atx = a.getContext("2d");
        for (var i = 0; i < asientos[0].length; i++) {
            for (var j = 0; j < asientos.length; j++) {
                atx.fillStyle = "#b0acac";
                if(asientos[j][i] == false){
                    atx.fillStyle = "#e22727";
                }
                atx.fillRect(i*55 + 25, j*55 +120 , 40, 40);
            }
        }
    }

    function clearBoard(){
        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.beginPath();
    }


    return {
        cambiarNombre: cambiarNombre,
        cambiarFecha: cambiarFecha,
        getFunctionsByCinemaAndDate: getFunctionsByCinemaAndDate,
        getDetallesDeUnaFuncion: getDetallesDeUnaFuncion
    }

})();
