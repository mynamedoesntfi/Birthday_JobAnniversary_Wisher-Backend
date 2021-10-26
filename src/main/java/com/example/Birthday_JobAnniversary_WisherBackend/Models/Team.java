package com.example.Birthday_JobAnniversary_WisherBackend.Models;

import javax.persistence.*;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "team_ID")
    private int teamID;

    @Column(name = "team_name")
    private String teamname;

    @Column(name = "description")
    private String description;

    public Team() {
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
