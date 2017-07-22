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

import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.MethodOfTravelValidator;

/**
 * This test class, tests all the functionality for the Method Of Travel object.
 *
 * @author Virtusa Corporation
 */
public class ManageMethodOfTravelControllerTest extends BaseWebTest {

    /**
     * Represents an instance of ManageMethodOfTravelController.
     */
    private ManageMethodOfTravelController manageMethodOfTravelController;

    /** Represents commonService instance of {@link CommonService}. */
    @Autowired
    private CommonService commonService;

    /**
     * Represents an instance of MockHttpServletRequest.
     */
    private MockHttpServletRequest request;

    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;

    /**
     * Represents an instance of MethodOfTravel.
     */
    private MethodOfTravel methodOfTravel;

    /**
     * Represents an instance of MethodOfTravelValidator.
     */
    @Autowired
    private MethodOfTravelValidator methodOfTravelValidator;

    /**
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        methodOfTravel = new MethodOfTravel();
        model = new ModelMap();
        request = new MockHttpServletRequest();
        manageMethodOfTravelController = new ManageMethodOfTravelController();
        methodOfTravel.setTravelMethod("method");
        manageMethodOfTravelController.setCommonService(commonService);
    }

    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.ManageMethodOfTravelController# saveOrUpdateMethodOfTravel(
     * org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.ManageMethodOfTravelController, 
     * org.springframework.validation.BindingResult)}
     * .
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateMethodOfTravel() throws AkuraAppException {

        manageMethodOfTravelController.setMethodOfTravelValidator(methodOfTravelValidator);
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        String view =
                manageMethodOfTravelController.saveOrUpdateMethodOfTravel(methodOfTravel, mockBindingResult, request,
                        model);
        assertEquals("reference/manageMethodOfTravel", view);
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        String savedView =
                manageMethodOfTravelController.saveOrUpdateMethodOfTravel(methodOfTravel, mockBindingResult, request,
                        model);
        assertEquals("redirect:manageMethodOfTravel.htm", savedView);
        request.addParameter("travelId", "" + methodOfTravel.getTravelId());
    }

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        manageMethodOfTravelController.deleteMethodOfTravel(request, model);

    }

}
