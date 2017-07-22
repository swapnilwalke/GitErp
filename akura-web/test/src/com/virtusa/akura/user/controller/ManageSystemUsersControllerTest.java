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

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;

import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.UserService;

/**
 * @author Virtusa Corporation
 */
public class ManageSystemUsersControllerTest extends BaseWebTest {
    
    /** String attribute for holding LOGIN_COUNT. */
    private static final int LOGIN_COUNT = 3;
    
    /** String attribute for holding USER_LEVEL_LIST. */
    private static final String USER_LEVEL_LIST = "userLevelList";
    
    /** String attribute for holding MODEL_ATT_USER_DETAIL. */
    private static final String MODEL_ATT_USER_DETAIL = "userDetail";
    
    /** UsertService attribute for holding userService. */
    @Autowired
    private UserService userService;
    
    /** Holds manageSystemUsersController instance. */
    private ManageSystemUsersController manageSystemUsersController;
    
    /** Represents an instance of MockHttpSession. */
    private MockHttpSession session;
    
    /** Represents an instance of MockHttpServletRequest. */
    private MockHttpServletRequest request;
    
    /** Represents an instance of ModelMap. */
    private ModelMap model;
    
    /** Holds Language instance. */
    private UserLogin userLogin;
    
    /** Holds Language instance. */
    private UserLogin currentUserLogin;
    
    /** string constant for holding view name `Admin/manageSystemUsers`. */
    private static final String ADMIN_MANAGE_SYSTEM_USERS = "Admin/manageSystemUsers";
    
    /** String constant to hold redirect URL `redirect:manageSystemUsers.htm`. */
    private static final String REDIRECT_MANAGE_SYSTEM_USERS = "redirect:manageSystemUsers.htm";
    
    /** String attribute for holding VIEW_CREATE_SYSTEM_USER. */
    private static final String VIEW_CREATE_SYSTEM_USER = "reference/createSystemUser";
    
    /** String constant to hold request parameter `selectedUserStatus`. */
    private static final String SELECTED_USER_STATUS = "selectedUserStatus";
    
    /** string constant for holding `userLogin`. */
    private static final String USER_LOGIN = "userLogin";
    
    /** String constant to hold request parameter `selectedUserLoginId`. */
    private static final String SELECTED_USER_LOGIN_ID = "selectedUserLoginId";
    
    /** string constant for holding model attribute `userLoginList`. */
    private static final String USER_LOGIN_LIST = "userLoginList";
    
    /**
     * Represents the model name of the maximum number of the staff.
     */
    private static final String MAX_NUMBER = "maxNumber";
    
    /**
     * Represents the model name of the total number of records for staff.
     */
    private static final String NUMBER_OF_RECORDS = "numberOfRecords";
    
    /**
     * Name of the parameter name of the request for the start number for staff.
     */
    private static final String START_FROM = "startFrom";
    
    /**
     * Name of the parameter name of the request for the action.
     */
    private static final String ACTION_TYPE = "actionType";
    
    /**
     * Name for search.
     */
    private static final String SEARCH = "search";
    
    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        // initialize a UserLogin object.
        userLogin = new UserLogin();
        userLogin.setUserRoleId(2);
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
        
        // initialize a currentUserLogin object.
        currentUserLogin = new UserLogin();
        currentUserLogin.setUserRoleId(2);
        currentUserLogin.setFirstName("firstName");
        currentUserLogin.setLastName("lastName");
        currentUserLogin.setLoginAttempts(0);
        currentUserLogin.setEmail("current@email.com");
        currentUserLogin.setUserIdentificationNo("A00007");
        currentUserLogin.setUsername("currentUserName");
        currentUserLogin.setPassword("password");
        currentUserLogin.setUserLevel("2");
        currentUserLogin.setStatus(true);
        currentUserLogin.setModifiedTime(new Date());
        
        manageSystemUsersController = new ManageSystemUsersController();
        manageSystemUsersController.setUserService(userService);
        
        MockitoAnnotations.initMocks(this);
        model = new ModelMap();
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
    }
    
    /**
     * Test method for showManageSystemUsersView of ManageSystemUsersController.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowManageSystemUsersView() throws AkuraAppException {

        String view = manageSystemUsersController.showManageSystemUsersView(model);
        assertEquals(ADMIN_MANAGE_SYSTEM_USERS, view);
        
        assertTrue(model.containsAttribute(USER_LOGIN));
    }
    
    /**
     * Test method for searchSystemUser of ManageSystemUsersController.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    
    @Test
    public void testSearchSystemUser() throws AkuraAppException {

        try {
            // set selected userLoginId
            userService.createUser(userLogin, Integer.parseInt(userLogin.getUserIdentificationNo()));
            model.addAttribute(USER_LOGIN, userLogin);
            
            // set request parameters
            request.addParameter(START_FROM, "" + 0);
            request.addParameter(ACTION_TYPE, SEARCH);
            
            String view =
                    manageSystemUsersController.searchSystemUser((UserLogin) model.get(USER_LOGIN), request, model);
            
            assertEquals(ADMIN_MANAGE_SYSTEM_USERS, view);
            assertTrue(model.containsAttribute(NUMBER_OF_RECORDS));
            assertTrue(model.containsAttribute(START_FROM));
            assertTrue(model.containsAttribute(MAX_NUMBER));
            assertTrue(model.containsAttribute(USER_LOGIN_LIST));
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for deleteUserLogin of ManageSystemUsersController.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testDeleteUserLogin() throws AkuraAppException {

        try {
            // set selected userLoginId
            userService.createUser(userLogin, Integer.parseInt(userLogin.getUserIdentificationNo()));
            userLogin = userService.getUser(userLogin.getUsername());
            request.setParameter(SELECTED_USER_LOGIN_ID, "" + userLogin.getUserLoginId());
            
            // set current login user
            userService.createUser(currentUserLogin, Integer.parseInt(userLogin.getUserIdentificationNo()));
            currentUserLogin = userService.getUser(currentUserLogin.getUsername());
            session.setAttribute(USER_LOGIN, currentUserLogin);
            request.setSession(session);
            
            String view = manageSystemUsersController.deleteUserLogin(request, model);
            assertEquals(ADMIN_MANAGE_SYSTEM_USERS, view);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for updateUserStatus of ManageSystemUsersController.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testUpdateUserStatus() throws AkuraAppException {

        try {
            boolean oldUserStatus, newuserStatus;
            
            // set selected userLoginId
            userService.createUser(userLogin, Integer.parseInt(userLogin.getUserIdentificationNo()));
            userLogin = userService.getUser(userLogin.getUsername());
            request.setParameter(SELECTED_USER_LOGIN_ID, "" + userLogin.getUserLoginId());
            request.setParameter(SELECTED_USER_STATUS, "0");
            oldUserStatus = userLogin.isStatus();
            
            // set current login user
            userService.createUser(currentUserLogin, Integer.parseInt(userLogin.getUserIdentificationNo()));
            currentUserLogin = userService.getUser(currentUserLogin.getUsername());
            session.setAttribute(USER_LOGIN, currentUserLogin);
            request.setSession(session);
            
            String view = manageSystemUsersController.updateUserStatus(request, model);
            assertEquals(REDIRECT_MANAGE_SYSTEM_USERS, view);
            
            newuserStatus = userService.getAnyUser(userLogin.getUsername()).isStatus();
            assertTrue(oldUserStatus != newuserStatus);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for unlockUser of ManageSystemUsersController.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testUnlockUser() throws AkuraAppException {

        try {
            int loginAttempts;
            
            // set selected userLoginId
            userService.createUser(userLogin, Integer.parseInt(userLogin.getUserIdentificationNo()));
            userLogin.setLoginAttempts(LOGIN_COUNT);
            userService.updateUser(userLogin);
            userLogin = userService.getUser(userLogin.getUsername());
            request.setParameter(SELECTED_USER_LOGIN_ID, "" + userLogin.getUserLoginId());
            
            String view = manageSystemUsersController.unlockUser(request, model);
            assertEquals(REDIRECT_MANAGE_SYSTEM_USERS, view);
            
            loginAttempts = userService.getAnyUser(userLogin.getUsername()).getLoginAttempts();
            assertTrue(loginAttempts == 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for showManageSystemUsersView of ManageSystemUsersController.
     * 
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testEditSystemUser() throws AkuraAppException {

        userService.createUser(userLogin, Integer.parseInt(userLogin.getUserIdentificationNo()));
        userLogin = userService.getUser(userLogin.getUsername());
        // initiate request
        request.addParameter(SELECTED_USER_LOGIN_ID, userLogin.getUserLoginId() + "");
        
        String view = manageSystemUsersController.editSystemUser(request, model);
        assertEquals(VIEW_CREATE_SYSTEM_USER, view);
        
        assertTrue(model.containsAttribute(USER_LEVEL_LIST));
        assertTrue(model.containsAttribute(MODEL_ATT_USER_DETAIL));
        
        //without initialing request
        request.removeParameter(SELECTED_USER_LOGIN_ID);
        view = manageSystemUsersController.editSystemUser(request, model);
        assertEquals(REDIRECT_MANAGE_SYSTEM_USERS, view);
    }
    
    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        try {
            
            userService.deleteUser(userLogin);
            userService.deleteUser(currentUserLogin);
        } catch (AkuraException e) {
            e.printStackTrace();
        }
    }
}
