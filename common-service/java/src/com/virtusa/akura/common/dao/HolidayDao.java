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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This interface provides persistence layer functionality for the Holiday domain object.
 * 
 * @author Virtusa Corporation
 */
public interface HolidayDao extends BaseDao<Holiday> {
    
    /**
     * retrieving list of Holidays for a given year.
     * 
     * @param startDate the Date
     * @param endDate the Date
     * @return List {@link Holiday}
     */
    List<Holiday> getHolidayListByYear(Date startDate, Date endDate);
    
    /**
     * Retrieve the holiday by passing the holiday's holidayname.This returns any holiday with the holiday
     * passed.
     * 
     * @param holidayName - String
     * @param startDate - Date
     * @param endDate - Date
     * @return Holiday object.
     * @throws AkuraAppException SMS Exceptions.
     */
    Holiday getHolidayByName(String holidayName,Date startDate,Date endDate) throws AkuraAppException;
    
    
    /**
     * Retrieve the holiday by passing the holiday's holidayname.This returns any holiday with the holiday
     * passed.
     * 
     * @param startDate - Date
     * @param endDate - Date
     * @param startDate1 - Date
     * @param endDate1 - Date
     * @param startDate2 - Date
     * @param endDate2 - Date
     * @return Holiday object.
     * @throws AkuraAppException SMS Exceptions.
     */
    Holiday getHolidayListbyStartDateAndEndDate(Date startDate, Date endDate,
            Date startDate1, Date endDate1,Date startDate2, Date endDate2) throws AkuraAppException;
    
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
    
    List<Holiday> getHolidayListWithInDateRange(Date startDate, Date endDate, Date startDate1,
            Date endDate1, Date startDate2, Date endDate2) throws AkuraAppException; 
}
