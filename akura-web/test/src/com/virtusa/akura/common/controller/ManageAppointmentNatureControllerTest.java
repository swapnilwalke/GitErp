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

import com.virtusa.akura.api.dto.AppointmentNature;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.AppointmentNatureValidator;

/**
 * This test class, tests all the presentation related functionality for the Sport domain object.
 * 
 * @author Virtusa Corporation
 */
public class ManageAppointmentNatureControllerTest extends BaseWebTest {
    
    /**
     * Represents an instance of CreateSportController.
     */
    private AppointmentNatureController controller;
    
    /**
     * Represents an instance of CommonService.
     */
    @Autowired
    private StaffCommonService staffCommonService;
    
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
     * Represents an instance of Appointment nature.
     */
    private AppointmentNature appointmentNature;
    
    /** AppointmentNatureValidator attribute for holding appointmentnatureValidator. */
    @Autowired
    private AppointmentNatureValidator appointmentnatureValidator;
    
    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();
        
        appointmentNature = new AppointmentNature();
        appointmentNature.setDescription("AppointmentNatureTestDescription");
        
        model.addAttribute("appointmentNature", appointmentNature);
        
        controller = new AppointmentNatureController();
        controller.setStaffCommonService(staffCommonService);
    }
    
    /**
     * Test method for showAppointmentNature of CreateSportController.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowAppointmentNature() throws AkuraAppException {

        appointmentNature = staffCommonService.addAppointmentNature(appointmentNature);
        assertNotNull("Appointment Nature should not be null " + appointmentNature);
        
        request.addParameter("selectedAppointmentNatureId", "" + appointmentNature.getNatureId());
        
        String result = controller.showAppointmentNature(model);
        assertNotNull("ModelAndView should not be null", result);
        
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.AppointmentNatureController# 
     * saveOrUpdateAppointmentNature (org.springframework.ui.ModelMap, 
     * com.virtusa.sms.api.dto.AppointmentNature, 
     * org.springframework.validation.BindingResult)}
     * .
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    
    @Test
    public void testSaveOrUpdateAppointmentNature() throws AkuraAppException {

        try {
            controller.setAppointmentNatureValidator(appointmentnatureValidator);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
            String view = controller.saveOrUpdateAppointmentNature(request,model,appointmentNature, mockBindingResult);
            assertEquals("reference/manageAppointmentNature", view);
            Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
            String savedView =
                    controller.saveOrUpdateAppointmentNature(request,model,appointmentNature, mockBindingResult);
            assertEquals("redirect:manageSport.htm", savedView);
            assertTrue(model.size() > 0);
            request.addParameter("selectedSportId", "" + appointmentNature.getNatureId());
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
            String result = controller.deleteAppointmentNature(model,appointmentNature, request);
            assertNotNull("Appointment nature should not be null " + result);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
     

}
