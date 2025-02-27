package com.f5.factoria_musical.model;

import java.util.Base64;

public class Recording {

    private int id;
    private byte[] audioData;
    private String recordingDate; 
    private int duration;
    private String title;              
    private String pianoConfiguration; 

    public Recording() {
    }

    public Recording(int id, byte[] audioData, String recordingDate, int duration, String title, String pianoConfiguration) {
        this.id = id;
        this.audioData = audioData;
        this.recordingDate = recordingDate;
        this.duration = duration;
        this.title = title;
        this.pianoConfiguration = pianoConfiguration;
    }

    public int getId() {
        return id;
    }

    public byte[] getAudioData() {
        return audioData;
    }

    public String getRecordingDate() {
        return recordingDate;
    }

    public int getDuration() {
        return duration;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getPianoConfiguration() {
        return pianoConfiguration;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setAudioData(byte[] audioData) {
        this.audioData = audioData;
    }

    public void setRecordingDate(String recordingDate) {
        this.recordingDate = recordingDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setPianoConfiguration(String pianoConfiguration) {
        this.pianoConfiguration = pianoConfiguration;
    }

  
    public String getDetails() {
        return "Recording{" +
                "id=" + id +
                ", audioData='" + Base64.getEncoder().encodeToString(audioData) + '\'' +
                ", recordingDate='" + recordingDate + '\'' +
                ", duration=" + duration +
                ", title='" + title + '\'' +
                ", pianoConfiguration='" + pianoConfiguration + '\'' +
                '}';
    }
}
