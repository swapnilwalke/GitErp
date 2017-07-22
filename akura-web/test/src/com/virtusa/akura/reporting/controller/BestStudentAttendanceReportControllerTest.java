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

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.BestStudentAttendanceTemplate;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.ClassGradeDao;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.dao.SchoolClassDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.service.AttendanceReportingService;
import com.virtusa.akura.reporting.validator.GradeWiseBestStudentAttendanceValidator;

/**
 * @author Virtusa Corporation
 */

public class BestStudentAttendanceReportControllerTest extends BaseWebTest {

    /** constant of current year. */
    private static final int YEAR = 2013;

    /** constant of index month index. */
    private static final int INDEX_TWEL = 12;

    /** Defines Grade type object. */
    private Grade grade;

    /** Defines SchoolClass type object. */
    private SchoolClass schoolClass;

    /** Defines ClassGrade type object. */
    private ClassGrade classGrade;

    /** This instance will be dependency injected by type. */
    @Autowired
    private ClassGradeDao classGradeDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private SchoolClassDao schoolClassDao;

    /** Defines BestStudentAttendanceTemplate type object. */
    private BestStudentAttendanceTemplate bestStudentAttendanceTemplate;

    /** This instance will be dependency injected by type. */
    @Autowired
    private GradeDao gradeDao;

    /** Holding instance of testing controller. */
    private BestStudentAttendanceReportController controller;

    /** This instance will be dependency injected by type. */
    @Autowired
    private CommonService commonService;

    /** This instance will be dependency injected by type. */
    @Autowired
    private AttendanceReportingService attendanceReportingService;

    /** This instance will be dependency injected by type. */
    @Autowired
    private GradeWiseBestStudentAttendanceValidator gradeWiseBestStudentAttendanceValidator;

    /** Represents an instance of ModelMap. */
    private ModelMap modelMap;

    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;

    /** Represents an instance of response request. */
    private MockHttpServletRequest request;

    /** Represents an instance of response response. */
    private MockHttpServletResponse response;

    /**
     * Instantiate the setup method.
     *
     * @throws AkuraAppException - Exception
     */
    @Before
    public final void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);

        // Instantiates Grade object.
        grade = new Grade();
        grade.setDescription("Grade Test");
        grade.setModifiedTime(new Date());

        grade = gradeDao.save(grade);
        assertNotNull("New Grade should not be null " + grade);

        // Instantiates ScoolClass object.
        schoolClass = new SchoolClass();
        schoolClass.setDescription("ScoolClass test 1S**");
        schoolClass.setModifiedTime(new Date());

        schoolClass = schoolClassDao.save(schoolClass);
        assertNotNull("New ScoolClass should not be null " + schoolClass);

        // Instantiates classGrade object.
        classGrade = new ClassGrade();
        classGrade.setSchoolClass(schoolClass);
        classGrade.setGrade(grade);
        classGrade.setDescription("ClassGrade test 1SS*");
        classGrade.setModifiedTime(new Date());

        classGrade = classGradeDao.save(classGrade);
        assertNotNull("New ClassGrade should not be null " + classGrade);

        // Instantiates BestStudentAttendanceTemplate object.
        bestStudentAttendanceTemplate = new BestStudentAttendanceTemplate();

        bestStudentAttendanceTemplate.setGradeDescription(grade.getDescription());
        bestStudentAttendanceTemplate.setMonthId(INDEX_TWEL);
        bestStudentAttendanceTemplate.setYear(YEAR);

        assertNotNull("New BestStudentAttendanceTemplate should not be null " + bestStudentAttendanceTemplate);

        controller = new BestStudentAttendanceReportController();
        controller.setCommonService(commonService);
        controller.setAttendanceReportingService(attendanceReportingService);
        controller.setGradeWiseBestStudentAttendanceValidator(gradeWiseBestStudentAttendanceValidator);
        modelMap = new ModelMap();
        response = new MockHttpServletResponse();
    }

    /**
     * Test method for showReportForm method in BestStudentAttendanceReportController.
     *
     * @throws AkuraAppException - throws if exception occurs
     */
    @Test
    public void testShowReportForm() throws AkuraAppException {

        String returnString = controller.showReportForm(modelMap);
        assertEquals("reporting/gradeWiseBestStudentAttendance", returnString);
    }

    /**
     * Test method for populateGradeList method in BestStudentAttendanceReportController.
     *
     * @throws AkuraAppException - throws if exception occurs
     */
    @Test
    public void testPopulateGradeList() throws AkuraAppException {

        List<Grade> gradeList = controller.populateGradeList();
        assertNotNull("Grade List should not be null", gradeList);
    }

    /**
     * Test method for populateYearList method in BestStudentAttendanceReportController.
     *
     * @throws AkuraAppException - throws if exception occurs
     */
    @Test
    public void testPopulateYearList() throws AkuraAppException {

        List<String> yearList = controller.populateYearList();
        assertNotNull("Year List should not be null", yearList);

    }

    /**
     * Test method for generateReport method in BestStudentAttendanceReportController.
     *
     * @throws AkuraException throws if exception occurs
     */
    @Test
    public void testGenerateReport() throws AkuraException {

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        String view =
                controller
                        .generateReport(request, response, bestStudentAttendanceTemplate, mockBindingResult, modelMap);

        assertEquals("reporting/gradeWiseBestStudentAttendance", view.getBytes());

    }

    /**
     * Tear down method for each test case.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {

        gradeDao.delete(grade);
    }

}
