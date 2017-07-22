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

/**
 * Daily Attendance service implementation.
 * 
 * @author Virtusa Corporation
 */

public interface SpecialEventAttendanceService {
    
    /**
     * This method use to find student ids' of special event attendance by participation id.
     * 
     * @param participationId special event participation id
     * @return list of special event attendance
     * @throws AkuraAppException throws when exception occurs
     */
    List<Integer> getStudentsIDOfSpecialEventAttendance(int participationId)
            throws AkuraAppException;
}
