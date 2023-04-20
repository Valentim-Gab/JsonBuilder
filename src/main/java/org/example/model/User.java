package org.example.model;

import org.example.decorators.Json;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String cpf;
    private String email;
    private List<String> tasks = new ArrayList<String>();
    private List<Number> phones = new ArrayList<Number>();
    private Address address;
    private List<User> family = new ArrayList<>();

    public User() {}
    public User(int id, String name, String CPF, String email) {
        this.id = id;
        this.name = name;
        this.cpf = CPF;
        this.email = email;
    }

    @Json
    public LocalDate getDate() {
        return LocalDate.now();
    }

    public String test() {
        return "ERROR";
    }

    @Json
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Json
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Json
    public String getCpf() {
        return cpf;
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
    }

    @Json
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Json
    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    public void addTasks(String task) {
        this.tasks.add(task);
    }

    @Json
    public List<Number> getPhones() {
        return phones;
    }

    public void setPhones(List<Number> phones) {
        this.phones = phones;
    }

    @Json
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Json
    public List<User> getFamily() {
        return family;
    }
    
    public void setFamily(List<User> family) {
        this.family = family;
    }
}
