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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.SecurityQuestions;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.UserSecurityQuestions;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.dao.SecurityQuestionsDao;
import com.virtusa.akura.common.dao.UserSecurityQuestionsDao;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.common.validator.ForgotPasswordValidator;

/**
 * Test the forgot password controller.
 *
 * @author VIRTUSA corporation.
 */
public class ForgotPasswordControllerTest extends BaseWebTest {

    /** Constant for string "forgotPassword". */
    private static final String FORGOT_PASSWORD = "forgotPassword";

    /** Constant for string "UserLogin". */
    private static final String USER_LOGIN = "UserLogin";

    /** Constant for string "test". */
    private static final String TEST = "test";

    /** Constant for string "lastName". */
    private static final String LAST_NAME = "lastName";

    /** Constant for string "testAdmin". */
    private static final String TEST_ADMIN = "testAdmin";

    /** The constant for forgot password. */
    public static final String FROM_FORGOT_PASSWORD = "forgotPassword";

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

    /** Represents an instance of the forgotPasswordController. */
    private ForgotPasswordController forgotPasswordController;

    /** Represents an instance of the userService. */
    @Autowired
    private UserService userService;

    /** Represents an instance of the forgotPasswordValidator. */
    @Autowired
    private ForgotPasswordValidator forgotPasswordValidator;

    /** Represents an instance of the userLogin. */
    private UserLogin user;

    /** Represents an instance of session. */
    private MockHttpSession session;

    /** Represents an instance of ModelMap. */
    private ModelMap model;

    /** Represents an instance of BindingResult. */
    @Mock
    private BindingResult mockBindingResult;

    /** Represents an instance of securityQuestionsDao. */
    @Autowired
    private SecurityQuestionsDao securityQuestionsDao;

    /** Represents an instance of userSecurityQuestionsDao. */
    @Autowired
    private UserSecurityQuestionsDao userSecurityQuestionsDao;

    /** Represents an instance of securityQuestionsOne. */
    private SecurityQuestions securityQuestionsOne;

    /** Represents an instance of securityQuestionsTwo. */
    private SecurityQuestions securityQuestionsTwo;

    /** Represents an instance of userSecurityQuestionOne. */
    private UserSecurityQuestions userSecurityQuestionOne;

    /** Represents an instance of userSecurityQuestionTwo. */
    private UserSecurityQuestions userSecurityQuestionTwo;

    /**
     * This method will setup all objects required for test.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);

        assertNotNull(mockBindingResult);

        forgotPasswordController = new ForgotPasswordController();

        forgotPasswordController.setUserService(userService);

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
        userSecurityQuestionTwo.setSecurityQuestions(securityQuestionsTwo);
        userSecurityQuestionTwo.setAnswer(ANSWER_TWO);
        userSecurityQuestionTwo.setUserLogin(user);

        userService.createSystemUserSecurityQuestion(userSecurityQuestionTwo);
        assertNotNull(userSecurityQuestionTwo);

        model = new ModelMap();

        model.addAttribute(USER_LOGIN, user);

        session = new MockHttpSession();

        forgotPasswordController.setUserService(userService);

    }

    /**
     * This method test the show user forgot password form method.
     *
     * @throws AkuraAppException when error occurred during the process.
     */
    @Test
    public void testShowForgotPasswordForm() throws AkuraAppException {

        forgotPasswordController.setForgotPasswordValidator(forgotPasswordValidator);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);

        String view = forgotPasswordController.showForgotPasswordForm(model);

        assertEquals(FORGOT_PASSWORD, view);

    }

    /**
     * This method test the forgot password form submit handler.
     *
     * @throws AkuraAppException when error occurred during the process.
     */
    @Test
    public void testOnSubmit() throws AkuraAppException {

        forgotPasswordController.setForgotPasswordValidator(forgotPasswordValidator);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);

        String view = forgotPasswordController.onSubmit(user, mockBindingResult, model, session);
        assertEquals(FROM_FORGOT_PASSWORD, view);
    }

    /**
     * Run this clean up method after every execution.
     *
     * @throws AkuraAppException when fails to process.
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
