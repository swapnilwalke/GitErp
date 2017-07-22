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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.UserSecurityQuestions;
import com.virtusa.akura.api.dto.WrapperChangePassword;
import com.virtusa.akura.api.dto.WrapperSecurityQuestions;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.EmailService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.common.validator.UserPasswordValidator;
import com.virtusa.akura.common.validator.UserSecurityQuestionsValidator;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.email.EmailUtil;

/**
 * ChangePasswordController handles all the change password tasks that are specific to change the user
 * password.
 * 
 * @author Virtusa Corporation.
 */
@Controller
public class ChangePasswordController {
    
    /** Validator instance for User Security Questions. */
    private UserSecurityQuestionsValidator userSecurityQuestionsValidator;
    
    /** Validator instance for User password validation. */
    private UserPasswordValidator userPasswordValidator;
    
    /** User service instance to call user service functions. */
    private UserService userService;
    
    /** Email service instance to call email service functions. */
    private EmailService emailService;
    
    /** A constant for "user". */
    private static final String USER = "user";
    
    /** A constant for userName. */
    private static final String USER_NAME = "userName";
    
    /** A constant for mailErrors. */
    private static final String MAIL_ERRORS = "mailErrors";
    
    /** A constant for Userlogin. */
    private static final String USER_LOGIN = "UserLogin";
    
    /** A constant for userloginByName. */
    public static final String USERLOGINBYNAME = "UserLoginByName";
    
    /** A constant for userlogin. */
    public static final String USERLOGIN = "userLogin";
    
    /** A constant for "/submitPassword.htm". */
    public static final String SUBMIT_PASSWORD = "/submitPassword.htm";
    
    /** A constant for submitpassword. */
    public static final String SUBMITPASSWORD = "submitPassword";
    
    /** A constant for forgotpassword. */
    public static final String FORGOTPASSWORD = "forgotPassword";
    
    /** A constant for "/submitAnswers.htm". */
    public static final String SUBMITANSWERS = "/submitAnswers.htm";
    
    /** A constant for message. */
    public static final String MESSAGE = "message";
    
    /** link to get answer security questions page. */
    public static final String GET_USER_ANSWERS = "answerSecurityQuestions";
    
    /** link to get change password page. */
    public static final String GET_USER_PASSWORD = "changePassword";
    
    /** link to redirect user to welcome page. */
    public static final String FAILURE = "redirect:login.htm";
    
    /** path to email properties file. */
    public static final String EMAIL_PROPERTIES = "email";
    
    /** The admin email that send an email to user when password changes. */
    public static final String ADMIN_EMAIL = "admin.email";
    
    /** The admin email password. */
    public static final String ADMIN_EMAIL_PASSWORD = "email.password";
    
    /** The constant for holding the view template for New Password notification email. */
    public static final String NEW_PASSWORD_TO_USER = "email.template.newPasswordToUser";
    
    /** The constant for holding the change Password subject for email. */
    public static final String CHANGE_PASSWORD_SUBJECT = "changepassword.subject";
    
    /** The constant for holding the forgot Password subject for email. */
    public static final String FORGOT_PASSWORD_SUBJECT = "forgotpassword.subject";
    
    /** The constant for submit answers model name. */
    public static final String SUBMIT_ANSWERS = "submitAnswers";
    
    /** Represents the model attribute for "forgotPasswordSuccessOrError". */
    private static final String FORGOT_PASSWORD_SUCCESS_OR_ERROR = "forgotPasswordSuccessOrError";
    
    /**
     * Log the error messages.
     */
    private static final Logger LOG = Logger.getLogger(ChangePasswordController.class);
    
    /** Represents an instance of LoginController. */
    private LoginController loginController;
    
    /**
     * Injects an instance of LoginController.
     * 
     * @param loginControllerVal - an instance of LoginController.
     */
    public void setLoginController(LoginController loginControllerVal) {

        this.loginController = loginControllerVal;
    }
    
    /**
     * Setter to set UserSecurityQuestionsvalidator.
     * 
     * @param userSecurityQuestionsValidators - Pass a UserSecurityQuestionsvalidator to set.
     */
    public void setUserSecurityQuestionsValidator(UserSecurityQuestionsValidator userSecurityQuestionsValidators) {

        this.userSecurityQuestionsValidator = userSecurityQuestionsValidators;
    }
    
    /**
     * Setter to set PasswordValidator.
     * 
     * @param userPasswordValidators - Pass a PasswordValidator to set.
     */
    public void setUserPasswordValidator(UserPasswordValidator userPasswordValidators) {

        this.userPasswordValidator = userPasswordValidators;
    }
    
    /**
     * Setter to set user service.
     * 
     * @param userServices - Pass a user service to set.
     */
    public void setUserService(UserService userServices) {

        this.userService = userServices;
    }
    
    /**
     * Setter to set email service.
     * 
     * @param emailServices - Pass an email service to set.
     */
    public void setEmailService(EmailService emailServices) {

        this.emailService = emailServices;
    }
    
    /**
     * Request handler for show user security questions form.
     * 
     * @param modMap a map that keeps all the bonded values.
     * @param session get the logged i user information from session. The logged in user is always in the
     *        session.
     * @return {@link String} The string view name of user security question form "answerSecurityQuestions".
     * @throws AkuraAppException throws when process fails.
     */
    @RequestMapping(value = GET_USER_ANSWERS, method = RequestMethod.GET)
    public String showUserSecurityQuestionsForm(ModelMap modMap, HttpSession session) throws AkuraAppException {

        UserLogin userLogin = (UserLogin) session.getAttribute(USERLOGIN);
        UserLogin userLoginByName = (UserLogin) session.getAttribute(USERLOGINBYNAME);
        
        if (userLoginByName == null) {
            return FAILURE;
        }
        
        if ((userLogin != null && userService.isSecurityQuestionsExist(userLogin))
                || (userLoginByName != null && userService.isSecurityQuestionsExist(userLoginByName))) {
            
            if (userLogin == null) {
                userLogin = userLoginByName;
            }
            
            List<UserSecurityQuestions> userSecurityQuestions =
                    userService.getUserSecurityQuestionsById(userLogin.getUserLoginId());
            
            WrapperSecurityQuestions submitAnswers = new WrapperSecurityQuestions();
            UserSecurityQuestions userQuestionOne = userSecurityQuestions.get(0);
            UserSecurityQuestions userQuestionTwo = userSecurityQuestions.get(1);
            userQuestionOne.setAnswer(AkuraConstant.EMPTY_STRING);
            userQuestionTwo.setAnswer(AkuraConstant.EMPTY_STRING);
            submitAnswers.setUserQuestionOne(userQuestionOne);
            submitAnswers.setUserQuestionTwo(userQuestionTwo);
            modMap.addAttribute(SUBMIT_ANSWERS, submitAnswers);
            
        } else {
            WrapperSecurityQuestions submitAnswers = (WrapperSecurityQuestions) modMap.get(SUBMIT_ANSWERS);
            submitAnswers = new WrapperSecurityQuestions();
            submitAnswers.setUserQuestionTwo(new UserSecurityQuestions());
            submitAnswers.setUserQuestionOne(new UserSecurityQuestions());
            modMap.addAttribute(SUBMIT_ANSWERS, submitAnswers);
        }
        
        modMap.addAttribute(MESSAGE, MESSAGE);
        
        return GET_USER_ANSWERS;
        
    }
    
    /**
     * Request handler for submit user security question answers.
     * 
     * @param submitAnswers The wrapper security question to bind answers.
     * @param bindingResult The binding result object to bind all types of errors.
     * @param modelMap a map that keeps all the bonded values.
     * @param session get the logged i user information from session. The logged in user is always in the
     *        session.
     * @return view name of forgot password.
     * @throws AkuraAppException throws when fails.
     */
    @RequestMapping(value = SUBMITANSWERS, method = RequestMethod.POST)
    public String submitAnswers(@ModelAttribute(SUBMIT_ANSWERS) WrapperSecurityQuestions submitAnswers,
            BindingResult bindingResult, ModelMap modelMap, HttpSession session) throws AkuraAppException {

        UserLogin userLogin = (UserLogin) session.getAttribute(USERLOGIN);
        UserLogin userLoginByName = (UserLogin) session.getAttribute(USERLOGINBYNAME);
        boolean success = false;
        String mailError = null;
        
        if (userLogin == null) {
            userLogin = userLoginByName;
        }
        
        // set the user of security question one.
        UserSecurityQuestions userSecurityQuestionsOne = submitAnswers.getUserQuestionOne();
        
        // set the user of security question two.
        UserSecurityQuestions userSecurityQuestionsTwo = submitAnswers.getUserQuestionTwo();
        
        if (userSecurityQuestionsOne != null && userSecurityQuestionsTwo != null) {
            
            userSecurityQuestionsOne.setUserLogin(userLogin);
            
            userSecurityQuestionsTwo.setUserLogin(userLogin);
            
            userSecurityQuestionsValidator.validate(submitAnswers, bindingResult);
            
            List<UserSecurityQuestions> userSecurityQuestionsFromDB =
                    userService.getUserSecurityQuestionsById(userLogin.getUserLoginId());
            List<UserSecurityQuestions> userSecurityQuestionsSubmitted = new ArrayList<UserSecurityQuestions>();
            userSecurityQuestionsSubmitted.add(0, userSecurityQuestionsOne);
            userSecurityQuestionsSubmitted.add(1, userSecurityQuestionsTwo);
            
            if (!bindingResult.hasErrors()
                    && validateUserSecurityQuestions(userSecurityQuestionsSubmitted, userSecurityQuestionsFromDB,
                            bindingResult)) {
                
                success = true;
                
                if (session.getAttribute(FORGOTPASSWORD) != null) {
                    
                    modelMap.addAttribute(USER_LOGIN, userLogin);
                    
                    String userOldPassword = userLogin.getPassword();
                    
                    // Change user password.
                    String newPassword = userService.resetPassword(userLogin);
                    
                    try {
                        setMailProprties(userLogin, newPassword,
                                PropertyReader.getPropertyValue(EMAIL_PROPERTIES, FORGOT_PASSWORD_SUBJECT),
                                PropertyReader.getPropertyValue(EMAIL_PROPERTIES, NEW_PASSWORD_TO_USER));
                        
                        // Add the message to Model to pass a value.
                        modelMap.addAttribute(MESSAGE, AkuraWebConstant.PASSWORD_CHANGE_SUCCESSFUL_MESSAGE);
                        
                    } catch (MailException e) {
                        resetUserPassword(userLogin, userOldPassword);
                        modelMap.addAttribute(MESSAGE, null);
                        
                        if (e instanceof MailAuthenticationException) {
                            
                            mailError =
                                    new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.EMAIL_AUTHENTICATION_ERROR);
                            LOG.error(AkuraWebConstant.EMAIL_AUTHENTICATION_ERROR, e);
                            
                        } else if (e instanceof MailSendException) {
                            
                            mailError = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.EMAIL_SEND_ERROR);
                            LOG.error(AkuraWebConstant.EMAIL_SEND_ERROR, e);
                            
                        }
                        
                    } catch (AkuraAppException e) {
                        resetUserPassword(userLogin, userOldPassword);
                        mailError = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.EMAIL_SEND_ERROR);
                        LOG.error(AkuraWebConstant.EMAIL_SEND_ERROR, e);
                    } finally {
                        session.removeAttribute(FORGOTPASSWORD);
                        modelMap.addAttribute(MAIL_ERRORS, mailError);
                        
                    }
                    userLogin.setUsername(null);
                    return FORGOT_PASSWORD_SUCCESS_OR_ERROR;
                    
                }
            }
            
            if (bindingResult.hasErrors()) {
                return GET_USER_ANSWERS;
            }
            
        }
        
        if (success) {
            
            submitPasswordData(modelMap);
            
            return GET_USER_PASSWORD;
        }
        return GET_USER_ANSWERS;
        
    }
    
    /**
     * Request handler that handles the change password request.
     * 
     * @param modelMap a model that binds all the required information.
     * @param session To get the currently logged in user.
     * @return The view name of the submit password.If successful, the user will be redirected to Admin
     *         welcome page.
     */
    @RequestMapping(value = GET_USER_PASSWORD, method = RequestMethod.GET)
    public String changePassword(ModelMap modelMap, HttpSession session) {

        String userName = (String) session.getAttribute(USER_NAME);
        UserLogin userLogin = (UserLogin) session.getAttribute(USERLOGIN);
        
        //add userLogin object to the model
        modelMap.addAttribute(USERLOGIN, userLogin);
        
        if (userName == null && userLogin == null) {
            return FAILURE;
        } else if (userLogin != null) {
            
            submitPasswordData(modelMap);
            
            return GET_USER_PASSWORD;
            
        } else {
            
            submitPasswordData(modelMap);
            return GET_USER_PASSWORD;
        }
    }
    
    /**
     * Request handler for submit password.
     * 
     * @param submitPassword The model object that bind all passwords.
     * @param bindingResult The object to bind errors.
     * @param modelMap The model map binder.
     * @param session Get the currently logged in user.
     * @param request - an instance of request scope.
     * @return The view name of the submit password page.
     * @throws AkuraAppException Throws when fails.
     */
    @RequestMapping(value = SUBMIT_PASSWORD, method = RequestMethod.POST)
    public String submitPassword(@ModelAttribute(SUBMITPASSWORD) WrapperChangePassword submitPassword,
            BindingResult bindingResult, ModelMap modelMap, HttpSession session, HttpServletRequest request)
            throws AkuraAppException {

        String mailError = null;
        
        String userName = (String) session.getAttribute(USER_NAME);
        UserLogin userLogin = (UserLogin) session.getAttribute(USERLOGIN);
        UserInfo userInfo = (UserInfo) session.getAttribute(USER);
        
        if (userName == null && userLogin == null) {
            
            return FAILURE;
            
        } else if (session.getAttribute(USERLOGIN) != null) {
            
            userLogin = (UserLogin) session.getAttribute(USERLOGIN);
            
        } else {
            
            userLogin = userService.getAnyUser(userName);
        }
        
        userPasswordValidator.validate(submitPassword, bindingResult);
        
        if (bindingResult.hasErrors()) {
            return GET_USER_PASSWORD;
        }
        
        String password = submitPassword.getOldPassword().trim();
        String newPassword = submitPassword.getNewPassword().trim();
        String confirmPassword = submitPassword.getConfirmPassword().trim();
        
        if (!userService.validatePassword(password, userLogin)) {
            bindingResult.addError(new ObjectError(AkuraWebConstant.USER_INCORRECT_OLD_PASSWORD, new ErrorMsgLoader()
                    .getErrorMessage(AkuraWebConstant.USER_INCORRECT_OLD_PASSWORD)));
            
        } else if (!newPassword.equals(confirmPassword)) {
            
            bindingResult.addError(new ObjectError(AkuraWebConstant.INCORRECT_COMPARE_PASSWORD, new ErrorMsgLoader()
                    .getErrorMessage(AkuraWebConstant.INCORRECT_COMPARE_PASSWORD)));
        }
        
        if (bindingResult.hasErrors()) {
            return GET_USER_PASSWORD;
        }
        
        loginController.getPublications(modelMap, request);
        
        if (userService.changePassword(userLogin, newPassword)) {
            
            userLogin.setStatus(true);
            
            /* check user password status, whether system generated or not. */
            if (userLogin.getGeneratedPassword()) {
                userLogin.setGeneratedPassword(Boolean.FALSE);
            }
            userService.updateUser(userLogin);
            
            try {
                setMailProprties(userLogin, newPassword,
                        PropertyReader.getPropertyValue(EMAIL_PROPERTIES, CHANGE_PASSWORD_SUBJECT),
                        PropertyReader.getPropertyValue(EMAIL_PROPERTIES, NEW_PASSWORD_TO_USER));
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
                
                bindingResult.addError(new ObjectError(MAIL_ERRORS, mailError));
                
                return userInfo.getDefaultUserHomeUrl();
                
            }
            
        }
        return userInfo.getDefaultUserHomeUrl();
    }
    
    /**
     * Set the Mail properties to send an email to the User.
     * 
     * @param userLogin - Pass the UserLogin of this user.
     * @param newPassword - Pass the changed password to the user via email.
     * @param strSubject - message subject.
     * @param templateName - name of the velocity template.
     * @return check whether Mail property setting is a success(return true) or not.
     * @throws AkuraAppException the AkuraAppException.
     */
    private boolean setMailProprties(UserLogin userLogin, String newPassword, String strSubject, String templateName)
            throws AkuraAppException {

        // To Address should be the email address of the user.
        String strToAddress = userLogin.getEmail();
        
        // From Address should be the email address of the institution.
        String strFromAddress = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, ADMIN_EMAIL);
        
        Map<String, Object> mapObjectMap = new HashMap<String, Object>();
        mapObjectMap.put(MESSAGE, AkuraWebConstant.PASSWORD_CHANGE_SUCCESSFUL_MESSAGE);
        mapObjectMap.put(AkuraWebConstant.USER_NAME_LOW, userLogin.getUsername());
        mapObjectMap.put(AkuraWebConstant.PASSWORD, newPassword);
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
    
    /**
     * The validateUserSecurityQuestions method validates the user security question answers provided by the
     * user with the answers from the database.
     * 
     * @param userQuestionsSubmitted The userSecurity questions submitted by the user.
     * @param userQuestionsFromDB The security questions saved in the database.
     * @param bindingResult - to bind errors.
     * @return true if the validation of security questions answers are success.
     */
    private boolean validateUserSecurityQuestions(List<UserSecurityQuestions> userQuestionsSubmitted,
            List<UserSecurityQuestions> userQuestionsFromDB, BindingResult bindingResult) {

        boolean success = true;
        
        if (!userQuestionsSubmitted.get(0).getAnswer().trim().equals(userQuestionsFromDB.get(0).getAnswer())
                || !userQuestionsSubmitted.get(1).getAnswer().trim().equals(userQuestionsFromDB.get(1).getAnswer())) {
            bindingResult.addError(new ObjectError(AkuraWebConstant.USER_INCORRECT_ANSWERS, new ErrorMsgLoader()
                    .getErrorMessage(AkuraWebConstant.USER_INCORRECT_ANSWERS)));
            success = false;
        }
        return success;
        
    }
    
    /**
     * Fill the Wrapper Change password object with Three user logins.
     * 
     * @param modelMap model that binds all the required information.
     */
    private void submitPasswordData(ModelMap modelMap) {

        WrapperChangePassword submitPassword = new WrapperChangePassword();
        submitPassword.setOldPassword(AkuraConstant.EMPTY_STRING);
        submitPassword.setNewPassword(AkuraConstant.EMPTY_STRING);
        submitPassword.setConfirmPassword(AkuraConstant.EMPTY_STRING);
        
        //set username and email
        UserLogin userLogin = (UserLogin) modelMap.get(USERLOGIN);
        submitPassword.setUsername(userLogin.getUsername());
        submitPassword.setEmail(userLogin.getEmail());
        
        modelMap.addAttribute(SUBMITPASSWORD, submitPassword);
        modelMap.addAttribute(MESSAGE, MESSAGE);
    }
    
    /**
     * The resetUserPassword method reset the changed password to the old password.
     * 
     * @param userLogin The user who changed his/her password recently.
     * @param oldPassword The old password that has been changed recently.
     * @return true if the password change successful.
     * @throws AkuraAppException Throws while the user updates an existing user.
     */
    private boolean resetUserPassword(UserLogin userLogin, String oldPassword) throws AkuraAppException {

        boolean success = false;
        
        if (userLogin != null && oldPassword != null) {
            userLogin.setPassword(oldPassword);
            userService.updateUser(userLogin);
            success = true;
        }
        return success;
    }
    
}
