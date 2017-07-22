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
 * This class is used to get and set fields of Security Question domain.
 * 
 * @author Virtusa Corporation
 * 
 */
public class SecurityQuestions extends BaseDomain {


    /**
	 * Holds serial version id.
	 */
	private static final long serialVersionUID = 1L;

	/** The constant for string ", securityQuestion=". */
	private static final String SECURITY_QUESTION = ", securityQuestion=";
	
	/** The constant for string "SecurityQuestions securityQuestionsId=". */
	private static final String SECURITY_QUESTIONS_SECURITY_QUESTIONS_ID = "SecurityQuestions securityQuestionsId=";

	/**
	 * Property securityQuestionsId type integer.
	 */
	private int securityQuestionsId;

	/**
	 * Property securityQuestion type String.
	 */
	private String securityQuestion;
	
	/**
	 * Default constructor for SecurityQuestion.
	 */
	public SecurityQuestions(){
		
	}

	/**
	 * Get the securityQuestionsId.
	 * 
	 * @return securityQuestionsId
	 */
	public int getSecurityQuestionsId() {
		return securityQuestionsId;
	}

	/**
	 * Set the securityQuestionsId.
	 * 
	 * @param securityQuestionsId
	 *            to be set.
	 */
	public void setSecurityQuestionsId(int securityQuestionsId) {
		this.securityQuestionsId = securityQuestionsId;
	}

	/**
	 * 
	 * Get the SecurityQuestion.
	 * 
	 * @return SecurityQuestion.
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * Set the SecurityQuestion.
	 * 
	 * @param SecurityQuestion
	 *            to be set.
	 */
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	  /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
	public String toString() {

        return SECURITY_QUESTIONS_SECURITY_QUESTIONS_ID + securityQuestionsId + SECURITY_QUESTION
                + securityQuestion;
    }

	
}
