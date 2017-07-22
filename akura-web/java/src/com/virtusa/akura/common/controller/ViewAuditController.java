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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.AuditEventType;
import com.virtusa.akura.api.dto.BusinessAudit;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.auditing.service.AuditManagerService;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.common.validator.ViewAuditDetailsValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * The ViewAuditController is to handle view audit details related functionalities.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ViewAuditController {
    
    /** attribute for holding string please select. */
    private static final String PLEASE_SELECT = "0";
    
    /** attribute for holding url searchAuditDetails.htm. */
    private static final String SEARCH_AUDIT_DETAILS_HTM = "searchAuditDetails.htm";
    
    /** attribute for holding string selectedUser. */
    private static final String SELECTED_USER = "selectedUser";
    
    /** attribute for holding string selectedEvent. */
    private static final String SELECTED_EVENT = "selectedEvent";
    
    /** attribute for holding string businessAuditList. */
    private static final String BUSINESS_AUDIT_LIST = "businessAuditList";
    
    /** attribute for holding view name reference/viewAuditDetails. */
    private static final String REFERENCE_VIEW_AUDIT_DETAILS = "reference/viewAuditDetails";
    
    /** attribute for holding string businessAudit. */
    private static final String BUSINESS_AUDIT = "businessAudit";
    
    /** attribute for holding string eventTypeList. */
    private static final String EVENT_TYPE_LIST = "eventTypeList";
    
    /** attribute for holding string userLoginList. */
    private static final String USER_LOGIN_LIST = "userLoginList";
    
    /** userService attribute for holding UserService. */
    private UserService userService;
    
    /** viewAuditDetailsValidator attribute for holding ViewAuditDetailsValidator. */
    private ViewAuditDetailsValidator viewAuditDetailsValidator;
    
    /** auditManagerService attribute for holding AuditManagerService. */
    private AuditManagerService auditManagerService;
    
    /** commonService attribute for holding CommonService. */
    private CommonService commonService;
    
    /**
     * Name of the parameter name of the request for the start number for staff.
     */
    private static final String START_FROM = "startFrom";
    
    /**
     * Name of the parameter name of the request for the action.
     */
    private static final String ACTION_TYPE = "actionType";
    
    /**
     * Represents the model name of the maximum number of the staff.
     */
    private static final String MAX_NUMBER = "maxNumber";
    
    /**
     * Represents the model name of the total number of records for staff.
     */
    private static final String NUMBER_OF_RECORDS = "numberOfRecords";
    
    /**
     * Represents the value for model attribute.
     */
    private static final String EMPTY = "Empty";
    
    /**
     * Name for search.
     */
    private static final String SEARCH = "search";
    
    /**
     * Number of records per page.
     */
    private static final int PAGE_SIXE = 10;
    
    /**
     * Total number of records in staff search.
     */
    private transient int numberOfRecords;
    
    /**
     * Setter method for commonService.
     * 
     * @param commonServiceRef - CommonService object.
     */
    public void setCommonService(CommonService commonServiceRef) {
    
        this.commonService = commonServiceRef;
    }
    
    /**
     * Setter method for user service.
     * 
     * @param userServiceRef - UserService object.
     */
    public void setUserService(UserService userServiceRef) {
    
        this.userService = userServiceRef;
    }
    
    /**
     * Setter method for auditManagerService.
     * 
     * @param auditManagerServiceRef - AuditManagerService object.
     */
    public void setAuditManagerService(AuditManagerService auditManagerServiceRef) {
    
        this.auditManagerService = auditManagerServiceRef;
    }
    
    /**
     * Setter method for viewAuditDetailsValidator.
     * 
     * @param viewAuditDetailsValidatorRef - ViewAuditDetailsValidator object.
     */
    public void setViewAuditDetailsValidator(ViewAuditDetailsValidator viewAuditDetailsValidatorRef) {
    
        this.viewAuditDetailsValidator = viewAuditDetailsValidatorRef;
    }
    
    /**
     * Method is to return UserLogin reference data.
     * 
     * @return UserLogin List - User Login reference data.
     * @throws AkuraAppException throws if exception occurs.
     */
    @ModelAttribute(USER_LOGIN_LIST)
    public List<UserLogin> populateUserLoginList() throws AkuraAppException {
    
        List<Integer> userRoleIdList = new ArrayList<Integer>();
        userRoleIdList.add(AkuraConstant.ROLE_ADMIN);
        userRoleIdList.add(AkuraConstant.ROLE_TEACHER);
        userRoleIdList.add(AkuraConstant.ROLE_CLERICAL_STAFF);
        // get user login list for known role ids
        List<UserLogin> userLoginList = userService.getUserLoginListByUserRole(userRoleIdList);
        
        return SortUtil.sortUserLoginList(userLoginList);
    }
    
    /**
     * Method is to return event type list.
     * 
     * @return eventTypeList - Event Type Value List.
     * @throws AkuraAppException throws if exception occurs.
     */
    @ModelAttribute(EVENT_TYPE_LIST)
    public List<AuditEventType> populateEventTypeList() throws AkuraAppException {
    
        return SortUtil.sortAuditEventType(commonService.getAuditEventType());
    }
    
    /**
     * Navigate method for view audit details view .
     * 
     * @param modelMap of type ModelMap
     * @return java.lang.String
     * @throws AkuraAppException throw exception if occur.
     */
    
    @RequestMapping(method = RequestMethod.GET)
    public String showViewAuditForm(final ModelMap modelMap) throws AkuraAppException {
    
        BusinessAudit businessAudit = (BusinessAudit) modelMap.get(BUSINESS_AUDIT);
        
        if (businessAudit == null) {
            businessAudit = new BusinessAudit();
        }
        modelMap.addAttribute(BUSINESS_AUDIT, businessAudit);
        
        return REFERENCE_VIEW_AUDIT_DETAILS;
    }
    
    /**
     * method to search audit details according to the given search criteria.
     * 
     * @param modelMap model map to set data
     * @param businessAudit of type BusinessAudit
     * @param errors of type BindingResult
     * @param request of type HttpServletRequest
     * @return String value of jsp page to direct
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(method = RequestMethod.POST, value = SEARCH_AUDIT_DETAILS_HTM)
    public String searchAuditDetails(ModelMap modelMap, @ModelAttribute(BUSINESS_AUDIT) BusinessAudit businessAudit,
            BindingResult errors, final HttpServletRequest request) throws AkuraAppException {
    
        viewAuditDetailsValidator.validate(businessAudit, errors);
        
        if (errors.hasErrors()) {
            
            modelMap.addAttribute(SELECTED_EVENT, businessAudit.getEventType().getDescription());
            
            if (!businessAudit.getUserLogin().getUsername().isEmpty()
                    || businessAudit.getUserLogin().getUsername().equals(PLEASE_SELECT)) {
                modelMap.addAttribute(SELECTED_USER, businessAudit.getUserLogin().getUsername());
            }
            
            return REFERENCE_VIEW_AUDIT_DETAILS;
        } else {
            
            int maxNumber;
            final String actionType = request.getParameter(ACTION_TYPE);
            final int startFrom = Integer.parseInt(request.getParameter(START_FROM));
            final int endNumber = startFrom + PAGE_SIXE;
            
            List<BusinessAudit> businessAuditRecordList = auditManagerService.searchAudit(businessAudit);
            
            if (actionType.equals(SEARCH)) {
                
                numberOfRecords = businessAuditRecordList.size();
            }
            
            SortUtil.sortBusinessAudit(businessAuditRecordList);
            
            if (numberOfRecords < endNumber) {
                maxNumber = numberOfRecords;
            } else {
                maxNumber = endNumber;
            }
            
            final List<BusinessAudit> reultsForPage = businessAuditRecordList.subList(startFrom, maxNumber);
            
            if (reultsForPage.isEmpty()) {
                modelMap.addAttribute(BUSINESS_AUDIT_LIST, EMPTY);
            } else {
                modelMap.addAttribute(BUSINESS_AUDIT_LIST, reultsForPage);
            }
            
            modelMap.addAttribute(NUMBER_OF_RECORDS, numberOfRecords);
            modelMap.addAttribute(START_FROM, startFrom);
            modelMap.addAttribute(MAX_NUMBER, maxNumber);
            
            modelMap.addAttribute(SELECTED_EVENT, businessAudit.getEventType().getDescription());
            
            if (!businessAudit.getUserLogin().getUsername().isEmpty()
                    || businessAudit.getUserLogin().getUsername().equals(PLEASE_SELECT)) {
                modelMap.addAttribute(SELECTED_USER, businessAudit.getUserLogin().getUsername());
            }
            
            return REFERENCE_VIEW_AUDIT_DETAILS;
        }
        
    }
    
}
