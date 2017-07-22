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

/**
 * interface to declare the auditing related services.
 * 
 * @author Virtusa Corporation
 */
public interface AuditManagerService {
    
    /**
     * This method is to create audit record.
     * @param businessAudit of type BusinessAudit
     * @return BusinessAudit object
     * @throws AkuraAppException throws if exception occurs.
     */
    BusinessAudit createAudit(BusinessAudit businessAudit) throws AkuraAppException;
    
    /**
     * This method is to search audit record.
     * @param businessAudit of type BusinessAudit
     * @return BusinessAudit object list
     * @throws AkuraAppException throws if exception occurs.
     */
    List<BusinessAudit> searchAudit(BusinessAudit businessAudit) throws AkuraAppException;
    
    /**
     * This method is to create auditDetail record.
     * 
     * @param businessAuditDetail of type BusinessAuditDetails
     * @return BusinessAuditDetails object
     * @throws AkuraAppException throws if exception occurs.
     */
    BusinessAuditDetails createAuditDetail(BusinessAuditDetails businessAuditDetail) throws AkuraAppException;
    
    /**
     * This method is to save auditDetail record list.
     * 
     * @param businessAuditDetailList list of type BusinessAuditDetails
     * @throws AkuraAppException throws if exception occurs.
     */
    void saveAllAuditDetail(List<BusinessAuditDetails> businessAuditDetailList) throws AkuraAppException;
    
    /**
     * This method is to get auditDetail record list by business audit id.
     * 
     * @param businessAuditId of type integer
     * @return list of type BusinessAuditDetails
     * @throws AkuraAppException throws if exception occurs.
     */
    List<BusinessAuditDetails> getAuditDetailsList(int businessAuditId) throws AkuraAppException;
    
}
