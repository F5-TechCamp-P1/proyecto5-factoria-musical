package com.f5.factoria_musical.model;

public class Piano {
    private int id;
    private float volume;
    private String soundType;
    private String configuration; // Stores configuration as a string

    // Constructor
    public Piano(int id, float volume, String soundType) {
        this.id = id;
        this.volume = volume;
        this.soundType = soundType;
        this.configuration = "Default"; // Default configuration
    }

    // Getters
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

    // Setters
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

    // Method to display piano details
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

