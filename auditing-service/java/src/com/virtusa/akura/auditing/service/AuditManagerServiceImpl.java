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

package com.virtusa.akura.auditing.service;

import java.util.List;

import com.virtusa.akura.api.dto.BusinessAudit;
import com.virtusa.akura.api.dto.BusinessAuditDetails;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.auditing.dao.BusinessAuditDao;
import com.virtusa.akura.auditing.dao.BusinessAuditDeatilsDao;

/**
 * AuditManageService implementation.
 * 
 * @author Virtusa Corporation
 */
public class AuditManagerServiceImpl implements AuditManagerService {
    
    /** businessAuditDao attribute for holding BusinessAuditDao. */
    private BusinessAuditDao businessAuditDao;
    
    /** businessAuditDeatilsDao attribute for holding BusinessAuditDeatilsDao. */
    private BusinessAuditDeatilsDao businessAuditDeatilsDao;
    
    /**
     * Set businessAuditDao object.
     * 
     * @param businessAuditDaoRef set businessAuditDao object
     */
    public void setBusinessAuditDao(BusinessAuditDao businessAuditDaoRef) {
    
        this.businessAuditDao = businessAuditDaoRef;
    }
    
    /**
     * Set businessAuditDeatilsDao object.
     * 
     * @param businessAuditDeatilsDaoRef set businessAuditDeatilsDao object
     */
    
    public void setBusinessAuditDeatilsDao(BusinessAuditDeatilsDao businessAuditDeatilsDaoRef) {
    
        this.businessAuditDeatilsDao = businessAuditDeatilsDaoRef;
    }
    
    /**
     * This method is to create audit record.
     * 
     * @param businessAudit of type BusinessAudit
     * @return BusinessAudit object
     * @throws AkuraAppException throws if exception occurs.
     */
    public BusinessAudit createAudit(BusinessAudit businessAudit) throws AkuraAppException {
    
        return businessAuditDao.save(businessAudit);
        
    }
    
    /**
     * {@inheritDoc}
     */
    public List<BusinessAudit> searchAudit(BusinessAudit businessAudit) throws AkuraAppException {
    
        return businessAuditDao.searchAudit(businessAudit);
    }
    
    /**
     * {@inheritDoc}
     */
    public BusinessAuditDetails createAuditDetail(BusinessAuditDetails businessAuditDetail) throws AkuraAppException {
    
        return businessAuditDeatilsDao.save(businessAuditDetail);
    }
    
    /**
     * {@inheritDoc}
     */
    public void saveAllAuditDetail(List<BusinessAuditDetails> businessAuditDetailList) throws AkuraAppException {
    
        businessAuditDeatilsDao.saveOrUpdateAll(businessAuditDetailList);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<BusinessAuditDetails> getAuditDetailsList(int businessAuditId) throws AkuraAppException {
    
        return businessAuditDeatilsDao.getAuditDetailsList(businessAuditId);
    }
}
