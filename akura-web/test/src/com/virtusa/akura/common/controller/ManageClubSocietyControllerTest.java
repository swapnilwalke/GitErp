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

import com.virtusa.akura.api.dto.ClubSociety;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.ClubSocietyValidator;

/**
 * This test class, tests all the presentation related functionality for the ClubSociety domain object.
 *
 * @author Virtusa Corporation
 */
public class ManageClubSocietyControllerTest extends BaseWebTest {

    /**
     * Represents an instance of ManageClubSocietyController.
     */
    private ManageClubSocietyController controller;

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
     * Represents an instance of ClubSociety.
     */
    private ClubSociety clubSociety;

    /** ClubSocietyValidator attribute for holding clubSocietyValidator. */
    @Autowired
    private ClubSocietyValidator clubSocietyValidator;



    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();

        clubSociety = new ClubSociety();
        clubSociety.setName("testClubSocietyName");
        clubSociety.setDescription("testClubSocietyDescription");

        model.addAttribute("clubSociety", clubSociety);

        controller = new ManageClubSocietyController();
        controller.setCommonService(commonService);
    }

    /**
     * Test method for ShowClubSocietyDetail of ManageClubSocietyController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowClubSocietyDetail() throws AkuraAppException {

        clubSociety = commonService.addClubSociety(clubSociety);
        assertNotNull("ClubSociety should not be null " + clubSociety);

        //request.addParameter("clubSocietyId", "" + clubSociety.getClubSocietyId());

        String result = controller.showClubSocietyDetail(model);
        assertNotNull("ModelAndView should not be null", result);

    }

    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.ManageClubSocietyController# saveOrUpdateClubSociety
     * (org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.ClubSociety,
     *  org.springframework.validation.BindingResult)}.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateClubSociety() throws AkuraAppException {
        try{
        controller.setClubSocietyValidator(clubSocietyValidator);
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        String view = controller.saveOrUpdateClubSociety(clubSociety, mockBindingResult, request, model);

        assertEquals("reference/manageClubSociety", view);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        String savedView = controller.saveOrUpdateClubSociety(clubSociety, mockBindingResult, request, model);

        assertEquals("redirect:manageClubSociety.htm", savedView);
        assertTrue(model.size() > 0);

        request.addParameter("clubSocietyId", "" + clubSociety.getClubSocietyId());
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
            String result =  controller.deleteClubSociety(clubSociety, request, model);
            assertNotNull("ClubSociety should not be null " + result);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }

    }

}
