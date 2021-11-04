package com.example.Birthday_JobAnniversary_WisherBackend.Models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class UserReturn {

    private int userID;
    private String username;
    private String firstName;
    private String lastName;
    private String contact;
    private String image;
    private String gender;
    private String address;
    private Date birthDate;
    private Date hireDate;
    private String email;
    private String role;
    private int teamID;

    public UserReturn() {
    }

    public UserReturn(User user) {
        this.userID = user.getUserID();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.contact = user.getContact();
        this.image = user.getImage();
        this.gender = user.getGender();
        this.address = user.getAddress();
        this.birthDate = user.getBirthDate();
        this.hireDate = user.getHireDate();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.teamID = user.getTeamID();
    }

    public UserReturn(int userID, String username, String firstName, String lastName, String contact, String image, String gender, String address, Date birthDate, Date hireDate, String email, String role, int teamID) {
        this.userID = userID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.image = image;
        this.gender = gender;
        this.address = address;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.email = email;
        this.role = role;
        this.teamID = teamID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public static List<UserReturn> convertUsersList(List<User> users) {
        List<UserReturn> userReturns = new ArrayList<>();
        for (User user :
                users) {
            userReturns.add(new UserReturn(user));
        }
        return  userReturns;
    }
}
