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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;

/** this class will test StudentTermMarkController functionalities. */
public class StudentTermMarkControllerTest extends BaseWebTest {
    
    /** StudentService attribute for holding studentService. */
    @Autowired
    private StudentService studentService;
    
    /** CommonService attribute for holding commonService. */
    @Autowired
    private CommonService commonService;
    
    /** Represents an instance of StudentTermMarkController to Test. */
    private StudentTermMarkController studentTermMarkController;
    
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
        
        studentTermMarkController = new StudentTermMarkController();
        studentTermMarkController.setCommonService(commonService);
        studentTermMarkController.setStudentService(studentService);
        
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
