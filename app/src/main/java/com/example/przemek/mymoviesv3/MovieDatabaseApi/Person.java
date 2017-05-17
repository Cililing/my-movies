package com.example.przemek.mymoviesv3.MovieDatabaseApi;


public class Person {

    private int id = 0;
    private String name = "name";
    private String character = "character";
    private String profilePath = "profilePath";

    public Person(int id, String name, String character, String profilePath) {
        this.id = id;
        this.name = name;
        this.character = character;
        this.profilePath = profilePath;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getProfilePath() {
        return profilePath.equals("null")
                ? "null"
                : ApiParameters.defaultImageRequest + profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
