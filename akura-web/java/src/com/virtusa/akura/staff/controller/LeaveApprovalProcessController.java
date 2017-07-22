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
package com.virtusa.akura.staff.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.CommonEmailBean;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.dto.StaffLeaveStatus;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.attendance.service.DailyAttendanceService;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.service.EmailService;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.staff.validator.LeaveApprovalProcessValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.email.EmailUtil;


/**
 * The LeaveApprovalProcessController is to manage staff LeaveApprovalProcess tab functionalities such as
 * Approve, Reject Leave.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class LeaveApprovalProcessController {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(LeaveApprovalProcessController.class);
    
    /** String attribute for holding USER_LOGIN. */
    private static final String USER_LOGIN = "userLogin";

    /** String attribute for holding STAFF_LEAVE. */
    private static final String MODEL_ATT_STAFF_LEAVE = "staffLeave";
    
    /** String attribute for holding REQ_MAP_VALUE_STAFF_LEAVE_LIST. */
    private static final String REQ_MAP_VALUE_STAFF_LEAVE_LIST ="/leaveApprovalProcess.htm";
    
    /** String attribute for holding VIEW_LEAVE_APPROVAL_PROCESS. */
    private static final String VIEW_LEAVE_APPROVAL_PROCESS = "staff/leaveApprovalProcess";
    
    /** attribute for holding model attribute for leaveStatusList. */
    private static final String LEAVE_STATUS_LIST = "leaveStatusList";
    
    /** model attribute of staff leave List. */
    private static final String MODEL_ATT_STAFF_LEAVE_LIST = "staffLeaveList";
    
    /** String attribute for holding DEFALUT_DATE_FORMAT. */
    private static final String DEFALUT_DATE_FORMAT = "yyyy-MM-dd";
    
    /** String attribute for holding ERROR_MSG. */
    private static final String MSG_UPDATED_SUCCESSFULLY = "LEVAPPRVAL.UPDATED.SUCCESSFULLY";
    
    /** attribute for holding model attribute success message. */
    private static final String SUCCESS_MESSAGE = "successMessage";
    
    /** attribute for holding model attribute email message. */
    private static final String EMAIL_MESSAGE = "emailMessage";
    
    /** path to email properties file. */
    public static final String EMAIL_PROPERTIES = "email";
    
    /** The admin email that send an email to user when password changes. */
    public static final String ADMIN_EMAIL = "admin.email";
    
    /** String attribute for holding LEAVE_APPROVAL_TEMPLATE. */
    private static final String LEAVE_APPROVAL_TEMPLATE = "staffLeave.approve.reject.template";

    /** String attribute for holding LEAVE_APPROVAL_MAIL_SUBJECT. */
    private static final String LEAVE_APPROVAL_MAIL_SUBJECT = "staffLeave.approval.subject";
    
    /** String attribute for holding NO_OF_DAYS_FIELD. */
    private static final String NO_OF_DAYS_FIELD = "noOfdays";

    /** String attribute for holding STAFF_LEAVE_TYPE_FIELD. */
    private static final String STAFF_LEAVE_TYPE_FIELD = "staffLeaveType";

    /** String attribute for holding DATE_TO_FIELD. */
    private static final String DATE_TO_FIELD = "dateTo";

    /** String attribute for holding DATE_FROM_FIELD. */
    private static final String DATE_FROM_FIELD = "dateFrom";

    /** String attribute for holding REG_NO_FIELD. */
    private static final String REG_NO_FIELD = "regNo";
    
    /** String attribute for holding FULL_NAME_FIELD. */
    private static final String FULL_NAME_FIELD = "fullName";
    
    /** String attribute for holding LEAVE_EMAIL_SUCCESS. */
    private static final String LEAVE_EMAIL_SUCCESS = "LEVAPPRVAL.EMAIL.SEND.SUCCESS";
    
    /** String attribute for holding LEAVE_EMAIL_UNSUCCESS. */
    private static final String LEAVE_EMAIL_UNSUCCESS = "LEVAPPRVAL.EMAIL.SEND.UNSUCCESS";
    
    /** String attribute for holding STAFF_EMAIL_ADDRESS_NOT_EXIST. */
    private static final String STAFF_EMAIL_ADDRESS_NOT_EXIST = "LEVAPPRVAL.EMAIL.ADDRESS.NOT.EXIST";

    /** String attribute for holding COMMENT. */
    private static final String COMMENT = "comment";

    /** String attribute for holding LEAVE_STATUS. */
    private static final String LEAVE_STATUS = "leaveStatus";
    
    /** String attribute for holding ERROR_SENDING_MAIL_LOG. */
    private static final String ERROR_SENDING_MAIL_LOG = "Error Sending Mail ( sendApprovalMail method )";
    
    /** attribute for holding edit message. */
    private static final String EDITPANE = "editpane";
    
    /** attribute for holding edit id. */
    private static final String EDIT_LEAVE_ID = "editedStaffLeaveId";
    
    /** StaffService attribute for holding staffService. */
    private StaffService staffService;
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** StaffCommonService attribute for holding staffCommonService. */
    private StaffCommonService staffCommonService;
    
    /** EmailService attribute for holding emailService. */
    private EmailService emailService;
    
    /** DailyAttendanceService attribute for holding dailyAttendanceService. */
    private DailyAttendanceService dailyAttendanceService;
    
    /** LeaveApprovalProcessValidator attribute for holding leaveApprovalProcessValidator. */
    private LeaveApprovalProcessValidator leaveApprovalProcessValidator;
    
    
    /**
     * Sets an instance of StaffService.
     * 
     * @param staffServiceVal - the relevant instance of StaffService
     */
    public void setStaffService(StaffService staffServiceVal) {
    
        this.staffService = staffServiceVal;
    }

    /**
     * @param commonServiceVal the commonService to set
     */
    public void setCommonService(CommonService commonServiceVal) {
    
        this.commonService = commonServiceVal;
    }
    
    /**
     * @param staffCommonServiceVal the StaffCommonService to set
     */
    public void setStaffCommonService(StaffCommonService staffCommonServiceVal) {
    
        this.staffCommonService = staffCommonServiceVal;
    }

    /**
     * @param emailServiceVal the emailService to set
     */
    public void setEmailService(EmailService emailServiceVal) {
    
        this.emailService = emailServiceVal;
    }

    /**
     * @param approvalProcessValidator the leaveApprovalProcessValidator to set
     */
    public void setLeaveApprovalProcessValidator(LeaveApprovalProcessValidator approvalProcessValidator) {
    
        this.leaveApprovalProcessValidator = approvalProcessValidator;
    }
    
    /**
     * @param dailyAttendanceServiceVal the dailyAttendanceService to set
     */
    public void setDailyAttendanceService(DailyAttendanceService dailyAttendanceServiceVal) {
    
        this.dailyAttendanceService = dailyAttendanceServiceVal;
    }

    /**
     * intiBinder method is to bind date class with the date format.
     * 
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    
        binder.getBindingResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFALUT_DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
    }
    
    
    /**
     * Method to set values to leave status list in JSP.
     * 
     * @return a list of leave status.
     * @throws AkuraException SMS exception throw.
     */
    @ModelAttribute(LEAVE_STATUS_LIST)
    public List<StaffLeaveStatus> populateLeaveStatusList() throws AkuraException {
    
        List<StaffLeaveStatus> staffLeaveStatusList = new ArrayList<StaffLeaveStatus>();
        // Remove the in-progress status from the list.
        for (StaffLeaveStatus staffLeaveStatus : staffService.getLeaveStatusList()) {
            if (staffLeaveStatus.getStaffLeaveStatusId() != AkuraConstant.THREE) {
                staffLeaveStatusList.add(staffLeaveStatus);
            }
        }
        return staffLeaveStatusList;
    }
    
    /**
     * Populate in progress Staff Leave list.
     * 
     * @return staffLeaveList
     * @throws AkuraAppException - AkuraAppException
     */
    @ModelAttribute(MODEL_ATT_STAFF_LEAVE_LIST)
    public List<Object[]> populateStaffLeaveList() throws AkuraAppException {
    
        return staffService.getStaffLeaveListByStatusId(AkuraConstant.THREE);
    }
    
    /**
     * 
     * handle GET requests for Staff_leave_approval_process view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(method = RequestMethod.GET,value = REQ_MAP_VALUE_STAFF_LEAVE_LIST)
    public String showLeaveApprovalProcessPage(ModelMap model) throws AkuraAppException {
    
        StaffLeave staffLeave = (StaffLeave) model.get(MODEL_ATT_STAFF_LEAVE);
        if (staffLeave == null) {
            staffLeave = new StaffLeave();
        }
        
        model.addAttribute(MODEL_ATT_STAFF_LEAVE_LIST, populateStaffLeaveList());
        model.addAttribute(MODEL_ATT_STAFF_LEAVE, staffLeave);
        return VIEW_LEAVE_APPROVAL_PROCESS;
    }
    
    /**
     * handle POST requests for Staff_leave_approval_process view.
     * 
     * @param staffLeave - StaffLeave.
     * @param request - HttpServletRequest.
     * @param session - HttpSession.
     * @param bindingResult - BindingResult.
     * @param modelMap - ModelMap.
     * @return ModelAndView.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(method = RequestMethod.POST, value = REQ_MAP_VALUE_STAFF_LEAVE_LIST)
    public ModelAndView updateStaffLeaveStatus(@ModelAttribute(MODEL_ATT_STAFF_LEAVE) StaffLeave staffLeave,
            BindingResult bindingResult, HttpServletRequest request, HttpSession session, ModelMap modelMap)
            throws AkuraAppException {
    
        String reDirectPage = VIEW_LEAVE_APPROVAL_PROCESS;
        
        String emailMessage = null;
        
        leaveApprovalProcessValidator.validate(staffLeave, bindingResult);
        if (!bindingResult.hasErrors()) {
            
            UserLogin userLogin = (UserLogin) session.getAttribute(USER_LOGIN);
            staffLeave.setUserLogin(userLogin);
            staffLeave.setApprovedRejectedDate(new Date());
            staffService.updateStaffLeave(staffLeave);
            
            // Set the time in and time out in attendance for half day.
            if (staffLeave.getNoOfDays() == AkuraConstant.ZERO_POINT_FIVE
                    && staffLeave.getDateType().equals(AkuraConstant.HALF_DAY)) {
                
                dailyAttendanceService.updateAttendanceForApprovedHalfDay(staffLeave.getStaffId(),
                        staffLeave.getFromDate());
            }
            
            String message = new ErrorMsgLoader().getErrorMessage(MSG_UPDATED_SUCCESSFULLY);
            modelMap.addAttribute(SUCCESS_MESSAGE, message);
            
            Staff staff = staffService.findStaff(staffLeave.getStaffId());
            // Check the email address is exist.
            if (staff.getEmail() != null && !staff.getEmail().isEmpty()) {
                
                // Sending email.
                if (sendLeaveApprovalEmail(staff, modelMap, staffLeave)) {
                    emailMessage = new ErrorMsgLoader().getErrorMessage(LEAVE_EMAIL_SUCCESS);
                    modelMap.addAttribute(SUCCESS_MESSAGE, message.concat(emailMessage));
                }
            } else {
                emailMessage = new ErrorMsgLoader().getErrorMessage(STAFF_EMAIL_ADDRESS_NOT_EXIST);
                modelMap.addAttribute(EMAIL_MESSAGE, emailMessage);
            }
            
            reDirectPage = showLeaveApprovalProcessPage(modelMap);
        } else {
            modelMap.addAttribute(EDITPANE, EDITPANE);
            modelMap.addAttribute(EDIT_LEAVE_ID, staffLeave.getStaffLeaveId());
        }
        
        return new ModelAndView(reDirectPage, modelMap);
        
    }
    
    /**
     * Send leave approval email.
     * 
     * @param model - ModelMap
     * @param staff - Staff
     * @param staffLeave -StaffLeave 
     * @throws AkuraAppException - AkuraAppException
     * 
     * @return succesEmail
     */
    private boolean sendLeaveApprovalEmail(Staff staff, ModelMap model, StaffLeave staffLeave) 
            throws AkuraAppException {
    
        String mailError = null;
        boolean flag = false;
        // Get the User Login by email address.
        
        try {
            
            // To Address should be the email address of the user.
            String strFromAddress = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, ADMIN_EMAIL);
            
            // From Address should be the email address of the admin.
            String strToAddress = staff.getEmail();
            
            // Get the subject of the email.
            String strSubject = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, LEAVE_APPROVAL_MAIL_SUBJECT);
            // Get the template name.
            String templateName = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, LEAVE_APPROVAL_TEMPLATE);
            
            // Leave approval process email should be copied to the staff member.
            List<String> ccList = new ArrayList<String>();
            ccList.add(strFromAddress);
            
            // Set properties.
            Map<String, Object> mapObjectMap = new HashMap<String, Object>();
            mapObjectMap.put(FULL_NAME_FIELD, staff.getFullName());
            mapObjectMap.put(REG_NO_FIELD, staff.getRegistrationNo());
            mapObjectMap.put(LEAVE_STATUS, staffService.findStaffStatusById(staffLeave.getStaffLeaveStatusId())
                    .getDescription());
            mapObjectMap.put(STAFF_LEAVE_TYPE_FIELD,
                    staffCommonService.findStaffLeaveTypeById(staffLeave.getStaffLeaveTypeId()).getDescription());
            mapObjectMap.put(DATE_FROM_FIELD, DateUtil.getFormatDate(staffLeave.getFromDate()));
            mapObjectMap.put(DATE_TO_FIELD, DateUtil.getFormatDate(staffLeave.getToDate()));
            mapObjectMap.put(NO_OF_DAYS_FIELD, staffLeave.getNoOfDays());
            mapObjectMap.put(COMMENT, staffLeave.getComment());
            
            // Include email header information
            EmailUtil.addHeaderForEmail(mapObjectMap);
            CommonEmailBean commonEmailBean = new CommonEmailBean();
            commonEmailBean.setToAddress(strToAddress);
            commonEmailBean.setFromAddress(strFromAddress);
            commonEmailBean.setCcAddresses(ccList);
            commonEmailBean.setMailTemplate(templateName);
            commonEmailBean.setObjectMap(mapObjectMap);
            commonEmailBean.setSubject(strSubject);
            
            flag = emailService.sendMail(commonEmailBean);
            
        } catch (MailException e) {
            
            mailError = new ErrorMsgLoader().getErrorMessage(LEAVE_EMAIL_UNSUCCESS);
            LOG.error(ERROR_SENDING_MAIL_LOG + e);
        } catch (AkuraAppException e) {
            mailError = new ErrorMsgLoader().getErrorMessage(LEAVE_EMAIL_UNSUCCESS);
            LOG.error(AkuraWebConstant.EMAIL_SEND_ERROR, e);
        }
        
        if (mailError != null) {
            model.addAttribute(EMAIL_MESSAGE, mailError);
        }
        return flag;
    }
}
