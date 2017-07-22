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

import java.text.ParseException;
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

import com.virtusa.akura.api.dto.CommonEmailBean;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.dto.StaffLeaveType;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.DayTypeLeaveDayException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.api.exception.ExistStaffLeaveException;
import com.virtusa.akura.api.exception.LeaveApplyBeforeJoinDateException;
import com.virtusa.akura.api.exception.LeaveDayHolidayException;
import com.virtusa.akura.api.exception.NotAvailableLeavesForLeaveTypeException;
import com.virtusa.akura.common.service.EmailService;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.staff.validator.StaffLeaveValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.email.EmailUtil;

/**
 * The StaffLeaveController is to manage staff leave tab functionalities such as add, edit and delete leaves.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StaffLeaveController {
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StaffLeaveController.class);
    
    /** String attribute for error delete staffLeave. */
    private static final String ERROR_DEELETE_STAFFLEAVE = "Error while deleting the staffLeave --> ";
    
    /** String attribute for holding defalut_date_format. */
    private static final String DEFALUT_DATE_FORMAT = "yyyy-MM-dd";
    
    /** model attribute of staff leave. */
    private static final String MODEL_ATT_STAFF_LEAVE = "staffLeave";
    
    /** view get method staff leave. */
    private static final String VIEW_GET_STAFF_LEAVE = "staff/staffLeave";
    
    /** model attribute of staff leave List. */
    private static final String MODEL_ATT_STAFF_LEAVE_LIST = "staffLeaveList";
    
    /** model attribute of Year List. */
    private static final String MODEL_ATT_YEAR_LIST = "yearList";
    
    /** String attribute for holding staff id. */
    private static final String STAFF_ID = "staffId";
    
    /** request mapping value for save or update staff leave. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateStaffLeave.htm";
    
    /** view post method staff leave. */
    private static final String VIEW_POST_MANAGE_STAFF_LEAVE = "redirect:staffLeave.htm";
    
    /** view staff leave. */
    private static final String VIEW_STAFF_LEAVE = "/staffLeave.htm";
    
    /** request mapping value for delete staff leave. */
    private static final String REQ_MAP_VALUE_DELETE = "/deleteStaffLeave.htm";
    
    /** request value for Staff Leave id. */
    private static final String REQ_STAFF_LEAVE_ID = "staffLeaveId";
    
    /** request value for Year. */
    private static final String REQ_SELECTED_YEAR = "SelectedYear";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.STUDENTLEAVE.DELETE";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** attribute for holding message. */
    private static final String DEPARTURE_DATE = "departureDate";
    
    /** request value for userLoginId. */
    private static final String REQUEST_USER_LOGIN_ID = "userLoginId";
    
    /** key to hold string of year end date. */
    private static final String END_DATE = "-12-31";
    
    /** key to hold string of year start date. */
    private static final String START_DATE = "-01-01";
    
    /** attribute for holding model attribute for staffLeaveTypeInfoList. */
    private static final String MODEL_ATTR_STAFF_LEAVE_TYPE_INFO_LIST = "staffLeaveTypeInfoList";
    
    /** attribute for holding model attribute for staffLeaveTypeList. */
    private static final String MODEL_ATTR_STAFF_LEAVE_TYPE_LIST = "staffLeaveTypeList";
    
    /** attribute for holding model attribute for currentYear. */
    private static final String MODEL_ATT_CURRENT_YEAR = "currentYear";
    
    /** String attribute for holding EMPTY_STRING. */
    private static final String EMPTY_STRING = "";
    
    /** path to email properties file. */
    public static final String EMAIL_PROPERTIES = "email";
    
    /** The admin email that send an email to user when password changes. */
    public static final String ADMIN_EMAIL = "admin.email";
    
    /** String attribute for holding MESSAGE_LEAVE_UNSUCCESS. */
    private static final String MESSAGE_LEAVE_UNSUCCESS = "STAFF.LEAVE.LEAVE.UNSUCCESS";
    
    /** String attribute for holding MESSAGE_LEAVE_SUCCESS. */
    private static final String MESSAGE_LEAVE_SUCCESS = "STAFF.LEAVE.LEAVE.SUCCESS";
    
    /** Model attribute of success message. */
    private static final String MODEL_ATT_SUCCESS_MESSAGE = "successMessage";
    
    /** String attribute for holding ERROR_SENDING_MAIL_LOG. */
    private static final String ERROR_SENDING_MAIL_LOG = "Error Sending Mail ( sendApprovalMail method )";
    
    /** String attribute for holding APPLIED_DATE_FIELD. */
    private static final String APPLIED_DATE_FIELD = "appliedDate";
    
    /** String attribute for holding NO_OF_DAYS_FIELD. */
    private static final String NO_OF_DAYS_FIELD = "noOfdays";
    
    /** String attribute for holding DATE_TYPE_FIELD. */
    private static final String DATE_TYPE_FIELD = "dateType";
    
    /** String attribute for holding STAFF_LEAVE_TYPE_FIELD. */
    private static final String STAFF_LEAVE_TYPE_FIELD = "staffLeaveType";
    
    /** String attribute for holding DATE_TO_FIELD. */
    private static final String DATE_TO_FIELD = "dateTo";
    
    /** String attribute for holding DATE_FROM_FIELD. */
    private static final String DATE_FROM_FIELD = "dateFrom";
    
    /** String attribute for holding REASON_FIELD. */
    private static final String REASON_FIELD = "reason";
    
    /** String attribute for holding REG_NO_FIELD. */
    private static final String REG_NO_FIELD = "regNo";
    
    /** String attribute for holding FULL_NAME_FIELD. */
    private static final String FULL_NAME_FIELD = "fullName";
    
    /** String attribute for holding LEAVE_APPROVAL_TEMPLATE. */
    private static final String LEAVE_APPROVAL_TEMPLATE = "staffLeave.approval.template";
    
    /** String attribute for holding LEAVE_APPROVAL_MAIL_SUBJECT. */
    private static final String LEAVE_APPROVAL_MAIL_SUBJECT = "staffLeave.approval.subject";
    
    /** String attribute for holding USER_LOGIN_SESSION. */
    private static final String USER_LOGIN_SESSION = "userLogin";
    
    /** String attribute for holding STAFF_LEAVE_LEAVE_SUCCESS_MSG. */
    private static final String STAFF_LEAVE_LEAVE_SUCCESS_MSG = "STAFF.LEAVE.LEAVE.SUCCESS.MAIL";
    
    /** String attribute for holding STAFF_LEAVE_LEAVE_UPDATED_MSG. */
    private static final String STAFF_LEAVE_LEAVE_UPDATED_MSG = "STAFF.LEAVE.LEAVE.UPDATED";
    
    /** Represent error message when parsing the date. */
    private static final String DATE_CONVERSION_ERROR = "Date Conversion Error";
    
    /** StaffService attribute for holding staffService. */
    private StaffService staffService;
    
    /** staffCommonService attribute for holding StaffCommonService. */
    private StaffCommonService staffCommonService;
    
    /** EmailService attribute for holding emailService. */
    private EmailService emailService;
    
    /** StaffLeaveValidator attribute for holding StaffLeaveValidator. */
    private StaffLeaveValidator staffLeaveValidator;
    
    /** UserService attribute for holding userService. */
    private UserService userService;
    
    /**
     * Set the service instance to inject the service.
     * 
     * @param userServiceRef the userService to set
     */
    public void setUserService(UserService userServiceRef) {
    
        this.userService = userServiceRef;
    }
    
    /**
     * Set the service instance to inject the service.
     * 
     * @param staffServiceRef the studentService to set
     */
    public void setStaffService(StaffService staffServiceRef) {
    
        this.staffService = staffServiceRef;
    }
    
    /**
     * Set the service instance to inject the service.
     * 
     * @param staffCommonServiceRef the StaffCommonService to set
     */
    public void setStaffCommonService(StaffCommonService staffCommonServiceRef) {
    
        this.staffCommonService = staffCommonServiceRef;
    }
    
    /**
     * Set the service instance to inject the service.
     * 
     * @param emailServiceRef the EmailService to set
     */
    public void setEmailService(EmailService emailServiceRef) {
    
        this.emailService = emailServiceRef;
    }
    
    /**
     * Set the staffLeaveValidator instance to inject the validator.
     * 
     * @param staffLeaveValidatorRef the staffLeaveValidator to set
     */
    public void setStaffLeaveValidator(StaffLeaveValidator staffLeaveValidatorRef) {
    
        this.staffLeaveValidator = staffLeaveValidatorRef;
    }
    
    /**
     * intiBinder method is to bind date class with the date format.
     * 
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    
        binder.initDirectFieldAccess();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFALUT_DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    /**
     * Handle GET requests for Staff_Leave view.
     * 
     * @param model - ModelMap
     * @param session - {@link HttpSession}
     * @param request - HttpServletRequest
     * @return the name of the view.
     * @throws AkuraAppException - throw detailed exception.
     */
    @RequestMapping(value = VIEW_STAFF_LEAVE)
    public String showStaffLeaveForm(ModelMap model, HttpSession session, HttpServletRequest request)
            throws AkuraAppException {
    
        if (session.getAttribute(STAFF_ID) != null && (Integer) session.getAttribute(STAFF_ID) != 0) {
            
            int staffId = (Integer) session.getAttribute(STAFF_ID);
            Date departureDate = getExistStaff(staffId).getDateOfDeparture();
            model.addAttribute(DEPARTURE_DATE, departureDate);
            
            setCurrentYearModelAttribute(model, request);
        }
        
        StaffLeave staffLeave = (StaffLeave) model.get(MODEL_ATT_STAFF_LEAVE);
        
        if (staffLeave == null) {
            staffLeave = new StaffLeave();
        }
        model.addAttribute(MODEL_ATT_STAFF_LEAVE, staffLeave);
        if (model.get(MESSAGE) != null || model.get(MODEL_ATT_SUCCESS_MESSAGE) != null) {
            List<StaffLeave> staffLeaveDetailsList = populateStaffLeaveList(model, session, request);
            List<Object[]> staffLeaveTypeInfoList = populateStaffLeaveTypeInfo(session, request);
            model.addAttribute(MODEL_ATT_STAFF_LEAVE_LIST, staffLeaveDetailsList);
            model.addAttribute(MODEL_ATTR_STAFF_LEAVE_TYPE_INFO_LIST, staffLeaveTypeInfoList);
        }
        return VIEW_GET_STAFF_LEAVE;
        
    }
    
    /**
     * Populate list of staff leaves for the selected staff.
     * 
     * @param model - {@link ModelMap}
     * @param session - {@link HttpSession}
     * @param request - {@link HttpServletRequest}
     * @return list of staff leaves.
     * @throws AkuraAppException - throw detailed exception.
     */
    @ModelAttribute(MODEL_ATT_STAFF_LEAVE_LIST)
    public List<StaffLeave> populateStaffLeaveList(ModelMap model, HttpSession session, HttpServletRequest request)
            throws AkuraAppException {
    
        String strYr = DateUtil.getStringYear(new Date());
        String year = request.getParameter(REQ_SELECTED_YEAR);
        
        String strStartDate = EMPTY_STRING;
        String strEndDate = EMPTY_STRING;
        
        String strYyear = null;
        int staffId = 0;
        if (session.getAttribute(STAFF_ID) != null) {
            
            staffId = Integer.parseInt(session.getAttribute(STAFF_ID).toString());
        }
        Date joinDate = getJoinDateByStaffId(staffId);
        
        if (year == null) {
            strYyear = strYr;
            if (strYr.equals(DateUtil.getStringYear(joinDate))) {
                strStartDate = DateUtil.getFormatDate(joinDate);
                strEndDate = strYr + END_DATE;
            } else {
                strStartDate = strYr + START_DATE;
                strEndDate = strYr + END_DATE;
            }
            
        } else {
            strYyear = year;
            if (year.equals(DateUtil.getStringYear(joinDate))) {
                strStartDate = DateUtil.getFormatDate(joinDate);
                strEndDate = year + END_DATE;
            } else {
                strStartDate = year + START_DATE;
                strEndDate = year + END_DATE;
            }
        }
        try {
            List<StaffLeave> staffLeaves =
                    staffService.getStaffLeaveListByDatePeriodAndStaffId(DateUtil.convertStringToDate(strStartDate),
                            DateUtil.convertStringToDate(strEndDate), staffId);
            model.addAttribute(REQ_SELECTED_YEAR, strYyear);
            
            return SortUtil.sortStaffLeaveListByFromDate(staffLeaves);
        } catch (ParseException e) {
            throw new AkuraAppException(DATE_CONVERSION_ERROR, e);
        }
        
    }
    
    /**
     * Populate Staff Leave Type list.
     * 
     * @param session - {@link HttpSession}
     * @return staffLeaveTypeList
     * @throws AkuraAppException - AkuraAppException
     */
    @ModelAttribute(MODEL_ATTR_STAFF_LEAVE_TYPE_LIST)
    public List<StaffLeaveType> populateStaffLaveTypeList(HttpSession session) throws AkuraAppException {
    
        int staffId = 0;
        if (session.getAttribute(STAFF_ID) != null) {
            
            staffId = Integer.parseInt(session.getAttribute(STAFF_ID).toString());
        }
        char gender = getExistStaff(staffId).getGender();
        List<StaffLeaveType> staffLeaveTypeList = staffCommonService.getStaffLeaveTypeList();
        List<StaffLeaveType> tempLeaveTypeList = new ArrayList<StaffLeaveType>();
        for (StaffLeaveType s : staffLeaveTypeList) {
            if (s.getGender() == 'A' || s.getGender() == gender) {
                tempLeaveTypeList.add(s);
            }
        }
        return SortUtil.sortStaffLeaveList(tempLeaveTypeList);
    }
    
    /**
     * Populate Staff Leave Type Info.
     * 
     * @param session - HttpSession
     * @param request - HttpServletRequest
     * @return List of Object array
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(MODEL_ATTR_STAFF_LEAVE_TYPE_INFO_LIST)
    public List<Object[]> populateStaffLeaveTypeInfo(HttpSession session, HttpServletRequest request)
            throws AkuraAppException {
    
        String strYr = DateUtil.getStringYear(new Date());
        String year = request.getParameter(REQ_SELECTED_YEAR);
        String strStartDate = EMPTY_STRING;
        String strEndDate = EMPTY_STRING;
        String strYyear = null;
        
        if (year == null) {
            strYyear = strYr;
            strStartDate = strYr + START_DATE;
            strEndDate = strYr + END_DATE;
            
        } else {
            strYyear = year;
            strStartDate = year + START_DATE;
            strEndDate = year + END_DATE;
        }
        
        List<Object[]> leaveInfoList = null;
        int staffId = 0;
        try {
            if (session.getAttribute(STAFF_ID) != null) {
                
                staffId = Integer.parseInt(session.getAttribute(STAFF_ID).toString());
                Date joinDate = getJoinDateByStaffId(staffId);
                
                if (strYyear.equals(DateUtil.getStringYear(joinDate))) {
                    Date toDate = DateUtil.convertStringToDate(strEndDate);
                    leaveInfoList = staffService.findStaffLeaveTypeInfoForJoinYear(staffId, joinDate, toDate);
                } else {
                    Date fromDate = DateUtil.convertStringToDate(strStartDate);
                    leaveInfoList = staffService.findStaffLeaveTypeInfo(staffId, fromDate);
                }
                
                return SortUtil.sortStaffLeaveTypeInfoList(leaveInfoList);
            } else {
                return null;
            }
        } catch (ParseException e) {
            throw new AkuraAppException(DATE_CONVERSION_ERROR, e);
        }
        
    }
    
    /**
     * Populate year list.
     * 
     * @param session - HttpSession
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return List of years
     * @throws AkuraAppException - AkuraAppException
     */
    
    @ModelAttribute(MODEL_ATT_YEAR_LIST)
    public List<String> populateYearList(HttpSession session, HttpServletRequest request, ModelMap model)
            throws AkuraAppException {
    
        int staffId = Integer.parseInt(session.getAttribute(STAFF_ID).toString());
        Date joinDate = getJoinDateByStaffId(staffId);
        int year = DateUtil.getYearFromDate(joinDate);
        int strYr = Integer.parseInt(DateUtil.getStringYear(new Date()));
        final int maxYear = strYr - year + 1;
        List<String> years = DateUtil.getPastYears(maxYear);
        years.add((strYr + 1) + EMPTY_STRING);
        
        model.addAttribute(MODEL_ATT_YEAR_LIST, SortUtil.sortStringYearList(years));
        
        return SortUtil.sortStringYearList(years);
    }
    
    /**
     * This method is to Save/Update staff leave.
     * 
     * @param staffLeave - {@link StaffLeave}
     * @param bindingResult - {@link BindingResult}
     * @param request - {@link HttpServletRequest}
     * @param model - {@link ModelMap}
     * @param session - {@link HttpSession}
     * @return the name of the view.
     * @throws AkuraAppException - throw detailed exception.
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateStaffLeave(@ModelAttribute(MODEL_ATT_STAFF_LEAVE) StaffLeave staffLeave,
            BindingResult bindingResult, HttpServletRequest request, ModelMap model, HttpSession session)
            throws AkuraAppException {
    
        String successMessage = null;
        int staffId = 0;
        if (session.getAttribute(STAFF_ID) != null) {
            staffId = Integer.parseInt(session.getAttribute(STAFF_ID).toString());
        }
        if (request.getParameter(REQUEST_USER_LOGIN_ID).toString() != null
                && !request.getParameter(REQUEST_USER_LOGIN_ID).toString().equals(EMPTY_STRING)) {
            int userLoginId = Integer.parseInt(request.getParameter(REQUEST_USER_LOGIN_ID).toString());
            UserLogin userLogin = userService.findUserLogin(userLoginId);
            staffLeave.setUserLogin(userLogin);
        }
        staffLeaveValidator.validate(staffLeave, bindingResult);
        
        // validate the user inputs.
        if (bindingResult.hasErrors()) {
            setCurrentYearModelAttribute(model, request);
            return VIEW_GET_STAFF_LEAVE;
        } else {
            
            try {
                if (staffLeave.getStaffLeaveId() == 0) {
                    
                    StaffLeave addStaffLeave = staffService.addStaffLeave(staffLeave, staffId, request);
                    
                    if (addStaffLeave != null) {
                        if (sendLeaveApprovalEmail(session, model, addStaffLeave)) {
                            successMessage = new ErrorMsgLoader().getErrorMessage(MESSAGE_LEAVE_SUCCESS);
                            model.addAttribute(MODEL_ATT_SUCCESS_MESSAGE, successMessage);
                        } else {
                            successMessage = new ErrorMsgLoader().getErrorMessage(STAFF_LEAVE_LEAVE_SUCCESS_MSG);
                            model.addAttribute(MODEL_ATT_SUCCESS_MESSAGE, successMessage);
                        }
                    }
                } else {
                    staffService.updateStaffLeave(staffLeave, staffId, request);
                    successMessage = new ErrorMsgLoader().getErrorMessage(STAFF_LEAVE_LEAVE_UPDATED_MSG);
                    model.addAttribute(MODEL_ATT_SUCCESS_MESSAGE, successMessage);
                }
            } catch (LeaveApplyBeforeJoinDateException e) {
                // Check whether the from date of the leave is before the join date.
                setCurrentYearModelAttribute(model, request);
                model.addAttribute(MESSAGE, e.getErrorCode());
                return VIEW_GET_STAFF_LEAVE;
                
            } catch (ExistStaffLeaveException e) {
                // Get exist staff leave and validate exist leave.
                setCurrentYearModelAttribute(model, request);
                model.addAttribute(MESSAGE, e.getErrorCode());
                return VIEW_GET_STAFF_LEAVE;
                
            } catch (DayTypeLeaveDayException e) {
                // Check whether the half day or short leave exist on one day.
                setCurrentYearModelAttribute(model, request);
                model.addAttribute(MESSAGE, e.getErrorCode());
                return VIEW_GET_STAFF_LEAVE;
                
            } catch (LeaveDayHolidayException e) {
                // Check whether the applied days are holidays.
                setCurrentYearModelAttribute(model, request);
                model.addAttribute(MESSAGE, e.getErrorCode());
                return VIEW_GET_STAFF_LEAVE;
                
            } catch (NotAvailableLeavesForLeaveTypeException e) {
                // Check whether are there any available leaves for the selected staff leave type.
                setCurrentYearModelAttribute(model, request);
                model.addAttribute(MESSAGE, e.getErrorCode());
                return VIEW_GET_STAFF_LEAVE;
                
            }
        }
        return showStaffLeaveForm(model, session, request);
    }
    
    /**
     * Send leave approval email.
     * 
     * @param session - HttpSession
     * @param model - model
     * @param addStaffLeave - StaffLeave
     * @throws AkuraAppException - AkuraAppException
     * @return succesEmail
     */
    private boolean sendLeaveApprovalEmail(HttpSession session, ModelMap model, StaffLeave addStaffLeave)
            throws AkuraAppException {
    
        String mailError = null;
        boolean flag = false;
        // Get the User Login by email address.
        
        try {
            // Get user login session attribute.
            UserLogin userLogin = (UserLogin) session.getAttribute(USER_LOGIN_SESSION);
            
            // To Address should be the email address of the user.
            String strFromAddress = userLogin.getEmail();
            // Get Exist Staff object.
            Staff existStaff = getExistStaff(addStaffLeave.getStaffId());
            // Get staff member email address.
            String staffEmailAddress = existStaff.getEmail();
            // From Address should be the email address of the admin.
            String strToAddress = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, ADMIN_EMAIL);
            // Get the subject of the email.
            String strSubject = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, LEAVE_APPROVAL_MAIL_SUBJECT);
            // Get the template name.
            String templateName = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, LEAVE_APPROVAL_TEMPLATE);
            // Leave approval process email should be copied to the staff member.
            List<String> ccList = new ArrayList<String>();
            if (staffEmailAddress != null && !staffEmailAddress.isEmpty()) {
                ccList.add(staffEmailAddress);
            }
            ccList.add(strFromAddress);
            
            // Set properties.
            Map<String, Object> mapObjectMap = new HashMap<String, Object>();
            mapObjectMap.put(FULL_NAME_FIELD, existStaff.getFullName());
            mapObjectMap.put(REG_NO_FIELD, existStaff.getRegistrationNo());
            mapObjectMap.put(REASON_FIELD, addStaffLeave.getReason());
            mapObjectMap.put(DATE_FROM_FIELD, DateUtil.getFormatDate(addStaffLeave.getFromDate()));
            mapObjectMap.put(DATE_TO_FIELD, DateUtil.getFormatDate(addStaffLeave.getToDate()));
            mapObjectMap.put(STAFF_LEAVE_TYPE_FIELD,
                    staffCommonService.findStaffLeaveTypeById(addStaffLeave.getStaffLeaveTypeId()).getDescription());
            mapObjectMap.put(DATE_TYPE_FIELD, addStaffLeave.getDateType());
            mapObjectMap.put(NO_OF_DAYS_FIELD, addStaffLeave.getNoOfDays());
            mapObjectMap.put(APPLIED_DATE_FIELD, DateUtil.getFormatDate(addStaffLeave.getAppliedDate()));
            
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
            mailError = new ErrorMsgLoader().getErrorMessage(MESSAGE_LEAVE_UNSUCCESS);
            LOG.error(ERROR_SENDING_MAIL_LOG + e);
        } catch (AkuraAppException e) {
            mailError = new ErrorMsgLoader().getErrorMessage(MESSAGE_LEAVE_UNSUCCESS);
            LOG.error(ERROR_SENDING_MAIL_LOG + e);
        }
        
        if (mailError != null) {
            model.addAttribute(MESSAGE, mailError);
        }
        return flag;
    }
    
    /**
     * Get join date by staff id.
     * 
     * @param staffId - staffId
     * @return JoinDate
     * @throws AkuraAppException - AkuraAppException
     */
    private Date getJoinDateByStaffId(int staffId) throws AkuraAppException {
    
        Staff staff = getExistStaff(staffId);
        return staff.getDateOfHire();
    }
    
    /**
     * Delete a staff leave.
     * 
     * @param request {@link HttpServletRequest}
     * @param model {@link ModelMap}
     * @param session {@link HttpSession}
     * @return view of the staff leave.
     * @throws AkuraAppException - throw the detailed exception.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteStaffLeave(HttpServletRequest request, ModelMap model, HttpSession session)
            throws AkuraAppException {
    
        try {
            int staffLeaveId = Integer.parseInt(request.getParameter(REQ_STAFF_LEAVE_ID));
            StaffLeave staffLeave = staffService.findStaffLeaveById(staffLeaveId);
            staffService.deleteStaffLeave(staffLeave);
            setCurrentYearModelAttribute(model, request);
            
        } catch (AkuraAppException e) {
            LOG.error(ERROR_DEELETE_STAFFLEAVE + e.toString());
            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
            StaffLeave staffLeave = new StaffLeave();
            model.addAttribute(MODEL_ATT_STAFF_LEAVE, staffLeave);
            model.addAttribute(MESSAGE, message);
            setCurrentYearModelAttribute(model, request);
            return VIEW_GET_STAFF_LEAVE;
        }
        return VIEW_POST_MANAGE_STAFF_LEAVE;
    }
    
    /**
     * Get the existing staff object by staff id.
     * 
     * @param staffId - staffId
     * @return exist Staff object
     * @throws AkuraAppException - AkuraAppException
     */
    private Staff getExistStaff(int staffId) throws AkuraAppException {
    
        return staffService.findStaff(staffId);
    }
    
    /**
     * Set Current Year Model Attribute.
     * 
     * @param model - ModelMap
     * @param request - HttpServletRequest
     * @throws AkuraAppException - AkuraAppException
     */
    private void setCurrentYearModelAttribute(ModelMap model, HttpServletRequest request) throws AkuraAppException {
    
        String currentYear = DateUtil.getStringYear(new Date());
        String selectedYear = request.getParameter(REQ_SELECTED_YEAR);
        
        if (selectedYear == null) {
            model.addAttribute(MODEL_ATT_CURRENT_YEAR, currentYear);
            model.addAttribute(REQ_SELECTED_YEAR, currentYear);
        } else {
            if (Integer.parseInt(selectedYear) + 1 == Integer.parseInt(currentYear)
                    || Integer.parseInt(selectedYear) == Integer.parseInt(currentYear)
                    || Integer.parseInt(selectedYear) == Integer.parseInt(currentYear) + 1) {
                model.addAttribute(MODEL_ATT_CURRENT_YEAR, currentYear);
                model.addAttribute(REQ_SELECTED_YEAR, selectedYear);
            }
        }
    }
    
}
