package com.example.Birthday_JobAnniversary_WisherBackend.Services;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.Wish;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.WishRequestBody;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.UserRepository;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class WishService {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private UserRepository userRepository;

    public Wish wishUserByID(Integer toID, WishRequestBody wishRequestBody) throws Exception {

        User toUser = userRepository.getUserById(toID);

        Date eventDate = null;
        if(wishRequestBody.getSubject().equals("BIRTHDAY WISHES"))
            eventDate = toUser.getBirthDate();
        else if(wishRequestBody.getSubject().equals("JOB ANNIVERSARY WISHES"))
            eventDate = toUser.getHireDate();

        if (eventDate == null)
            throw new Exception("Target user's event date is unknown");

        return wishRepository.addWish(toID, wishRequestBody, eventDate);
    }
}
