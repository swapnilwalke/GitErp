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

package com.virtusa.akura.staff.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The StaffDetailsLoadController is to keep staff id in the session.
 *
 * @author Virtusa Corporation
 */
@Controller
public class StaffDetailsLoadController {

    /** The redirect view of staff member details. */
    private static final String REDIRECT_VIEW_STAFF_MEMBER_DETAILS = "redirect:viewStaffMemberDetails.htm";

    /** The request mapping name to load staff member data. */
    private static final String LOAD_STAFF_DETAILS = "/loadStaffDetails.htm";

    /** The staff id of the staff member. */
    private static final String STAFF_ID = "staffId";

    /**
     * Get the search staff id and set it to admin details to view the search staff details.
     *
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return view of student details
     */
    @RequestMapping(value = LOAD_STAFF_DETAILS)
    public String loadData(HttpServletRequest request, HttpSession session, ModelMap model) {

        if (request.getParameter(STAFF_ID) != null) {
            int staffId = Integer.parseInt(request.getParameter(STAFF_ID));
            session.setAttribute(STAFF_ID, staffId);
        }

        return REDIRECT_VIEW_STAFF_MEMBER_DETAILS;
    }
}
