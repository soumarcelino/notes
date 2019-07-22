package com.m.notas.models;

import java.util.Calendar;
import java.util.Date;

public class Note {
    private String name;
    private String description;
    private Date creationDate;
    private Date lastUpdate;
    public Note(String name, String description){
        this.name = name;
        this.description = description;
        this.creationDate = Calendar.getInstance().getTime();
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
