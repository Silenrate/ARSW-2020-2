var apiclient = (function () {
    let url="https://walterosweather.herokuapp.com/weather/";
    //let url="http://localhost:8080/weather/";
    function buscar(name,callback,errorCallback){
        //const axios = require('axios').default;
        axios({
            method: "get",
            url: url+name,
        })
            .then(response => {callback(response.data,name);})
            .catch(error => {errorCallback("No existen datos con el nombre "+name);})
    }
    function initMap(name){
        fetch(url+name)
            .then(function(response){return response.json()})
            .then(app.plotMarkers);
    }

    return {
        buscar:buscar,
        initMap:initMap
    };
})();
