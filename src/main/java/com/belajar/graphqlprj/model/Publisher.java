package com.belajar.graphqlprj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Publisher {

    @Id
    private Integer id;

    private String publisherName;
    private String city;

    public Publisher(Integer id, String publisherName, String city) {
        this.id = id;
        this.publisherName = publisherName;
        this.city = city;
    }

    public Publisher() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}