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

import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * Maintains the login of roles, Student, Admin and Student.
 *
 * @author Virtusa Corporation
 */
public interface LoginDelegate {

    /**
     * Maintains the login of UserInfo as AdminDetails or StudentDetails.
     *
     * @param userInfo - an instance of UserInfo.
     * @param session - an instance of HttpSession.
     * @return - the view the login.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    String welcomeUser(UserInfo userInfo, HttpSession session) throws AkuraAppException;
    
    /**
     * populate student progress bar values.
     * 
     * @param studentId - integer
     * @param year - Date
     * @return average in double.
     * @throws AkuraAppException when exception occurs
     */
    Map<String, Double> populateStudentProgressBar(int studentId, Date year) throws AkuraAppException;

}
