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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.ClassTeacher;
import com.virtusa.akura.api.dto.CommonEmailBean;
import com.virtusa.akura.api.dto.MessageToTeacher;
import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.SubjectTeacher;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.EmailService;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.student.service.ParentService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.email.EmailUtil;

/**
 * This is controller for send message from parent to teachers.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class MessageToTeacherControler {
    
    /** model attribute name for command object. */
    private static final String MESSAGE_TT = "messageTT";
    
    /** email template model attribute name. */
    private static final String MESSAGE_BODY = "messageBody";
    
    /** email template model attribute name. */
    private static final String FROM_NAME = "from";
    
    /** email template model attribute name. */
    private static final String TO_NAME = "toName";
    
    /** email template model attribute name. */
    private static final String CLASS_GRADE = "classGrade";
    
    /** email template model attribute name. */
    private static final String STUDENT_NAME = "student";
    
    /** return view name. */
    private static final String STUDENT_MESSAGE_TO_TEACHER = "student/messageToTeacher";
    
    /** error message for no email address found. */
    private static final String NO_EMAIL_ADDRESS_FOUND = "EMAIL.STAFF.NO_EMAIL_FOUND";
    
    /** error message for no CC address found. */
    private static final String UNABLE_FIND_CC_ADRESS = "EMAIL.STAFF.NO_CC_EMAIL_FOUND";
    
    /** error message for unable to send email. */
    private static final String UNABLE_TO_SEND_EMAIL = "EMAIL.ERROR";
    
    /** success message for sending email. */
    private static final String SUCCESSFULY_SEND = "EMAIL.STAFF.SUCCESS_SEND";
    
    /** model attribute for select a teacher. */
    private static final String TEACHER_MAP = "teacherMap";
    
    /** model attribute name for error messages. */
    private static final String ERROR_MESSAGE = "errorMessage";
    
    /** model attribute name for success messages. */
    private static final String SUCCESS_MESSAGE = "successMessage";
    
    /** class teacher label. */
    private static final String CLASS_TEACHER = "Class Teacher";
    
    /** session attribute name current student. */
    private static final String STUDENT_ID = "studentId";
    
    /** session attribute name. */
    private static final String USER = "user";
    
    /** Email template name. */
    private static final String EMAIL_TEMPLATE_MESSAGE_TO_TEACHER = "email.template.messageToTeacher";
    
    /** slash with spaces . */
    private static final String SLASH = " / ";
    
    /** Colon . */
    private static final String COLON = " : ";
    
    /** send email request URL . */
    private static final String SEND_EMAIL_REQUEST_HTM = "/sendEmailRequest.htm";
    
    /** Message to teacher view URL . */
    private static final String MESSAGE_TO_TEACHER_HTM = "/messageToTeacher.htm";
    
    /** ParentService attribute for holding parentService. */
    private ParentService parentService;
    
    /** StudentService attribute for holding studentService. */
    private StudentService studentService;
    
    /** StaffService attribute for holding staffService. */
    private StaffService staffService;
    
    /** emailService attribute for holding EmailService. */
    private EmailService emailService;
    
    /** email property file name. */
    private static final String EMAIL = "email";
    
    /** property key value. */
    private static final String ADMIN_EMAIL = "admin.email";
    
    /** Logger to log the exceptions. */
    private static final Logger LOG = Logger.getLogger(MessageToTeacherControler.class);
    
    /**
     * handle request to view message UI .
     * 
     * @param formMTT command object
     * @param model model data map
     * @param session HttpSession object
     * @return view name
     * @throws AkuraAppException when exception occurs
     */
    @RequestMapping(value = MESSAGE_TO_TEACHER_HTM, method = RequestMethod.GET)
    public String messageToTeacherForm(@ModelAttribute(MESSAGE_TT) MessageToTeacher formMTT, ModelMap model,
            HttpSession session) throws AkuraAppException {
    
        Integer studentId = (Integer) session.getAttribute(STUDENT_ID);
        
        // student current class info
        List<StudentClassInfo> curentClssList =
                studentService.getStudentClassInfoByStudentId(studentId, DateUtil.currentYearOnly());
        if (curentClssList != null && !curentClssList.isEmpty()) {
            // list should contain one element
            StudentClassInfo curentClss = curentClssList.get(0);
            
            // map contain key as staff id and value to display Teacher name with subject
            Map<Integer, String> teacherMap = new HashMap<Integer, String>();
            
            List<SubjectTeacher> subTeacherList =
                    staffService.getSubjectTeachers(curentClss.getClassGrade(), DateUtil.currentYearOnly());
            
            if (subTeacherList != null) {
                for (SubjectTeacher subTeacher : subTeacherList) {
                    String description = null;
                    description =
                            subTeacher.getStaff().getNameWithIntials() + COLON
                                    + subTeacher.getGradeSubject().getSubject().getDescription();
                    teacherMap.put(subTeacher.getStaff().getStaffId(), description);
                    
                }
            }
            
            ClassTeacher classTeacher =
                    staffService.getClassTeacher(curentClss.getClassGrade(), DateUtil.currentYearOnly());
            
            if (classTeacher != null) {
                int classTeacherId = classTeacher.getStaff().getStaffId();
                if (teacherMap.containsKey(classTeacherId)) {
                    // if class Teacher may also be a subject teacher
                    String description = teacherMap.get(classTeacherId);
                    description += SLASH + CLASS_TEACHER;
                    teacherMap.put(classTeacherId, description);
                } else {
                    String description = null;
                    description = classTeacher.getStaff().getNameWithIntials() + COLON + CLASS_TEACHER;
                    teacherMap.put(classTeacherId, description);
                }
            }
            
            formMTT.setTeacherMap(teacherMap);
        }
        return STUDENT_MESSAGE_TO_TEACHER;
    }
    
    /**
     * handle request for mail send action.
     * 
     * @param formMTT comand object
     * @param model model data map
     * @param request HttpServletRequest object
     * @param session HttpSession object
     * @return view name
     * @throws AkuraAppException when exception occurs
     */
    @RequestMapping(value = SEND_EMAIL_REQUEST_HTM, method = RequestMethod.POST)
    public String sendEmailRequest(@ModelAttribute(MESSAGE_TT) MessageToTeacher formMTT, ModelMap model,
            HttpServletRequest request, HttpSession session) throws AkuraAppException {
    
        String messageSubject = formMTT.getSubject();
        String message = formMTT.getMessage();
        int staffId = formMTT.getToStaffId();
        
        ErrorMsgLoader errorMsgLoader = new ErrorMsgLoader();
        
        if (staffId > 0 && !messageSubject.trim().isEmpty() && !message.trim().isEmpty()) {
            
            Staff teacher = staffService.findStaff(staffId);
            String emailAdd = teacher.getEmail();
            if (emailAdd != null && !emailAdd.isEmpty()) {
                
                // to get the parent info from the currently login user(parent)
                // and user must be a Parent
                UserInfo userInfo = (UserInfo) session.getAttribute(USER);
                Parent parent = parentService.getParentByNIC(userInfo.getUserLevelIdentifier());
                
                // to get student info
                Integer studentId = (Integer) session.getAttribute(STUDENT_ID);
                Student student = studentService.findStudent(studentId);
                
                // ccAddress
                List<String> ccAddress = null;
                if (formMTT.getCcStaffId() != null && !formMTT.getCcStaffId().isEmpty()) {
                     ccAddress = staffService.getStaffEmails(formMTT.getCcStaffId());
                    // if no CC address found
                    if (ccAddress == null || ccAddress.isEmpty()) {
                        model.addAttribute(ERROR_MESSAGE, errorMsgLoader.getErrorMessage(UNABLE_FIND_CC_ADRESS));
                    }
                }
                
                // send email
                try {
                    sendMailHelper(emailAdd, ccAddress, teacher.getNameWithIntials(), parent, student, formMTT);
                    model.addAttribute(SUCCESS_MESSAGE, errorMsgLoader.getErrorMessage(SUCCESSFULY_SEND));
                    // clear the model data (default form)
                    formMTT.clearAttributes();
                    
                } catch (AkuraAppException e) {
                    model.addAttribute(ERROR_MESSAGE, errorMsgLoader.getErrorMessage(UNABLE_TO_SEND_EMAIL));
                    LOG.error("------exception sending Mail----------" + e.toString());
                }
            } else {
                model.addAttribute(ERROR_MESSAGE, errorMsgLoader.getErrorMessage(NO_EMAIL_ADDRESS_FOUND));
            }
        } else {
            String errorMessage = errorMsgLoader.getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            model.addAttribute(ERROR_MESSAGE, errorMessage);
        }
        
        return messageToTeacherForm(formMTT, model, session);
    }
    
    /**
     * Helper method for send email.
     * 
     * @param toAddress email address to send
     * @param ccAddressList CC email address
     * @param toName senders name
     * @param parent Parent object
     * @param student Student object
     * @param formMTT form data object
     * @throws AkuraAppException when exception occurs
     */
    private void sendMailHelper(String toAddress, List<String> ccAddressList, String toName, Parent parent,
            Student student, MessageToTeacher formMTT) throws AkuraAppException {
    
        List<StudentClassInfo> curentClssList =
                studentService.getStudentClassInfoByStudentId(student.getStudentId(), DateUtil.currentYearOnly());
        // list should contain one element
        StudentClassInfo curentClss = curentClssList.get(0);
        String classGrade = curentClss.getClassGrade().getDescription();
        
        Map<String, Object> mailData = new HashMap<String, Object>();
        mailData.put(MESSAGE_BODY, formMTT.getMessage());
        mailData.put(FROM_NAME, parent.getNameWithInitials());
        mailData.put(TO_NAME, toName);
        mailData.put(STUDENT_NAME, student.getNameWtInitials());
        mailData.put(CLASS_GRADE, classGrade);
        
        sendEmailTo(toAddress, ccAddressList, formMTT.getSubject(), mailData);
        
    }
    
    /**
     * Send mails to given info, admin email as form address.
     * 
     * @param toAddress email address of the teacher
     * @param ccAddressList CC email address
     * @param subject email subject
     * @param mailData model data for email View
     * @throws AkuraAppException when exception occurs
     */
    private void sendEmailTo(String toAddress, List<String> ccAddressList, String subject, Map<String, Object> mailData)
            throws AkuraAppException {
    
        String adminEmail = PropertyReader.getPropertyValue(EMAIL, ADMIN_EMAIL);
        
        try {
            
            CommonEmailBean commonEmailBean = new CommonEmailBean();
            String templateName = PropertyReader.getPropertyValue(EMAIL, EMAIL_TEMPLATE_MESSAGE_TO_TEACHER);
            
            commonEmailBean.setSubject(subject);
            commonEmailBean.setFromAddress(adminEmail);
            commonEmailBean.setToAddress(toAddress);
            
            if (ccAddressList != null && !ccAddressList.isEmpty()) {
                commonEmailBean.setCcAddresses(ccAddressList);
            }
            
            EmailUtil.addHeaderForEmail(mailData);
            commonEmailBean.setObjectMap(mailData);
            
            commonEmailBean.setMailTemplate(templateName);
            emailService.sendMail(commonEmailBean);
            commonEmailBean.setAttachementPath(null);
            
        } catch (ResourceNotFoundException e) {
            throw new AkuraAppException(AkuraConstant.EMAIL_ERROR, e);
        } catch (MailSendException e) {
            throw new AkuraAppException(AkuraConstant.EMAIL_ERROR, e);
        }
        
    }
    
    /**
     * Setter method for ParentService.
     * 
     * @param parentServiceArg ParentService object to set
     */
    public void setParentService(ParentService parentServiceArg) {
    
        this.parentService = parentServiceArg;
    }
    
    /**
     * Setter method for StudentService.
     * 
     * @param studentServiceArg StudentService object to set
     */
    public void setStudentService(StudentService studentServiceArg) {
    
        this.studentService = studentServiceArg;
    }
    
    /**
     * Setter method for StaffService.
     * 
     * @param staffServiceArg StaffService to set
     */
    public void setStaffService(StaffService staffServiceArg) {
    
        this.staffService = staffServiceArg;
    }
    
    /**
     * Setter method for EmailService.
     * 
     * @param emailServiceArg EmailService to set
     */
    public void setEmailService(EmailService emailServiceArg) {
    
        this.emailService = emailServiceArg;
    }
    
}
