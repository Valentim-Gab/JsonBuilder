package org.example.model;

import java.util.ArrayList;
import java.util.List;

import org.example.decorators.Json;

public class Address {
    private List<Number> num = new ArrayList<>();
    private String street;
    private String neighborhood;
    private City city;

    @Json
    public List<Number> getNum() {
        return num;
    }

    public void setNum(List<Number> num) {
        this.num = num;
    }

    @Json
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Json
    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    @Json
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
