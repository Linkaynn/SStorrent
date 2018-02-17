package com.jeseromero.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Log")
@Table(name = "log")
public class Log implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "message")
    private String message;

	@Column(name = "date")
	private Date date;

    public Log() {

    }
    public Log(String username, String message, Date date){
        this.username = username;
        this.message = message;
	    this.date = date;
    }

}
