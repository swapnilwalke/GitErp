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

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.EmploymentStatus;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.EmploymentStatusValidator;


/**
 * This test class, tests all the presentation related functionality for the Employment Status domain
 * object.
 * 
 * @author VIRTUSA
 *
 */
public class ManageEmploymentStatusControllerTest {
    
    /**
     * Represents an instance of ManageEmploymentStatusController.
     */
    private ManageEmploymentStatusController controller;
    
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
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;
    
    /**
     * Represents an instance of EmploymentStatus.
     */
    private EmploymentStatus employmentStatus;
    
    /** EmploymentStatusValidator attribute for holding clubSocietyValidator. */
    @Autowired
    private EmploymentStatusValidator employmentStatusValidator;
    
    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        
        employmentStatus = new EmploymentStatus();
        employmentStatus.setDescription("testEmploymentStatus");
        
        model.addAttribute("employmentStatus", employmentStatus);
        
        controller = new ManageEmploymentStatusController();
        controller.setCommonService(commonService);
    }
    
   
    /**
     * Test method for ShowStaffServiceCategoryDetails of ManageStaffServiceCategoryController.
     * 
     * @throws AkuraAppException when an error has occurred during processing 
     */
    @Test
    public void testShowEmploymentStatus() throws AkuraAppException {

        employmentStatus = commonService.addEmploymentStatus(employmentStatus);
        assertNotNull("Employment Status Not be null" + employmentStatus);
        
        String result = controller.showEmploymentStatus(model);
        assertNotNull("Model And View should not be null", result);   
    }
    
    /**
     * Test method for saveOrUpdateEmploymentStatus() method.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateEmploymentStatus() throws AkuraAppException {

        try {
            
            controller.setEmploymentStatusValidator(employmentStatusValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);            
            String view =
                    controller.saveOrUpdateEmploymentStatus(employmentStatus, mockBindingResult, model);
            
            assertEquals("reference/manageEmploymentStatus", view);            
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            
            String savedView =
                    controller.saveOrUpdateEmploymentStatus(employmentStatus, mockBindingResult, model);
            
            assertEquals("reference/manageEmploymentStatus", savedView);            
            assertTrue(model.size() > 0);
            
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
            
            String result = controller.deleteEmploymentStatus(model,employmentStatus);            
            assertNotNull("Employment Status should not be null " + result);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }   
    }
}
