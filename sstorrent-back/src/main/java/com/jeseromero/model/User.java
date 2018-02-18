package com.jeseromero.model;

import com.jeseromero.persistence.DBSessionFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "User")
@Table(name = "user")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

	@Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    // Yes, I know that is an anti-pattern, but men, its just a few users.
    // https://vladmihalcea.com/2016/09/13/the-best-way-to-handle-the-lazyinitializationexception/
    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "user_id")
    private Set<Search> searches;

    // Yes, I know that is an anti-pattern, but men, its just a few users.
    // https://vladmihalcea.com/2016/09/13/the-best-way-to-handle-the-lazyinitializationexception/
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_mirror",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "mirror_id")
    )
    private Set<Mirror> mirrors;

    public User() {}

    public User(String username, String name, String email, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Search> getSearches() {
        return searches;
    }

    public void setSearches(Set<Search> searches) {
        this.searches = searches;
    }

    public Set<Mirror> getMirrors() {
        return mirrors;
    }

    public void setMirrors(Set<Mirror> mirrors) {
        this.mirrors = mirrors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (o.getClass() == String.class) {
            return id == Integer.parseInt((String) o);
        }

        if (getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void refresh() {
        DBSessionFactory.openSession().refresh(this);
    }

    public void addSearch(Search search) {
        searches.add(search);
    }

    public boolean isAdmin() {
        return username.equals("root");
    }
}
