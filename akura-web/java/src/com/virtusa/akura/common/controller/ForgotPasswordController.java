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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.CommonEmailBean;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.UserSecurityQuestions;
import com.virtusa.akura.api.dto.WrapperSecurityQuestions;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.EmailService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.common.validator.ForgotPasswordValidator;
import com.virtusa.akura.common.validator.ForgotUserNameValidator;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.email.EmailUtil;

/**
 * Controller Class for ForgotPassword. If a user forgot his/her password and want to login to the system,
 * He/She can get a new password through email to login by clicking the Forgot Password link in the login
 * page. User will receive an email with new Password. Also this controller handles the process of forgot
 * username. If a users forgot their usernames, they can get retrieve their username by providing the email
 * they have used to register in the system.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ForgotPasswordController {
    

    /**
     * UserService is used to get the User by His/Her Username, reset the User Password and get the list of
     * UserLogin from database.
     */
    private UserService userService;
    
    /** Email service instance to call email service functions. */
    private EmailService emailService;
    
    /** Validate the entered User name. */
    private ForgotPasswordValidator forgotPasswordValidator;
    
    /** Validate the entered email. */
    private ForgotUserNameValidator forgotUserNameValidator;
    
    /**
     * Log the error messages.
     */
    private static final Logger LOG = Logger.getLogger(ChangePasswordController.class);
    
    /** The constant for "error". */
    private static final String ERROR = "error";
    
    /** The constant for "redirect:answerSecurityQuestions.htm". */
    private static final String REDIRECT_ANSWER_SECURITY_QUESTIONS_HTM = "redirect:answerSecurityQuestions.htm";
    
    /** The constant for "UserLoginByName". */
    private static final String USER_LOGIN_BY_NAME = "UserLoginByName";
    
    /** user login model attribute. */
    private static final String USER_LOGIN = "UserLogin";
    
    /** The constant for "forgotUsername". */
    private static final String FORGOT_USERNAME = "forgotUsername";
    
    /** path to email properties file. */
    public static final String EMAIL_PROPERTIES = "email";
    
    /** The constant for holding the view template for New Password notification email. */
    public static final String FORGOT_USERANAME_TO_USER = "email.template.usernameToUser";
    
    /** The admin email that send an email to user when password changes. */
    public static final String ADMIN_EMAIL = "admin.email";
    
    /** The admin email password. */
    public static final String ADMIN_EMAIL_PASSWORD = "email.password";
    
    /** The subject of the mail send to the user who forgot his/her password. */
    public static final String FORGOT_PASSWORD_SUBJECT = "subject";
    
    /** The constant for "answerSecurityQuestions". */
    public static final String GET_USER_ANSWERS = "answerSecurityQuestions";
    
    /** The constant for submit answers model name. */
    public static final String SUBMIT_ANSWERS = "submitAnswers";
    
    /** The constant for forgot password. */
    public static final String FROM_FORGOT_PASSWORD = "forgotPassword";
    
    /** message model attribute. */
    private static final String MESSAGE = "message";
    
    /** Email propety mailErrors. */
    private static final String MAIL_ERRORS = "mailErrors";
    
    /** The constant for string "/submitUserName.htm". */
    private static final String SUBMIT_USERNAME_HTM = "/submitUserName.htm";
    
    /** The constant for holding the forgot username email subject. */
    public static final String FORGOT_USERNAME_SUBJECT = "forgotusername.subject";
    
    /** The constant for holding the "forgotUsername.htm" . */
    public static final String FORGOT_USERNAME_HTM = "forgotUsername.htm";
    
    /** The constant for holding the "submitEmail.htm" . */
    public static final String SUBMIT_EMAIL_HTM = "submitEmail.htm";
    
    /** The constant for holding the "forgotPasswordSuccessOrError" . */
    private static final String FORGOT_PASSWORD_SUCCESS_OR_ERROR = "forgotPasswordSuccessOrError";

    /**
     * User forgot the password and click the link "forgot password" on the login page to go to the forgot
     * password page.
     * 
     * @param modMap - Add the UserLogin object into ModelMap if it is not there.
     * @return the name of the View.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showForgotPasswordForm(ModelMap modMap) {

        if (modMap.get(USER_LOGIN) == null) {
            UserLogin userLogin = new UserLogin();
            modMap.addAttribute(USER_LOGIN, userLogin);
        }
        
        modMap.addAttribute(MESSAGE, null);
        return FROM_FORGOT_PASSWORD;
        
    }
    
    /**
     * User forgot the username and click the link "forgot username" on the login page to go to the forgot
     * username page.
     * 
     * @param modMap - Add the userlogin object if it is not there.
     * @return return the forgot username page to the user.
     */
    @RequestMapping(value = FORGOT_USERNAME_HTM, method = RequestMethod.GET)
    public String showForgotUsernameForm(ModelMap modMap) {

        if (modMap.get(USER_LOGIN) == null) {
            UserLogin userLogin = new UserLogin();
            modMap.addAttribute(USER_LOGIN, userLogin);
        }
        
        modMap.addAttribute(MESSAGE, null);
        return FORGOT_USERNAME;
        
    }
    
    /**
     * This method handles the submit user email address request. User enters his/her email address into the
     * given field and click submit.
     * 
     * @param userLogin - the object to bind the user's email address.
     * @param bindingResult - bind errors to the binding results.
     * @param modMap - model to add attributes.
     * @param session - to get and set session attributes and parameters.
     * @return the view to be return after success or failure.
     * @throws AkuraAppException throws a detailed exception.
     */
    @RequestMapping(value = SUBMIT_EMAIL_HTM, method = RequestMethod.POST)
    public String submitEmail(@ModelAttribute(USER_LOGIN) UserLogin userLogin, BindingResult bindingResult,
            ModelMap modMap, HttpSession session) throws AkuraAppException {

        String error = null;
        String mailError = null;
        
        // Validate the email address.
        forgotUserNameValidator.validate(userLogin, bindingResult);
        
        // if there are errors then go back to the previous page and show the error message.
        if (bindingResult.hasErrors()) {
            return FORGOT_USERNAME;
        }
        
        // Get the User Login by email address.
        UserLogin user = userService.getAnyUserByEmail(userLogin.getEmail().trim());
        
        // If there is no user account with the given email address. UserLogin will be empty.
        if (user == null) {
            
            error = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.FORGOT_USERNAME_ERROR_EMAIL);
            bindingResult.addError(new ObjectError(ERROR, error));
            return FORGOT_USERNAME;
            
        } else {
            
            try {
                setMailProprties(user, PropertyReader.getPropertyValue(EMAIL_PROPERTIES, FORGOT_USERNAME_SUBJECT),
                        PropertyReader.getPropertyValue(EMAIL_PROPERTIES, FORGOT_USERANAME_TO_USER));
                
                // Add the message to Model to pass a value.
                modMap.addAttribute(MESSAGE, AkuraWebConstant.USERNAEM_SEND_SUCCESSFUL_MESSAGE);
                
            } catch (MailException e) {
                
                if (e instanceof MailAuthenticationException) {
                    
                    mailError = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.EMAIL_AUTHENTICATION_ERROR);
                    LOG.error(AkuraWebConstant.EMAIL_AUTHENTICATION_ERROR, e);
                    
                } else if (e.getCause() instanceof MailSendException) {
                    mailError = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.EMAIL_SEND_ERROR);
                    LOG.error(AkuraWebConstant.EMAIL_SEND_ERROR, e);
                }
                
                mailError = new ErrorMsgLoader().getErrorMessage(AkuraConstant.EMAIL_ERROR);
                LOG.error(AkuraConstant.EMAIL_ERROR, e);
            } catch (AkuraAppException e) {
                mailError = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.EMAIL_SEND_ERROR);
                LOG.error(AkuraWebConstant.EMAIL_SEND_ERROR, e);
            }
            
            if (mailError != null) {
                modMap.addAttribute(MAIL_ERRORS, mailError);
            }
            
        }
        userLogin.setEmail(null);
        return FORGOT_PASSWORD_SUCCESS_OR_ERROR;
        
    }
    
    /**
     * The user enter his/her username in the given field and click submit to get a new password.
     * 
     * @param userLogin - set the UserLogin object.
     * @param bindingResult - bind the results to the model.
     * @param modMap - ModelMap to get view.
     * @param session - HttpSession.
     * @return - the name of the view.
     * @throws AkuraAppException - throws AkuraAppException if fail.
     */
    @RequestMapping(value = SUBMIT_USERNAME_HTM, method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute(USER_LOGIN) UserLogin userLogin, BindingResult bindingResult,
            ModelMap modMap, HttpSession session) throws AkuraAppException {

        String error = null;
        String mailError = null;
        
        // Validate the Username.
        forgotPasswordValidator.validate(userLogin, bindingResult);
        
        if (bindingResult.hasErrors()) {
            return FROM_FORGOT_PASSWORD;
        }
        
        // Get the Username from the userlogin object.
        String userName = userLogin.getUsername().trim();
        
        UserLogin user = userService.getUser(userName);
        
        if (user == null) {
            
            error = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.USERNAME_ERROR);
            
        } else if (user.getLoginAttempts() == AkuraConstant.PARAM_INDEX_THREE) {
            
            error = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.USER_BLOCKED_ERROR);
            
        } else if (!userService.isSecurityQuestionsExist(user)) {
            
            error = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.NO_ANSWERS_ERROR);
            
        }
        
        if (user != null && user.getLoginAttempts() != AkuraConstant.PARAM_INDEX_THREE
                && userService.isSecurityQuestionsExist(user)) {
            
            session.setAttribute(USER_LOGIN_BY_NAME, user);
            List<UserSecurityQuestions> userSecurityQuestions =
                    userService.getUserSecurityQuestionsById(user.getUserLoginId());
            
            WrapperSecurityQuestions submitAnswers = new WrapperSecurityQuestions();
            UserSecurityQuestions userQuestionOne = userSecurityQuestions.get(AkuraConstant.PARAM_INDEX_ZERO);
            UserSecurityQuestions userQuestionTwo = userSecurityQuestions.get(AkuraConstant.PARAM_INDEX_ONE);
            userQuestionOne.setAnswer(AkuraConstant.EMPTY_STRING);
            userQuestionTwo.setAnswer(AkuraConstant.EMPTY_STRING);
            submitAnswers.setUserQuestionOne(userQuestionOne);
            submitAnswers.setUserQuestionTwo(userQuestionTwo);
            modMap.addAttribute(SUBMIT_ANSWERS, submitAnswers);
            session.setAttribute(FROM_FORGOT_PASSWORD, FROM_FORGOT_PASSWORD);
            return REDIRECT_ANSWER_SECURITY_QUESTIONS_HTM;
            
        } else {
            
            userLogin.setUsername(null);
            bindingResult.addError(new ObjectError(ERROR, error));
            modMap.addAttribute(MESSAGE, null);
            modMap.addAttribute(MAIL_ERRORS, mailError);
            
            return FROM_FORGOT_PASSWORD;
        }
        
    }
    
    /**
     * Setter to set the userService.
     * 
     * @param userServices - pass a UserService to setter.
     */
    public void setUserService(UserService userServices) {

        this.userService = userServices;
    }
    
    /**
     * Setter to set the emailService.
     * 
     * @param emailServiceVal - pass a EmailService to setter.
     */
    public void setEmailService(EmailService emailServiceVal) {

        this.emailService = emailServiceVal;
    }
    
    /**
     * Setter to set the forgot password validator.
     * 
     * @param forgotPasswordValidators - pass a ForgotPasswordValidator to setter.
     */
    public void setForgotPasswordValidator(ForgotPasswordValidator forgotPasswordValidators) {

        this.forgotPasswordValidator = forgotPasswordValidators;
    }
    
    /**
     * Setter to set the forgot Username validator.
     * 
     * @param forgotUserNameValidatorVal - pass a ForgotUsernameValidator to setter.
     */
    public void setForgotUserNameValidator(ForgotUserNameValidator forgotUserNameValidatorVal) {

        this.forgotUserNameValidator = forgotUserNameValidatorVal;
    }
    
    /**
     * Set the Mail properties to send an email to the User.
     * 
     * @param userLogin - Pass the UserLogin of this user.
     * @param strSubject - message subject.
     * @param templateName - name of the velocity template.
     * @return check whether Mail property setting is a success(return true) or not.
     * @throws AkuraAppException the AkuraAppException.
     */
    private boolean setMailProprties(UserLogin userLogin, String strSubject, String templateName)
            throws AkuraAppException {

        // To Address should be the email address of the user.
        String strToAddress = userLogin.getEmail();
        
        // From Address should be the email address of the institution.
        String strFromAddress = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, ADMIN_EMAIL);
        
        Map<String, Object> mapObjectMap = new HashMap<String, Object>();
        mapObjectMap.put(MESSAGE, AkuraWebConstant.USERNAEM_SEND_SUCCESSFUL_MESSAGE);
        mapObjectMap.put(AkuraWebConstant.USER_NAME_LOW, userLogin.getUsername());
        mapObjectMap.put(AkuraWebConstant.NAME, userLogin.getFirstName());
        
        // Include email header information
        EmailUtil.addHeaderForEmail(mapObjectMap);
        CommonEmailBean commonEmailBean = new CommonEmailBean();
        commonEmailBean.setToAddress(strToAddress);
        commonEmailBean.setFromAddress(strFromAddress);
        commonEmailBean.setMailTemplate(templateName);
        commonEmailBean.setObjectMap(mapObjectMap);
        commonEmailBean.setSubject(strSubject);
        
        return emailService.sendMail(commonEmailBean);
        
    }
    
}
