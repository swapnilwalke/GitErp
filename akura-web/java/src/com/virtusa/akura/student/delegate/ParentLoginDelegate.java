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

package com.virtusa.akura.student.delegate;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.virtusa.akura.api.dto.ParentDetails;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.delegate.LoginDelegate;
import com.virtusa.akura.student.service.ParentService;

/**
 * Maintains the login of role, Parent.
 * 
 * @author Virtusa Corporation
 */
public class ParentLoginDelegate implements LoginDelegate {
    
    /** Redirect URL. */
    private static final String STUDENT_PARENT_WELCOME = "student/parentWelcome";
    
    /**
     * Log the error messages.
     */
    private static final Logger LOG = Logger.getLogger(ParentLoginDelegate.class);
    
    /** Represents an instance of ParentService. */
    private ParentService parentService;
    
    /**
     * set the parentService object.
     * 
     * @param parentServiceArg ParentService object to set.
     */
    public void setParentService(ParentService parentServiceArg) {

        this.parentService = parentServiceArg;
    }
    
    /**
     * Maintains the login of UserInfo as Parent.
     * 
     * @param userInfo - an instance of UserInfo.
     * @param session - an instance of HttpSession.
     * @return - the view the login.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Override
    public String welcomeUser(UserInfo userInfo, HttpSession session) throws AkuraAppException {

        String userRedirectUrl = STUDENT_PARENT_WELCOME;
        // int userLevelIdentifier =
        // parentService.getParentByNIC(userInfo.getUserLevelIdentifier()).getParentId();
        String identificationNo = "";
        if (userInfo.getUserId() == 0) {
            identificationNo = userInfo.getUserLevelIdentifier().isEmpty() ? "0" : userInfo.getUserLevelIdentifier();
        } else {
            identificationNo = userInfo.getUserId() + "";
        }
        
        int parentId = Integer.parseInt(identificationNo);
        userInfo.setUserId(parentId);
        userInfo.setDefaultUserHomeUrl(userRedirectUrl);
        
        // set parent NIC no
        ((ParentDetails) userInfo).setNicNo(parentService.findParent(parentId).getNationalIdNo());
        // return setUserInfo(session, userInfo, userRedirectUrl, userLevelIdentifier);
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
