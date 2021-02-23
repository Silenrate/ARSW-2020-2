package edu.eci.arsw.openweather.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad con la información del clima
 */
public class WeatherByCity {
    private String city;
    private String country;
    private String image;
    private double temperature;
    private double thermalSensation;
    private double windSpeed;
    private double longitude;
    private double latitude;
    private int pressure;
    private int humidity;
    @JsonIgnore
    private LocalDateTime time;

    /**
     * Constructor por defecto de la clase WeatherByCity
     */
    public WeatherByCity() {
    }

    /**
     * Constructor extenso de la clase WeatherByCity
     *
     * @param city             Ciudad de la información
     * @param country          País de la información
     * @param temperature      Temperatura del lugar
     * @param thermalSensation Temperatura de la sensación térmica del lugar
     * @param windSpeed        Velocidad del Viento del lugar
     * @param pressure         Presión atmosférica del lugar
     * @param humidity         Nivel de Humedad del lugar
     */
    public WeatherByCity(String city, String country, double temperature, double thermalSensation, double windSpeed, int pressure, int humidity) {
        this.city = city;
        this.country = country;
        this.temperature = temperature;
        this.thermalSensation = thermalSensation;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getThermalSensation() {
        return thermalSensation;
    }

    public void setThermalSensation(double thermalSensation) {
        this.thermalSensation = thermalSensation;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "WeatherByCity{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", image='" + image + '\'' +
                ", temperature=" + temperature +
                ", thermalSensation=" + thermalSensation +
                ", windSpeed=" + windSpeed +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherByCity weather = (WeatherByCity) o;
        return Double.compare(weather.temperature, temperature) == 0 &&
                Double.compare(weather.thermalSensation, thermalSensation) == 0 &&
                Double.compare(weather.windSpeed, windSpeed) == 0 &&
                Double.compare(weather.longitude, longitude) == 0 &&
                Double.compare(weather.latitude, latitude) == 0 &&
                pressure == weather.pressure &&
                humidity == weather.humidity &&
                Objects.equals(city, weather.city) &&
                Objects.equals(country, weather.country) &&
                Objects.equals(image, weather.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country, image, temperature, thermalSensation, windSpeed, longitude, latitude, pressure, humidity);
    }
}
