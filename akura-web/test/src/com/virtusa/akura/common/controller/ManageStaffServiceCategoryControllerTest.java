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

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.StaffServiceCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.StaffServiceCategoryValidator;

/**
 * This test class, tests all the presentation related functionality for the Staff Service Category domain
 * object.
 * 
 * @author Virtusa Corporation
 */
public class ManageStaffServiceCategoryControllerTest extends BaseWebTest {
    
    /**
     * Represents an instance of ManageStaffCategoryController.
     */
    private ManageStaffServiceCategoryController controller;
    
    /**
     * Represents an instance of CommonService.
     */
    @Autowired
    private StaffCommonService staffCommonService;
    
    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;
    
    /**
     * Represents an instance of HttpServletRequest.
     */
    private HttpServletRequest request;
    
    /**
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;
    
    /**
     * Represents an instance of SteffServiceCategory.
     */
    private StaffServiceCategory staffServiceCategory;
    
    /** StaffServiceCategoryValidator attribute for holding clubSocietyValidator. */
    @Autowired
    private StaffServiceCategoryValidator staffServiceCategoryValidator;
    
    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        
        staffServiceCategory = new StaffServiceCategory();
        staffServiceCategory.setDescription("testStaffServiceCategory");
        
        model.addAttribute("staffServiceCategory", staffServiceCategory);
        
        controller = new ManageStaffServiceCategoryController();
        controller.setStaffCommonService(staffCommonService);
        
    }
    
    /**
     * Test method for saveOrUpdateStaffServiceCategory() method.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateStaffServiceCategory() throws AkuraAppException {

        try {
            
            controller.setStaffServiceCategoryValidator(staffServiceCategoryValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);            
            String view =
                    controller
                            .saveOrUpdateStaffServiceCategory(staffServiceCategory, mockBindingResult, model,request);
            
            assertEquals("reference/manageStaffServiceCategory", view);            
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            
            String savedView =
                    controller
                            .saveOrUpdateStaffServiceCategory(staffServiceCategory, mockBindingResult, model,request);
            
            assertEquals("redirect:manageStaffServiceCategory.htm", savedView);            
            assertTrue(model.size() > 0);
            
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }        
    }
    
    /**
     * Test method for ShowStaffServiceCategoryDetails of ManageStaffServiceCategoryController.
     * 
     * @throws AkuraAppException when an error has occurred during processing
     */
    @Test
    public void testShowStaffServiceCategoryDetails() throws AkuraAppException {

        staffServiceCategory = staffCommonService.addStaffServiceCategory(staffServiceCategory);
        assertNotNull("Staff Service Category Not be null" + staffServiceCategory);
        
        String result = controller.showStaffServiceCategoryDetails(model);
        assertNotNull("Model And View should not be null", result);        
    }
    
    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        try {
            
            String result = controller.deleteStaffServiceCategory(staffServiceCategory, model);            
            assertNotNull("Staff Service Category should not be null " + result);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }        
    }    
}
