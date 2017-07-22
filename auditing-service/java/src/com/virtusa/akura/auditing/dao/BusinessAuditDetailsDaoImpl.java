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

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.BusinessAuditDetails;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the BusinessAuditDetails object.
 * 
 * @author Virtusa Corporation
 */
public class BusinessAuditDetailsDaoImpl extends BaseDaoImpl<BusinessAuditDetails> implements BusinessAuditDeatilsDao {
    
    /**
     * attribute for holding query name.
     */
    private static final String GET_AUDIT_DETAILS_LIST = "getAuditDetailsList";
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<BusinessAuditDetails> getAuditDetailsList(int businessAuditId) throws AkuraAppException {
    
        try {
            
            return (List<BusinessAuditDetails>) getHibernateTemplate().findByNamedQuery(GET_AUDIT_DETAILS_LIST,
                    businessAuditId);
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
}
