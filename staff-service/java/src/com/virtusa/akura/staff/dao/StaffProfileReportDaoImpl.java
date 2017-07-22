
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


package com.virtusa.akura.staff.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StaffProfileReportTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;


/**
 * @author Virtusa Corporation
 *
 */

public class StaffProfileReportDaoImpl extends BaseDaoImpl<StaffProfileReportTemplate> 
    implements StaffProfileReportDao {
    
    /** The Constant GET_STAFF_PROFILE_REPORT_TEMPLATE_BY_STAFF_ID. */
    private static final String GET_STAFF_PROFILE_REPORT_TEMPLATE_BY_STAFF_ID = 
            "getStaffProfileReportTemplateByStaffId";
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StaffProfileReportDaoImpl.class);
    /**
     * Get staff profile detail by staff id.
     * 
     * @param staffId - staffId
     * @return List StaffProfileReportTemplate
     * @throws AkuraAppException AkuraAppException
     */
    @SuppressWarnings("unchecked")
    public List<StaffProfileReportTemplate> getStaffProfileInfoByStaffId(int staffId) throws AkuraAppException {
        
        try {
            return getHibernateTemplate().findByNamedQuery(GET_STAFF_PROFILE_REPORT_TEMPLATE_BY_STAFF_ID, staffId);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.DB_CONNECTION_ERROR + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
}
