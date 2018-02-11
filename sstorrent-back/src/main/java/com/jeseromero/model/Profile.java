package com.jeseromero.model;

import com.jeseromero.model.lightweight.JSONable;

import java.util.Set;

public class Profile extends JSONable{
    private Set<String> searches;

    public void setSearches(Set<String> searches) {
        this.searches = searches;
    }
}
