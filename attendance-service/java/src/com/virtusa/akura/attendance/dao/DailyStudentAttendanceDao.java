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

package com.virtusa.akura.attendance.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.DailyStudentAttendance;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the DailyStudentAttendance object.
 * 
 * @author Virtusa Corporation
 */
public interface DailyStudentAttendanceDao extends BaseDao<DailyStudentAttendance> {
    
    /**
     * Generate attendance data list of students.
     * 
     * @param selectedDate of type date
     * @param classGradeId of type integer
     * @return list of type DailyStudentAttendance
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<DailyStudentAttendance> getStudentAttandanceList(Date selectedDate, int classGradeId) throws AkuraAppException;
    
    /**
     * Finds the DailyStudentAttendance object for the given studentId and date.
     * 
     * @param studentId of type integer
     * @param date of type date
     * @return - a list of DailyStudentAttendance
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<DailyStudentAttendance> findByStudentId(int studentId, Date date) throws AkuraAppException;
    
    /**
     * Saves data into the database.
     * 
     * @param stuDailyAttendance - a set of DailyStudentAttendance objects.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void loadDataIntoDatabase(Set<DailyStudentAttendance> stuDailyAttendance) throws AkuraAppException;
    
    /**
     * Searches the daily attendance for a given primary key of the student.
     * 
     * @param studentId - primary key of the student.
     * @param year - the current year.
     * @return - a list of daily attendance of the student.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<DailyStudentAttendance> searchAttendance(int studentId, int year) throws AkuraAppException;
    
    /**
     * get student's attendance for given time rage.
     * 
     * @param studentId studentId value
     * @param from date rage form
     * @param to date rage to
     * @return List
     * @throws AkuraAppException The exception details that occurred when processing.
     */
    List<DailyStudentAttendance> getAttendanceBettween(int studentId, Date from, Date to) throws AkuraAppException;
    
    /**
     * get the list of DailyStudentAttendance of students with the status other than 1.
     * 
     * @param selectedDate - date of interest.
     * @param classGradeId - class if the student.
     * @return list of Daily StudentAttendance.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<DailyStudentAttendance> getNonCurrentStudentAttendanceList(int classGradeId, Date selectedDate)
            throws AkuraAppException;
    
}
