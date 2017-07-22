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

package com.virtusa.akura.student.controller;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.Seminar;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentSeminar;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;

public class StudentAcademicLifeControllerTest extends BaseWebTest {
    
    /** hold the student seminar detail String. */
    private static final String STU_SEMINAR_DETAIL = "StudentTermMarkContTest_SeminarDetail";
    
    /** StudentService attribute for holding studentService. */
    @Autowired
    private StudentService studentService;
    
    /** CommonService attribute for holding commonService. */
    @Autowired
    private CommonService commonService;
    
    
    
    /** Represents an instance of StudentTermMarkController to Test. */
    private StudentAcademicLifeController studentAcademicLifeController;
    
    /** Represents an instance of MockHttpServletRequest. */
    private MockHttpServletRequest request;
    
    /** Represents an instance of MockHttpServletSession. */
    private MockHttpSession session;
    
    /** Represents an instance of ModelMap. */
    private ModelMap model;
    
    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;
    
    /** hold the student id. */
    private int studentId = 0;
    
    /** hold the year. */
    private Date year;
    
    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        
        studentAcademicLifeController = new StudentAcademicLifeController();
        studentAcademicLifeController.setCommonService(commonService);
        studentAcademicLifeController.setStudentService(studentService);
        
        // db must contain a student
        List<Student> studList = studentService.getStudentList();
        if (studList != null && !studList.isEmpty()) {
            studentId = studList.get(0).getStudentId();
            // check StudentClassInfo is there to get year
            List<StudentClassInfo> stuInfoList = studentService.getStudentClassInfoByStudentId2(studentId);
            if (stuInfoList != null && !stuInfoList.isEmpty()) {
                // set year locally
                year = stuInfoList.get(0).getYear();
                // set grade to request parameter to get year in controller method
                request.setParameter("selectedGrade", String.valueOf(stuInfoList.get(0).getStudentClassInfoId()));
            }// else year will take as current year
        } else {
            assertTrue("db must contain a student to test this unit test", false);
        }
        session.setAttribute("studentId", studentId);
        // request.setParameter("selectSemID","");
        // request.setParameter("StudentSemID", "");
        request.setParameter("seminarDetailArea", STU_SEMINAR_DETAIL);
        
    }
    
    /**
     * this method will test only return views.
     * 
     * @throws AkuraAppException -when exception occurs.
     */
    @Test
    public void showMarksTest() throws AkuraAppException {

        // this method will test only return views
        // model attributes(data) may be null
        
        // call the showMarks to invoke populateSeminarsList()
        ModelAndView modelview = studentAcademicLifeController.showMarks(request, model, session);
        String viewName = modelview.getViewName();
        String expecView = "student/academicDetails";
        assertEquals("return View is incorrect", expecView, viewName);
        
    }
    
    /**
     * this method will test saveEditSeminarsDetails() function.
     * 
     * @throws AkuraAppException -when exception occurs.
     */
    @Test
    public void saveEditSeminarsDetailsTest() throws AkuraAppException {

        // db should have a seminar to add or modify seminar
        List<Seminar> seminarList = studentService.getAllSeminars();
        
        if (seminarList != null && !seminarList.isEmpty()) {
            request.setParameter("selectSemID", String.valueOf(seminarList.get(0).getSeminarId()));
        }
        
        String expecView = "student/academicDetails";
        // test add new student seminar
        request.setParameter("StudentSemID", "0");
        
        // Testing the controller
        ModelAndView modelview = studentAcademicLifeController.saveEditSeminarsDetails(request, model, session);
        String viewName = modelview.getViewName();
        assertEquals("return View is incorrect", expecView, viewName);
        
        // to edit student seminar
        List<StudentSeminar> stuSemList = studentService.getAllStudentSeminars(studentId, year);
        if (stuSemList != null && !stuSemList.isEmpty()) {
            request.setParameter("StudentSemID", String.valueOf(stuSemList.get(0).getStudentSeminarId()));
            
            // Testing the controller
            modelview = studentAcademicLifeController.saveEditSeminarsDetails(request, model, session);
            viewName = modelview.getViewName();
            assertEquals("return View is incorrect", expecView, viewName);
        }
        
        // to delete the test record form db
        for (StudentSeminar sSeminar : stuSemList) {
            if (sSeminar.getDescription().equals(STU_SEMINAR_DETAIL)) {
                studentService.deleteStudentSeminar(sSeminar.getStudentSeminarId());
            }
        }
        
    }
    
    /**
     * this method will test deleteSeminarsDetails() function.
     * 
     * @throws AkuraAppException -when exception occurs.
     */
    @Test
    public void deleteSeminarsDetailsTest() throws AkuraAppException {

        // db should have a seminar to add or modify seminar
        List<Seminar> seminarList = studentService.getAllSeminars();
        if (seminarList != null && !seminarList.isEmpty()) {
            
            StudentSeminar studentSemObj = new StudentSeminar();
            Seminar seminar = new Seminar();
            seminar.setSeminarId(seminarList.get(0).getSeminarId());
            studentSemObj.setSeminar(seminar);
            studentSemObj.setStudentId(studentId);
            studentSemObj.setYear(DateUtil.currentYear());
            studentSemObj.setDescription(STU_SEMINAR_DETAIL);
            // add studentSeminar to test delete function
            studentService.saveStudetnSeminar(studentSemObj);
            request.setParameter("StudentSemID", String.valueOf(studentSemObj.getStudentSeminarId()));
            System.out.println(studentSemObj.getStudentSeminarId());
        }
        
        // testing delete
        ModelAndView modelview = studentAcademicLifeController.deleteSeminarsDetails(request, model, session);
        String expecView = "student/academicDetails";
        String viewName = modelview.getViewName();
        assertEquals("return View is incorrect", expecView, viewName);
        
    }
    
    /**
     * to close the Test units.
     * 
     * @throws AkuraAppException -when exception occurs.
     */
    @After
    public void tearDown() throws AkuraAppException {

    }
}
