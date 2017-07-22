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

package com.virtusa.akura.user.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.UserRoleDao;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.user.validator.UserRoleValidator;

/**
 * @author Virtusa Corporation
 */
public class ManageUserRolesControllerTest extends BaseWebTest {
    
    /** UsertService attribute for holding userService. */
    @Autowired
    private UserService userService;
    
    /** Holds ManageUserRolesController instance. */
    private ManageUserRolesController manageUserRolesController;
    
    /** Represents an instance of the userRoleValidator. */
    @Autowired
    private UserRoleValidator userRoleValidator;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private UserRoleDao userRoleDao;
    
    /**
     * Represents an instance of UserRole.
     */
    private UserRole userRole;
    
    /** Represents an instance of MockHttpSession. */
    private MockHttpSession session;
    
    /** Represents an instance of MockHttpServletRequest. */
    private MockHttpServletRequest request;
    
    /** Represents an instance of ModelMap. */
    private ModelMap model;
    
    /** String constant to hold view `manageUserRole`. */
    private static final String MANAGE_USER_ROLE_VIEW = "manageUserRole";
    
    /** String constant to hold view `userRoleDetails`. */
    private static final String USER_ROLE_DETAILS_VIEW = "userRoleDetails";
    
    /** String constant to hold `userRole`. */
    private static final String USER_ROLE = "userRole";
    
    /** String constant for hold model attribute `moduleList`. */
    private static final String MODULE_LIST = "moduleList";
    
    /** The id of the user role. */
    private static final String USER_ROLE_ID = "userRoleId";
    
    /** String constant for hold model attribute `rolePrivilegeList`. */
    private static final String ROLE_PRIVILEGE_LIST = "rolePrivilegeList";
    
    /** view post method manage user roles. */
    private static final String REDIRECT_MANAGE_USER_ROLES_HTM = "redirect:manageUserRoles.htm";
    
    /** String constant for hold model attribute `privilegeList`. */
    private static final String PRIVILEGE_LIST = "privilegeList";
    
    /** String constant for hold model attribute `roleList`. */
    private static final String ROLE_LIST = "roleList";
    
    /** String constant for hold request parameter 'selectedRole'. */
    private static final String SELECTED_ROLE = "selectedRole";
    
    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;
    
    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        // Instantiates a UserRole object.
        UserRole newUserRole = new UserRole();
        newUserRole.setRole("RolePrivilegeDaoImplTest :TestUserRole Name");
        newUserRole.setDescription("RolePrivilegeDaoImplTest :TestUserRole description");
        userRole = userRoleDao.save(newUserRole);
        assertNotNull("UserRole saved to db ", userRole);
        
        manageUserRolesController = new ManageUserRolesController();
        manageUserRolesController.setUserService(userService);
        
        MockitoAnnotations.initMocks(this);
        model = new ModelMap();
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
    }
    
    /**
     * Test method for showManageUserRolesView of ManageUserRolesController.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowManageUserRolesView() throws AkuraAppException {

        String view = manageUserRolesController.showManageUserRolesView(model);
        assertEquals(MANAGE_USER_ROLE_VIEW, view);
        
        assertTrue(model.containsAttribute(USER_ROLE));
    }
    
    /**
     * Test method for newUserRoleDetailsForm of ManageUserRolesController.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testNewUserRoleDetailsForm() throws AkuraAppException {

        String view = manageUserRolesController.newUserRoleDetailsForm(request, model);
        assertEquals(USER_ROLE_DETAILS_VIEW, view);
        
        assertTrue(model.containsAttribute(USER_ROLE));
        assertTrue(model.containsAttribute(MODULE_LIST));
    }
    
    /**
     * Test method for showUserRoleDetails of ManageUserRolesController.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowUserRoleDetails() throws AkuraAppException {

        // test with user role in session
        session.setAttribute(USER_ROLE_ID, userRole.getUserRoleId());
        
        String view = manageUserRolesController.showUserRoleDetails(request, model, session);
        assertEquals(USER_ROLE_DETAILS_VIEW, view);
        
        assertTrue(model.containsAttribute(USER_ROLE));
        assertTrue(model.containsAttribute(ROLE_PRIVILEGE_LIST));
        assertTrue(model.containsAttribute(MODULE_LIST));
        
        // test without user role in session
        session.clearAttributes();
        view = manageUserRolesController.showUserRoleDetails(request, model, session);
        assertEquals(REDIRECT_MANAGE_USER_ROLES_HTM, view);
    }
    
    /**
     * Test method for saveOrUpdateUserRole of ManageUserRolesController.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateUserRole() throws AkuraAppException {

        manageUserRolesController.setUserRoleValidator(userRoleValidator);
        request.addParameter(PRIVILEGE_LIST, "");
        request.addParameter(ROLE_LIST, "");
        
        // test with validator errors
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(Boolean.TRUE);
        
        String view = manageUserRolesController.saveOrUpdateUserRole(model, request, userRole, mockBindingResult);
        assertEquals(USER_ROLE_DETAILS_VIEW, view);
        assertTrue(model.containsAttribute(MODULE_LIST));
        
        // test without validator errors
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(Boolean.FALSE);
        view = manageUserRolesController.saveOrUpdateUserRole(model, request, userRole, mockBindingResult);
        assertEquals(REDIRECT_MANAGE_USER_ROLES_HTM, view);
    }
    
    /**
     * Test method for deleteUserRole of ManageUserRolesController.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testDeleteUserRole() throws AkuraAppException {

        // Instantiates a UserRole object.
        UserRole newUserRole = new UserRole();
        newUserRole.setRole("RolePrivilegeDaoImplTest :TestUserRole Name");
        newUserRole.setDescription("RolePrivilegeDaoImplTest :TestUserRole description");
        newUserRole = userRoleDao.save(newUserRole);
        request.addParameter(SELECTED_ROLE, newUserRole.getUserRoleId() + "");
        
        String view = manageUserRolesController.deleteUserRole(request, model);
        assertEquals(REDIRECT_MANAGE_USER_ROLES_HTM, view);
    }
    
    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        try {
            userRoleDao.delete(userRole);
        } catch (AkuraException e) {
            e.printStackTrace();
        }
    }
    
}
