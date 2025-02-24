package com.f5.factoria_musical.model;

public class Recording {

    private int id;
    private String audioData;
    private String date;
    private String duration;

    public Recording () {

    }

    public Recording
    public Grabacion(int id, String audioData, String date, String duration) {
        this.id = id;
        this.audioData = audioData;
        this.date = date;
        this.duration = duration;

    }
    public int getId() {
        return id;
    }

    public String getAudioData() {
        return audioData;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setAudioData(String audioData) {
        this.audioData = audioData;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDetails () {
        return "Recording{" +
                "id=" + id +
                ", audioData='" + audioData + '\'' +
                ", date='" + date + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

}

    


