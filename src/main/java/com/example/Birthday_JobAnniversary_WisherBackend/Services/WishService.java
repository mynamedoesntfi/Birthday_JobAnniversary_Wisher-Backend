package com.example.Birthday_JobAnniversary_WisherBackend.Services;

import com.example.Birthday_JobAnniversary_WisherBackend.Models.Enums.EventSubject;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.User;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.Wish;
import com.example.Birthday_JobAnniversary_WisherBackend.Models.WishRequestBody;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.UserRepository;
import com.example.Birthday_JobAnniversary_WisherBackend.Repositories.WishRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class WishService {

    private static final Logger logger = LoggerFactory.getLogger(WishService.class);

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    public Wish wishUserByID(Integer toID, WishRequestBody wishRequestBody) throws Exception {

        User toUser = userService.getUserById(toID);
        if(toID.equals(wishRequestBody.getFromID()))
            throw new Exception("Cannot wish yourself");
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


    //region EMAIL


    /**
     * TODO: uncomment to change to repeat everyday instead of every 30s
     */
    //@Scheduled(cron ="s m h dom m dow")
    @Scheduled(cron = "0 0 10 * * *")
    //@Scheduled(cron = "*/30 * * * * *")
    void scheduledJobs() {

        /**
         * TODO: Uncomment following to add email sending
         */
        //retrieveWishes();

        logger.info("DUMMY TEXT INSTEAD OF SENDING EMAIL");
    }


    void retrieveWishes() {

        /**
         * TODO: uncomment to send only today's wishes
         */
        //List<Wish> wishes = wishRepository.getAllWishesForToday();
        List<Wish> wishes = wishRepository.getAllWishes();

        HashMap<Integer, HashMap<String, SimpleMailMessage>> messages = compileMessages(wishes);

        logger.info("Compiled: " + messages.toString());

        List<HashMap<String, SimpleMailMessage>> listOfMaps = new ArrayList<>(messages.values());
        List<SimpleMailMessage> allMessages = new ArrayList<>();
        for (HashMap<String, SimpleMailMessage> map :
                listOfMaps) {
            emailService.sendEmails(new ArrayList<>(map.values()));
        }

        /**
         * TODO: Change status of today's wishes
         */
    }

    private HashMap<Integer, HashMap<String, SimpleMailMessage>> compileMessages(List<Wish> wishes) {
        HashMap<Integer, HashMap<String, SimpleMailMessage>> messages = new HashMap<>();

        for (Wish wish :
                wishes) {
            User fromUser = userService.getUserById(wish.getSenderID());
            User toUser = userService.getUserById(wish.getReceiverID());

            /** Same Person */
            if(messages.containsKey(wish.getReceiverID())) {

                HashMap<String, SimpleMailMessage> messageMap = messages.get(wish.getReceiverID());

                /** Same Subject */
                if(messageMap.containsKey(wish.getSubject())) {
                    //Edit the message
                    SimpleMailMessage mailMessage = (SimpleMailMessage) messageMap.get(wish.getSubject());
                    String mailMessageText = mailMessage.getText().concat(craftText(fromUser, wish.getMessage()));
                    mailMessage.setText(mailMessageText);
                }
                /** Different Subject */
                else {
                    SimpleMailMessage mailMessage = createNewMessage(fromUser, toUser, wish);

                    messageMap.put(wish.getSubject(), mailMessage);

                    messages.put(wish.getReceiverID(), messageMap);
                }
            }
            /** Different Person */
            else {
                SimpleMailMessage mailMessage = createNewMessage(fromUser, toUser, wish);

                HashMap<String, SimpleMailMessage> messageMap = new HashMap<>();
                messageMap.put(wish.getSubject(), mailMessage);

                messages.put(wish.getReceiverID(), messageMap);
            }
        }

        return messages;
    }

    private SimpleMailMessage createNewMessage(User fromUser, User toUser, Wish wish) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("sdcb0113977-4a2a66@inbox.mailtrap.io");
        mailMessage.setTo(toUser.getEmail());
        mailMessage.setSubject(wish.getSubject());

        mailMessage.setText(craftText(fromUser, wish.getMessage()));
        return mailMessage;
    }

    private String craftText(User user, String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append(user.getName());
        sb.append("(").append(user.getUsernameUserID()).append(")\n\n");
        sb.append("\t\t");
        sb.append(message).append("\n");
        return sb.toString();
    }

    //endregion
}
