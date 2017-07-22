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

package com.virtusa.akura.attendance.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.SpecialEventsAttendance;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the SpecialEventsAttendance object.
 * 
 * @author Virtusa Corporation
 */
public class SpecialEventsAttendanceDaoImpl extends BaseDaoImpl<SpecialEventsAttendance> implements
        SpecialEventsAttendanceDao {
    

    /** attribute for holding string getSpecialEventAttendanceObject. */
    private static final String GET_SPECIAL_EVENT_ATTENDANCE_OBJECT = "getSpecialEventAttendanceObject";
    
    /** attribute for holding string getSpecialEventAttendanceList. */
    private static final String GET_SPECIAL_EVENT_ATTENDANCE_LIST = "getSpecialEventAttendanceList";
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<SpecialEventsAttendance> getSpecialEventAttandanceList(int participationId) throws AkuraAppException {
    
        try {
            return (List<SpecialEventsAttendance>) getHibernateTemplate().findByNamedQuery(
                    GET_SPECIAL_EVENT_ATTENDANCE_LIST, participationId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<SpecialEventsAttendance> getSpecialEventsAttendanceObjectByStudentId(int studentId, int participationId)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_SPECIAL_EVENT_ATTENDANCE_OBJECT, participationId,
                    studentId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    

    /** Represent the named query. */ 
    private static final String GET_STUDENTS_IDS_OF_SPECIAL_EVENT_ATTENDANCE = "getStudentsIdsOfSpecialEventAttendance";
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findStudentsIdsOfSpecialEventAttendance(int participationId)
            throws AkuraAppException {
        
        try {
            return getHibernateTemplate().findByNamedQuery(GET_STUDENTS_IDS_OF_SPECIAL_EVENT_ATTENDANCE,
                    participationId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }    

}
