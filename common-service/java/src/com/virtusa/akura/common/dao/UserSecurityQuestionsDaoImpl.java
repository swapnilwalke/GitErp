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

package com.virtusa.akura.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.UserSecurityQuestions;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * @author Virtusa Corporation.
 */
public class UserSecurityQuestionsDaoImpl extends BaseDaoImpl<UserSecurityQuestions> implements
        UserSecurityQuestionsDao {
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(UserSecurityQuestionsDaoImpl.class);
    
    /**
     * Name of the Query to get security questions.
     */
    private static final String GET_USER_SECURITY_QUESTION = "getUserSecurityQuestionById";
    
    /** The Constant ERROR_WHILE_RETRIEVE_USER_SECURITY_QUESTIONS. */
    private static final String ERROR_WHILE_RETRIEVE_USER_SECURITY_QUESTIONS =
            "Error while retrieve UserSecurityQuestions--->";
    
    /**
     * Get the UserSecurityQuestions of this user.
     * 
     * @param userLoginId User's login Id.
     * @return The user specific security question.
     * @throws AkuraAppException when fail.
     */
    
    @SuppressWarnings("unchecked")
    public List<UserSecurityQuestions> getUserSecurityQuestionByUserLoginId(int userLoginId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_USER_SECURITY_QUESTION, new Object[] { userLoginId });
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVE_USER_SECURITY_QUESTIONS + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR + e);
        }
        
    }
    
}
