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

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.Exam;
import com.virtusa.akura.api.dto.ExamMark;
import com.virtusa.akura.api.dto.ExamSubject;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.dao.SubjectDao;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.dao.ExamMarksDao;
import com.virtusa.akura.student.dao.StudentDao;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;

/**
 * This test class, tests all the presentation related functionality for the Exam Marks domain object.
 *
 * @author Virtusa Corporation
 */
public class ExamMarkControllerTest extends BaseWebTest {

    /** Represents an instance of MockHttpServletRequest. */
    private MockHttpServletRequest request;

    /** Represents an instance of ModelMap. */
    private ModelMap model;

    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;

    /** This instance will be dependency injected by type. */
    @Autowired
    private ExamMarksDao examMarksDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private SubjectDao subjectDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private CommonService commonService;

    /** This instance will be dependency injected by type. */
    @Autowired
    private StudentService studentService;

    /** This instance will be dependency injected by type. */
    @Autowired
    private GradeDao gradeDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private StudentDao studentDao;

    /** Represents an instance of Student. */
    private Student student;

    /** Represents an instance of Exam. */
    private Exam exam;

    /** Represents an instance of Grade. */
    private Grade grade;

    /** Represents an instance of Subject. */
    private Subject subject;

    /** Represents an instance of Subject. */
    private ExamMark examMark;

    /** Represents an instance of ExamSubject. */
    private ExamSubject examSubject;

    /** Represents an instance of the ExamSubjectController. */
    private ExamMarksController examMarksController;

    /** Represents the assertion for the subject save. */
    private static final String SUBJECT_SAVE_ASSERT = "Subject type should not be null ";

    /** Represents the description of the Subject. */
    private static final String SUBJECT_DESCRIPTION = "SubjectDes";

    /** Represents the assertion for the exam save. */
    private static final String SAVE_ASSERT = "Exam type should not be null ";

    /** Represents the description of the Exam. */
    private static final String NEW_EXAM = "New test exam";

    /** Represents the description of the grade. */
    private static final String GRADE_DESCRIPTION = "TestGrade";

    /** Represents the marks for the exam. */
    private static final float MARKS = 46.0f;

    /** Represents the path of the studentExam marks page. */
    private static final String RESULT_PAGE = "student/studentExamMarks";

    /** Represents the parameter name of exam key. */
    private static final String EXAM_ID = "examId";

    /** Represents the assertion for the student save. */
    private static final String STUDENT_SAVE_ASSERT = "Student should not be null.";

        /**
         * @throws AkuraAppException - The exception details that occurred when processing.
         */
        @Before
        public void setUp() throws AkuraAppException {

            grade = new Grade();
            grade.setDescription(GRADE_DESCRIPTION);
            gradeDao.save(grade);

            exam = new Exam();
            exam.setDescription(NEW_EXAM);
            exam.setModifiedTime(new Date());
            exam.setGradeId(grade.getGradeId());
            exam = commonService.addExam(exam);
            assertNotNull(SAVE_ASSERT + exam);

            subject = new Subject();
            subject.setDescription(SUBJECT_DESCRIPTION);
            subject.setModifiedTime(new Date());

            subject = subjectDao.save(subject);
            assertNotNull(SUBJECT_SAVE_ASSERT + subject);

            examSubject = new ExamSubject();
            examSubject.setModifiedTime(new Date());
            examSubject.setExam(exam);
            examSubject.setSubject(subject);

            examSubject = commonService.addExamSubject(examSubject);
            assertNotNull(SUBJECT_SAVE_ASSERT + examSubject);

            student = new Student();
            student.setAdmissionNo("014252a33");
            student.setNameWtInitials("I Laa3");
            student.setLastName("Lolratne ");
            student.setAddress("Gampaha");
            student.setFullName("Lol Lolrathne");
            student.setModifiedTime(new Date());
            student.setGender('M');
            student.setEmail("afernando.virtusa.com");
            student.setIsOldBoy(false);
            student.setDateOfBirth(new Date());
            student.setAdmissionDate(new Date());
            student.setFirstSchoolDay(new Date());

            Student newStudent = studentDao.save(student);
            assertNotNull(STUDENT_SAVE_ASSERT + newStudent);

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);

            examMark = new ExamMark();
            examMark.setExamSubject(examSubject.getExamSubjectId());
            examMark.setMarks(MARKS);
            examMark.setYear(DateUtil.getDate(year));
            examMark.setStudent(student.getStudentId());

            MockitoAnnotations.initMocks(this);
            assertNotNull(mockBindingResult);
            model = new ModelMap();
            request = new MockHttpServletRequest();

            examMarksController = new ExamMarksController();
            examMarksController.setCommonService(commonService);
            examMarksController.setStudentService(studentService);
        }


    /**
     * Test method for {@link com.virtusa.akura.student.controller.ExamMarksController#
     * manageExamMarks(org.springframework.ui.ModelMap)}.
     */
    @Test
    public void testManageExamMarks() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        request = new MockHttpServletRequest();
        request.setAttribute(EXAM_ID, exam.getExamId());
        request.setAttribute(AkuraWebConstant.YEAR, year);
        String view = examMarksController.manageExamMarks(model);
        assertEquals(view , RESULT_PAGE);
    }

    /**
     * Deletes the saved objects.
     *
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @After
    public void tearDown() throws AkuraAppException {
        examMarksDao.delete(examMark);
        studentDao.delete(student);
        commonService.deleteExamSubject(examSubject);
        subjectDao.delete(subject);
        commonService.deleteExam(exam);
        gradeDao.delete(grade);
    }
}
