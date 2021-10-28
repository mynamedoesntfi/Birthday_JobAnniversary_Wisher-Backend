package com.example.Birthday_JobAnniversary_WisherBackend.Services;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Team;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.TeamRepository;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

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
}
