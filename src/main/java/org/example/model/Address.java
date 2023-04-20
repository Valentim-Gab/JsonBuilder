package org.example.model;

import org.example.decorators.Json;

public class Address {
    private int num;
    private String street;
    private String neighborhood;
    private City city;

    @Json
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
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
