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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.BusinessAudit;
import com.virtusa.akura.api.exception.AkuraAppException;


/**
 * This interface provides persistence layer functionality for the BusinessAudit object.
 *
 * @author Virtusa Corporation
 */
public interface BusinessAuditDao extends BaseDao<BusinessAudit>{
    
    /**
     * This method is to search business audit details.
     * 
     * @param businessAudit of type BusinessAudit
     * @return list of business audit objects
     * @throws AkuraAppException throws if exception occurs.
     */
    List<BusinessAudit> searchAudit(BusinessAudit businessAudit) throws AkuraAppException;

}
