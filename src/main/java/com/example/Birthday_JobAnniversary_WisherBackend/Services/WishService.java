package com.example.Birthday_JobAnniversary_WisherBackend.Services;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Enums.EventSubject;
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
        if(toID.equals(wishRequestBody.getFromID()))
            throw new Exception("Cannot wish yourself, fromID:" + wishRequestBody.getFromID() + " -> toID:" + toID);
        if (toUser.getEmail() == null)
            throw new Exception("Target user does not have an email address");

        Date eventDate = null;
        if(wishRequestBody.getSubject().equals(EventSubject.BIRTHDAY_WISHES.toString()))
            eventDate = toUser.getBirthDate();
        else if(wishRequestBody.getSubject().equals(EventSubject.JOB_ANNIVERSARY_WISHES.toString()))
            eventDate = toUser.getHireDate();

        if (eventDate == null)
            throw new Exception("Target user's event date is unknown");

        return wishRepository.addWish(toID, wishRequestBody, eventDate);
    }
}
