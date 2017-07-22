/*
 * < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
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
 * This class is used to get and set fields of user Security Question domain.
 * 
 * @author Virtusa Corporation.
 */
public class UserSecurityQuestions extends BaseDomain {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /** The constant for user Security Question id string. */
    private static final String USER_SECURITY_QUESTION_ID = "UserSecurityQuestions userSecurityQuestionId=";
    
    /** The user Security Question id value. */
    private int userSecurityQuestionId;
    
    /** The user's UserLoginId . */
    private UserLogin userLogin;
    
    /** The user's securityQuestionsId. */
    private SecurityQuestions securityQuestions;
    
    /** User's security question answer. */
    private String answer;
    
    /**
     * Get the user securityQuestionId.
     * 
     * @return userSecurityQuestionId
     */
    public int getUserSecurityQuestionId() {
    
        return userSecurityQuestionId;
    }
    
    /**
     * Set the user securityQuestionId.
     * 
     * @param userSecurityQuestionIdVlaue user security question id to set
     */
    public void setUserSecurityQuestionId(int userSecurityQuestionIdVlaue) {
    
        this.userSecurityQuestionId = userSecurityQuestionIdVlaue;
    }
    
    /**
     * Get the user UserLogin.
     * 
     * @return userSecurityQuestionId
     */
    public String getAnswer() {
    
        return answer;
    }
    
    /**
     * Set the answer.
     * 
     * @param answerVlaue returns.
     */
    public void setAnswer(String answerVlaue) {
    
        this.answer = answerVlaue;
    }
    
    /**
     * Get the userLogin.
     * 
     * @return userLogin.
     */
    public UserLogin getUserLogin() {
    
        return userLogin;
    }
    
    /**
     * Set the user Login.
     * 
     * @param userLoginVlaue be set.
     */
    public void setUserLogin(UserLogin userLoginVlaue) {
    
        this.userLogin = userLoginVlaue;
    }
    
    /**
     * Get the security questions.
     * 
     * @return securityQuestions returns.
     */
    public SecurityQuestions getSecurityQuestions() {
    
        return securityQuestions;
    }
    
    /**
     * Set the security questions.
     * 
     * @param securityQuestionsValue the user security question to be set.
     */
    public void setSecurityQuestions(SecurityQuestions securityQuestionsValue) {
    
        this.securityQuestions = securityQuestionsValue;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return USER_SECURITY_QUESTION_ID + userSecurityQuestionId;
    }
    
}
