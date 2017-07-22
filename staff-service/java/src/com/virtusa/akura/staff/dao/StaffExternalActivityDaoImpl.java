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

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StaffExternalActivity;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * Implements StaffExternalActivityDao and provides persistence layer functionality for the
 * StaffExternalActivity domain object.
 * 
 * @author Virtusa Corporation
 */
public class StaffExternalActivityDaoImpl extends BaseDaoImpl<StaffExternalActivity> implements
        StaffExternalActivityDao {
    
    /** The Constant GET_STAFF_EXTERNAL_ACTIVITY_BY_STAFF_ID. */
    private static final String GET_STAFF_EXTERNAL_ACTIVITY_BY_STAFF_ID = "getStaffExternalActivityByStaffId";

    /**
     * Holds logger property to log errors.
     */
    private static final Logger LOG = Logger.getLogger(StaffExternalActivityDaoImpl.class);
    
    /** The Constant GET_STAFF_EXTERNAL_ACTIVITY. */
    private static final String GET_STAFF_EXTERNAL_ACTIVITY = "getStaffExternalActivity";
    
    /** The Constant ERROR_WHILE_RETRIEVE_STAFF_EXTERNAL_ACTIVITY. */
    private static final String ERROR_WHILE_RETRIEVE_STAFF_EXTERNAL_ACTIVITY =
            "Error while retrieve staff external activity ---> ";
    
    /** The Constant GET_STAFF_EXTERNAL_ACTIVITY_BY_YEAR. */
    private static final String GET_STAFF_EXTERNAL_ACTIVITY_BY_YEAR = "getStaffExternalActivityByYear";
    
    /**
     * Retrieves list of External Activity for a staff member based on the selected year.
     * 
     * @param staffId - holds integer type
     * @param dateSelectedYear - holds date type
     * @return list of club societies for a selected year of a staff.
     * @throws AkuraAppException - throw detailed exception when retrieve list of staff external activities
     */
    public List<?> getStaffExternalActivityByYearList(int staffId, Date dateSelectedYear) throws AkuraAppException {

        try {
            if (dateSelectedYear == null) {
                return getHibernateTemplate().findByNamedQuery(GET_STAFF_EXTERNAL_ACTIVITY_BY_STAFF_ID, staffId);
            } else {
                return getHibernateTemplate().findByNamedQuery(GET_STAFF_EXTERNAL_ACTIVITY_BY_YEAR, staffId,
                        dateSelectedYear);
            }
            
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVE_STAFF_EXTERNAL_ACTIVITY + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Retrieves list of External Activity for a staff member based on the selected year.
     * 
     * @param staffId - holds integer type
     * @param dateSelectedYear - holds date type
     * @param activity - holds string type
     * @param achievement - holds string type
     * @return list of club societies for a selected year of a staff.
     * @throws AkuraAppException - throw detailed exception when retrieve list of staff external activities
     */
    @SuppressWarnings("unchecked")
    public List<StaffExternalActivity> getStaffExternalActivity(int staffId, Date dateSelectedYear, String activity,
            String achievement) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_STAFF_EXTERNAL_ACTIVITY, staffId, dateSelectedYear,
                    activity, achievement);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVE_STAFF_EXTERNAL_ACTIVITY + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
