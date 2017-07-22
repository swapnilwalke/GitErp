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

package com.virtusa.akura.attendance.service;

import java.util.List;

import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.attendance.dao.SpecialEventsAttendanceDao;

/**
 * Daily Attendance service implementation.
 *
 * @author Virtusa Corporation
 */

public class SpecialEventAttendanceServiceImpl implements SpecialEventAttendanceService {
    
    /** specialEventsAttendanceDao property to hold SpecialEventsAttendanceDao attribute. */
    private SpecialEventsAttendanceDao specialEventsAttendanceDao;
        
    /**
     * Setter method for specialEventsAttendanceDao.
     *     
     * @param vSpecialEventsAttendanceDao SpecialEventsAttendanceDao object
     */
    public void setSpecialEventsAttendanceDao(SpecialEventsAttendanceDao vSpecialEventsAttendanceDao) {
    
        this.specialEventsAttendanceDao = vSpecialEventsAttendanceDao;
    }

    /** {@inheritDoc} 
     * */
    @Override
    public List<Integer> getStudentsIDOfSpecialEventAttendance(int participationId) throws AkuraAppException {
    
        return specialEventsAttendanceDao.findStudentsIdsOfSpecialEventAttendance(participationId);
        
    }  
    
    
    
}
