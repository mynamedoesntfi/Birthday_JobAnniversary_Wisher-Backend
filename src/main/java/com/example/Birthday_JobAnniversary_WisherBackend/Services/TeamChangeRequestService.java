package com.example.Birthday_JobAnniversary_WisherBackend.Services;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.TeamChangeRequest;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.TeamChangeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamChangeRequestService {

    @Autowired
    private UserService userService;

    @Autowired
    private TeamChangeRequestRepository teamChangeRequestRepository;

    public TeamChangeRequest createTeamChangeRequest(Integer userID, Integer teamID) {
        User user = userService.getUserById(userID);
        return teamChangeRequestRepository.createTeamChangeRequest(user, teamID);
    }

    public List<TeamChangeRequest> getALlTeamChangeRequests() {
        return teamChangeRequestRepository.getAllTeamChangeRequests();
    }
}
