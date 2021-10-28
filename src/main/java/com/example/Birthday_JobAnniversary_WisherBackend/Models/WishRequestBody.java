package com.example.Birthday_JobAnniversary_WisherBackend.Models;

public class WishRequestBody {

    private int fromID;
    private String subject;
    private String message;

    public int getFromID() {
        return fromID;
    }

    public void setFromID(int fromID) {
        this.fromID = fromID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
