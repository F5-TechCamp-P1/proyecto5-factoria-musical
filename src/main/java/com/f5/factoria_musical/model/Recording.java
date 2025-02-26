package com.f5.factoria_musical.model;

public class Recording {

    private int id;
    private byte [] audioData;
    private String recordingDate;
    private int duration;

    public Recording() {

    }

    public Recording(int id, byte[] audioData, String recordingDate, int duration) {
        this.id = id;
        this.audioData = audioData;
        this.recordingDate = recordingDate;
        this.duration = duration;

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

    public String getDetails() {
        return "Recording{" +
                "id=" + id +
                ", audioData='" + audioData + '\'' +
                ", date='" + recordingDate + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

}
