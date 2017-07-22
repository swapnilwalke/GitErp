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
 * WrapperSecurityQuestions is used to get and set fields of the wrapper Security Question domain.
 * 
 * @author Virtusa Corporation
 */
public class WrapperSecurityQuestions extends BaseDomain {
    
    /** The constant for String ", userQuestionTwo=". */
    private static final String USER_QUESTION_TWO = ", userQuestionTwo=";
    
    /** The constant for String "WrapperSecurityQuestions userQuestionOne=". */
    private static final String WRAPPER_SECURITY_QUESTIONS_USER_QUESTION_ONE =
            "WrapperSecurityQuestions userQuestionOne=";
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Property userQuestionOne type UserSecurityQuestions .
     */
    private UserSecurityQuestions userQuestionOne;
    
    /**
     * Property userQuestionTwo type UserSecurityQuestions .
     */
    private UserSecurityQuestions userQuestionTwo;
    
    /**
     * Constructor for Wrapper Security Questions..
     */
    public WrapperSecurityQuestions() {
    
    }
    
    /**
     * Get the user security question One.
     * 
     * @return userQuestionOne the user security question return.
     */
    public UserSecurityQuestions getUserQuestionOne() {
    
        return userQuestionOne;
    }
    
    /**
     * Set the user security question One.
     * 
     * @param userQuestionOneVlaue the user security question to be set.
     */
    public void setUserQuestionOne(UserSecurityQuestions userQuestionOneVlaue) {
    
        this.userQuestionOne = userQuestionOneVlaue;
    }
    
    /**
     * Get the user security question Two.
     * 
     * @return userQuestionTwo the user security question return.
     */
    public UserSecurityQuestions getUserQuestionTwo() {
    
        return userQuestionTwo;
    }
    
    /**
     * Set the user security question Two.
     * 
     * @param userQuestionTwoVlaue the user security question to be set.
     */
    public void setUserQuestionTwo(UserSecurityQuestions userQuestionTwoVlaue) {
    
        this.userQuestionTwo = userQuestionTwoVlaue;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return WRAPPER_SECURITY_QUESTIONS_USER_QUESTION_ONE + userQuestionOne + USER_QUESTION_TWO + userQuestionTwo;
    }
    
}
