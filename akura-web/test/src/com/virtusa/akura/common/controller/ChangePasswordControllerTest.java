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

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.SecurityQuestions;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.UserSecurityQuestions;
import com.virtusa.akura.api.dto.WrapperChangePassword;
import com.virtusa.akura.api.dto.WrapperSecurityQuestions;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.SecurityQuestionsDao;
import com.virtusa.akura.common.dao.UserSecurityQuestionsDao;
import com.virtusa.akura.common.service.EmailService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.common.validator.UserPasswordValidator;
import com.virtusa.akura.common.validator.UserSecurityQuestionsValidator;

/**
 * This unit test class test the ChangePasswordController class methods.
 * 
 * @author VIRTUSA corporation.
 */
public class ChangePasswordControllerTest extends BaseWebTest {
    
    /** Represents an instance of the userService. */
    @Autowired
    private UserService userService;
    
    /** Represents an instance of the emailServices. */
    @Autowired
    private EmailService emailServices;
    
    /** Represents an instance of the changePasswordController. */
    private ChangePasswordController changePasswordController;
    
    /** Represents an instance of the session. */
    private MockHttpSession session;
    
    /** Represents an instance of the request. */
    private MockHttpServletRequest request;
    
    /** Represents an instance of the model. */
    private ModelMap model;
    
    /** Represents an instance of the UserLogin. */
    private UserLogin user;
    
    /** Represents an instance of the securityQuestionsDao. */
    @Autowired
    private SecurityQuestionsDao securityQuestionsDao;
    
    /** Represents an instance of the userSecurityQuestionsDao. */
    @Autowired
    private UserSecurityQuestionsDao userSecurityQuestionsDao;
    
    /** Represents an instance of the securityQuestionsOne. */
    private SecurityQuestions securityQuestionsOne;
    
    /** Represents an instance of the securityQuestionsTwo. */
    private SecurityQuestions securityQuestionsTwo;
    
    /** Represents an instance of the userSecurityQuestionOne. */
    private UserSecurityQuestions userSecurityQuestionOne;
    
    /** Represents an instance of the userSecurityQuestionSubmitOne. */
    private UserSecurityQuestions userSecurityQuestionSubmitOne;
    
    /** Represents an instance of the userSecurityQuestionSubmitTwo. */
    private UserSecurityQuestions userSecurityQuestionSubmitTwo;
    
    /** Represents an instance of the userSecurityQuestionTwo. */
    private UserSecurityQuestions userSecurityQuestionTwo;
    
    /** Represents an instance of the WrapperSecurityQuestions. */
    private WrapperSecurityQuestions submitAnswers;
    
    /** Represents an instance of WrapperChangePassword. */
    private WrapperChangePassword wrapperChangePassword;
    
    /** Represents an instance of userPasswordValidator. */
    @Autowired
    private UserPasswordValidator userPasswordValidator;
    
    /** Represents an instance of userSecurityQuestionsValidator. */
    @Autowired
    private UserSecurityQuestionsValidator userSecurityQuestionsValidator;
    
    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;
    
    // /** Represents an instance of commonEmailBeans. */
    // @Autowired
    // private CommonEmailBean commonEmailBeans;
    
    /** Constant for string "user". */
    private static final String USER = "user";
    
    /** Constant for string "defaultURL". */
    private static final String DEFAULT_URL = "defaultURL";
    
    /** Constant for string "new". */
    private static final String NEW = "new";
    
    /** Constant for string "userName". */
    private static final String USER_NAME = "userName";
    
    /** Constant for string "changePassword". */
    private static final String CHANGE_PASSWORD = "changePassword";
    
    /** Constant for string "userLogin". */
    private static final String USER_LOGIN = "userLogin";
    
    /** Constant for string "answerSecurityQuestions". */
    private static final String ANSWER_SECURITY_QUESTIONS = "answerSecurityQuestions";
    
    /** Constant for string "redirect:login.htm". */
    private static final String REDIRECT_LOGIN_HTM = "redirect:login.htm";
    
    /** Constant for string "UserLoginByName". */
    private static final String USER_LOGIN_BY_NAME = "UserLoginByName";
    
    /** Constant for string "test". */
    private static final String TEST = "test";
    
    /** Constant for string "lastName". */
    private static final String LAST_NAME = "lastName";
    
    /** Constant for string "testAdmin". */
    private static final String TEST_ADMIN = "testAdmin";
    
    /** Represents an instance of the "virtusa@virtusa.com". */
    private static final String VIRTUSA_VIRTUSA_COM = "virtusa@virtusa.com";
    
    /** Constant for string "Test Question Two?". */
    private static final String TEST_QUSTION_TWO = "Test Qustion Two?";
    
    /** Constant for string "Test Question One?". */
    private static final String TEST_QUESTION_ONE = "Test Question One?";
    
    /** Constant for string "Answer Two". */
    private static final String ANSWER_TWO = "Answer Two";
    
    /** Constant for string "Answer One". */
    private static final String ANSWER_ONE = "Answer One";
    
    /**
     * Execute this setup method before every test method.
     * 
     * @throws AkuraAppException throws when fails.
     */
    @Before
    public void setUp() throws AkuraAppException {

        changePasswordController = new ChangePasswordController();
        changePasswordController.setUserService(userService);
        
        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        
        session = new MockHttpSession();
        request = new MockHttpServletRequest();
        
        model = new ModelMap();
        
        user = new UserLogin();
        user.setUserRoleId(1);
        user.setFirstName(TEST_ADMIN);
        user.setLastName(LAST_NAME);
        user.setUsername(TEST);
        user.setPassword(TEST);
        user.setStatus(true);
        user.setEmail(VIRTUSA_VIRTUSA_COM);
        userService.createUser(user, Integer.parseInt(user.getUserIdentificationNo()));
        assertNotNull(user);
        
        securityQuestionsOne = new SecurityQuestions();
        securityQuestionsOne.setSecurityQuestion(TEST_QUESTION_ONE);
        securityQuestionsDao.save(securityQuestionsOne);
        assertNotNull(securityQuestionsOne);
        
        securityQuestionsTwo = new SecurityQuestions();
        securityQuestionsTwo.setSecurityQuestion(TEST_QUSTION_TWO);
        securityQuestionsDao.save(securityQuestionsTwo);
        assertNotNull(securityQuestionsTwo);
        
        userSecurityQuestionOne = new UserSecurityQuestions();
        userSecurityQuestionOne.setSecurityQuestions(securityQuestionsOne);
        userSecurityQuestionOne.setAnswer(ANSWER_ONE);
        userSecurityQuestionOne.setUserLogin(user);
        userService.createSystemUserSecurityQuestion(userSecurityQuestionOne);
        assertNotNull(userSecurityQuestionOne);
        userSecurityQuestionTwo = new UserSecurityQuestions();
        
        userSecurityQuestionTwo = new UserSecurityQuestions();
        userSecurityQuestionTwo.setSecurityQuestions(securityQuestionsTwo);
        userSecurityQuestionTwo.setAnswer(ANSWER_TWO);
        userSecurityQuestionTwo.setUserLogin(user);
        userService.createSystemUserSecurityQuestion(userSecurityQuestionTwo);
        assertNotNull(userSecurityQuestionTwo);
        
    }
    
    /**
     * This method test the showUserSecurityQuestionsForm method.
     * 
     * @throws AkuraAppException throws when fails.
     */
    @Test
    public void testShowUserSecurityQuestionsForm() throws AkuraAppException {

        session.setAttribute(USER_LOGIN_BY_NAME, user);
        
        String viewReturn = changePasswordController.showUserSecurityQuestionsForm(model, session);
        
        assertEquals(ANSWER_SECURITY_QUESTIONS, viewReturn);
        
    }
    
    /**
     * This method test the submitAnswers method.
     * 
     * @throws AkuraAppException throws when fails.
     */
    @Test
    public void testSubmitAnswers() throws AkuraAppException {

        session.setAttribute(USER_LOGIN, user);
        
        changePasswordController.setUserSecurityQuestionsValidator(userSecurityQuestionsValidator);
        
        userSecurityQuestionSubmitOne = new UserSecurityQuestions();
        userSecurityQuestionSubmitOne.setSecurityQuestions(securityQuestionsOne);
        userSecurityQuestionSubmitOne.setAnswer(ANSWER_ONE);
        
        userSecurityQuestionSubmitTwo = new UserSecurityQuestions();
        userSecurityQuestionSubmitTwo.setSecurityQuestions(securityQuestionsTwo);
        userSecurityQuestionSubmitTwo.setAnswer(ANSWER_TWO);
        
        submitAnswers = new WrapperSecurityQuestions();
        submitAnswers.setUserQuestionOne(userSecurityQuestionSubmitOne);
        submitAnswers.setUserQuestionTwo(userSecurityQuestionSubmitTwo);
        
        String viewReturn = changePasswordController.submitAnswers(submitAnswers, mockBindingResult, model, session);
        assertEquals(CHANGE_PASSWORD, viewReturn);
    }
    
    /**
     * This method test the changePassword method.
     * 
     * @throws AkuraAppException throws when fails.
     */
    @Test
    public void testChangePassword() throws AkuraAppException {

        String viewReturn;
        viewReturn = changePasswordController.changePassword(model, session);
        assertEquals(REDIRECT_LOGIN_HTM, viewReturn);
        
        session.setAttribute(USER_LOGIN, user);
        viewReturn = changePasswordController.changePassword(model, session);
        assertEquals(CHANGE_PASSWORD, viewReturn);
        
        session.setAttribute(USER_NAME, user.getUsername());
        viewReturn = changePasswordController.changePassword(model, session);
        assertEquals(CHANGE_PASSWORD, viewReturn);
        
    }
    
    /**
     * This method tests the submit password method.
     * 
     * @throws AkuraAppException when fails to process.
     */
    @Test
    public void testSubmitPassword() throws AkuraAppException {

        UserInfo userInfo = new UserInfo(TEST, TEST, "1", 1, true, true, new ArrayList<GrantedAuthority>()) {
            
            @Override
            public String getUserLevelIdentifier() {

                return null;
            }
            
            @Override
            public void clear() {

            }
        };
        
        userInfo.setDefaultUserHomeUrl(DEFAULT_URL);
        session.setAttribute(USER_LOGIN, user);
        session.setAttribute(USER, userInfo);
        
        String viewReturn;
        
        wrapperChangePassword = new WrapperChangePassword();
        wrapperChangePassword.setOldPassword(TEST);
        wrapperChangePassword.setNewPassword(NEW);
        wrapperChangePassword.setConfirmPassword(NEW);
        
        changePasswordController.setUserPasswordValidator(userPasswordValidator);
        changePasswordController.setEmailService(emailServices);
        
        viewReturn =
                changePasswordController.submitPassword(wrapperChangePassword, mockBindingResult, model, session,
                        request);
        assertEquals(DEFAULT_URL, viewReturn);
        
    }
    
    /**
     * Execute this cleanup method after every test execution.
     * 
     * @throws AkuraAppException throws when fails.
     */
    @After
    public void tearDown() throws AkuraAppException {

        userSecurityQuestionsDao.delete(userSecurityQuestionTwo);
        userSecurityQuestionsDao.delete(userSecurityQuestionOne);
        securityQuestionsDao.delete(securityQuestionsTwo);
        securityQuestionsDao.delete(securityQuestionsOne);
        userService.deleteUser(user);
    }
    
}
