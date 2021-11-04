package com.example.Birthday_JobAnniversary_WisherBackend.Models;

import javax.persistence.*;

@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private int requestID;

    @Column(name = "user_ID")
    private int userID;

    @Column(name = "current_team_ID")
    private int currentTeamID;

    @Column(name = "new_team_ID")
    private int newTeamID;

    @Column(name = "status")
    private String status;

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCurrentTeamID() {
        return currentTeamID;
    }

    public void setCurrentTeamID(int currentTeamID) {
        this.currentTeamID = currentTeamID;
    }

    public int getNewTeamID() {
        return newTeamID;
    }

    public void setNewTeamID(int newTeamID) {
        this.newTeamID = newTeamID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
