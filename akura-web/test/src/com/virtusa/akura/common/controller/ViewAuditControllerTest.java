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

import com.virtusa.akura.api.dto.AuditEventType;
import com.virtusa.akura.api.dto.BusinessAudit;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.auditing.service.AuditManagerService;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.UserLoginDao;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.common.validator.ViewAuditDetailsValidator;

/**
 * This test class, tests all the functionality for the View Audit.
 *
 * @author Virtusa Corporation
 */
public class ViewAuditControllerTest extends BaseWebTest {

    /**
     * Represents an instance of ViewAuditController.
     */
    private ViewAuditController  viewAuditController;

    /** Represents commonService instance of {@link UserService}. */
    @Autowired
    private UserService userService;
    
    /** Represents commonService instance of {@link AuditManagerService}. */
    @Autowired
    private AuditManagerService auditManagerService;
    
    /**
     * Represents an instance of UserLoginDao.
     */
    @Autowired
    private UserLoginDao userLoginDao;
   
    /**
     * Represents an instance of UserLogin.
     */
    private UserLogin userLogin;
    
    /**
     * Represents an instance of MockHttpServletRequest.
     */
    private MockHttpServletRequest request;

    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;

    /**
     * Represents an instance of BusinessAudit.
     */
    private BusinessAudit businessAudit;

    /**
     * Represents an instance of ViewAuditDetailsValidator.
     */
    @Autowired
    private ViewAuditDetailsValidator viewAuditDetailsValidator;

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
        businessAudit = new BusinessAudit();
        model = new ModelMap();
        request = new MockHttpServletRequest();
        viewAuditController = new ViewAuditController();
        viewAuditController.setAuditManagerService(auditManagerService);
        viewAuditController.setUserService(userService);
        viewAuditController.setViewAuditDetailsValidator(viewAuditDetailsValidator);
        
        userLogin = new UserLogin();
        userLogin.setUserRoleId(1);
        userLogin.setFirstName("firstName");
        userLogin.setLastName("lastName");
        userLogin.setLoginAttempts(0);
        userLogin.setEmail("userLogin@email.com");
        userLogin.setUserIdentificationNo("A00006");
        userLogin.setUsername("newUserName");
        userLogin.setPassword("password");
        userLogin.setUserLevel("2");
        userLogin.setStatus(true);
        userLogin.setModifiedTime(new Date());
        userLoginDao.save(userLogin);
        assertNotNull(userLogin);
        
        AuditEventType auditEventType = new AuditEventType();
        auditEventType.setAuditEventTypeId(1);
        
        businessAudit = new BusinessAudit();
        businessAudit.setBusinessFunction("businessFunctionValue");
        businessAudit.setDate(new Date());
        businessAudit.setEventType(auditEventType);
        businessAudit.setModule("moduleValue");
        businessAudit.setUserLogin(userLogin);
        businessAudit.setModifiedTime(new Date());
        businessAudit.setFromDate("");
        businessAudit.setToDate("");
        
        
        model.addAttribute("businessAudit", businessAudit);
        request.addParameter("actionType", "search");
        request.addParameter("startFrom", "0");
        
    }

    /**
     * Test method for searchAuditDetails.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateMethodOfTravel() throws AkuraAppException {

        
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        String view =
                viewAuditController.searchAuditDetails(model, businessAudit, mockBindingResult, request);
        assertEquals("reference/viewAuditDetails", view);
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        String savedView =
                viewAuditController.searchAuditDetails(model, businessAudit, mockBindingResult, request);
        assertEquals("reference/viewAuditDetails", savedView);
        
    }

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {
        userLoginDao.delete(userLogin);

    }

}
