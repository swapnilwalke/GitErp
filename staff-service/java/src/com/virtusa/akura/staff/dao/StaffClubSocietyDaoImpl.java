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
import com.virtusa.akura.api.dto.StaffClubSociety;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * Implements StaffClubSocietyDao and provides persistence layer functionality for the StaffClubSociety domain
 * object.
 * 
 * @author Virtusa Corporation
 */
public class StaffClubSocietyDaoImpl extends BaseDaoImpl<StaffClubSociety> implements StaffClubSocietyDao {
    
    /** The Constant GET_STAFF_CLUB_SOCIETY_BY_STAFF_ID. */
    private static final String GET_STAFF_CLUB_SOCIETY_BY_STAFF_ID = "getStaffClubSocietyByStaffId";

    /**
     * Holds logger property to log errors.
     */
    private static final Logger LOG = Logger.getLogger(StaffClubSocietyDaoImpl.class);
    
    /** The Constant GET_STAFF_CLUB_SOCIETY. */
    private static final String GET_STAFF_CLUB_SOCIETY = "getStaffClubSociety";
    
    /** The Constant ERROR_WHILE_RETRIEVE_STAFF_CLUB_SOCIETY. */
    private static final String ERROR_WHILE_RETRIEVE_STAFF_CLUB_SOCIETY =
            "Error while retrieve staff club society ---> ";
    
    /** The Constant GET_STAFF_CLUB_SOCIETY_BY_YEAR. */
    private static final String GET_STAFF_CLUB_SOCIETY_BY_YEAR = "getStaffClubSocietyByYear";
    
    /**
     * Retrieves list of cub&society for a staff member based on the selected year.
     * 
     * @param staffId - holds integer type
     * @param dateSelectedYear - holds date type
     * @return list of club societies for a selected year of a staff.
     * @throws AkuraAppException - throw detailed exception when retrieve list of staff club societies
     */
    public List<?> getStaffClubSocietyByYearList(int staffId, Date dateSelectedYear) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_STAFF_CLUB_SOCIETY_BY_YEAR, staffId, dateSelectedYear);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVE_STAFF_CLUB_SOCIETY + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Retrieves cub&society for a staff member based on the selected year.
     * 
     * @param staffId - holds integer type
     * @param dateSelectedYear - holds date type
     * @param clubSocietyId - holds integer type
     * @return list of club societies for a selected year of a staff.
     * @throws AkuraAppException - throw detailed exception when retrieve list of staff club societies
     */
    @SuppressWarnings("unchecked")
    public List<StaffClubSociety> getStaffClubSociety(int staffId, Date dateSelectedYear, int clubSocietyId)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_STAFF_CLUB_SOCIETY, staffId, dateSelectedYear,
                    clubSocietyId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVE_STAFF_CLUB_SOCIETY + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Retrieves ClubSociety for a staff member.
     *
     * @param staffId - holds integer type
     * @return list of club societies for a staff.
     * @throws AkuraAppException - throw detailed exception when retrieve list of staff club societies
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getStaffClubSocietyByStaffId(int staffId) throws AkuraAppException {
        
        try {          
            return getHibernateTemplate().findByNamedQuery(GET_STAFF_CLUB_SOCIETY_BY_STAFF_ID, staffId);
            
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVE_STAFF_CLUB_SOCIETY + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
}
