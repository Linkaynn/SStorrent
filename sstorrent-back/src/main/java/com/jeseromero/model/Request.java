package com.jeseromero.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Request")
@Table(name = "request")
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "message", nullable = false)
	private String message;

	public Request() {}

	public Request(String name, String email, String message) {
		this.name = name;
		this.email = email;
		this.message = message;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getMessage() {
		return message;
	}
}
