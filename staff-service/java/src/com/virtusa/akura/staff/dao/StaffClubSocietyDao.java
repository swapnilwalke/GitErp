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
import com.virtusa.akura.api.dto.StaffClubSociety;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * The interface StaffClubSocietyDao provides persistence layer functionality for the StaffClubSociety domain
 * object.
 *
 * @author Virtusa Corporation
 */
public interface StaffClubSocietyDao extends BaseDao<StaffClubSociety> {

    /**
     * Retrieves list of cub&society for a staff member based on the selected year.
     *
     * @param staffId - holds integer type
     * @param dateSelectedYear - holds date type
     * @return list of club societies for a selected year of a staff.
     * @throws AkuraAppException - throw detailed exception when retrieve list of staff club societies
     */
    List<?> getStaffClubSocietyByYearList(int staffId, Date dateSelectedYear) throws AkuraAppException;

    /**
     * Retrieves cub&society for a staff member based on the selected year.
     *
     * @param staffId - holds integer type
     * @param dateSelectedYear - holds date type
     * @param clubSocietyId - holds integer type
     * @return list of club societies for a selected year of a staff.
     * @throws AkuraAppException - throw detailed exception when retrieve list of staff club societies
     */
    List<StaffClubSociety> getStaffClubSociety(int staffId, Date dateSelectedYear, int clubSocietyId)
            throws AkuraAppException;
    
    /**
     * Retrieves cub&societies for a staff member.
     *
     * @param staffId - holds integer type
     * @return list of club societies for a staff.
     * @throws AkuraAppException - throw detailed exception when retrieve list of staff club societies
     */
    List<Object[]> getStaffClubSocietyByStaffId(int staffId) throws AkuraAppException;
}
