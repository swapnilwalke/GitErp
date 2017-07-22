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
import com.virtusa.akura.api.dto.StaffSeminar;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * Implements StaffSeminarDao and provides persistence layer functionality for the StaffSeminar domain object.
 * 
 * @author Virtusa Corporation
 */
public class StaffSeminarDaoImpl extends BaseDaoImpl<StaffSeminar> implements StaffSeminarDao {
    
    /** The Constant GET_STAFF_SEMINAR_BY_STAFF_ID. */
    private static final String GET_STAFF_SEMINAR_BY_STAFF_ID = "getStaffSeminarByStaffId";

    /** Holds logger property to log errors. */
    private static final Logger LOG = Logger.getLogger(StaffSeminarDaoImpl.class);
    
    /** The Constant ERROR_WHILE_RETRIEVE_STAFF_SEMIANR. */
    private static final String ERROR_WHILE_RETRIEVE_STAFF_SEMIANR = "Error while retrieve staff semianr ---> ";
    
    /** The Constant GET_STAFF_SEMINAR_BY_YEAR. */
    private static final String GET_STAFF_SEMINAR_BY_YEAR = "getStaffSeminarByYear";
    
    /**
     * Retrieves list of seminars for a staff member based on the selected year.
     * 
     * @param staffId - holds integer type
     * @param dateSelectedYear - holds date type
     * @return list of club societies for a selected year of a staff.
     * @throws AkuraAppException - throw detailed exception when retrieve list of staff seminars
     */
    public List<?> getStaffSeminarByYearList(int staffId, Date dateSelectedYear) throws AkuraAppException {

        try {
            if (dateSelectedYear == null) {
                return getHibernateTemplate().findByNamedQuery(GET_STAFF_SEMINAR_BY_STAFF_ID, staffId);
            } else {                
                return getHibernateTemplate().findByNamedQuery(GET_STAFF_SEMINAR_BY_YEAR, staffId, dateSelectedYear);
            }
            
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVE_STAFF_SEMIANR + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
