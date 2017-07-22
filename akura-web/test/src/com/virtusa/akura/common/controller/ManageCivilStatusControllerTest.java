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

import com.virtusa.akura.api.dto.CivilStatus;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.CivilStatusValidator;

/**
 * @author Virtusa Corporation
 */

public class ManageCivilStatusControllerTest extends BaseWebTest {

    /** Represents an instance of CivilStatus. */
    private CivilStatus civilStatus;

    /** Represents an instance of ManageCivilStatusController. */
    private ManageCivilStatusController civilStatusController;

    /** Represents an instance of CivilStatusValidator. */
    @Autowired
    private CivilStatusValidator statusValidator;

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

        civilStatus = new CivilStatus();
        civilStatus.setDescription("New Professional");
        civilStatus.setModifiedTime(new Date());

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();

        civilStatusController = new ManageCivilStatusController();
        civilStatusController.setStaffCommonService(staffCommonService);
    }

    
    /**
     * Test method for SaveOrUpdateCivilStatus of ManageCivilStatusController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateCivilStatus() throws AkuraAppException {

        try {
            model.addAttribute("civilStatus", civilStatus);
            civilStatusController.setCivilStatusValidator(statusValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view =
                    civilStatusController.saveOrUpdateCivilStatus(
                            civilStatus, mockBindingResult, request, model);
            assertEquals("reference/manageCivilStatus", view);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView =
                    civilStatusController.saveOrUpdateCivilStatus(
                            civilStatus, mockBindingResult, request, model);
            assertEquals("redirect:manageCivilStatus.htm", savedView);
            assertTrue(model.size() > 0);

            request.addParameter("civilStatusId",
                    "" + civilStatus.getCivilStatusId());

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

            String result = civilStatusController.deleteCivilStatus(model,civilStatus);
            assertNotNull("CivilStatus should not be null " + result);

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
