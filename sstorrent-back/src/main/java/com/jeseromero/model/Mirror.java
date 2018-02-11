package com.jeseromero.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Mirror")
@Table(name = "mirror")
public class Mirror implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "collapsed_name")
    private String collapsedName;

    @Column(name = "working")
    private boolean working;

    // Yes, I know that is an anti-pattern, but men, its just a few users.
    // https://vladmihalcea.com/2016/09/13/the-best-way-to-handle-the-lazyinitializationexception/
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "mirrors")
    private Set<User> users;

    public Mirror() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollapsedName() {
        return collapsedName;
    }

    public void setCollapsedName(String collapsedName) {
        this.collapsedName = collapsedName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean isWorking() {
        return working;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if (o.getClass() == String.class) {
            return ((String) o).equals(this.collapsedName);
        }

        if (getClass() != o.getClass()) return false;

        Mirror mirror = (Mirror) o;
        return id == mirror.id && Objects.equals(collapsedName, mirror.collapsedName);

    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, collapsedName, working, users);
    }
}
