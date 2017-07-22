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

package com.virtusa.akura.staff.delegate;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.virtusa.akura.api.dto.AdminDetails;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.delegate.LoginDelegate;
import com.virtusa.akura.staff.service.StaffService;

/**
 * Controls the staff login.
 * 
 * @author Virtusa Corporation
 */
@Component
public class StaffLoginDelegate implements LoginDelegate {
    
    /** url for admin login. */
    private static final String ADMIN_WELCOME = "admin/welcome";
    
    /** Represents an instance of StaffService. */
    private StaffService staffService;
    
    /**
     * Sets an instance of StaffService.
     * 
     * @param staffServiceVal - an instance of StaffService.
     */
    public void setStaffService(StaffService staffServiceVal) {

        this.staffService = staffServiceVal;
    }
    
    /** {@inheritDoc} */
    public String welcomeUser(UserInfo userInfo, HttpSession session) throws AkuraAppException {

        String userRedirectUrl = ADMIN_WELCOME;
        // int userLevelIdentifier =
        // staffService.findStaffIdForRegistrationNo(userInfo.getUserLevelIdentifier());
        String identificationNo = "";
        if (userInfo.getUserId() == 0) {
            if(userInfo.getUserLevelIdentifier() == null || userInfo.getUserLevelIdentifier().isEmpty()){
                identificationNo = "0";
            }else{
                identificationNo = userInfo.getUserLevelIdentifier();
            }
        } else {
            identificationNo = userInfo.getUserId() + "";
        }
        
        int staffId = Integer.parseInt(identificationNo);
        userInfo.setUserId(staffId);
        userInfo.setDefaultUserHomeUrl(userRedirectUrl);
        
        // set the registration no
        if (staffId != 0) {
            ((AdminDetails) userInfo).setRegistrationNo(staffService.findStaff(staffId).getRegistrationNo());
        }
        
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
