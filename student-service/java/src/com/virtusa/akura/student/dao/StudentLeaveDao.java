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

package com.virtusa.akura.student.dao;

import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */
public interface StudentLeaveDao extends BaseDao<StudentLeave> {
    
    /**
     * Retrieve all the available StudentLeave information given by student Id.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @return List of StudentLeave
     * @throws AkuraAppException - Throw Exception
     */
    List<StudentLeave> findStudentLeaveByStudentId(int studentId) throws AkuraAppException;
    
    /**
     * Returns the absent days for a given primary key of a student.
     * 
     * @param studentId - the primary key of the student.
     * @param year - the current year.
     * @return - a list of student leave.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentLeave> getStudentLeaveListByStudentId(int studentId, int year) throws AkuraAppException;
    
    /**
     * Finds the already leaves for the given student and the given date range.
     * 
     * @param studentId - the key of the student
     * @param dateFrom - the start date of the leave
     * @param dateTo - the end date of the leave
     * @return - a list of leaves
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentLeave> findAlreadyExistLeave(int studentId, Date dateFrom, Date dateTo) throws AkuraAppException;
    
    /**
     * Check today is within an existing StudentLeave period.
     * 
     * @param studentId - the id of the student.
     * @return - a list of StudentLeave
     * @throws AkuraAppException - Detailed exception throws when fails.
     */
    List<StudentLeave> checkTodayIsWithinLeavePeriod(int studentId) throws AkuraAppException;
    
    /**
     * Check today is within an existing StudentLeave period.
     * 
     * @param studentId - the id of the student.
     * @param date - date to be checked for leaves.
     * @return - a list of StudentLeave
     * @throws AkuraAppException - Detailed exception throws when fails.
     */
    List<StudentLeave> checkSelectedDateIsWithinLeavePeriod(int studentId, Date date) throws AkuraAppException;

    /**
     * get the student's present days list for a selected date range.
     * 
     * @param studentId - the id of the student.
     * @param fromDate - fromDate to be checked for present days.
     * @param toDate - toDate to be checked for present days.
     * @return - a list of StudentLeave
     * @throws AkuraAppException - Detailed exception throws when fails.
     */
    List<Date> getStudentPresentDaysList(int studentId, Date fromDate, Date toDate) throws AkuraAppException;
    
}
