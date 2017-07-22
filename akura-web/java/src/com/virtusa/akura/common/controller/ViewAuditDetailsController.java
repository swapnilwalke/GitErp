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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.BusinessAuditDetails;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.auditing.service.AuditManagerService;

/**
 * The ViewAuditDetailsController is to handle view audit more details related functionalities.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ViewAuditDetailsController {
    
    /** attribute for holding view name reference/moreAuditDetails. */
    private static final String REFERENCE_MORE_AUDIT_DETAILS = "reference/moreAuditDetails";
    
    /** attribute for holding string auditDetailsList. */
    private static final String AUDIT_DETAILS_LIST = "auditDetailsList";
    
    /** attribute for holding string auditDetailId. */
    private static final String AUDIT_DETAIL_ID = "auditDetailId";
    
    /** auditManagerService attribute for holding AuditManagerService. */
    private AuditManagerService auditManagerService;
    
    /**
     * Setter method for auditManagerService.
     * 
     * @param auditManagerServiceRef - AuditManagerService object.
     */
    public void setAuditManagerService(AuditManagerService auditManagerServiceRef) {
    
        this.auditManagerService = auditManagerServiceRef;
    }
    
    /**
     * Navigate method for view audit details view .
     * 
     * @param modelMap of type ModelMap
     * @param request - HttpServletRequest
     * @return java.lang.String
     * @throws AkuraAppException throw exception if occur.
     */
    
    @RequestMapping(method = RequestMethod.GET)
    public String showMoreDetailsForm(final ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {
    
        String auditId = request.getParameter(AUDIT_DETAIL_ID);
        
        List<BusinessAuditDetails> auditDetailsList =
                auditManagerService.getAuditDetailsList(Integer.parseInt(auditId));
        
        modelMap.addAttribute(AUDIT_DETAILS_LIST, auditDetailsList);
        return REFERENCE_MORE_AUDIT_DETAILS;
    }
    
}
