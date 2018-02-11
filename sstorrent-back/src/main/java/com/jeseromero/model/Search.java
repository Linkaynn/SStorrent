package com.jeseromero.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Search")
@Table(name = "search")
public class Search implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "search", nullable = false)
    private String search;

	@Column(name = "user_id", nullable = false)
	private int userId;

	public Search() {}

    public Search(User user, String value) {
    	userId = user.getId();
    	search = value;
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
