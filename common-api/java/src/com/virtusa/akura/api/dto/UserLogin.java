/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
 *
 * Copyright (C) 2012 Virtusa Corporation.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.virtusa.akura.api.dto;

/**
 * This class will hold the Properties of the UserLogin.
 * 
 * @author Virtusa Corporation
 */
public class UserLogin extends BaseDomain {
    
    /** auto-serial no. */
    private static final long serialVersionUID = 1L;
    
    /**
     * represent the unique Id for the user.
     */
    private int userLoginId;
    
    /**
     * property loginAttempts type integer.
     */
    private int loginAttempts;
    
    /**
     * holds userRole id for the user.
     */
    private int userRoleId;
    
    /**
     * holds the userIdentificationNo No of the user.
     */
    private String userIdentificationNo;
    
    /**
     * holds the user level of the user.
     */
    private String userLevel;
    
    /**
     * holds the username of the user.
     */
    private String username;
    
    /**
     * holds the password of the user.
     */
    private String password;
    
    /**
     * holds the email address for the user.
     */
    private String email;
    
    /**
     * holds firstName of the user.
     */
    private String firstName;
    
    /**
     * holds last name of the user.
     */
    private String lastName;
    
    /**
     * holds the active status of the user.
     */
    private boolean status;
    
    /**
     * holds the password status of the user, whether system generated or not.
     */
    private boolean generatedPassword;
    
    /**
     * holds the current status of the user, whether he departures or not.
     */
    private boolean deleted;
    
    /**
     * Default constructor.
     */
    public UserLogin() {

    }
    
    /**
     * return the userLoginId of the user.
     * 
     * @return the userLoginId
     */
    public int getUserLoginId() {

        return userLoginId;
    }
    
    /**
     * set the userLoginId of the user.
     * 
     * @param intUserLoginId the userLoginId to set
     */
    public void setUserLoginId(int intUserLoginId) {

        this.userLoginId = intUserLoginId;
    }
    
    /**
     * return the username of the user.
     * 
     * @return the username.
     */
    public String getUsername() {

        return username;
    }
    
    /**
     * set the username of the user.
     * 
     * @param strUsername the username to set
     */
    public void setUsername(String strUsername) {

        this.username = strUsername;
    }
    
    /**
     * return the password of the user.
     * 
     * @return the password
     */
    public String getPassword() {

        return password;
    }
    
    /**
     * set the password of the user.
     * 
     * @param strPassword the password to set
     */
    public void setPassword(String strPassword) {

        this.password = strPassword;
    }
    
    /**
     * return the status of the user.
     * 
     * @return the status
     */
    public boolean isStatus() {

        return status;
    }
    
    /**
     * set the the active status of the user.
     * 
     * @param checkStatus the status to set
     */
    public void setStatus(boolean checkStatus) {

        this.status = checkStatus;
    }
    
    /**
     * return the password status of the user.
     * 
     * @return the password status
     */
    public Boolean getGeneratedPassword() {

        return generatedPassword;
    }
    
    /**
     * set the the status of the user password.
     * 
     * @param boolGeneratedPassword the password status to set
     */
    public void setGeneratedPassword(Boolean boolGeneratedPassword) {

        this.generatedPassword = boolGeneratedPassword;
    }
    
    /**
     * return the LoginAttempts of the user.
     * 
     * @return the loginAttempts
     */
    public int getLoginAttempts() {

        return loginAttempts;
    }
    
    /**
     * set the the active status of the user.
     * 
     * @param intLoginAttempts the loginAttempts to set
     */
    public void setLoginAttempts(int intLoginAttempts) {

        this.loginAttempts = intLoginAttempts;
    }
    
    /**
     * return email address of the user.
     * 
     * @return the email
     */
    public String getEmail() {

        return email;
    }
    
    /**
     * set the email address of the user.
     * 
     * @param emailVal the email to set
     */
    public void setEmail(String emailVal) {

        this.email = emailVal;
    }
    
    /**
     * return userLevel of the user.
     * 
     * @return the userLevel
     */
    public String getUserLevel() {

        return userLevel;
    }
    
    /**
     * set the user level of the user.
     * 
     * @param userLevelVal the userLevel to set
     */
    public void setUserLevel(String userLevelVal) {

        this.userLevel = userLevelVal;
    }
    
    /**
     * return firstName of the user.
     * 
     * @return the firstName
     */
    public String getFirstName() {

        return firstName;
    }
    
    /**
     * set the firstName of the User.
     * 
     * @param firstNameVal the firstName to set
     */
    public void setFirstName(String firstNameVal) {

        this.firstName = firstNameVal;
    }
    
    /**
     * return lastName of the user.
     * 
     * @return the lastName
     */
    public String getLastName() {

        return lastName;
    }
    
    /**
     * set lastName of the user.
     * 
     * @param lastNameVal the lastName to set
     */
    public void setLastName(String lastNameVal) {

        this.lastName = lastNameVal;
    }
    
    /**
     * return userIdentificationNo of the User. if the user is student this no will be the admission no if the
     * user is a staff member it will be registration No
     * 
     * @return the userIdentificationNo
     */
    public String getUserIdentificationNo() {

        return userIdentificationNo;
    }
    
    /**
     * set userIdentificationNo for the user. if the user is student this no will be the admission no if the
     * user is a staff member it will be registration No
     * 
     * @param userIdentificationNoVal the userIdentificationNo to set
     */
    public void setUserIdentificationNo(String userIdentificationNoVal) {

        this.userIdentificationNo = userIdentificationNoVal;
    }
    
    /**
     * return userRoleId of the User.
     * 
     * @return the userRoleId
     */
    public int getUserRoleId() {

        return userRoleId;
    }
    
    /**
     * set userRoleId for the user.
     * 
     * @param userRoleIdVal the userRoleId to set
     */
    public void setUserRoleId(int userRoleIdVal) {

        this.userRoleId = userRoleIdVal;
    }
    
    /**
     * return the deleted status of the staff.
     * 
     * @return the deleted status .
     */
    public boolean getDeleted() {
    
        return deleted;
    }
    /**
     * set the the deleted status of the staff.
     * 
     * @param deletedVal the deleted status to set
     */
    
    public void setDeleted(boolean deletedVal) {
    
        this.deleted = deletedVal;
    }
    
    /**
     * overridden toString method to represent the UserLogin object.
     * 
     * @return String representation of the UserName.
     */
    @Override
    public String toString() {

        return getUsername();
    }
    
}
