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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.BloodGroupValidator;

/**
 * This test class, tests all the functionality for the Blood Group object.
 *
 * @author Virtusa Corporation
 */
public class BloodGroupControllerTest extends BaseWebTest {

    /**
     * Represents an instance of BloodGroupController.
     */
    private BloodGroupController bloodGroupController;

    /**
     * Represents an instance of CommonService.
     */
    @Autowired
    private CommonService commonService;

    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;

    /**
     * Represents an instance of BloodGroup.
     */
    private BloodGroup bloodGroup;

    /**
     * Represents an instance of BloodGroupValidator.
     */
    @Autowired
    private BloodGroupValidator bloodGroupValidator;

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
        bloodGroupController = new BloodGroupController();

        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("bloodGroup");

        bloodGroupController.setCommonService(commonService);

    }

    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.BloodGroupController# saveOrUpdateBloodGroup
     * (org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.BloodGroup,
     * org.springframework.validation.BindingResult)}.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateBloodGroup() throws AkuraAppException {

        bloodGroupController.setBloodGroupValidator(bloodGroupValidator);
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        String view = bloodGroupController.saveOrUpdateBloodGroup(bloodGroup, mockBindingResult, model);
        assertEquals("reference/manageBloodGroup", view);
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        String savedView = bloodGroupController.saveOrUpdateBloodGroup(bloodGroup, mockBindingResult, model);
        assertEquals("redirect:manageBloodGroup.htm", savedView);

    }

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        try {
            String result = bloodGroupController.deleteBloodGroup(model, bloodGroup);

            assertNotNull("BloodGroup should not be null " + result);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }

    }

}
