package com.example.Birthday_JobAnniversary_WisherBackend.Services;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Request;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.RequestRepository;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RequestService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;

    public Map<?,?> createRequest(Integer userID, Integer teamID) {
        User user = userService.getUserById(userID);
        return requestRepository.createRequest(user, teamID);
    }

    public List<Map<?,?>> getAllRequests() {
        return requestRepository.getAllRequests();
    }

    public Map<?,?> approveRequestByID(Integer requestID) {
        Map<?,?> request = requestRepository.approveRequestByID(requestID);
        System.out.println(request);
        // change the team for the user assuming team exists
        userRepository.changeTeamByID((int) request.get("user_id"),(int) request.get("new_team_id"));
        return request;
    }

    public Map<?,?> declineRequestByID(Integer requestID) {
        return requestRepository.declineRequestByID(requestID);
    }

    public List<Map<?,?>> getAllPendingRequests(Integer userID) {
        return requestRepository.getAllPendingRequests(userID);
    }

    public List<Map<?,?>> getRequestsByUserId(Integer id) {
        return requestRepository.getRequestsByUserId(id);
    }
}
