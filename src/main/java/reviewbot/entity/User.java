/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by jtidwell on 4/20/2015.
 */
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Size(min = 1, max=40)
    private String username;

    @Size(min = 1, max=40)
    private String password;

    @Size(min = 1, max=40)
    private String forename;

    @Size(min = 1, max=40)
    private String surname;

    //@OneToMany(fetch = FetchType.EAGER)
    //private List<Book> books;

    @Column(name = "admin", columnDefinition="bit")
    private Integer admin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

}
