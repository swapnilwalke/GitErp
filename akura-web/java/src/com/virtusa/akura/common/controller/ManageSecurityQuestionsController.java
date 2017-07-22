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

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.virtusa.akura.api.dto.SecurityQuestions;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.UserSecurityQuestions;
import com.virtusa.akura.api.dto.WrapperChangePassword;
import com.virtusa.akura.api.dto.WrapperSecurityQuestions;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.common.validator.SecurityQuestionsValidator;

/**
 * ManageSecurityQuestionsController manages all the User Secuirty questions related operations. Get the
 * answers for user security questions from user is the main function of this class.
 *
 * @author Virtusa Corporation.
 */
@Controller
@SessionAttributes("userLogin")
public class ManageSecurityQuestionsController {

    /** The userservice instance to call service operatons related to user. */
    private UserService userService;

    /** The SecurityQuestionsValidator instance to validate security questions. */
    private SecurityQuestionsValidator securityQuestionsValidator;

    /** The constant for submitPassword. */
    private static final String SUBMIT_PASSWORD = "submitPassword";

    /** The constant for userName. */
    private static final String USER_NAME = "userName";

    /** link to redirect user to change password page. */
    private static final String REDIRECT_CHANGE_PASSWORD = "redirect:changePassword.htm";

    /** link to redirect user to welcome page. */
    public static final String SUCCESS = "redirect:login.htm";

    /** The constant for questions list. */
    public static final String SECURITY_QUESTIONS_LIST = "questionList";

    /** The constant for security questions. */
    public static final String SECURITY_QUESTIONS = "securityQuestions";

    /** The constant for manage security question. */
    public static final String MANAGE_SECURITY_QUESTIONS = "manageSecurityQuestion";

    /** The constant for submit answers model name. */
    public static final String SUBMIT_ANSWERS = "submitAnswers";

    /** The constant for submit user answers view name. */
    public static final String SUBMIT_USER_ANSWERS = "/submitUserAnswers.htm";

    /** A constant for userlogin. */
    public static final String USERLOGIN = "userLogin";

    /**
     * Setter to set user service.
     *
     * @param userServices - Pass a user service to set.
     */
    public void setUserService(UserService userServices) {

        this.userService = userServices;
    }

    /**
     * Setter to set PasswordValidator.
     *
     * @param securityQuestionValidator - Pass a SecurityQuestionValidator to set.
     */
    public void setSecurityQuestionsValidator(SecurityQuestionsValidator securityQuestionValidator) {

        this.securityQuestionsValidator = securityQuestionValidator;
    }

    /**
     * This method process the request for user security question form and the user is prompted to select
     * his/her security questions and answers before he/she submit the form.
     *
     * @param modMap This map serves as the model holder for Servlet.
     * @param session to pass values.
     * @return {@link String} The view name pass to request handler.
     * @throws AkuraAppException Throws when processing the request fails.
     */
    @RequestMapping(value = MANAGE_SECURITY_QUESTIONS, method = RequestMethod.GET)
    public String showSecurityQuestionForm(

    ModelMap modMap, HttpSession session) throws AkuraAppException {

        String userName = (String) session.getAttribute(USER_NAME);
        if (userName == null) {
            return SUCCESS;
        }

        if (modMap.get(SUBMIT_ANSWERS) == null) {

            WrapperSecurityQuestions submitAnswers = new WrapperSecurityQuestions();
            modMap.addAttribute(SUBMIT_ANSWERS, submitAnswers);

        } else {
            WrapperSecurityQuestions submitAnswers = (WrapperSecurityQuestions) modMap.get(SUBMIT_ANSWERS);
            submitAnswers = new WrapperSecurityQuestions();
            submitAnswers.setUserQuestionTwo(new UserSecurityQuestions());
            submitAnswers.setUserQuestionOne(new UserSecurityQuestions());
            modMap.addAttribute(SUBMIT_ANSWERS, submitAnswers);
        }

        return MANAGE_SECURITY_QUESTIONS;

    }

    /**
     * This method process the submit user answer for security questions.The user will be redirected to home
     * page after he/she entered answers.
     *
     * @param submitAnswers The WrapperSecurityQuestion object that is in the model for processing.
     * @param bindingResult Error are bound to this object.
     * @param session to get the currently logged in user.
     * @param modMap This map serves as the model holder for Servlet. Use the model to get the
     *        WrapperSecurityQuestion type model.
     * @return {@link String} The view name pass to request handler.
     * @throws AkuraAppException Throws when processing the request fails.
     */
    @RequestMapping(value = SUBMIT_USER_ANSWERS, method = RequestMethod.POST)
    public String onSubmitQuestions(@ModelAttribute(SUBMIT_ANSWERS) WrapperSecurityQuestions submitAnswers,
            BindingResult bindingResult, ModelMap modMap, HttpSession session) throws AkuraAppException {

        String userName = (String) session.getAttribute(USER_NAME);
        if (userName == null) {
            return SUCCESS;
        }
        UserLogin userLogin = userService.getAnyUser(userName);

        boolean userQuestionExist = userService.isSecurityQuestionsExist(userLogin);

        if (submitAnswers == null) {
            submitAnswers = new WrapperSecurityQuestions();
            modMap.addAttribute(SUBMIT_ANSWERS, submitAnswers);
            return MANAGE_SECURITY_QUESTIONS;
        }
        if (submitAnswers.getUserQuestionOne() != null && submitAnswers.getUserQuestionTwo() != null) {

            // set the user of security question one.
            UserSecurityQuestions userSecurityQuestionsOne = submitAnswers.getUserQuestionOne();
            userSecurityQuestionsOne.setUserLogin(userLogin);

            // set the user of security question two.
            UserSecurityQuestions userSecurityQuestionsTwo = submitAnswers.getUserQuestionTwo();
            userSecurityQuestionsTwo.setUserLogin(userLogin);

            securityQuestionsValidator.validate(submitAnswers, bindingResult);

            if (bindingResult.hasErrors()) {
                return MANAGE_SECURITY_QUESTIONS;
            }

            if (userQuestionExist) {
                modMap.addAttribute(AkuraWebConstant.USER_SECURITY_QUESTIONS_EXIST_MESSAGE, new ErrorMsgLoader()
                        .getErrorMessage(AkuraWebConstant.USER_SECURITY_QUESTIONS_EXIST));
                return MANAGE_SECURITY_QUESTIONS;
            } else {

                userService.createSystemUserSecurityQuestion(userSecurityQuestionsOne);

                userService.createSystemUserSecurityQuestion(userSecurityQuestionsTwo);

            }

        }

        WrapperChangePassword submitPassword = new WrapperChangePassword();
        submitPassword.setOldPassword(AkuraConstant.EMPTY_STRING);
        submitPassword.setNewPassword(AkuraConstant.EMPTY_STRING);
        submitPassword.setConfirmPassword(AkuraConstant.EMPTY_STRING);
        modMap.addAttribute(SUBMIT_PASSWORD, submitPassword);
        return REDIRECT_CHANGE_PASSWORD;
    }

    /**
     * Get the List of security questions.
     *
     * @return list of security questions.
     * @throws AkuraAppException Throws when fails.
     */
    @ModelAttribute(SECURITY_QUESTIONS_LIST)
    public List<SecurityQuestions> securityQuestionsList() throws AkuraAppException {

        return userService.getAllSecurityQuestions();
    }

}
