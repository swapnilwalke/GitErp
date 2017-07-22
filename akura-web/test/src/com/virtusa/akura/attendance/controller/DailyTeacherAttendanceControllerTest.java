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

package com.virtusa.akura.attendance.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;

import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.attendance.service.DailyAttendanceService;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.staff.service.StaffService;

/**
 * Test DailyAttendanceController functionalities.
 *
 * @author Virtusa Corporation
 */
public class DailyTeacherAttendanceControllerTest extends BaseWebTest {

    /**
     * attendanceReportingService To invoke service methods.
     */
    @Autowired
    private DailyAttendanceService dailyAttendanceService;



    /**
     * StaffService To invoke service methods.
     */
    @Autowired
    private StaffService staffService;

    /**
     * commonService To invoke service methods.
     */
    @Autowired
    private CommonService commonService;

    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;

    /**
     * Represents an instance of MockHttpServletRequest.
     */
    private MockHttpServletRequest request;


    /**
     * Represents an instance of TeacherAttendanceController.
     */
    private TeacherAttendanceController  teacherAttendanceController;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        teacherAttendanceController = new TeacherAttendanceController();

        teacherAttendanceController.setDailyAttendanceService(dailyAttendanceService);
        teacherAttendanceController.setStaffService(staffService);
        teacherAttendanceController.setCommonService(commonService);
        model = new ModelMap();
        request = new MockHttpServletRequest();
        request.addParameter("select", "true");
        request.addParameter("date", "2012-02-28");
        request.addParameter("staffIdList", "001");

    }

    /**
     * Test method for showDailyTeacherAttendance.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowDailyTeacherAttendance() throws AkuraAppException {

        String view = teacherAttendanceController.showDailyTeacherAttendance(model);
        // Check that we returned back to the original form:
        assertEquals("attendance/teacherAttendance", view);
    }

    /**
     * Test method for searchTeacherAttendance.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSearchTeacherAttendance() throws AkuraAppException {

        String view = teacherAttendanceController.searchTeacherAttendance(request, model);
        // Check that we returned back to the original form:
        assertEquals("attendance/teacherAttendance", view);

    }

    /**
     * Test method for saveorupdateTeacherAttendance.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveorupdateTeacherAttendance() throws AkuraAppException {

        String view = teacherAttendanceController.saveorupdateTeacherAttendance(request, model);
        // Check that we returned back to the original form:
        assertEquals("attendance/teacherAttendance", view);

    }











}
