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

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Stream;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentTemporaryLeave;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;

import com.virtusa.akura.student.validator.StudentTemporaryLeaveValidator;

/**
 * @author Virtusa Corporation
 */

public class StudentTemporaryLeaveReJoinControllerTest extends BaseWebTest {

    /** constant of current year. */
    private static final int YEAR = 2013;

    /** constant of maximun mark. */
    private static final int MAX_MARKS = 100;

    /** StudentService attribute for holding studentService. */
    @Autowired
    private StudentService studentService;

    /** Holds CommonService instance. */
    @Autowired
    private CommonService commonService;

    /** Holds StudentTemporaryLeaveController instance. */
    private StudentTemporaryLeaveController controller;

    /** Holds StudentTemporaryLeaveValidator instance. */
    @Autowired
    private StudentTemporaryLeaveValidator studentTemporaryLeaveValidator;

    /** Represents an instance of ModelMap. */
    private ModelMap model;

    /** Holds Student instance. */
    private Student student;

    /** Defines Grade type object. */
    private Grade grade;

    /** Defines Subject type object. */
    private Subject subject;

    /** This instance will be dependency injected by type. */
    @Autowired
    private GradeDao gradeDao;

    /** Represents an instance of the ClassGrade. */
    private ClassGrade classGrade;

    /** Represents an instance of the GradeSubject. */
    private GradeSubject gradeSubject;

    /** Represents an instance of the Stream. */
    private Stream stream;

    /** Represents an instance of the SchoolClass. */
    private SchoolClass schoolClass;

    /** Represents an instance of the List. */
    private List<ClassGrade> classGradeList;

    /** Represents an instance of the List. */
    private List<GradeSubject> gradeSubjectList;

    /** Defines StudentTemporaryLeave type object. */
    private StudentTemporaryLeave studentTemporaryLeave;

    /** Represents an instance of ModelMap. */
    private ModelMap modelMap;

    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;

    /** Represents an instance of response request. */
    private MockHttpServletRequest request;

    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult bindingResult;

    /** Represents an instance of Session. */
    @Mock
    private MockHttpSession session;

    /**
     * Instantiate the setup method.
     *
     * @throws AkuraAppException - Exception
     */
    @Before
    public final void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);

        // Instantiates student object.
        student = new Student();
        student.setAdmissionNo("33066");
        student.setNameWtInitials("L.L.G");
        student.setLastName("Lolratne");
        student.setAddress("Gampaha");
        student.setFullName("Lol Lolrathne");
        student.setModifiedTime(new Date());
        student.setGender('M');
        student.setIsOldBoy(false);
        student.setDateOfBirth(new Date());
        student.setAdmissionDate(new Date());
        student.setFirstSchoolDay(new Date());
        assertNotNull(" Student type should not be null" + student);

        student = studentService.saveStudent(student);
        assertNotNull(" Student saved to db " + student);

        // Instantiates Grade object.
        grade = new Grade();
        grade.setDescription("Grade Test");
        grade.setModifiedTime(new Date());
        assertNotNull(" Grade type should not be null " + grade);

        grade = gradeDao.save(grade);
        assertNotNull("New Grade saved to db " + grade);

        schoolClass = new SchoolClass();
        schoolClass.setDescription("SchoolClass");
        schoolClass.setModifiedTime(new Date());
        assertNotNull("SchoolClass type should not be null " + schoolClass);

        schoolClass = commonService.addClass(schoolClass);
        assertNotNull("SchoolClass saved to db " + schoolClass);

        classGrade = new ClassGrade();
        classGrade.setDescription("ClassGrade Test");
        classGrade.setGrade(grade);
        classGrade.setSchoolClass(schoolClass);
        classGrade.setModifiedTime(new Date());
        assertNotNull("ClassGrade type should not be null " + classGrade);

        classGrade = commonService.saveClassGrade(classGrade);
        assertNotNull("ClassGrade saved to db  " + classGrade);

        classGradeList = new ArrayList<ClassGrade>();
        classGradeList.add(classGrade);
        assertNotNull("classGradeList type should not be null " + classGradeList);

        stream = new Stream();
        stream.setDescription("Maths");
        stream.setModifiedTime(new Date());
        assertNotNull("Stream type should not be null " + stream);

        stream = commonService.addStream(stream);
        assertNotNull("stream saved to db  " + stream);

        subject.setDescription("Subject Test");
        subject.setStream(stream);
        subject.setSubjectCode("code test");
        subject.setModifiedTime(new Date());
        assertNotNull("Subject type should not be null " + subject);

        subject = commonService.addSubject(subject);
        assertNotNull("Subject saved to db  " + subject);

        gradeSubject = new GradeSubject();
        gradeSubject.setGrade(grade);
        gradeSubject.setSubject(subject);
        gradeSubject.setIsOptionalSubject(Boolean.FALSE);
        gradeSubject.setMaximumMark(MAX_MARKS);
        gradeSubject.setSubjectCode("Subject Code Test");
        gradeSubject.setModifiedTime(new Date());
        assertNotNull("Grade Subject type should not be null " + gradeSubject);

        gradeSubject = commonService.addGradeSubject(gradeSubject);
        assertNotNull("Grade Subject saved to db  " + gradeSubject);

        gradeSubjectList = new ArrayList<GradeSubject>();
        gradeSubjectList.add(gradeSubject);
        assertNotNull("GradeSubjectList type should not be null " + gradeSubjectList);

        // Instantiates BestStudentAttendanceTemplate object.
        studentTemporaryLeave = new StudentTemporaryLeave();
        studentTemporaryLeave.setClassGradeDescription(classGrade.getDescription());
        studentTemporaryLeave.setYear(YEAR);
        assertNotNull("New StudentTemporaryLeave should not be null " + studentTemporaryLeave);

        controller = new StudentTemporaryLeaveController();
        controller.setCommonService(commonService);
        controller.setStudentService(studentService);
        controller.setTemporaryLeaveValidator(studentTemporaryLeaveValidator);
        modelMap = new ModelMap();
        request = new MockHttpServletRequest();
    }

    /**
     * Test method for addStudentTempLeave method in StudentTemporaryLeaveController.
     *
     * @throws AkuraAppException - throws if exception occurs
     */
    @Test
    public void testAddStudentTempLeave() throws AkuraAppException {

        String returnString = controller.addStudentTempLeave(modelMap);
        assertEquals("student/activeStudentTemporaryLeave", returnString);
    }

    /**
     * Test method for populateStudentTempLeaveList method in StudentTemporaryLeaveController.
     *
     * @throws AkuraAppException - throws if exception occurs
     */
    @Test
    public void testPopulateStudentTempLeaveList() throws AkuraAppException {

        List<StudentTemporaryLeave> studentTempLeaveList = controller.populateStudentTempLeaveList(model, request);
        assertNotNull("Student Temporary Leave List should not be null", studentTempLeaveList);
    }

    /**
     * Test method for populateClassList method in StudentTemporaryLeaveController.
     *
     * @throws AkuraAppException - throws if exception occurs
     */
    @Test
    public void testPopulateClassList() throws AkuraAppException {

        List<ClassGrade> classList = controller.populateClassList();
        assertNotNull("Class List should not be null", classList);

    }

    /**
     * Test method for populateYearList method in StudentTemporaryLeaveController.
     *
     * @throws AkuraAppException - throws if exception occurs
     */
    @Test
    public void testPopulateYearList() throws AkuraAppException {

        List<String> yearList = controller.populateYearList();
        assertNotNull("Year List should not be null", yearList);

    }

    /**
     * Test method for reJoinStudent method in StudentTemporaryLeaveController.
     *
     * @throws AkuraException throws if exception occurs
     * @throws IOException
     * @throws ParseException - throws if exception occurs.
     * @throws IOException - throws if exception occurs.
     */
    @Test
    public void testReJoinStudent() throws AkuraException, ParseException, IOException {

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        String view = controller.reJoinStudent(request, model, session, studentTemporaryLeave, bindingResult);

        assertEquals("student/activeStudentTemporaryLeave", view.getBytes());
    }

    /**
     * Test method for extendTemporaryStudent method in StudentTemporaryLeaveController.
     *
     * @throws AkuraAppException - throws if exception occurs
     */
    @Test
    public void testExtendTemporaryStudent() throws AkuraAppException {

        String view = controller.extendTemporaryStudent(modelMap, request);
        assertEquals("student/activeStudentTemporaryLeave", view.getBytes());
    }

    /**
     * Test method for saveExtendedTemporaryStudent method in StudentTemporaryLeaveController.
     *
     * @throws AkuraAppException - throws if exception occurs
     */
    @Test
    public void testSaveExtendedTemporaryStudent() throws AkuraAppException {

        String view = controller.saveExtendedTemporaryStudent(request, model, studentTemporaryLeave, session);
        assertEquals("student/activeStudentTemporaryLeave", view.getBytes());
    }

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        commonService.deleteGradeSubjectList(gradeSubjectList);
        commonService.deleteGradeSubject(gradeSubject);
        commonService.deleteSubject(subject);
        commonService.deleteStream(stream);
        commonService.deleteSchoolClass(schoolClass);
        commonService.deleteGrade(grade);
        studentService.deleteStudent(student.getStudentId());

    }

}
