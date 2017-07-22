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
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffPastService;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.api.exception.InvalidRejoinDateException;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.staff.validator.StaffDepartureDetailsValidator;
import com.virtusa.akura.staff.validator.StaffPastServiceValidator;

/**
 * SchoolController is to handle Add/Edit/Delete/Search View operations for Subject,Stream in school module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StaffPastServiceController {
    
    /** Represents the model attribute `success`. */
    private static final String MODEL_ATTRIB_SUCCESS = "success";
    
    /** Represents the return url for `saveStaffTerminate.htm`. */
    private static final String SAVE_STAFF_TERMINATE_HTM = "saveStaffTerminate.htm";
    
    /** Represents the return url for staff departure details. */
    private static final String ADMIN_MANAGE_STAFF_DEPARTURE_DETAILS_HTM = "/staffDepartureDetails.htm";
    
    /** view get method past staff service. */
    private static final String VIEW_GET_STAFF_DEPARTURE_DETAILS = "staff/staffDepartureDetails";
    
    /** StaffService attribute for holding staffService. */
    private StaffService staffService;
    
    /** Represents an instance of staffDepartureDetailsValidator. */
    private StaffDepartureDetailsValidator staffDepartureDetailsValidator;
    
    /** String attribute for holding selected staff id. */
    private static final String SELECTED_STAFF_ID = "selectedStaffId";
    
    /** Represents the model attribute for staff. */
    private static final String STAFF_PAST_SERVICE = "staffPastService";
    
    /** String attribute for holding defalut_date_format. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /** model attribute of message. */
    private static final String MODEL_ATT_MESSAGE = "message";
    
    /** Represents the model value for successful addition. */
    private static final String SUCCESSFULLY_ADDED = "REF.UI.STAFF.DEPARTURE_DETAILS.SUCCESSFULLY_ADDED";
    
    /** Represents the error message for error. */
    private static final String ERROR_MSG = "REF.UI.STAFF.DEPARTURE_DETAILS.ERROR";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StaffPastServiceController.class);
    
    /** Represents the retrun url for view past staff service module. */
    private static final String ADMIN_MANAGE_VIEW_PAST_STAFF_SERVICE_HTM = "/viewPastStaffService.htm";
    
    /** view get method past staff service. */
    private static final String VIEW_GET_PAST_STAFF_SERVICE = "staff/viewPastStaffService";
    
    /** model attribute of staff leave List. */
    private static final String MODEL_ATT_PAST_STAFF_SERVICE_LIST = "pastStaffServiceList";
    
    /** Represents the model attribute for staff. */
    private static final String STAFF = "staff";
    
    /** Represents the model attribute for fullName. */
    private static final String FULL_NAME = "fullName";
    
    /** Represents the model attribute for nameWithIntials. */
    private static final String NAME_WT_INTIALS = "nameWithIntials";  
    
    /** Represents the retrun url for re-join staff member. */
    private static final String REJOIN_STAFF_MEMBER_HTM = "/manageRejoinStaffMember.htm";
    
    /** Represents the model attribute meassge. */
    private static final String MESSAGE = "messages";
    
    /** key to hold message when staff rejoin success. */
    private static final String STAFF_REJOIN_SUCCESS_MESSSAGE = "STAFF.REJOIN.SUCCESS.MESSAGE";
    
    /** key to hold error message "STAFF.REJOIN.INVALID.DATE.MESSAGE" . */
    private static final String ERROR_MESSAGE_STAFF_REJOIN = "STAFF.REJOIN.INVALID.DATE.MESSAGE";
    
    /** key to hold String "listCount". */
    private static final String LIST_COUNT = "listCount";
    
    /**
     * Represents an instance of the StaffPastServiceValidator.
     */
    private StaffPastServiceValidator staffPastServiceValidator;
    
    /**
     * Represents an instance of the StaffService.
     * 
     * @param staffServiceObj - Staff Service object.
     */
    public void setStaffService(StaffService staffServiceObj) {

        this.staffService = staffServiceObj;
    }
    
    /**
     * Represents an instance of the staffDepartureDetailsValidator.
     * 
     * @param staffDepartureDetailsValidatorObj - staffDeparture DetailsValidator object.
     */
    public void setStaffDepartureDetailsValidator(StaffDepartureDetailsValidator staffDepartureDetailsValidatorObj) {

        this.staffDepartureDetailsValidator = staffDepartureDetailsValidatorObj;
    }
    
    /**
     * Initializes the reference data that is to be previewed on the UI.
     * 
     * @param request - an object of HttpServletRequest
     * @param model - a HashMap that contains information of the Past staff service.
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = ADMIN_MANAGE_STAFF_DEPARTURE_DETAILS_HTM)
    public final String showStaffTerminateForm(final ModelMap model, final HttpServletRequest request)
            throws AkuraAppException {

        String staffId = request.getParameter(SELECTED_STAFF_ID);
        
        if (staffId != null) {
            int staffIdVal = Integer.parseInt(staffId);
            
            Staff staff = staffService.findStaff(staffIdVal);
            StaffPastService staffPastService = new StaffPastService();
            staffPastService.setStaff(staff);
            
            model.addAttribute(STAFF_PAST_SERVICE, staffPastService);
            
        }
        
        return VIEW_GET_STAFF_DEPARTURE_DETAILS;
        
    }
    
    /**
     * Terminates a staff member.
     * 
     * @param request - an object of HttpServletRequest
     * @param model - a HashMap that contains information of the Past staff service.
     * @param staffPastService - command object .
     * @param bindingResult - {@link BindingResult}
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = SAVE_STAFF_TERMINATE_HTM)
    public final String terminateStaff(final ModelMap model, final HttpServletRequest request,
            @ModelAttribute(STAFF_PAST_SERVICE) final StaffPastService staffPastService, BindingResult bindingResult)
            throws AkuraAppException {

        String returnResult = VIEW_GET_STAFF_DEPARTURE_DETAILS;
        int staffId =
                request.getParameter(SELECTED_STAFF_ID) == null ? 0 : Integer.parseInt(request
                        .getParameter(SELECTED_STAFF_ID));
        
        staffDepartureDetailsValidator.validate(staffPastService, bindingResult);
        
        if (bindingResult.hasErrors()) {
            
            if (staffId != 0) {
                Staff staff = staffService.findStaff(staffId);
                staffPastService.setStaff(staff);
                model.addAttribute(STAFF_PAST_SERVICE, staffPastService);
            }
        } else {
            String strMessage = null;
            
            try {
                Staff staff = new Staff();
                staff.setStaffId(staffId);
                staffPastService.setStaff(staff);
                staffPastService.setRegistrationNo(staffService.findStaff(staffId).getRegistrationNo());
                staffService.terminateStaff(staffPastService);
                
                strMessage = new ErrorMsgLoader().getErrorMessage(SUCCESSFULLY_ADDED);
                model.addAttribute(MODEL_ATTRIB_SUCCESS, true);
            } catch (AkuraAppException e) {
                strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MSG);
                model.addAttribute(STAFF_PAST_SERVICE, staffPastService);
            } finally {
                model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
            }
            
        }
        
        return returnResult;
        
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
     * Sets an instance of StaffPastServiceValidator.
     * 
     * @param staffPastServiceValidatorVal - the instance of staffPastServiceValidatorVal
     */
    public final void setStaffPastServiceValidator(final StaffPastServiceValidator staffPastServiceValidatorVal) {

        staffPastServiceValidator = staffPastServiceValidatorVal;
    }
    
    /**
     * Initializes the reference data that is to be previewed on the UI.
     * 
     * @param request - an object of HttpServletRequest
     * @param staff - an instance of Staff
     * @param model - a HashMap that contains information of the Past staff service.
     * @param session - a session to pass values.
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = ADMIN_MANAGE_VIEW_PAST_STAFF_SERVICE_HTM)
    public final String viewPastStaffService(final ModelMap model, final HttpSession session,
            final HttpServletRequest request, @ModelAttribute(STAFF) final Staff staff) throws AkuraAppException {

        String staffId = request.getParameter(SELECTED_STAFF_ID);
        String staffIdAttribute = (String) session.getAttribute(SELECTED_STAFF_ID);
        String message = request.getParameter(MESSAGE);
        if (staffId == null && staffIdAttribute != null) {
            staffId = staffIdAttribute;
            session.removeAttribute(SELECTED_STAFF_ID);
            if (message != null) {
                model.addAttribute(MESSAGE, message);
                request.removeAttribute(MESSAGE);
            }
        }
        
        setStaffPastServiceList(model, staffId);
        StaffPastService staffPastService = new StaffPastService();
        model.addAttribute(STAFF_PAST_SERVICE, staffPastService);
        return VIEW_GET_PAST_STAFF_SERVICE;
        
    }
    
    /**
     * Re join a staff member.
     * 
     * @param request - an object of HttpServletRequest
     * @param session - a session to store selected staffId.
     * @param model - a HashMap that contains information of the Staff member
     * @param staffpastservice - an instance of StaffPastService
     * @param result - containing list of errors and staff instance to which data was bound
     * @return - name of the view which is redirected to
     * @throws AkuraException - The exception details that occurred when saving or updating a Country
     *         instance.
     */
    @RequestMapping(value = REJOIN_STAFF_MEMBER_HTM, method = RequestMethod.POST)
    public final String rejoinStaffMember(final HttpServletRequest request, final HttpSession session,
            @ModelAttribute(STAFF_PAST_SERVICE) final StaffPastService staffpastservice, BindingResult result,
            final ModelMap model) throws AkuraException {

        String returnResult = VIEW_GET_PAST_STAFF_SERVICE;
        
        staffPastServiceValidator.validate(staffpastservice, result);
        
        String staffId = request.getParameter(SELECTED_STAFF_ID);
        session.setAttribute(SELECTED_STAFF_ID, staffId);
        setStaffPastServiceList(model, staffId);
        
        if (result.hasErrors()) {
            
            return returnResult;
        } else {
            
            int staffIdVal = Integer.parseInt(staffId);
            
            try {
                
                boolean joinStatus =
                        staffService.rejoinStaffMemberService(staffIdVal, staffpastservice.getDateOfJoin());
                
                if (joinStatus) {
                    String message = new ErrorMsgLoader().getErrorMessage(STAFF_REJOIN_SUCCESS_MESSSAGE);
                    model.addAttribute(MODEL_ATTRIB_SUCCESS, message);
                    
                    return VIEW_GET_PAST_STAFF_SERVICE;
                }
                
            } catch (InvalidRejoinDateException e) {
                
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MESSAGE_STAFF_REJOIN);
                model.addAttribute(MESSAGE, message);
                
            }
            
        }
        
        return returnResult;
        
    }
    
    /**
     * Set the staff past service list.
     * 
     * @param model - model to store and transport objects.
     * @param staffId - Staff Id of the staff member.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    private void setStaffPastServiceList(final ModelMap model, String staffId) throws AkuraAppException {

        if (staffId != null) {
            int staffIdVal = Integer.parseInt(staffId);
            List<StaffPastService> staffPastService = staffService.getPastStaffServiceByStaffId(staffIdVal);
            String fullName = staffService.findStaff(staffIdVal).getFullName();
            String nameWithIntials = staffService.findStaff(staffIdVal).getNameWithIntials();
            int listCount = 0;
            
            model.addAttribute(NAME_WT_INTIALS, nameWithIntials);  
            model.addAttribute(FULL_NAME, fullName);
            model.addAttribute(MODEL_ATT_PAST_STAFF_SERVICE_LIST, staffPastService);
            model.addAttribute(LIST_COUNT, listCount);
            
        }
    }
    
}
