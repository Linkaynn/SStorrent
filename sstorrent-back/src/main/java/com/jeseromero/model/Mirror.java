package com.jeseromero.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Mirror")
@Table(name = "mirror")
public class Mirror extends SModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "language")
    private String language;

    @Column(name = "working")
    private boolean working;

    // Yes, I know that is an anti-pattern, but men, its just a few users.
    // https://vladmihalcea.com/2016/09/13/the-best-way-to-handle-the-lazyinitializationexception/
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "mirrors")
    private Set<User> users;

    public Mirror() {}

    public Mirror(String name, String language, boolean working) {
        this.name = name;
        this.language = language;
        this.working = working;
    }

    public String getName() {
        return name;
    }

	public String getLanguage() {
		return language;
	}

	public boolean isWorking() {
        return working;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if (o.getClass() == String.class) {
            return ((String) o).equals(this.name);
        }

        if (getClass() != o.getClass()) return false;

        Mirror mirror = (Mirror) o;
        return id == mirror.id && Objects.equals(name, mirror.name);

    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, working, users);
    }
}
