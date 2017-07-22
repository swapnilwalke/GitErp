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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.StaffSport;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * The interface StaffSportDao provides persistence layer functionality for the StaffSport domain object.
 *
 * @author Virtusa Corporation
 */
public interface StaffSportDao extends BaseDao<StaffSport> {

    /**
     * Gets the staff sport list by year.
     *
     * @param staffId the staff id
     * @param dateSelectedYear the date selected year
     * @return the staff sport list by year
     * @throws AkuraAppException the sMS app exception
     */
    List<StaffSport> getStaffSportListByYear(int staffId, Date dateSelectedYear) throws AkuraAppException;

    /**
     * Gets the staff sports.
     *
     * @param staffId the staff id
     * @param selectedYear the selected year
     * @param sportCategoryId the sport category id
     * @return the staff sports
     * @throws AkuraAppException - detailed exception
     */
    List<StaffSport> getStaffSports(int staffId, Date selectedYear, int sportCategoryId)
            throws AkuraAppException;
    
    /**
     * Get staff sports by staff id.
     * 
     * @param staffId staffId
     * @return list of staff sports.
     * @throws AkuraAppException AkuraAppException
     */
    List<Object[]> getStaffSportByStaffId(int staffId) throws AkuraAppException ;
}
