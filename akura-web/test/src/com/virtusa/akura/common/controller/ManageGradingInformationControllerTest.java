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

import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.GradingValidator;

/**
 * @author Virtusa Corporation
 */

public class ManageGradingInformationControllerTest extends BaseWebTest {

    /** commonService attribute for holding commonService. */
    @Autowired
    private CommonService commonService;

    /** Holds gradingValidator instance of {@link GradingValidator}. */
    @Autowired
    private GradingValidator gradingValidator;

    /** Represents an instance of ManageGradingInformationController. */
    private ManageGradingInformationController gradingInformationController;

    /** Represents an instance of Grading. */
    private Grading grading;

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

        grading = new Grading();
        grading.setDescription("GradingInfo");
        grading.setGradeAcronym("X");
        grading.setModifiedTime(new Date());

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();

        gradingInformationController = new ManageGradingInformationController();
        gradingInformationController.setCommonService(commonService);
    }

    /**
     * Test method for ShowInformationDetail of ManageGradingInformationController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowInformationDetail() throws AkuraAppException {

        grading = commonService.createGrading(grading);
        assertNotNull("Grading type should not be null " + grading);

        model.addAttribute("grading", grading);
        request.addParameter("gradingId", "" + grading.getGradingId());

        String result = gradingInformationController.showGradingInformation(model);
        assertNotNull("ModelAndView should not be null", result);

    }

    /**
     * Test method for SaveOrUpdateProfessionalQualification of ManageProfessionalQualificationController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateGrading() throws AkuraAppException {

        try {
            model.addAttribute("grading", grading);
            gradingInformationController.setGradingValidator(gradingValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view = gradingInformationController.saveOrUpdateGrading(grading, mockBindingResult, request, model);

            assertEquals("reference/manageGradingInformation", view);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView =
                    gradingInformationController.saveOrUpdateGrading(grading, mockBindingResult, request, model);
            assertEquals("redirect:manageGradingInformation.htm", savedView);
            assertTrue(model.size() > 0);

            request.addParameter("gradingId", "" + grading.getGradingId());

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
            String result = gradingInformationController.deleteGrading(request, model);
            assertNotNull("ProfessionalQualification type should not be null " + result);

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
