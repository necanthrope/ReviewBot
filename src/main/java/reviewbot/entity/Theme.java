/*
 * Copyright (c) 2015. ReviewBot by Jeremy Tidwell is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * Based on a work at https://github.com/necanthrope/ReviewBot.
 */

package reviewbot.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by jtidwell on 4/7/2015.
 */
@Entity
@Table(name="themes")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer theme;

    @Size(min = 1, max=30)
    private String name;

    @Column(name = "description", columnDefinition="text" )
    private String description;


    public Integer getTheme() {
        return theme;
    }

    public void setTheme(Integer theme) {
        this.theme = theme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
