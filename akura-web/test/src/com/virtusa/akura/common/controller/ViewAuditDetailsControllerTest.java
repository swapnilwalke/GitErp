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
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.auditing.service.AuditManagerService;
import com.virtusa.akura.common.BaseWebTest;

/**
 * This test class, tests all the functionality for the View Audit.
 *
 * @author Virtusa Corporation
 */
public class ViewAuditDetailsControllerTest extends BaseWebTest {

    /**
     * Represents an instance of ViewAuditDetailsController.
     */
    private ViewAuditDetailsController  viewAuditDetailsController;
   
    /** Represents commonService instance of {@link AuditManagerService}. */
    @Autowired
    private AuditManagerService auditManagerService;
    
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
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();
        viewAuditDetailsController = new ViewAuditDetailsController();
        viewAuditDetailsController.setAuditManagerService(auditManagerService);
        
        request.addParameter("auditDetailId", "1");
        
    }

    /**
     * Test method for showMoreDetailsForm.
     * 
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowMoreDetailsForm() throws AkuraAppException {

        String savedView =
                viewAuditDetailsController.showMoreDetailsForm(model, request);
        assertEquals("reference/moreAuditDetails", savedView);
        
    }

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {
       

    }

}
