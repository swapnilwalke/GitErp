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

import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.SportValidator;

/**
 * This test class, tests all the presentation related functionality for the Sport domain object.
 *
 * @author Virtusa Corporation
 */
public class CreateSportControllerTest extends BaseWebTest {

    /**
     * Represents an instance of CreateSportController.
     */
    private CreateSportController controller;

    /**
     * Represents an instance of CommonService.
     */
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
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;

    /**
     * Represents an instance of Sport.
     */
    private Sport sport;

    /** SportValidator attribute for holding sportValidator. */
    @Autowired
    private SportValidator sportValidator;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();

        sport = new Sport();
        sport.setDescription("sportTestDescription");

        model.addAttribute("sport", sport);

        controller = new CreateSportController();
        controller.setCommonService(commonService);
    }

    /**
     * Test method for ShowSportDetail of CreateSportController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowSportDetail() throws AkuraAppException {

        sport = commonService.addSport(sport);
        assertNotNull("Sport should not be null " + sport);

        request.addParameter("selectedSportId", "" + sport.getSportId());

        String result = controller.showSportDetail(model);
        assertNotNull("ModelAndView should not be null", result);

    }

    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.CreateSportController# saveOrUpdateSport 
     * (org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.Sport, org.springframework.validation.BindingResult)}.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateSport() throws AkuraAppException {

        try {
            controller.setSportValidator(sportValidator);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view = controller.saveOrUpdateSport(sport, mockBindingResult, request, model);

            assertEquals("reference/createSport", view);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView = controller.saveOrUpdateSport(sport, mockBindingResult, request, model);

            assertEquals("redirect:manageSport.htm", savedView);
            assertTrue(model.size() > 0);
            request.addParameter("selectedSportId", "" + sport.getSportId());

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
            String result = controller.deleteSport(sport, request, model);
            assertNotNull("Sport should not be null " + result);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }

    }

}
