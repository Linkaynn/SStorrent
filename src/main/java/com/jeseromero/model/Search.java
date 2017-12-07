package com.jeseromero.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Search")
@Table(name = "Search")
public class Search implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "search", nullable = false)
    private String search;

    public Search() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

}
