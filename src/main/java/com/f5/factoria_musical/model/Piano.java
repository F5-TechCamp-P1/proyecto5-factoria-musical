package com.f5.factoria_musical.model;

public class Piano {
    private int id;
    private float volume;
    private String soundType;
    private String configuration; 

    
    public Piano(int id, float volume, String soundType) {
        this.id = id;
        this.volume = volume;
        this.soundType = soundType;
        this.configuration = "Default"; 
    }

    
    public int getId() {
        return id;
    }

    public float getVolume() {
        return volume;
    }

    public String getSoundType() {
        return soundType;
    }

    public String getConfiguration() {
        return configuration;
    }

    
    public void setId(int id) {
        this.id = id;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void setSoundType(String soundType) {
        this.soundType = soundType;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    
    @Override
    public String toString() {
        return "Piano{" +
                "id=" + id +
                ", volume=" + volume +
                ", soundType='" + soundType + '\'' +
                ", configuration='" + configuration + '\'' +
                '}';
    }
}

