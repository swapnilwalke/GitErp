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

package com.virtusa.akura.common.controller;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.Exam;
import com.virtusa.akura.api.dto.ExamSubject;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.ExamSubjectValidator;

/**
 * This test class, tests all the presentation related functionality for the Exam Subject domain object.
 *
 * @author Virtusa Corporation
 */
public class ExamSubjectControllerTest extends BaseWebTest {

    /** Represents request scope attribute for subjects. */
    private static final String SUBJECTS = "allSubjectIds";

    /** Represents request scope attribute for exam subject. */
    private static final String EXAM_SUBJECT = "examSubject";

    /** Represents the view when the save function is success. */
    private static final String EXAM_SUBJECT_SUCCESS = "reference/manageExamSubject";

    /** Represents the view when the save function is not success. */
    private static final String EXAM_SUBJECT_RESULT_ERROR = "reference/manageExamSubject";

    /** Represents the assertion for the subject save. */
    private static final String SUBJECT_SAVE_ASSERT = "Subject type should not be null ";

    /** Represents the description of the Subject. */
    private static final String SUBJECT_DESCRIPTION = "SubjectDescription";

    /** Represents the assertion for the exam save. */
    private static final String SAVE_ASSERT = "Exam type should not be null ";

    /** Represents the description of the Exam. */
    private static final String NEW_EXAM = "New exam";

    /** Represents the description of the Exam Subject. */
    private static final String EXAM_DESCRIPTION = "examDescription";

    /** Represents an instance of the ExamSubjectController. */
    private ExamSubjectController examSubjectController;

    /** Represents an instance of the ExamSubject. */
    private ExamSubject examSubject;

    /** Represents an instance of the GradeSubjectValidator. */
    @Autowired
    private ExamSubjectValidator examSubjectValidator;

    /** Represents an instance of the commonService. */
    @Autowired
    private CommonService commonService;

    /** Represents an instance of the Exam. */
    private Exam exam;

    /** Represents an instance of the Subject. */
    private Subject subject;

    /** Represents an instance of MockHttpServletRequest. */
    private MockHttpServletRequest request;

    /** Represents an instance of ModelMap. */
    private ModelMap model;

    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;

    /**
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        exam = new Exam();
        exam.setDescription(NEW_EXAM);
        exam.setModifiedTime(new Date());

        exam = commonService.addExam(exam);
        assertNotNull(SAVE_ASSERT + exam);

        subject = new Subject();
        subject.setDescription(SUBJECT_DESCRIPTION);
        subject.setModifiedTime(new Date());

        subject = commonService.addSubject(subject);
        assertNotNull(SUBJECT_SAVE_ASSERT + subject);

        examSubject = new ExamSubject();
        examSubject.setModifiedTime(new Date());
        examSubject.setExam(exam);
        examSubject.setSubject(subject);

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();

        examSubjectController = new ExamSubjectController();
        examSubjectController.setCommonService(commonService);
    }

    /**
     * Test method for SaveOrUpdateexamSubject of examSubjectController.
     *
     * @throws AkuraAppException - The exception details that occurred when saving or updating.
     */
    @Test
    public void testSaveOrUpdateExamSubject() throws AkuraAppException {

        try {
            String[] values = new String[1];
            values[0] = "" + subject.getSubjectId();
            request.addParameter(SUBJECTS, values);
            model.addAttribute(EXAM_SUBJECT, examSubject);
            request.addParameter(EXAM_DESCRIPTION, NEW_EXAM);
            examSubjectController.setExamSubjectValidator(examSubjectValidator);
            boolean result = true;
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(result);
            String view =
                    examSubjectController.saveOrUpdateExamSubjects(model, examSubject, mockBindingResult, request);

            assertEquals(EXAM_SUBJECT_RESULT_ERROR, view);
            result = false;
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(result);
            String savedView =
                    examSubjectController.saveOrUpdateExamSubjects(model, examSubject, mockBindingResult, request);
            assertEquals(EXAM_SUBJECT_SUCCESS, savedView);
            assertTrue(model.size() > 0);

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        commonService.deleteExamSubject(examSubject);
        commonService.deleteExam(exam);
        commonService.deleteSubject(subject);
    }
}
