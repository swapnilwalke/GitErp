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

package com.virtusa.akura.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The UserRoleDetailsLoadController is to keep UserRole id in the session.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class UserRoleDetailsLoadController {
    
    /** The redirect view of user role details. */
    private static final String REDIRECT_VIEW_USER_ROLE_DETAILS = "redirect:viewUserRoleDetails.htm";
    
    /** The request mapping name to load user role data. */
    private static final String LOAD_USER_ROLE_DETAILS = "loadUserRolefDetails.htm";
    
    /** The id of the user role. */
    private static final String USER_ROLE_ID = "userRoleId";
    
    /**
     * Get the user role id and set it to admin details to view the search staff details.
     * 
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return view of student details
     */
    @RequestMapping(value = LOAD_USER_ROLE_DETAILS)
    public String loadData(HttpServletRequest request, HttpSession session, ModelMap model) {

        if (request.getParameter(USER_ROLE_ID) != null) {
            int userRoleId = Integer.parseInt(request.getParameter(USER_ROLE_ID));
            session.setAttribute(USER_ROLE_ID, userRoleId);
        }
        
        return REDIRECT_VIEW_USER_ROLE_DETAILS;
    }
}
