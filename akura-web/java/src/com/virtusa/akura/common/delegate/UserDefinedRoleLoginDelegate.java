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

package com.virtusa.akura.common.delegate;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * Controls the user defined role login.
 * 
 * @author Virtusa Corporation
 */
@Component
public class UserDefinedRoleLoginDelegate implements LoginDelegate {
    
    /** url for admin login. */
    private static final String ADMIN_WELCOME = "admin/welcome";
    
    /** {@inheritDoc} */
    public String welcomeUser(UserInfo userInfo, HttpSession session) throws AkuraAppException {

        String userRedirectUrl = ADMIN_WELCOME;
        userInfo.setDefaultUserHomeUrl(userRedirectUrl);
        
        return userRedirectUrl;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Double> populateStudentProgressBar(int studentId, Date year) throws AkuraAppException {
    
        // TODO Auto-generated method stub
        return null;
    }
}
