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

import com.virtusa.akura.api.dto.Assignment;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.AssignmentValidator;

/**
 * @author Virtusa Corporation
 */

public class ManageAssignmentControllerTest extends BaseWebTest {

    /** Represents an instance of the ManageAssignment Controller. */
    private ManageAssignmentController manageAssignmentController;

    /** Represents an instance of the GradeSubject. */
    private GradeSubject gradeSubject;

    /** Represents an instance of the AssignmentValidator. */
    @Autowired
    private AssignmentValidator assignmentValidator;

    /** Represents an instance of the commonService. */
    @Autowired
    private CommonService commonService;

    /** Represents an instance of the Grade. */
    private Grade grade;

    /** Represents an instance of the Subject. */
    private Subject subject;

    /** Represents an instance of the Assignment. */
    private Assignment assignment;

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

        // create a grade object
        grade = new Grade();
        grade.setDescription("Grade 3");
        grade.setModifiedTime(new Date());

        // save grade object
        grade = commonService.saveGrade(grade);
        assertNotNull("Grade type should not be null " + grade);

        // create a subject object
        subject = new Subject();
        subject.setDescription("Soc.Study");
        subject.setModifiedTime(new Date());

        // save a subject object
        subject = commonService.addSubject(subject);
        assertNotNull("Subject type should not be null " + subject);

        // create a grade subject object
        gradeSubject = new GradeSubject();
        gradeSubject.setSubjectCode("Grade 3-Soc.Study");
        gradeSubject.setModifiedTime(new Date());
        gradeSubject.setGrade(grade);
        gradeSubject.setSubject(subject);

        // add grade subject object
        gradeSubject = commonService.addGradeSubject(gradeSubject);
        assertNotNull("GradeSubject type should not be null " + gradeSubject);

        assignment = new Assignment();
        assignment.setDate(new Date());
        assignment.setDescription("assignment for practiclas");
        assignment.setGradeDescription(grade.getDescription());
        assignment.setSubjectDescription(subject.getDescription());
        assignment.setName("Assignment I");
        assignment.setGradeSubject(gradeSubject);
        assignment.setModifiedTime(new Date());

        assignment = commonService.addAssignment(assignment);
        assertNotNull("Assignment type should not be null " + assignment);

        // create assignment object

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();

        manageAssignmentController = new ManageAssignmentController();
        manageAssignmentController.setCommonService(commonService);
    }

    /**
     * Test method for manageSaveOrUpdateAssignment of ManageAssignmentController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testmanageSaveOrUpdateAssignment() throws AkuraAppException {

        try {
            String[] values = new String[1];
            values[0] = "" + gradeSubject.getGradeSubjectId();
            request.addParameter("selectSubjects", values);
            model.addAttribute("Wrapperassignment", assignment);

            manageAssignmentController.setAssignmentValidator(assignmentValidator);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view =
                    manageAssignmentController.saveOrUpdateAssignment(assignment, mockBindingResult, model, request);

            assertEquals("reference/manageAssignment", view);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView =
                    manageAssignmentController.saveOrUpdateAssignment(assignment, mockBindingResult, model, request);
            assertEquals("redirect:manageAssignment.htm", savedView);
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

        commonService.deleteAssignment(assignment);
        commonService.deleteGradeSubject(gradeSubject);
        commonService.deleteGrade(grade);
        commonService.deleteSubject(subject);
    }
}
