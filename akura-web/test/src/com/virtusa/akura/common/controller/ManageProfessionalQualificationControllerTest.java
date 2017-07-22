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

import com.virtusa.akura.api.dto.ProfessionalQualification;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.ProfessionalQualificationValidator;

/**
 * @author Virtusa Corporation
 */

public class ManageProfessionalQualificationControllerTest extends BaseWebTest {

    /** Represents an instance of ProfessionalQualification. */
    private ProfessionalQualification professionalQualification;

    /** Represents an instance of ManageProfessionalQualificationController. */
    private ManageProfessionalQualificationController professionalQualificationController;

    /** Represents an instance of ProfessionalQualificationValidator. */
    @Autowired
    private ProfessionalQualificationValidator qualificationValidator;

    /** staffCommonService attribute for holding StaffCommonService. */
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

        professionalQualification = new ProfessionalQualification();
        professionalQualification.setDescription("New Professional");
        professionalQualification.setModifiedTime(new Date());

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();

        professionalQualificationController = new ManageProfessionalQualificationController();
        professionalQualificationController.setStaffCommonService(staffCommonService);
    }

    /**
     * Test method for ShowEducationalQualificationDetail of ManageEducationalQualificationController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowEducationalQualificationDetail() throws AkuraAppException {

        professionalQualification = staffCommonService.createProfessionalQualification(professionalQualification);
        assertNotNull("ProfessionalQualification type should not be null " + professionalQualification);

        model.addAttribute("professionalQualification", professionalQualification);

        request.addParameter("professionalQualificationId",
                "" + professionalQualification.getProfessionalQualificationId());

        String result = professionalQualificationController.showProfessionalQualificationDetail(model);
        assertNotNull("ModelAndView should not be null", result);

    }

    /**
     * Test method for SaveOrUpdateProfessionalQualification of ManageProfessionalQualificationController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateProfessionalQualification() throws AkuraAppException {

        try {
            model.addAttribute("professionalQualification", professionalQualification);
            professionalQualificationController.setProfessionalQualificationValidator(qualificationValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view =
                    professionalQualificationController.saveOrUpdateprofessionalQualification(
                            professionalQualification, mockBindingResult, request, model);
            assertEquals("reference/manageProfessionalQualification", view);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView =
                    professionalQualificationController.saveOrUpdateprofessionalQualification(
                            professionalQualification, mockBindingResult, request, model);
            assertEquals("redirect:manageProfessionalQualification.htm", savedView);
            assertTrue(model.size() > 0);

            request.addParameter("professionalQualificationId",
                    "" + professionalQualification.getProfessionalQualificationId());

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

            String result = professionalQualificationController.deleteProfessionalQualification(request, model);
            assertNotNull("ProfessionalQualification type should not be null " + result);

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
