package com.alissonpedrina.cli.domain;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private String location;
    private String name;
    private List<Recipe> recipes;

    public Project(String name) {
        this.name = name;
        this.recipes = new ArrayList<>();

    }

    public Project(){
        this.recipes = new ArrayList<>();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
