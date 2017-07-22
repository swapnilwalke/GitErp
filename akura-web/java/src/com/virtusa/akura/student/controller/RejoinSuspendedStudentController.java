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

package com.virtusa.akura.student.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.CommonEmailBean;
import com.virtusa.akura.api.dto.Parent;

import com.virtusa.akura.api.dto.StudentDiscipline;
import com.virtusa.akura.api.dto.StudentParent;

import com.virtusa.akura.api.dto.SuspendStudent;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.dto.UserLogin;

import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.api.exception.InvalidRejoinDateException;
import com.virtusa.akura.api.exception.SuspendRejoinReasonValidationException;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.EmailService;
import com.virtusa.akura.common.service.UserService;

import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.student.validator.RejoinSuspendedStudentValidator;

import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;

import com.virtusa.akura.util.email.EmailUtil;

/**
 * RejoinSuspendedStudentController is to handle rejoin suspended student function.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class RejoinSuspendedStudentController {
    
    /** Represents the return url for student suspend details. */
    private static final String ADMIN_MANAGE_STUDENT_SUSPEND_DETAILS_HTM = "/suspendStudentDetails.htm";
    
    /** Represents the model attribute for student. */
    private static final String STUDENT = "student";
    
    /** Represents the model attribute for student. */
    private static final String SELECTED_STUDENT_ID = "selectedStudentId";
    
    /** Represents the model attribute for messages. */
    private static final String MESSAGE = "messages";
    
    /** view get method suspended student service. */
    private static final String VIEW_GET_SUSPENDED_STUDENT_REJOIN = "student/viewSuspendedStudentDetails";
    
    /** model attribute of student leave List. */
    private static final String MODEL_ATT_SUSPENDED_STUDENT_DETAIL_LIST = "suspendedStudentDetailList";
    
    /** key to hold String "listCount". */
    private static final String LIST_COUNT = "listCount";
    
    /** key to hold String "fullName". */
    private static final String FULL_NAME = "fullName";
    
    /** key to hold String "fullName". */
    private static final String ADMISSION_NUMBER = "admissionNumber";
    
    /** Represents the return url for re-join student member. */
    private static final String ACTIVE_SUSPENDED_STUDENT_HTM = "/manageActivateSuspendedStudent.htm";
    
    /** key to hold String "listCount". */
    private static final String SUSPEND_STUDENT = "suspendStudent";
    
    /** key to hold String "dateOfReJoin". */
    private static final String DATE_OF_RE_JOIN = "dateOfRejoin";
    
    /** key to hold String "dateOfReJoin". */
    private static final String REASON = "txtReason";
    
    /** String attribute for holding defalut_date_format. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /** key to hold error message invalid data type . */
    private static final String ERROR_MESSAGE_STUDENT_REJOIN = "STUDENT.REJOIN.INVALID.DATE.MESSAGE";
    
    /** key to hold error message "Error throws while updating a student member." . */
    private static final String ERROR_THROWS_WHILE_UPDATING_A_STUDENT_MEMBER = "STUDENT.UPDATE.STUDENT.ERROR";
    
    /** key to hold String "AkuraErrorMsg". */
    private static final String AKURA_ERROR_MSG = "AkuraErrorMsg";
    
    /** key to hold error message student rejoin update error. */
    private static final String STUDENT_REJOIN_UPDATE_SUDENT_ERROR = "STUDENT.REJOIN.UPDATE.STUDENT.ERROR";
    
    /** Represents the model attribute `success`. */
    private static final String MODEL_ATTRIB_SUCCESS = "success";
    
    /** key to hold message when student rejoin success. */
    private static final String STUDENT_REJOIN_SUCCESS_MESSSAGE = "STUDENT.REJOIN.SUCCESS.MESSAGE";
    
    /** model attribute of student discipline. */
    private static final String MODEL_ATT_STUDENT_DISCIPLINE_LIST = "studentDisciplineList";
    
    /** String attribute for holding SESSION_USER. */
    private static final String SESSION_USER = "user";
    
    /** path to email properties file. */
    public static final String EMAIL_PROPERTIES = "email";
    
    /** The admin email that send an email to user when password changes. */
    public static final String ADMIN_EMAIL = "admin.email";
    
    /** String attribute for holding MAIL_SUBJECT. */
    private static final String MAIL_SUBJECT = "suspendedStudentRejoin.subject";
    
    /** Represents the model value for successful addition. */
    private static final String SUCESS_EMAIL = "REF.UI.STUDENT.SUCESS_EMAIL";
    
    /** Represents the model value for successful addition. */
    private static final String FAILURE_EMAIL_PARENT_EMAIL = "REF.UI.STUDENT.FAILURE_EMAIL_PARENT_EMAIL";
    
    /**
     * The constant for hold confirmation velocity file name.
     */
    public static final String VELOCITY_TEMPLEAVECONFIRMATION_TO_USER = "email.template.studentSuspendDetail";
    
    /** model attribute of Successmessage. */
    private static final String MODEL_ATT_SUCCESS_MESSAGE = "successmessage";
    
    /** Represents the model value for successful addition. */
    private static final String FAILURE_EMAIL_PARENT_NOT_EXIST = "REF.UI.STUDENT.FAILURE_EMAIL_PARENT_NOTEXIST";
    
    /** Represents the model value for successful addition. */
    private static final String FAILURE_EMAIL_PARENT_EXIXTS = "REF.UI.STUDENT.FAILURE_FAILURE_EMAIL_PARENT_EXIXTS";
    
    /** Represents the model value for successful addition. */
    private static final String FAILURE_EMAIL_SERVER_ERROR = "REF.UI.STUDENT.FAILURE_SERVER_ERROR";
    
    /** model attribute of message. */
    private static final String MODEL_ATT_MESSAGE = "message";
    
    /** model attribute of message. */
    private static final String PARENT = "parentName";
    
    /**   key to hold String "nameWithInitial".   */
        private static final String NAME_WITH_INITIALS = "nameWithInitial";
    
    /**
     * Represents an instance of the studentService.
     */
    private StudentService studentService;
    
    /** emailService attribute for holding EmailService. */
    private EmailService emailService;
    
    /** String attribute for holding SESSION_USER. */
    private UserService userService;
    
    /**
     * Return userService of the Student.
     * 
     * @return the userService
     */
    public UserService getUserService() {
    
        return userService;
    }
    
    /**
     * Set userService.
     * 
     * @param userServiceVal the userService to set
     */
    public void setUserService(UserService userServiceVal) {
    
        this.userService = userServiceVal;
    }
    
    /**
     * Set the service instance to inject the service.
     * 
     * @param emailServiceValue the EmailService to set
     */
    public void setEmailService(EmailService emailServiceValue) {
    
        this.emailService = emailServiceValue;
    }
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(RejoinSuspendedStudentController.class);
    
    /**
     * Represents an instance of the RejoinSuspendedStudentValidator.
     */
    
    private RejoinSuspendedStudentValidator rejoinSuspendedStudentValidator;
    
    /**
     * Represents an instance of the rejoinSuspendedStudentValidator.
     * 
     * @param rejoinSuspendedStudentValidatorObj - studentService object.
     */
    public void setRejoinSuspendedStudentValidator(RejoinSuspendedStudentValidator rejoinSuspendedStudentValidatorObj) {
    
        this.rejoinSuspendedStudentValidator = rejoinSuspendedStudentValidatorObj;
    }
    
    /**
     * Represents an instance of the StudentService.
     * 
     * @param studentServiceObj - studentService object.
     */
    public void setStudentService(StudentService studentServiceObj) {
    
        this.studentService = studentServiceObj;
    }
    
    /**
     * Registers the given custom property editor for all properties of the Date type.
     * 
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public final void initBinder(final WebDataBinder binder) {
    
        binder.getBindingResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    /**
     * Get all the available disciplines for a particular student.
     * 
     * @param session - {@link HttpSession}
     * @param request -HttpServletRequest
     * @return list of StudentDiscipline
     * @throws AkuraAppException - Throw detailed exception
     */
    
    @ModelAttribute(MODEL_ATT_STUDENT_DISCIPLINE_LIST)
    public List<StudentDiscipline> populateStudentDiscipline(HttpSession session, HttpServletRequest request)
            throws AkuraAppException {
    
        int studentId = Integer.parseInt(request.getParameter(SELECTED_STUDENT_ID));
        
        return studentService.viewStudentDisciplineInfo(studentId);
        
    }
    
    /**
     * Initializes the reference data that is to be previewed on the UI.
     * 
     * @param request - an object of HttpServletRequest
     * @param student - an instance of Student
     * @param model - a HashMap that contains information of the suspended student service.
     * @param session - a session to pass values.
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = ADMIN_MANAGE_STUDENT_SUSPEND_DETAILS_HTM)
    public final String viewSuspendedStudentDetails(final ModelMap model, final HttpSession session,
            final HttpServletRequest request, @ModelAttribute(STUDENT) final Student student) throws AkuraAppException {
    
        String studentId = request.getParameter(SELECTED_STUDENT_ID);
        String studentIdAttribute = (String) session.getAttribute(SELECTED_STUDENT_ID);
        String message = request.getParameter(MESSAGE);
        
        if (studentId == null && studentIdAttribute != null) {
            studentId = studentIdAttribute;
            session.removeAttribute(SELECTED_STUDENT_ID);
            if (message != null) {
                model.addAttribute(MESSAGE, message);
                request.removeAttribute(MESSAGE);
                
            }
            
        }
        setSuspendedStudentList(model, studentId);
        SuspendStudent suspendStudent = new SuspendStudent();
        model.addAttribute(SUSPEND_STUDENT, suspendStudent);
        
        return VIEW_GET_SUSPENDED_STUDENT_REJOIN;
        
    }
    
    /**
     * Set the suspended student list list.
     * 
     * @param model - model to store and transport objects.
     * @param studentId - student Id of the student.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    private void setSuspendedStudentList(final ModelMap model, String studentId) throws AkuraAppException {
    
        if (studentId != null) {
            int studentIdVal = Integer.parseInt(studentId);
            
            List<SuspendStudent> suspendedStudentDetails = studentService.getSuspendedStudentByStudentId(studentIdVal);
            Student student = studentService.findStudent(studentIdVal);
            String fullName = student.getFullName();
            String admissionNumber = student.getAdmissionNo();
            String nameWithInitial = student.getNameWtInitials();
            
            int listCount = 0;
            
            if (suspendedStudentDetails != null && !suspendedStudentDetails.isEmpty()) {
                listCount = suspendedStudentDetails.size() - 1;
            }
            
            model.addAttribute(ADMISSION_NUMBER, admissionNumber);
            model.addAttribute(FULL_NAME, fullName);
            model.addAttribute(NAME_WITH_INITIALS, nameWithInitial);
            model.addAttribute(MODEL_ATT_SUSPENDED_STUDENT_DETAIL_LIST, suspendedStudentDetails);
            model.addAttribute(LIST_COUNT, listCount);
            
        }
        
    }
    
    /**
     * Re join a student member.
     * 
     * @param request - an object of HttpServletRequest
     * @param session - a session to store selected studentId.
     * @param model - a HashMap that contains information of the Staff member
     * @param suspendStudent - an instance of SuspendStudent
     * @param result - containing list of errors and student instance to which data was bound
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when saving or updating a
     *         suspendStudent instance.
     * @throws IOException - throws when fails to update the suspended student.
     * @throws ParseException - when fails to parse a String to date.
     */
    @RequestMapping(value = ACTIVE_SUSPENDED_STUDENT_HTM, method = RequestMethod.POST)
    public final String rejoinSuspendedStudent(final HttpServletRequest request, final HttpSession session,
            final ModelMap model, @ModelAttribute(SUSPEND_STUDENT) final SuspendStudent suspendStudent,
            BindingResult result) throws AkuraAppException, IOException, ParseException {
    
        String returnResult = VIEW_GET_SUSPENDED_STUDENT_REJOIN;
        
        // String reason = request.getParameter(REASON);
        String reason = suspendStudent.getCurtailedOrExtendedReason();
        String strMessage = null;
        String message = "";
        
        // validate suspededStudent object
        rejoinSuspendedStudentValidator.validate(suspendStudent, result);
        
        String studentId = request.getParameter(SELECTED_STUDENT_ID);
        session.setAttribute(SELECTED_STUDENT_ID, studentId);
        setSuspendedStudentList(model, studentId);
        
        // if inputs are incorrect return error message
        if (result.hasErrors()) {
            strMessage = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            model.addAttribute(MESSAGE, strMessage);
            return returnResult;
        }
        
        Date rejoinedDate = suspendStudent.getActivatedDate();
        String date = rejoinedDate.toString();
        
        if (!date.equals("")) {
            
            int studentIdVal = Integer.parseInt(studentId);
            
            try {
                Date convertedDate = suspendStudent.getActivatedDate();
                
                boolean joinStatus =
                        studentService.rejoinSuspendedStudentMemberService(studentIdVal, convertedDate, reason);
                
                // if process is success display success message
                if (joinStatus) {
                    this.sendConfirmationMail(studentIdVal, model, session, convertedDate, reason);
                    message = new ErrorMsgLoader().getErrorMessage(STUDENT_REJOIN_SUCCESS_MESSSAGE);
                    model.addAttribute(MODEL_ATTRIB_SUCCESS, message);
                    // add activation detail to model object
                    List<SuspendStudent> suspendedStudentDetails =
                            studentService.getSuspendedStudentByStudentId(studentIdVal);
                    model.addAttribute(MODEL_ATT_SUSPENDED_STUDENT_DETAIL_LIST, suspendedStudentDetails);
                    return VIEW_GET_SUSPENDED_STUDENT_REJOIN;
                    
                }
                
            } catch (InvalidRejoinDateException e) {
                
                String messageError = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_STUDENT_REJOIN);
                model.addAttribute(MESSAGE, messageError);
                
            } catch (IOException e) {
                
                LOG.error(ERROR_THROWS_WHILE_UPDATING_A_STUDENT_MEMBER + e.toString());
                throw new AkuraAppException(PropertyReader.getPropertyValue(AKURA_ERROR_MSG,
                        STUDENT_REJOIN_UPDATE_SUDENT_ERROR), e);
                
            } catch (SuspendRejoinReasonValidationException e) {
                message = e.getErrorCode();
                model.addAttribute(MESSAGE, message);
                
            }
            
        }
        return returnResult;
        
    }
    
    /**
     * Send the confirmation mail.
     * 
     * @param studentIdVal - Student Id
     * @param model - model
     * @param session - a session to pass values.
     * @param convertedDate - Date rejoin date.
     * @param reason - String extended/Curtailed reason
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    private void sendConfirmationMail(int studentIdVal, ModelMap model, final HttpSession session, Date convertedDate,
            String reason) throws AkuraAppException {
    
        String str = null;
        
        try {
            List<StudentParent> studentParent = studentService.getParentId(studentIdVal);
            int a = studentParent.size();
            
            if (a > 0) {
                
                StudentParent studentParentnew = studentParent.get(0);
                
                Parent parent = studentParentnew.getParent();
                
                if (!(parent == null)) {
                    
                    String strToemail = parent.getEmail();
                    
                    if (!(strToemail.equals(AkuraConstant.EMPTY_STRING))) {
                        CommonEmailBean commonEmailBean = new CommonEmailBean();
                        if (session.getAttribute(SESSION_USER) != null) {
                            UserInfo userInfo = (UserInfo) session.getAttribute(SESSION_USER);
                            String logginUserName = userInfo.getUsername();
                            UserLogin userLogin = userService.getUserByName(logginUserName);
                            String strFromAddress = userLogin.getEmail();
                            commonEmailBean.setFromAddress(strFromAddress);
                            
                        }
                        
                        String templateName = null;
                        
                        Map<String, Object> mapObjectMap = new HashMap<String, Object>();
                        
                        Student studentName = studentService.findStudent(studentIdVal);
                        String fullName = studentName.getFullName();
                        String strDate = DateUtil.getFormatDate(convertedDate);
                        String strParent = parent.getFullName();
                        
                        templateName =
                                PropertyReader.getPropertyValue(EMAIL_PROPERTIES,
                                        VELOCITY_TEMPLEAVECONFIRMATION_TO_USER);
                        
                        mapObjectMap.put(FULL_NAME, fullName);
                        mapObjectMap.put(DATE_OF_RE_JOIN, strDate);
                        mapObjectMap.put(REASON, reason);
                        mapObjectMap.put(PARENT, strParent);
                        
                        // Include email header information
                        EmailUtil.addHeaderForEmail(mapObjectMap);
                        String subject = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, MAIL_SUBJECT);
                        
                        commonEmailBean.setToAddress(strToemail);
                        commonEmailBean.setMailTemplate(templateName);
                        commonEmailBean.setObjectMap(mapObjectMap);
                        commonEmailBean.setSubject(subject);
                        emailService.sendMail(commonEmailBean);
                        
                        str = new ErrorMsgLoader().getErrorMessage(SUCESS_EMAIL);
                        
                        model.addAttribute(MODEL_ATT_SUCCESS_MESSAGE, str);
                    } else {
                        str = new ErrorMsgLoader().getErrorMessage(FAILURE_EMAIL_PARENT_EMAIL);
                        model.addAttribute(MODEL_ATT_MESSAGE, str);
                    }
                } else {
                    str = new ErrorMsgLoader().getErrorMessage(FAILURE_EMAIL_PARENT_NOT_EXIST);
                    model.addAttribute(MODEL_ATT_MESSAGE, str);
                }
            } else {
                str = new ErrorMsgLoader().getErrorMessage(FAILURE_EMAIL_PARENT_EXIXTS);
                model.addAttribute(MODEL_ATT_MESSAGE, str);
            }
        } catch (MailException e) {
            
            LOG.error("Error Sending Mail ( sendConfirmationMail method )" + e);
            str = new ErrorMsgLoader().getErrorMessage(FAILURE_EMAIL_SERVER_ERROR);
            model.addAttribute(MODEL_ATT_MESSAGE, str);
            
        }
        
    }
    
}

