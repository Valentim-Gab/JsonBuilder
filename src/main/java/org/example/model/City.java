package org.example.model;

import java.util.ArrayList;
import java.util.List;

import org.example.decorators.Json;

public class City {
    private int cod;
    private String name;
    private String state;
    private String Country;
    private List<String> beaches = new ArrayList<>();

    @Json
    public List<String> getBeaches() {
        return beaches;
    }

    public void setBeaches(List<String> beaches) {
        this.beaches = beaches;
    }

    @Json
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    @Json
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Json
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Json
    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
