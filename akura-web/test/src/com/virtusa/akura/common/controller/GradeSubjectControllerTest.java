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

import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.GradeSubjectValidator;

/**
 * @author Virtusa Corporation
 */

public class GradeSubjectControllerTest extends BaseWebTest {

    /** Represents an instance of the GradeSubjectController. */
    private GradeSubjectController gradeSubjectController;

    /** Represents an instance of the GradeSubject. */
    private GradeSubject gradeSubject;

    /** Represents an instance of the GradeSubjectValidator. */
    @Autowired
    private GradeSubjectValidator gradeSubjectValidator;

    /** Represents an instance of the commonService. */
    @Autowired
    private CommonService commonService;

    /** Represents an instance of the Grade. */
    private Grade grade;

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
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        grade = new Grade();
        grade.setDescription("New GradeAAA");
        grade.setModifiedTime(new Date());

        grade = commonService.saveGrade(grade);
        assertNotNull("Grade type should not be null " + grade);

        subject = new Subject();
        subject.setDescription("SubjectDescriptionAAA");
        subject.setModifiedTime(new Date());

        subject = commonService.addSubject(subject);
        assertNotNull("Subject type should not be null " + subject);

        gradeSubject = new GradeSubject();
        gradeSubject.setSubjectCode("GradeSubjectAAA");
        gradeSubject.setModifiedTime(new Date());
        gradeSubject.setGrade(grade);
        gradeSubject.setSubject(subject);        

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();

        gradeSubjectController = new GradeSubjectController();
        gradeSubjectController.setCommonService(commonService);
    }

    /**
     * Test method for SaveOrUpdateGradeSubject of GradeSubjectController.
     *
     * @throws AkuraException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateGradeSubject() throws AkuraException {

        try {
            String[] values = new String[1];
            values[0] = "" + subject.getSubjectId();
            request.addParameter("subjects", values);
            model.addAttribute("gradeSubject", gradeSubject);

            gradeSubjectController.setGradeSubjectValidator(gradeSubjectValidator);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view =
                    gradeSubjectController.saveOrUpdateGradeSubjects(model, gradeSubject, mockBindingResult, 
                            request, null);

            assertEquals("reference/manageSubjectGrade", view);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView =
                    gradeSubjectController.saveOrUpdateGradeSubjects(model, gradeSubject, mockBindingResult,
                            request, null);
            assertEquals("redirect:manageSubjectGrade.htm", savedView);
            assertTrue(model.size() > 0);

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        commonService.deleteGradeSubject(gradeSubject);
        commonService.deleteGrade(grade);
        commonService.deleteSubject(subject);
    }
}
