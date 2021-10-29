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
    private int new_team_ID;

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

    public int getNew_team_ID() {
        return new_team_ID;
    }

    public void setNew_team_ID(int new_team_ID) {
        this.new_team_ID = new_team_ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
