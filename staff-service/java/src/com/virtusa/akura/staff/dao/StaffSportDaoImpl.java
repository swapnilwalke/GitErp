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
import com.virtusa.akura.api.dto.StaffSport;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * Implements StaffSportDao and provides persistence layer functionality for the StaffSport domain object.
 *
 * @author Virtusa Corporation
 */
public class StaffSportDaoImpl extends BaseDaoImpl<StaffSport> implements StaffSportDao {

    /** The Constant GET_STAFF_SPORTS_BY_STAFF_ID. */
    private static final String GET_STAFF_SPORTS_BY_STAFF_ID = "getStaffSportsByStaffId";

    /** Holds logger property to log errors. */
    private static final Logger LOG = Logger.getLogger(StaffSportDaoImpl.class);

    /** The Constant CHECK_STAFF_SPORTS_EXIST. */
    private static final String CHECK_STAFF_SPORTS_EXIST = "checkStaffSportsExist";

    /** The Constant GET_STAFF_SPORTS_BY_YEAR. */
    private static final String GET_STAFF_SPORTS_BY_YEAR = "getStaffSportsByYear";

    /**
     * Gets the staff sport list by year.
     *
     * @see com.virtusa.akura.staff.dao.StaffSportDao#getStaffSportListByYear()
     * @param staffId - type int
     * @param dateSelectedYear - type Date
     * @return the staff sport list by year
     * @throws AkuraAppException the sMS app exception
     */
    @SuppressWarnings("unchecked")
    public List<StaffSport> getStaffSportListByYear(int staffId, Date dateSelectedYear) throws AkuraAppException {

        try {
            
            return getHibernateTemplate().findByNamedQuery(GET_STAFF_SPORTS_BY_YEAR, staffId, dateSelectedYear);
            
        } catch (DataAccessException e) {
            LOG.error("Error while retrieve staff sport ---> " + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /**
     * Gets the staff sports.
     *
     * @see com.virtusa.akura.staff.dao.StaffSportDao#getStaffSports(int, java.util.Date, int, int)
     * @param staffId the staff id
     * @param selectedYear the selected year
     * @param sportCategoryId the sport category id
     * @return the staff sports
     * @throws AkuraAppException - detailed exception
     */
    @SuppressWarnings("unchecked")
    public List<StaffSport> getStaffSports(int staffId, Date selectedYear, int sportCategoryId)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(CHECK_STAFF_SPORTS_EXIST, staffId, selectedYear,
                    sportCategoryId);
        } catch (DataAccessException e) {
            LOG.error("Error while retrieve staff sport1 ---> " + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Get staff sports by staff id.
     * 
     * @param staffId staffId
     * @return list of staff sports.
     * @throws AkuraAppException AkuraAppException
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getStaffSportByStaffId(int staffId) throws AkuraAppException {
        
        try {
            return getHibernateTemplate().findByNamedQuery(GET_STAFF_SPORTS_BY_STAFF_ID, staffId);
            
        } catch (DataAccessException e) {
            LOG.error("Error while retrieve staff sport1 ---> " + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
