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

package com.virtusa.akura.auditing.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.BusinessAudit;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * This class provides persistence layer functionality for the BusinessAudit object.
 * 
 * @author Virtusa Corporation
 */
public class BusinessAuditDaoImpl extends BaseDaoImpl<BusinessAudit> implements BusinessAuditDao {
    
    /**
     * attribute for holding query name.
     */
    private static final String BUSINESS_AUDIT_SEARCH = "businessAuditSearch";
    
    /**
     * attribute for holding query name.
     */
    private static final String BUSINESS_AUDIT_SEARCH_NO_DATE = "businessAuditSearchWithNoDate";
    
    /** The constant for "%" character. */
    private static final String MODULO_STRING = "%";
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<BusinessAudit> searchAudit(BusinessAudit businessAudit) throws AkuraAppException {
    
        try {
            String username = "";
            String eventType= "";
            String businessFunction = "";
            String module = "";
            if (!businessAudit.getUserLogin().getUsername().equals("0")) {
                username = businessAudit.getUserLogin().getUsername();
            }
            
            if (!businessAudit.getEventType().getDescription().equals("0")) {
                eventType = businessAudit.getEventType().getDescription();
            }
            
            if (!businessAudit.getBusinessFunction().trim().isEmpty()) {
                businessFunction = MODULO_STRING + businessAudit.getBusinessFunction().trim() + MODULO_STRING;
            }
            
            if (!businessAudit.getModule().trim().isEmpty()) {
                module = MODULO_STRING + businessAudit.getModule().trim() + MODULO_STRING;
            }
            
            Date fromDate = null;
            Date toDate = null;
            //if user enter both to date and from date
            if (!businessAudit.getFromDate().isEmpty() && !businessAudit.getToDate().isEmpty()) {
                
                fromDate = DateUtil.getParseDate(businessAudit.getFromDate());
                toDate = DateUtil.getParseDate(businessAudit.getToDate());
                
                return (List<BusinessAudit>) getHibernateTemplate().findByNamedQuery(BUSINESS_AUDIT_SEARCH, username,
                        username, businessFunction, businessFunction, eventType, eventType, module, module, fromDate,
                        toDate);
                
                //if user enter only from date
            } else if (!businessAudit.getFromDate().isEmpty() && businessAudit.getToDate().isEmpty()) {
                
                fromDate = DateUtil.getParseDate(businessAudit.getFromDate());
                toDate = new Date(); //get current date as to date
                
                return (List<BusinessAudit>) getHibernateTemplate().findByNamedQuery(BUSINESS_AUDIT_SEARCH, username,
                        username, businessFunction, businessFunction, eventType, eventType, module, module, fromDate,
                        toDate);
                
            } else {
                //if user dosen't enter any date
                return (List<BusinessAudit>) getHibernateTemplate().findByNamedQuery(BUSINESS_AUDIT_SEARCH_NO_DATE,
                        username, username, businessFunction, businessFunction, eventType, eventType, module, module);
            }
            
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
}
