package com.chemwater.week6day1hw;

public class Celebrities {
    private String name ;
    private String actor ;
    private String musician ;
    private String politician ;


    public Celebrities() {
    }

    public Celebrities(String name, String actor, String musician, String politician) {
        this.name = name ;
        this.actor = actor;
        this.musician = musician;
        this.politician = politician;
    }

    public String getName() {
        return name ;
    }

    public void setName(String name) {
        this.name = name ;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getMusician() {
        return musician;
    }

    public void setMusician(String musician) {
        this.musician = musician;
    }

    public String getPolitician() {
        return politician;
    }

    public void setPolitician(String politician) {
        this.politician = politician;
    }

    @Override
    public String toString() {
        return "Celebrities{" +
                "name='" + name + '\'' +
                ", actor='" + actor + '\'' +
                ", musician='" + musician + '\'' +
                ", politician='" + politician + '\'' +
                '}';
    }
}
