package com.example.smashpractice.models;

public class Note {

    String used;
    String fought;
    String result;
    String text;

    public Note () {

    }

    public Note(String used, String fought, String result, String text) {
        this.used = used;
        this.fought = fought;
        this.result = result;
        this.text = text;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getFought() {
        return fought;
    }

    public void setFought(String fought) {
        this.fought = fought;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
