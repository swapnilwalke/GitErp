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
import java.util.LinkedList;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.StudentPerformanceReportData;
import com.virtusa.akura.api.dto.StudentTermMarkPerformance;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.controller.GradeSubjectController;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.service.CommonReportingService;
import com.virtusa.akura.reporting.validator.CRStudentPerformanceBetweenValidator;
import com.virtusa.akura.student.service.StudentService;

/**
 * @author Virtusa Corporation
 */

public class StudentPerformanceControllerTest extends BaseWebTest {

    /**
     * systemConfig property file.
     */
    public static final String SYSTEM_CONFIG = "systemConfig";

    /**
     * Represents an instance of StudentPerformanceController.
     */
    private StudentPerformanceController studentPerformanceController;

    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;

    /**
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;

    /** Represents an instance of Grade. */
    private Grade grade;

    /** This instance will be dependency injected by type. */
    @Autowired
    private GradeDao gradeDao;

    /** Represents an instance of the Subject. */
    private Subject subject;

    /** Represents an instance of the commonService. */
    @Autowired
    private CommonService commonService;

    /** Represents an instance of the studentService. */
    @Autowired
    private StudentService studentService;

    /** Represents an instance of the GradeSubjectController. */
    private GradeSubjectController gradeSubjectController;

    /** Represents an instance of the GradeSubject. */
    private GradeSubject gradeSubject;

    /** This instance will be dependency injected by type. */
    @Autowired
    private CRStudentPerformanceBetweenValidator cRStudentPerformanceBetweenValidator;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);

        // grade object save
        grade = new Grade();
        grade.setDescription("Grade13");
        gradeDao.save(grade);
        assertNotNull("Grade type " + grade);

        // terms will be added from script into DB

        // subject object save
        subject = new Subject();
        subject.setDescription("Pure Maths");
        subject.setModifiedTime(new Date());

        subject = commonService.addSubject(subject);
        assertNotNull("Subject type should not be null " + subject);

        gradeSubjectController = new GradeSubjectController();
        gradeSubjectController.setCommonService(commonService);

        studentPerformanceController = new StudentPerformanceController();

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        new MockHttpServletRequest();

        model = new ModelMap();

        studentPerformanceController.setCommonService(commonService);
        studentPerformanceController.setStudentService(studentService);
        studentPerformanceController.setcRStudentPerformanceBetweenValidator(cRStudentPerformanceBetweenValidator);
    }

/**
      * Test method for {@link com.virtusa.sms.reporting.controller.StudentPerformanceController.
      * showReportForm(org.springframework.ui.ModelMap)
      *
      * @throws AkuraAppException when an error has occurred during processing.
      */
    @Test
    public void testShowReportForm() throws AkuraAppException {

        String view = studentPerformanceController.showReportForm(model);
        // Check that we returned back to the original form:
        assertEquals("reporting/studentPerformance", view);

    }

    /**
     * Test method for populateGradeList method in StudentPerformanceController.
     *
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testPopulateGradeList() throws AkuraAppException {

        List<Grade> list = studentPerformanceController.populateGradeList();
        assertNotNull("grade list" + list);
    }

    /**
     * Test method for populateTermList method in StudentPerformanceController.
     *
     * @throws AkuraAppException throws if exception occurs
     */
    @Test
    public void testPopulateTermList() throws AkuraAppException {

        List<Term> list = studentPerformanceController.populateTermList();
        assertNotNull("term list" + list);
    }

/**
      * Test method for {@link com.virtusa.sms.reporting.controller.StudentPerformanceController.
      * onSubmit(org.springframework.ui.ModelMap)
      *
      * @throws AkuraAppException when an error has occurred during processing.
      * @throws JRException when an error has occurred during Jasper processing
      */
    @Test
    public void testOnSubmit() throws AkuraAppException, JRException {

        // find grade object by grade id
        Grade gradeObj = commonService.findGradeById(grade.getGradeId());
        assertNotNull(gradeObj);

        // list of grade subjects which is related to the grade
        List<GradeSubject> gradeSubjects = commonService.getGradeSubjectList(grade.getGradeId());
        assertNotNull("grade subject object" + gradeSubjects);

        // StudentTermMarkPerformance dto for hbm, DB map
        // get all students in selected grade, term, and with in marks range, for only current year
        // term i is 1
        // year is current year
        // lessMark is given 10.0
        // grater marks is given 80.0
        List<StudentTermMarkPerformance> studentTermsMarksList =
                studentService.getTermMarksByGradeYear(grade.getDescription(), 1, "2012", 10f, 80f);

        List<Integer> gradeSubjectsIds = new LinkedList<Integer>();
        // get first subject column - subject name
        gradeSubjectsIds.add(gradeSubjects.get(0).getGradeSubjectId());

        // get the data source as for reporting data
        List<StudentPerformanceReportData> reportDataList =
                studentPerformanceController.processReportData(studentTermsMarksList, gradeSubjectsIds);
        assertNotNull("report data list objetct" + reportDataList);

        // generate report - pass data source list to Jasper report
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportDataList);
        assertNotNull("report data source objetct" + dataSource);
    }

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        commonService.deleteGradeSubject(gradeSubject);
        commonService.deleteSubject(subject);
        commonService.deleteGrade(grade);
    }

}
