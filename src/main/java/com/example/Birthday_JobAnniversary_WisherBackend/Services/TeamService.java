package com.example.Birthday_JobAnniversary_WisherBackend.Services;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Team;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.TeamRepository;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TeamService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    /**
     * TODO: ENTER OTHER SERVICE QUERIES
     */


    public List<User> getTeamMembersByTeamId(Integer id) {
        return teamRepository.getTeamMembersByTeamId(id);
    }

    public Team getTeamById(Integer id) {
        return teamRepository.getTeamById(id);
    }

    public Team addNewTeam(Team team) {
        return teamRepository.addNewTeam(team);
    }

    public List<Team> getAllTeams() {
        return teamRepository.getAllTeams();
    }

    public List<Map<?,?>> getAllTeamsData() {
        return teamRepository.getAllTeamsData();
    }

    public List<User> deleteTeamById(Integer id) {

        List<User> teamMembers = teamRepository.getTeamMembersByTeamId(id);
        List<User> updatedMembers = new ArrayList<>();

        if(teamMembers != null)
            for (User member :
                    teamMembers) {
                updatedMembers.add(userRepository.removeUserFromTeam(member.getUserID()));
            }
        teamRepository.deleteTeam(id);

        if (teamMembers != null)
            return updatedMembers;
        return null;
    }
}
