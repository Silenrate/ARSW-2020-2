document.addEventListener('DOMContentLoaded', function () {
  if (document.querySelectorAll('#map').length > 0){
    if (document.querySelector('html').lang)
      lang = document.querySelector('html').lang;
    else
      lang = 'en';

    var js_file = document.createElement('script');
    js_file.type = 'text/javascript';
    js_file.src = 'https://maps.googleapis.com/maps/api/js?&signed_in=true&language=' + lang;
    document.getElementsByTagName('head')[0].appendChild(js_file);
  }
});
var app = (function () {
    let imageUrl = "https://openweathermap.org/img/wn/";
    function buscar(name){
        clearData();
        clearMap();
        if(name==null || name==""){
            alertError("The Place is Obligatory");
        }else{
            apiclient.buscar(name,updateDataAndConnect,alertError);
        }
    }
    function updateDataAndConnect(data,name){
        updateData(data);
        apiclient.initMap(name);
    }
    function updateData(data){
        $("#place").text(data.city+", "+data.country);
        $("#icon").attr("src",imageUrl+data.image+"@2x.png");
        $("#temp").text(data.temperature+"°C");
        $("#thermal").text("Feels like "+data.thermalSensation+"°C");
        $("#speed").text("Wind Speed: "+data.windSpeed+" m/s");
        $("#pressure").text("Pressure: "+data.pressure+" hPa");
        $("#humidity").text("Humidity: "+data.humidity+"%");
    }
    function clearData(){
        $("#place").text("");
        $("#icon").attr("src","/images/rain.png");
        $("#temp").text("0°C");
        $("#thermal").text("Feels like 0°C");
        $("#speed").text("Wind Speed: 0 m/s");
        $("#pressure").text("Pressure: 0 hPa");
        $("#humidity").text("Humidity: 0%");
    }
    var map;
    var markers;
    var bounds;

    function plotMarkers(m){
        console.log(m);
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: -34.397, lng: 150.644},
            zoom: 8
        });
        markers = [];
        bounds = new google.maps.LatLngBounds();
        var position = new google.maps.LatLng(m.latitude, m.longitude);
        markers.push(
            new google.maps.Marker({
                position: position,
                map: map,
                animation: google.maps.Animation.DROP
            })
        );
        bounds.extend(position);
        map.fitBounds(bounds);
    }
    function clearMap(){
        if (markers){
            markers.forEach(function (marker) {
                marker.setMap(null);
            });
        }
    }

    function alertError(message){
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: message,
        })
    }

    return {
        buscar:buscar,
        plotMarkers:plotMarkers,
        updateData:updateData
    };
})();