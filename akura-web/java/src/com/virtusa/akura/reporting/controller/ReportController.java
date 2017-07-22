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

package com.virtusa.akura.reporting.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.staff.service.StaffService;

/**
 * Controls the report main page.
 *
 * @author Virtusa Corporation
 */
@Controller
public class ReportController {

    /** The Constant STUDENT_ATTENDANCE_REPORTS_HTM. */
    private static final String STUDENT_ATTENDANCE_REPORTS_HTM = "/StudentAttendanceReports.htm";

    /** The Constant STUDENT_GENERAL_REPORTS_HTM. */
    private static final String STUDENT_GENERAL_REPORTS_HTM = "/StudentGeneralReports.htm";

    /** The Constant REPORTING_STUDENT_ATTENDANCE_REPORT. */
    private static final String REPORTING_STUDENT_ATTENDANCE_REPORT = "reporting/studentAttendanceReport";

    /** The Constant REPORTING_STUDENT_GENERAL_REPORT. */
    private static final String REPORTING_STUDENT_GENERAL_REPORT = "reporting/studentGeneralReport";

    /** The Constant STAFF_ATTENDANCE_REPORTS_HTM. */
    private static final String STAFF_ATTENDANCE_REPORTS_HTM = "/StaffAttendanceReports.htm";

    /** The Constant STAFF_GENERAL_REPORTS_HTM. */
    private static final String STAFF_GENERAL_REPORTS_HTM = "/StaffGeneralReports.htm";

    /** The Constant REPORTING_STAFF_ATTENDANCE_REPORT. */
    private static final String REPORTING_STAFF_ATTENDANCE_REPORT = "reporting/staffAttendanceReport";

    /** The Constant REPORTING_STAFF_GENERAL_REPORT. */
    private static final String REPORTING_STAFF_GENERAL_REPORT = "reporting/staffGeneralReport";

    /** The Constant IS_CLASS_TEACHER. */
    private static final String IS_CLASS_TEACHER = "isClassTeacher";

    /** key to hold string userLogin. */
    private static final String SESSION_USER_LOGIN = "userLogin";

    /** StaffService attribute for holding StaffService. */
    @Autowired
    private StaffService staffService;

    /**
     * Show student general reports.
     *
     * @param modelMap the model map
     * @return the string
     */
    @RequestMapping(value = STUDENT_GENERAL_REPORTS_HTM, method = RequestMethod.GET)
    public String showStudentGeneralReports(final ModelMap modelMap) {

        return REPORTING_STUDENT_GENERAL_REPORT;
    }

    /**
     * Show student attendance reports.
     *
     * @param modelMap the model map
     * @return the string
     */
    @RequestMapping(value = STUDENT_ATTENDANCE_REPORTS_HTM, method = RequestMethod.GET)
    public String showStudentAttendanceReports(final ModelMap modelMap) {

        return REPORTING_STUDENT_ATTENDANCE_REPORT;
    }

    /**
     * Show staff general reports.
     *
     * @param modelMap the model map
     * @return the string
     */
    @RequestMapping(value = STAFF_GENERAL_REPORTS_HTM, method = RequestMethod.GET)
    public String showStaffGeneralReports(final ModelMap modelMap) {

        return REPORTING_STAFF_GENERAL_REPORT;
    }

    /**
     * Show staff attendance reports.
     *
     * @param modelMap the model map
     * @return the string
     */
    @RequestMapping(value = STAFF_ATTENDANCE_REPORTS_HTM, method = RequestMethod.GET)
    public String showStaffAttendanceReports(final ModelMap modelMap) {

        return REPORTING_STAFF_ATTENDANCE_REPORT;
    }

    /**
     * sets StaffService object.
     *
     * @param staffServiceRef set staff service object.
     */

    public void setStaffService(StaffService staffServiceRef) {

        this.staffService = staffServiceRef;
    }

    /**
     * Check whether the staff member is a class teacher or not.
     *
     * @param staffId.
     * @param session - session.
     * @return true if the staff member is a classTeacher
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(IS_CLASS_TEACHER)
    public boolean isClassTeacher(HttpSession session) throws AkuraAppException {

        int staffId = 0;
        UserLogin userLogin = (UserLogin) session.getAttribute(SESSION_USER_LOGIN);

        if (userLogin.getUserIdentificationNo() != null && userLogin.getUserRoleId() == 2) {
            staffId = Integer.parseInt(userLogin.getUserIdentificationNo());

            boolean r = staffService.isClassTeacher(staffId);
            return r;

        } else {

            return true;
        }

    }

}
