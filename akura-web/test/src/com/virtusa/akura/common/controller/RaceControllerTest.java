/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import com.virtusa.akura.api.dto.Race;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.RaceValidator;

/**
 * This test class, tests all the functionality for the Race object.
 *
 * @author Virtusa Corporation
 */
public class RaceControllerTest extends BaseWebTest {

    /**
     * Represents an instance of RaceController.
     */
    private RaceController raceController;

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
     * Represents an instance of Race.
     */
    private Race race;

    /**
     * Represents an instance of RaceValidator.
     */
    @Autowired
    private RaceValidator raceValidator;

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
        model = new ModelMap();
        request = new MockHttpServletRequest();
        
        race = new Race();
        race.setDescription("race");
        
        model.addAttribute("race", race);
        
        raceController = new RaceController();
        raceController.setCommonService(commonService);

    }

    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.RaceController# saveOrUpdateRace 
     * (org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.Race, org.springframework.validation.BindingResult)}.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testsaveOrUpdateRace() throws AkuraAppException {

        try {
            raceController.setRaceValidator(raceValidator);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view = raceController.saveOrUpdateRace(race, mockBindingResult, request, model);

            assertEquals("reference/manageRace", view);

            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView = raceController.saveOrUpdateRace(race, mockBindingResult, request, model);

            assertEquals("redirect:manageRace.htm", savedView);
            assertTrue(model.size() > 0);
            request.addParameter("selectedRaceId", "" + race.getRaceId());

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
            String result = raceController.deleteRace(model, race);

            assertNotNull("Race should not be null " + result);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }

    }

}
