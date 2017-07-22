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

import com.virtusa.akura.api.dto.EducationalQualification;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.EducationalQualificationValidator;

/**
 * @author Virtusa Corporation
 */

public class EducationalQualificationControllerTest extends BaseWebTest {

    /** Represents an instance of EducationalQualification. */
    private EducationalQualification educationalQualification;

    /** Represents an instance of EducationalQualificationController. */
    private ManageEducationalQualificationController educationalQualificationController;

    /** Represents an instance of EducationalQualificationValidator. */
    @Autowired
    private EducationalQualificationValidator educationalQualificationValidator;

    /** CommonService attribute for holding commonService. */
    @Autowired
    private StaffCommonService staffCommonService;

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

        educationalQualification = new EducationalQualification();
        educationalQualification.setDescription("BSc in IT");
        educationalQualification.setModifiedTime(new Date());

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        model.addAttribute("educationalQualification", educationalQualification);
        request = new MockHttpServletRequest();

        educationalQualificationController = new ManageEducationalQualificationController();
        educationalQualificationController.setStaffCommonService(staffCommonService);
    }

    /**
     * Test method for ShowEducationalQualificationDetail of ManageEducationalQualificationController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowEducationalQualificationDetail() throws AkuraAppException {

        educationalQualification = staffCommonService.createEducationalQualification(educationalQualification);
        assertNotNull("EducationalQualification type should not be null " + educationalQualification);
        // model.addAttribute("educationalQualification", educationalQualification);
        request.addParameter("educationalQualificationId",
                "" + educationalQualification.getEducationalQualificationId());

        String result = educationalQualificationController.showEducationalQualificationDetail(model);
        assertNotNull("ModelAndView should not be null", result);

    }

    /**
     * Test method for SaveOrUpdateEducationalQualification of ManageEducationalQualificationController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateEducationalQualification() throws AkuraAppException {

        try {

            educationalQualificationController.setEducationalQualificationValidator(educationalQualificationValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view =
                    educationalQualificationController.saveOrUpdateEducationalQualification(educationalQualification,
                            mockBindingResult, request, model);
            assertEquals("reference/manageEducationalQualification", view);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView =
                    educationalQualificationController.saveOrUpdateEducationalQualification(educationalQualification,
                            mockBindingResult, request, model);
            assertEquals("redirect:manageEducationalQualification.htm", savedView);
            assertTrue(model.size() > 0);

            request.addParameter("educationalQualificationId",
                    "" + educationalQualification.getEducationalQualificationId());

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }

    }

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        try {
            String result = educationalQualificationController.deleteEducationalQualification(request, model);
             assertNotNull("EducationalQualification type should not be null " + result);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

}
