package com.example.Birthday_JobAnniversary_WisherBackend.Services;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.TeamChangeRequest;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.TeamChangeRequestRepository;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamChangeRequestService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamChangeRequestRepository teamChangeRequestRepository;

    public TeamChangeRequest createTeamChangeRequest(Integer userID, Integer teamID) {
        User user = userService.getUserById(userID);
        return teamChangeRequestRepository.createTeamChangeRequest(user, teamID);
    }

    public List<TeamChangeRequest> getALlTeamChangeRequests() {
        return teamChangeRequestRepository.getAllTeamChangeRequests();
    }

    public TeamChangeRequest approveRequestByID(Integer requestID) {
        TeamChangeRequest request = teamChangeRequestRepository.approveRequestByID(requestID);
        // change the team for the user assuming team exists
        userRepository.changeTeamByID(request.getUserID(), request.getNew_team_ID());
        return request;
    }

    public TeamChangeRequest declineRequestByID(Integer requestID) {
        return teamChangeRequestRepository.declineRequestByID(requestID);
    }
}
