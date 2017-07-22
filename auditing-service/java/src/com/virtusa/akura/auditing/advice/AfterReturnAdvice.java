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

package com.virtusa.akura.auditing.advice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.security.core.context.SecurityContextHolder;

import com.virtusa.akura.api.dto.AuditEventType;
import com.virtusa.akura.api.dto.AuditableBaseDomain;
import com.virtusa.akura.api.dto.BusinessAudit;
import com.virtusa.akura.api.dto.BusinessAuditDetails;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.auditing.service.AuditManagerService;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.UserService;

/**
 * Class use to audit methods.
 * 
 * @author Virtusa Corporation
 */
public class AfterReturnAdvice implements AfterReturningAdvice {
    
    /**
     * Logger to log the error information.
     */
    private static final Logger LOG = Logger.getLogger(AfterReturnAdvice.class);
    
    /** auditManagerService attribute for holding AuditManagerService. */
    private AuditManagerService auditManagerService;
    
    /** userService attribute for holding UserService. */
    private UserService userService;
    
    /** commonService attribute for holding CommonService. */
    private CommonService commonService;
    
    /**
     * Set commonService object.
     * 
     * @param commonServiceRef set commonService object
     */
    public void setCommonService(CommonService commonServiceRef) {
    
        this.commonService = commonServiceRef;
    }
    
    /**
     * Set userService object.
     * 
     * @param userServiceRef set userService object
     */
    public void setUserService(final UserService userServiceRef) {
    
        this.userService = userServiceRef;
    }
    
    /**
     * Set auditManagerService object.
     * 
     * @param auditManagerServiceRef set auditManagerService object
     */
    public void setAuditManagerService(final AuditManagerService auditManagerServiceRef) {
    
        this.auditManagerService = auditManagerServiceRef;
    }
    
    /**
     * audit methods after execute.
     * 
     * @param returnObj of type Object
     * @param method of type Method
     * @param args array of arguments
     * @param object of type Object
     * @throws AkuraAppException throws if exception occurs.
     */
    @Override
    public void afterReturning(final Object returnObj, final Method method, final Object[] args, final Object object)
            throws AkuraAppException {
    
        LOG.info("*******Ending method: " + method.getName() + "************");
        
        try {
            UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            
            BusinessAudit businessAudit = new BusinessAudit();
            businessAudit.setBusinessFunction(method.getName());
            businessAudit.setDate(new Date());
            
            List<AuditEventType> auditEventTypesList = commonService.getAuditEventType();
            
            Iterator<AuditEventType> auditEventTypeiterator = auditEventTypesList.iterator();
            
            while (auditEventTypeiterator.hasNext()) {
                AuditEventType auditEventType = auditEventTypeiterator.next();
                if (method.getName().toLowerCase().contains(auditEventType.getDescription().toLowerCase())) {
                    businessAudit.setEventType(auditEventType);
                    break;
                }
            }
            
            businessAudit.setModule(method.getDeclaringClass().getSimpleName());
            UserLogin userLogin = userService.getAnyUser(userInfo.getUsername());
            businessAudit.setUserLogin(userLogin);
            
            BusinessAudit returnBusinessAudit = auditManagerService.createAudit(businessAudit);
            
            if (args[0] instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<AuditableBaseDomain> auditableBaseDomainObj = (List<AuditableBaseDomain>) args[0];
                List<BusinessAuditDetails> businessAuditDetailsSaveList = new ArrayList<BusinessAuditDetails>();
                for (AuditableBaseDomain argsObjectList : auditableBaseDomainObj) {
                    
                    BusinessAuditDetails businessAuditDetails = new BusinessAuditDetails();
                    businessAuditDetails.setAttributeName(argsObjectList.getClass().toString());
                    businessAuditDetails.setAuditDescription(argsObjectList.auditToString());
                    businessAuditDetails.setBusinessAudit(returnBusinessAudit);
                    businessAuditDetailsSaveList.add(businessAuditDetails);
                    
                }
                auditManagerService.saveAllAuditDetail(businessAuditDetailsSaveList);
                
            } else if (args[0] instanceof AuditableBaseDomain) {
                AuditableBaseDomain argsObject = (AuditableBaseDomain) args[0];
                
                BusinessAuditDetails businessAuditDetails = new BusinessAuditDetails();
                businessAuditDetails.setAttributeName(argsObject.getClass().toString());
                businessAuditDetails.setAuditDescription(argsObject.auditToString());
                businessAuditDetails.setBusinessAudit(returnBusinessAudit);
                
                auditManagerService.createAuditDetail(businessAuditDetails);
            }
            
        } catch (AkuraAppException e) {
            LOG.error("******* Error in " + method.getName() + " audit ***************");
            LOG.error(e.toString());
            
        }
        
    }
    
}
