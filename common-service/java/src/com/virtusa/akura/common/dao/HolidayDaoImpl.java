/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

package com.virtusa.akura.common.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides the persistence level implementation for the Holiday domain object.
 * 
 * @author Virtusa Corporation
 */
public class HolidayDaoImpl extends BaseDaoImpl<Holiday> implements HolidayDao {
    
    /** key to hold named query getHolidayByYear. */
    private static final String GET_HOLIDAY_BY_YEAR = "getHolidayByYear";
    
    /** String constant for holding respective query name. */
    private static final String GET_HOLIDAY_BY_NAME = "getHolidayByName";
    
    /** String constant for holding respective query name. */
    private static final String GET_HOLIDAY_BY_START_DATE_AND_END_DATE = "getHolidayListbyStartDateAndEndDate";
    
    /**
     * retrieving list of Holidays for a given year.
     * 
     * @param startDate the Date
     * @param endDate the Date
     * @return List {@link Holiday}
     */
    @SuppressWarnings("unchecked")
    public List<Holiday> getHolidayListByYear(Date startDate, Date endDate) {
    
        return (List<Holiday>) getHibernateTemplate().findByNamedQuery(GET_HOLIDAY_BY_YEAR, startDate, endDate,
                startDate, endDate);
        
    }
    
    /**
     * Retrieve all the holiday's with the holidayname.
     * 
     * @param holidayName The name of the holiday.
     * @param startDate start date
     * @param endDate end date
     * @return holiday the holiday with the name "holidayName".
     * @throws AkuraAppException SMS Exceptions.
     */
    @SuppressWarnings("unchecked")
    public Holiday getHolidayByName(String holidayName, Date startDate, Date endDate) throws AkuraAppException {
    
        List<Holiday> holidayList = null;
        Holiday holiday = null;
        
        try {
            holidayList = getHibernateTemplate().findByNamedQuery(GET_HOLIDAY_BY_NAME, holidayName, startDate, endDate);
            if (holidayList != null && !holidayList.isEmpty()) {
                holiday = holidayList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return holiday;
    }
    
    /**
     * Retrieve all the holiday's with the holidayname.
     * 
     * @param startDate start date
     * @param endDate end date
     * @param startDate1 start date
     * @param endDate1 end date
     * @param startDate2 start date
     * @param endDate2 end date
     * @return holiday the holiday with the name "holidayName".
     * @throws AkuraAppException SMS Exceptions.
     */
    @SuppressWarnings("unchecked")
    public Holiday getHolidayListbyStartDateAndEndDate(Date startDate, Date endDate, Date startDate1, Date endDate1,
            Date startDate2, Date endDate2) throws AkuraAppException {
    
        List<Holiday> holidayList = null;
        Holiday holiday = null;
        
        try {
            holidayList =
                    getHibernateTemplate().findByNamedQuery(GET_HOLIDAY_BY_START_DATE_AND_END_DATE, startDate, endDate,
                            startDate1, endDate1, startDate2, endDate2);
            if (holidayList != null && !holidayList.isEmpty()) {
                holiday = holidayList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return holiday;
    }
    
    /**
     * Retrieve all the holiday's with the holidayname.
     * 
     * @param startDate start date
     * @param endDate end date
     * @param startDate1 start date
     * @param endDate1 end date
     * @param startDate2 start date
     * @param endDate2 end date
     * @return holiday the holiday with the name "holidayName".
     * @throws AkuraAppException SMS Exceptions.
     */
    @SuppressWarnings("unchecked")
    public List<Holiday> getHolidayListWithInDateRange(Date startDate, Date endDate, Date startDate1,
            Date endDate1, Date startDate2, Date endDate2) throws AkuraAppException {
    
        List<Holiday> holidayList = null;
        
        try {
            holidayList =
                    getHibernateTemplate().findByNamedQuery(GET_HOLIDAY_BY_START_DATE_AND_END_DATE, startDate, endDate,
                            startDate1, endDate1, startDate2, endDate2);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return holidayList;
    }
    
}
