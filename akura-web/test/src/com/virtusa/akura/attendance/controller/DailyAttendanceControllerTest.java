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
import com.virtusa.akura.student.service.StudentService;

/**
 * Test DailyAttendanceController functionalities.
 *
 * @author Virtusa Corporation
 */
public class DailyAttendanceControllerTest extends BaseWebTest {

    /**
     * attendanceReportingService To invoke service methods.
     */
    @Autowired
    private DailyAttendanceService dailyAttendanceService;

    /**
     * studentService To invoke service methods.
     */
    @Autowired
    private StudentService studentService;

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
     * Represents an instance of DailyAttendanceController.
     */
    private DailyAttendanceController dailyAttendanceController;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        dailyAttendanceController = new DailyAttendanceController();

        dailyAttendanceController.setDailyAttendanceService(dailyAttendanceService);
        dailyAttendanceController.setStudentService(studentService);
        dailyAttendanceController.setCommonService(commonService);
        model = new ModelMap();
        request = new MockHttpServletRequest();
        request.addParameter("select", "32");
        request.addParameter("date", "2012-02-28");
        request.addParameter("studentIdList", "001");

    }

    /**
     * Test method for showDailyAttendance.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowDailyAttendance() throws AkuraAppException {

        String view = dailyAttendanceController.showDailyAttendance(model);
        // Check that we returned back to the original form:
        assertEquals("attendance/dailyAttendance", view);
    }

    /**
     * Test method for searchStudentAttendance.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSearchStudentAttendance() throws AkuraAppException {

        String view = dailyAttendanceController.searchStudentAttendance(request, model);
        // Check that we returned back to the original form:
        assertEquals("attendance/dailyAttendance", view);

    }

    /**
     * Test method for saveorupdateStudentAttendance.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveorupdateStudentAttendance() throws AkuraAppException {

        String view = dailyAttendanceController.saveorupdateStudentAttendance(request, model);
        // Check that we returned back to the original form:
        assertEquals("attendance/dailyAttendance", view);

    }

}
