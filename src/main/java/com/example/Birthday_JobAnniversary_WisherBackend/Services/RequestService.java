package com.example.Birthday_JobAnniversary_WisherBackend.Services;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Request;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.RequestRepository;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;

    public Request createRequest(Integer userID, Integer teamID) {
        User user = userService.getUserById(userID);
        return requestRepository.createRequest(user, teamID);
    }

    public List<Request> getAllRequests() {
        return requestRepository.getAllRequests();
    }

    public Request approveRequestByID(Integer requestID) {
        Request request = requestRepository.approveRequestByID(requestID);
        // change the team for the user assuming team exists
        userRepository.changeTeamByID(request.getUserID(), request.getNew_team_ID());
        return request;
    }

    public Request declineRequestByID(Integer requestID) {
        return requestRepository.declineRequestByID(requestID);
    }

    public List<Request> getAllPendingRequests() {
        return requestRepository.getAllPendingRequests();
    }

    public List<Request> getRequestsByUserId(Integer id) {
        return requestRepository.getRequestsByUserId(id);
    }
}
