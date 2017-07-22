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
import org.springframework.web.bind.annotation.ResponseBody;

import com.virtusa.akura.api.dto.CommonEmailBean;
import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.dto.PastStudent;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentDiscipline;
import com.virtusa.akura.api.dto.StudentParent;
import com.virtusa.akura.api.dto.StudentStatus;
import com.virtusa.akura.api.dto.StudentTemporaryLeave;
import com.virtusa.akura.api.dto.SuspendStudent;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.WrapperTerminateStudent;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.AlreadyOnLeaveException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.EmailService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.student.validator.StudentSuspendValidator;
import com.virtusa.akura.student.validator.StudentTemporaryLeaveTerminateValidator;
import com.virtusa.akura.student.validator.StudentTerminateValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.email.EmailUtil;

/**
 * StudentTerminateController is to Past/TemporaryLeave/Suspend students in student module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StudentTerminateController {
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentTerminateController.class);
    
    /** Represents the retrun url for terminate student module. */
    private static final String ADMIN_MANAGE_VIEW_TERMINATE_STUDENT_HTM = "/terminateStudent.htm";
    
    /** view get method terminate student. */
    private static final String VIEW_GET_TERMINATE_STUDENT = "student/terminateStudent";
    
    /** Represents the model attribute for student. */
    private static final String STUDENT = "student";
    
    /** String attribute for holding selected student id. */
    private static final String SELECTED_STUDENT_ID = "selectedStudentId";
    
    /** String attribute for holding selected student id. */
    private static final String SELECT = "select";
    
    /** String attribute for holding selected student id. */
    private static final String SELECTVALUE = "selectvalue";
    
    /** model attribute of student discipline list. */
    private static final String MODEL_ATT_STUDENT_DISCIPLINE_LIST = "studentDisciplineList";
    
    /** model attribute of student discipline list. */
    private static final String MODEL_ATT_STUDENT_STATUS_LIST = "studentStatusList";
    
    /** model attribute of student discipline list. */
    private static final String MODEL_ATT_STUDENT_ID = "studentId";
    
    /** StudentService attribute for holding studentService. */
    private StudentService studentService;
    
    /** model attribute of wrapperQualifications list. */
    private static final String MODEL_ATT_WRAPPER_TERMINATE_STUDENT = "wrapperTerminateStudent";
    
    /** Represents the retrun url for terminate_Student member. */
    private static final String TERMINATE_STUDENT_HTM = "/saveOrUpdateTerminateStudent.htm";
    
    /** Represents the retrun url for terminate student module. */
    private static final String ADMIN_MANAGE_TERM_MARKS_TEMP_LEAVE_STUDENT_HTM = "checkTerminateStudent.htm";
    
    /** Represents the retrun url for terminate_Student member. */
    private static final String TERMINATE_TEMP_LEAVE_STUDENT_HTM = "/saveOrUpdateTempStudent.htm";
    
    /** String attribute for holding defalut_date_format. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /** Represents the model value for successful addition. */
    private static final String TEMP_LEAVE_SUCCESSFULLY_ADDED = "REF.UI.TEMPLEAVE.STUDENT.SUCCESSFULLY_ADDED";
    
    /** Represents the model value for successful addition. */
    private static final String SUSPEND_MESSAGE = "REF.UI.STUDENT.SUSPEND_STUDENT.SUCCESSFULLY_ADDED";
    
    /** Represents the model value for successful addition. */
    private static final String SUCESS_EMAIL = "REF.UI.STUDENT.SUCESS_EMAIL";
    
    /** Represents the model value for successful addition. */
    private static final String FAILURE_EMAIL_PARENT_EMAIL = "REF.UI.STUDENT.FAILURE_EMAIL_PARENT_EMAIL";
    
    /** Represents the model value for successful addition. */
    private static final String FAILURE_EMAIL_PARENT_NOT_EXIST = "REF.UI.STUDENT.FAILURE_EMAIL_PARENT_NOTEXIST";
    
    /** Represents the model value for successful addition. */
    private static final String FAILURE_EMAIL_PARENT_EXIXTS = "REF.UI.STUDENT.FAILURE_FAILURE_EMAIL_PARENT_EXIXTS";
    
    /** Represents the model value for successful addition. */
    private static final String FAILURE_EMAIL_SERVER_ERROR = "REF.UI.STUDENT.FAILURE_SERVER_ERROR";
    
    /** Represents the model value for successful addition. */
    private static final String SUCCESSFULLY_TERMINATED = "REF.UI.STUDENT.DEPARTURE_DETAILS.SUCCESSFULLY_TERMINATED";
    
    /** Represents the model attribute `success`. */
    private static final String MODEL_ATTRIB_SUCCESS = "success";
    
    /** key to hold string of true. */
    private static final String TRUE = "true";
    
    /** key to hold string of false. */
    private static final String FALSE = "false";
    
    /** key to hold string of temporary leave. */
    private static final String TEMPORARY_LEAVE = "Temporary Leave";
    
    /** key to hold string of Past. */
    private static final String PAST = "Past";
    
    /** Represents the model attribute `success`. */
    private static final String PAST_DELETE = "pastDelete";
    
    /** Represents the model attribute `success`. */
    private static final String TEMP_DELETE = "tempDelete";
    
    /** Represents an instance of staffDepartureDetailsValidator. */
    private StudentSuspendValidator studentSuspendValidator;
    
    /** Represents the error message for error. */
    private static final String ERROR_MSG = "REF.UI.STUDENT.DEPARTURE_DETAILS.ERROR";
    
    /** path to email properties file. */
    public static final String EMAIL_PROPERTIES = "email";
    
    /** The admin email that send an email to user when password changes. */
    public static final String ADMIN_EMAIL = "admin.email";
    
    /** model attribute of message. */
    private static final String MODEL_ATT_MESSAGE = "message";
    
    /** model attribute of Successmessage. */
    private static final String MODEL_ATT_SUCCESS_MESSAGE = "successmessage";
    
    /** String attribute for holding MESSAGE_TOPIC. */
    private static final String MESSAGE_TOPIC = "Student terminate details";
    
    /** String attribute for holding MAP_FNAME. */
    private static final String MAP_FNAME = "fName";
    
    /** String attribute for holding MAP_FNAME. */
    private static final String MAP_STFNAME = "stfName";
    
    /** String attribute for holding MAP_FNAME. */
    private static final String MAP_CONTENT = "content";
    
    /** String attribute for holding MAIL_SUBJECT. */
    private static final String MAIL_SUBJECT = "studentTerminate.subject";
    
    /** String attribute for holding MAP_TODATE . */
    private static final String MAP_TODATE = "toDate";
    
    /** String attribute for holding MAIL_SUBJECT. */
    private static final String MAP_FROMDATE = "fromDate";
    
    /** String attribute for holding MAP_REASON. */
    private static final String MAP_REASON = "reason";
    
    /**
     * The constant for hold confirmation velocity file name.
     */
    public static final String VELOCITY_CONFIRMATION_TO_USER = "email.template.studentTerminateDetail";
    
    /**
     * The constant for hold confirmation velocity file name.
     */
    public static final String VELOCITY_TEMPLEAVECONFIRMATION_TO_USER =
            "email.template.studentTerminateTempleaveDetail";
    
    /**
     * The constant for hold confirmation velocity file name.
     */
    public static final String VELOCITY_PAST_CONFIRMATION_TO_USER = "email.template.studentPastDetails";
    
    /** String attribute for holding SESSION_USER. */
    private static final String SESSION_USER = "user";
    
    /** Represents the model attribute `leave`. */
    private static final String MODEL_LEAVE_EXISTS = "leave";
    
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
    
    /** Represents an instance of studentTerminateValidator. */
    private StudentTerminateValidator studentTerminateValidator;
    
    /**
     * Represents an instance of the CommonService.
     */
    private CommonService commonService;
    
    /** Represents an instance of studentTemporaryLeaveTerminateValidator. */
    private StudentTemporaryLeaveTerminateValidator studentTemporaryLeaveTerminateValidator;
    
    /**
     * Sets an instance of CommonService.
     * 
     * @param commonServiceVal - the instance of CommonService
     */
    public final void setCommonService(final CommonService commonServiceVal) {

        commonService = commonServiceVal;
    }
    
    /**
     * Represents an instance of the StudentTerminateValidator.
     * 
     * @param studentTerminateValidatorObj - StudentTerminateValidator object.
     */
    public void setStudentTerminateValidator(StudentTerminateValidator studentTerminateValidatorObj) {

        this.studentTerminateValidator = studentTerminateValidatorObj;
    }
    
    /**
     * Set the service instance to inject the service.
     * 
     * @param studentServiceValue the studentService to set
     */
    public void setStudentService(StudentService studentServiceValue) {

        this.studentService = studentServiceValue;
    }
    
    /**
     * Represents an instance of the staffDepartureDetailsValidator.
     * 
     * @param studentSuspendValidatorObj - StudentSuspendValidator object.
     */
    public void setStudentSuspendValidator(StudentSuspendValidator studentSuspendValidatorObj) {

        this.studentSuspendValidator = studentSuspendValidatorObj;
    }
    
    /**
     * Represents an instance of the staffDepartureDetailsValidator.
     * 
     * @param studentTemporaryLeaveValidatorRef - studentTemporaryLeaveValidatorRef object.
     */
    public void setStudentTemporaryLeaveTerminateValidator(
            StudentTemporaryLeaveTerminateValidator studentTemporaryLeaveValidatorRef) {

        this.studentTemporaryLeaveTerminateValidator = studentTemporaryLeaveValidatorRef;
    }
    
    /**
     * Initializes the reference data that is to be previewed on the UI.
     * 
     * @param request - an object of HttpServletRequest
     * @param student - an instance of Student
     * @param model - a HashMap that contains information of the Past staff service.
     * @param session - a session to pass values.
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = ADMIN_MANAGE_VIEW_TERMINATE_STUDENT_HTM)
    public final String viewTerminateStudent(final ModelMap model, final HttpSession session,
            final HttpServletRequest request, @ModelAttribute(STUDENT) final Student student) throws AkuraAppException {

        String studentId = request.getParameter(SELECTED_STUDENT_ID);
        
        getDisiplinaryActionsList(model, studentId);
        
        Student objStudent = null;
        
        if (!(studentId.equals(AkuraConstant.EMPTY_STRING))) {
            int studentIdVal = 0;
            studentIdVal = Integer.parseInt(studentId);
            objStudent = (Student) studentService.viewStudent(studentIdVal);
            
            // Assign staff object to wrapperQualification.
            WrapperTerminateStudent wrapperterminatestudent = new WrapperTerminateStudent();
            
            wrapperterminatestudent.getSuspendStudent().setStudent(objStudent);
            wrapperterminatestudent.getStudentTemporaryLeave().setStudent(objStudent);
            wrapperterminatestudent.getPastStudent().setStudent(objStudent);
            
            model.addAttribute(MODEL_ATT_WRAPPER_TERMINATE_STUDENT, wrapperterminatestudent);
            
            model.addAttribute(MODEL_ATT_STUDENT_ID, objStudent);
            
        }
        
        return VIEW_GET_TERMINATE_STUDENT;
        
    }
    
    /**
     * Get the student disciplinary actions list.
     * 
     * @param model - model to store and transport objects.
     * @param studentId - Student Id of the student member.
     * @throws AkuraAppException - throws detailed exception when fails.
     * @throws ParseException
     */
    
    private void getDisiplinaryActionsList(final ModelMap model, String studentId) throws AkuraAppException {

        if (studentId != null) {
            int studentIdVal = Integer.parseInt(studentId);
            
            int thisYear = DateUtil.currentYearOnly();
            List<StudentDiscipline> studentDiscipline =
                    studentService.viewCurrentYearStudentDisciplineInfo(studentIdVal, thisYear);
            model.addAttribute(MODEL_ATT_STUDENT_DISCIPLINE_LIST, studentDiscipline);
            
        }
    }
    
    /**
     * SaveOrUpdateSuspendStudent.
     * 
     * @param request - an object of HttpServletRequest
     * @param session - a session to pass values.
     * @param model - model to store and transport objects.
     * @param wrapperterminatettudent - WrapperTerminateStudent object.
     * @param result - BindingResult
     * @throws AkuraAppException - The exception details that occurred when processing.
     * @throws IOException - The exception details that occurred when input output exception.
     * @return - name of the view which is redirected to.
     */
    @RequestMapping(value = TERMINATE_STUDENT_HTM)
    public final String saveOrUpdateSuspendStudent(final HttpServletRequest request, final HttpSession session,
            final ModelMap model,
            @ModelAttribute(MODEL_ATT_WRAPPER_TERMINATE_STUDENT) final WrapperTerminateStudent wrapperterminatettudent,
            BindingResult result) throws AkuraAppException, IOException {

        String returnResult = VIEW_GET_TERMINATE_STUDENT;
        String studentId = request.getParameter(SELECTED_STUDENT_ID);
        
        // Get the suspend student from the wrapper object.
        SuspendStudent suspendStudent = wrapperterminatettudent.getSuspendStudent();
        
        // Get the string value of terminateCriteria.
        String terminateCriteria = request.getParameter(SELECT);
        
        Student objStudent = null;
        
        // Get the description of the selected terminate criteria.
        String desTerminateCriteria = commonService.getTerminateStatus().get(1).getDescription();
        
        int studentIdVal = 0;
        
        if (!(studentId.equals(null))) {
            
            studentIdVal = Integer.parseInt(request.getParameter(SELECTED_STUDENT_ID));
            
        }
        
        studentIdVal = Integer.parseInt(studentId);
        int terminateCriteriaVal = Integer.parseInt(terminateCriteria);
        
        // Set the values to the student object.
        objStudent = studentService.findStudent(studentIdVal);
        objStudent.setStudentId(studentIdVal);
        objStudent.setStatusId(terminateCriteriaVal);
        suspendStudent.setStudent(objStudent);
        
        // Validate the suspend student.
        studentSuspendValidator.validate(suspendStudent, result);
        
        if (result.hasErrors()) {
            model.addAttribute(SELECT, terminateCriteria);
            model.addAttribute(SELECTVALUE, desTerminateCriteria);
            getDisiplinaryActionsList(model, studentId);
            model.addAttribute(MODEL_ATT_STUDENT_ID, objStudent);
            return returnResult;
        } else {
            
            try {
                boolean activityStatus = studentService.suspendStudent(suspendStudent);
                
                if (activityStatus) {
                    
                    this.sendConfirmationMail(studentIdVal, model, session, objStudent, "Suspended",
                            wrapperterminatettudent, terminateCriteriaVal);
                    
                    String strMessage = null;
                    
                    strMessage = new ErrorMsgLoader().getErrorMessage(SUSPEND_MESSAGE);
                    model.addAttribute(MODEL_ATT_STUDENT_ID, objStudent);
                    model.addAttribute(MODEL_ATTRIB_SUCCESS, strMessage);
                }
                
            } catch (AkuraAppException e) {
                String strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MSG);
                model.addAttribute(MODEL_ATTRIB_SUCCESS, strMessage);
                model.addAttribute(MODEL_ATT_STUDENT_ID, objStudent);
            } catch (AlreadyOnLeaveException e) {
                String strMessage = e.getErrorCode();
                model.addAttribute(SELECT, terminateCriteria);
                model.addAttribute(SELECTVALUE, desTerminateCriteria);
                getDisiplinaryActionsList(model, studentId);
                model.addAttribute(MODEL_ATT_STUDENT_ID, objStudent);
                
                model.addAttribute(MODEL_ATT_STUDENT_ID, objStudent);
                model.addAttribute(MODEL_LEAVE_EXISTS, strMessage);
                
            }
        }
        
        return returnResult;
        
    }
    
    /**
     * Checks term Marks completed.
     * 
     * @param request - an object of HttpServletRequest
     * @param session - a session to pass values.
     * @param model - model to store and transport objects.
     * @param wrapperterminatettudent - WrapperTerminateStudent object.
     * @param result - BindingResult
     * @throws AkuraAppException - The exception details that occurred when processing.
     * @throws IOException - The exception details that occurred when input output exception.
     * @throws ParseException -The exception details that occurred when parsing.
     * @return - name of the view which is redirected to.
     */
    
    @RequestMapping(value = ADMIN_MANAGE_TERM_MARKS_TEMP_LEAVE_STUDENT_HTM)
    @ResponseBody
    public String checkTermMarks(final HttpServletRequest request, final HttpSession session, final ModelMap model,
            @ModelAttribute(MODEL_ATT_WRAPPER_TERMINATE_STUDENT) final WrapperTerminateStudent wrapperterminatettudent,
            BindingResult result) throws AkuraAppException, IOException, ParseException {

        String studentId = request.getParameter(SELECTED_STUDENT_ID);
        
        int studentIdd = Integer.parseInt(studentId);
        
        if (!studentService.checkMarkEntriesCompleted(studentIdd)) {
            return TRUE;
            
        }
        
        return FALSE;
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
     * Terminates a student.
     * 
     * @param pastStudent - past Student object
     * @param model - a HashMap that contains information of the Past student.
     * @param request - an object of HttpServletRequest
     * @param wrapperterminatettudent - wrapper terminate student
     * @param result - Result
     * @param session - a session to pass values.
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     * @throws IOException - IOException
     * @throws ParseException - ParseException
     */
    
    @RequestMapping(value = "/saveOrUpdatePastStudent.htm")
    public final String saveOrUpdatePastStudent(PastStudent pastStudent, final ModelMap model,
            @ModelAttribute(MODEL_ATT_WRAPPER_TERMINATE_STUDENT) final WrapperTerminateStudent wrapperterminatettudent,
            final BindingResult result, final HttpServletRequest request, final HttpSession session)
            throws AkuraAppException, IOException, ParseException {

        String returnResult = VIEW_GET_TERMINATE_STUDENT;
        
        int studentIdVal =
                request.getParameter(SELECTED_STUDENT_ID) == null ? 0 : Integer.parseInt(request
                        .getParameter(SELECTED_STUDENT_ID));
        
        int terminateCriteriaVal = Integer.parseInt(request.getParameter(SELECT));
        
        Student student = studentService.findStudent(Integer.parseInt(request.getParameter(SELECTED_STUDENT_ID)));
        student.setStudentId(studentIdVal);
        student.setDateOfDeparture(wrapperterminatettudent.getPastStudent().getDateOfDeparture());
        /* set status Id of the student. */
        student.setStatusId(terminateCriteriaVal);
        
        pastStudent.setStudent(student);
        pastStudent.setDateOfDeparture(wrapperterminatettudent.getPastStudent().getDateOfDeparture());
        pastStudent.setCompleteClearance(wrapperterminatettudent.getPastStudent().getCompleteClearance());
        pastStudent.setReason(wrapperterminatettudent.getPastStudent().getReason());
        pastStudent.setFirstSchoolDay(student.getFirstSchoolDay());
        
        studentTerminateValidator.validate(pastStudent, result);
        if (result.hasErrors()) {
            model.addAttribute(SELECT, (request.getParameter(SELECT)));
            model.addAttribute(SELECTVALUE, PAST);
            
            model.addAttribute(MODEL_ATT_STUDENT_ID,
                    (Student) studentService.viewStudent(Integer.parseInt(request.getParameter(SELECTED_STUDENT_ID))));
            return returnResult;
        } else {
            
            String strMessage = null;
            String delete = request.getParameter(PAST_DELETE);
            
            try {
                boolean activityStatus = studentService.terminatePastStudent(pastStudent);
                if (activityStatus) {
                    if (delete.equals(TRUE)) {
                        studentService.deleteStudentTermmarks(studentIdVal);
                    }
                    /*
                     * String strToDate = ""; String strFromDate = pastStudent.getDateOfDeparture()
                     * .toString(); String strReason = pastStudent.getReason();
                     */
                    this.sendConfirmationMail(studentIdVal, model, session, student, PAST, wrapperterminatettudent,
                            terminateCriteriaVal);
                    
                    strMessage = new ErrorMsgLoader().getErrorMessage(SUCCESSFULLY_TERMINATED);
                    model.addAttribute(MODEL_ATT_STUDENT_ID, (Student) studentService.viewStudent(Integer
                            .parseInt(request.getParameter(SELECTED_STUDENT_ID))));
                    
                    model.addAttribute(MODEL_ATTRIB_SUCCESS, strMessage);
                }
            } catch (AkuraAppException e) {
                strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MSG);
                model.addAttribute(MODEL_ATTRIB_SUCCESS, strMessage);
                model.addAttribute(MODEL_ATT_STUDENT_ID, student);
            }
            
            return returnResult;
        }
    }
    
    /**
     * Get the terminate criteria list.
     * 
     * @param model - model to store and transport objects.
     * @param studentStatuDescription - String value of selected student status.
     * @throws ParseException -The exception details that occurred when parsing.
     * @throws AkuraAppException - throws detailed exception when fails.
     * @return - List of StudentStatus.
     */
    @ModelAttribute(MODEL_ATT_STUDENT_STATUS_LIST)
    public List<StudentStatus> getTerminateCriteria(final ModelMap model, String studentStatuDescription)
            throws AkuraAppException, ParseException {

        List<StudentStatus> studentStatus = commonService.getTerminateStatus();
        return studentStatus;
        
    }
    
    /**
     * Terminates a student.
     * 
     * @param request - an object of HttpServletRequest
     * @param session - a session to pass values.
     * @param model - model to store and transport objects.
     * @param wrapperterminatettudent - WrapperTerminateStudent object.
     * @param result - BindingResult
     * @throws AkuraAppException - The exception details that occurred when processing.
     * @throws IOException - The exception details that occurred when input output exception.
     * @throws ParseException -The exception details that occurred when parsing.
     * @return - name of the view which is redirected to.
     */
    @RequestMapping(value = TERMINATE_TEMP_LEAVE_STUDENT_HTM)
    public String saveOrUpdateStudentTemporaryLeave(ModelMap model,
            @ModelAttribute(MODEL_ATT_WRAPPER_TERMINATE_STUDENT) final WrapperTerminateStudent wrapperterminatettudent,
            BindingResult result, final HttpSession session, HttpServletRequest request) throws AkuraAppException,
            IOException, ParseException {

        int studentIdd = Integer.parseInt(request.getParameter(SELECTED_STUDENT_ID));
        String terminateCriteria = request.getParameter(SELECT);
        String returnResult = VIEW_GET_TERMINATE_STUDENT;
        int terminateCriteriaVal = Integer.parseInt(terminateCriteria);
        StudentTemporaryLeave studentTemporaryLeave = wrapperterminatettudent.getStudentTemporaryLeave();
        Student objStudent = null;
        int studentIdVal = 0;
        if (!(request.getParameter(SELECTED_STUDENT_ID).equals(AkuraConstant.EMPTY_STRING))) {
            
            studentIdVal = Integer.parseInt(request.getParameter(SELECTED_STUDENT_ID));
            
        }
        objStudent = studentService.findStudent(studentIdVal);
        
        // changing the status of the student
        objStudent.setStatusId(terminateCriteriaVal);
        studentTemporaryLeave.setStudent(objStudent);
        
        // validate the studentTemporaryLeave object
        studentTemporaryLeaveTerminateValidator.validate(studentTemporaryLeave, result);
        
        if (result.hasErrors()) {
            model.addAttribute(SELECT, terminateCriteria);
            model.addAttribute(SELECTVALUE, TEMPORARY_LEAVE);
            model.addAttribute(MODEL_ATT_STUDENT_ID, objStudent);
            return returnResult;
            
        } else {
            
            String strMessage = null;
            
            try {
                
                boolean activityStatus = studentService.terminateTemporaryLeaveStudent(studentTemporaryLeave);
                
                if (activityStatus) {
                    String delete = (request.getParameter(TEMP_DELETE));
                    if (delete.equals(TRUE)) {
                        studentService.deleteStudentTermmarks(studentIdd);
                    }
                    
                    this.sendConfirmationMail(studentIdd, model, session, objStudent, TEMPORARY_LEAVE,
                            wrapperterminatettudent, terminateCriteriaVal);
                    
                    strMessage = new ErrorMsgLoader().getErrorMessage(TEMP_LEAVE_SUCCESSFULLY_ADDED);
                    model.addAttribute(MODEL_ATTRIB_SUCCESS, strMessage);
                    model.addAttribute(MODEL_ATT_STUDENT_ID, objStudent);
                }
            } catch (AkuraAppException e) {
                strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MSG);
                model.addAttribute(MODEL_ATTRIB_SUCCESS, strMessage);
                model.addAttribute(MODEL_ATT_STUDENT_ID, objStudent);
                
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
     * @param student - Student object
     * @param content - Date Starting date.
     * @param wrapperterminatettudent - wrapperterminatettudent object
     * @param terminateCriteria - terminateCriteria.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    private void sendConfirmationMail(int studentIdVal, ModelMap model, final HttpSession session, Student student,
            String content, WrapperTerminateStudent wrapperterminatettudent, int terminateCriteria)
            throws AkuraAppException {

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
                        String toDateWithoutTime = null;
                        String fromDateWithoutTime = null;
                        String strReason = null;
                        Map<String, Object> mapObjectMap = new HashMap<String, Object>();
                        
                        if (terminateCriteria == AkuraConstant.PARAM_INDEX_FOUR) {
                            StudentTemporaryLeave studentTemporaryLeave =
                                    wrapperterminatettudent.getStudentTemporaryLeave();
                            
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            toDateWithoutTime = sdf.format(studentTemporaryLeave.getToDate());
                            fromDateWithoutTime = sdf.format(studentTemporaryLeave.getFromDate());
                            strReason = studentTemporaryLeave.getReason();
                            
                            templateName =
                                    PropertyReader.getPropertyValue(EMAIL_PROPERTIES,
                                            VELOCITY_TEMPLEAVECONFIRMATION_TO_USER);
                            mapObjectMap.put(MAP_TODATE, toDateWithoutTime);
                            mapObjectMap.put(MAP_FROMDATE, fromDateWithoutTime);
                            mapObjectMap.put(MAP_REASON, strReason);
                            
                        } else if (terminateCriteria == AkuraConstant.PARAM_INDEX_THREE) {
                            templateName =
                                    PropertyReader.getPropertyValue(EMAIL_PROPERTIES, VELOCITY_CONFIRMATION_TO_USER);
                            SuspendStudent suspendStudent = wrapperterminatettudent.getSuspendStudent();
                            
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            toDateWithoutTime = sdf.format(suspendStudent.getToDate());
                            fromDateWithoutTime = sdf.format(suspendStudent.getFromDate());
                            int disciplinaryActionId = suspendStudent.getDisciplinaryActionId();
                            
                            List<StudentDiscipline> listDisciplinaryAction =
                                    studentService.getSelectedDisciplinaryActionByStudent(disciplinaryActionId);
                            
                            String desDisciplinaryAction = listDisciplinaryAction.get(0).getComment();
                            if(suspendStudent.getDescription().trim() == null){
                            	strReason = desDisciplinaryAction;
                            }else{
                            strReason = desDisciplinaryAction + ", " + suspendStudent.getDescription();
                            }
                            mapObjectMap.put(MAP_TODATE, toDateWithoutTime);
                            mapObjectMap.put(MAP_FROMDATE, fromDateWithoutTime);
                            mapObjectMap.put(MAP_REASON, strReason);
                            
                        } else {
                            PastStudent pastStudent = wrapperterminatettudent.getPastStudent();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String dateWithoutTime;
                            
                            dateWithoutTime = sdf.format(pastStudent.getDateOfDeparture());
                            
                            strReason = pastStudent.getReason();
                            
                            mapObjectMap.put(MAP_FROMDATE, dateWithoutTime);
                            mapObjectMap.put(MAP_REASON, strReason);
                            templateName =
                                    PropertyReader.getPropertyValue(EMAIL_PROPERTIES,
                                            VELOCITY_PAST_CONFIRMATION_TO_USER);
                        }
                        
                        mapObjectMap.put(MODEL_ATT_MESSAGE, MESSAGE_TOPIC);
                        mapObjectMap.put(MAP_FNAME, parent.getFullName());
                        mapObjectMap.put(MAP_STFNAME, student.getFullName());
                        mapObjectMap.put(MAP_CONTENT, content);
                        
                        String subject = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, MAIL_SUBJECT);
                        
                        // Include email header information
                        EmailUtil.addHeaderForEmail(mapObjectMap);
                        
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
