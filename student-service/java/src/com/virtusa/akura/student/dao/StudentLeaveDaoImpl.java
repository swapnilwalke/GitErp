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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * @author Virtusa Corporation
 */
public class StudentLeaveDaoImpl extends BaseDaoImpl<StudentLeave> implements StudentLeaveDao {
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentLeaveDaoImpl.class);
    
    /** key to hold detailed error while retrieving student leave information. */
    private static final String ERROR_WHILE_RETRIEVING_STUDENT_LEAVE_INFO =
            "Error while retrieving student leave info ---> ";
    
    /** key to hold the named query findStudentClubAchievement. */
    private static final String GET_STUDENT_LEAVE_BY_STUDENT_ID = "getStudentLeaveByStudentId";
    
    /** Represent error message when parsing the date. */
    private static final String DATE_CONVERSION_ERROR = "Date Conversion Error";
    
    /** Represent the end date of the year. */
    private static final String YEAR_END_DATE = "-12-31";
    
    /** The constant for date format. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /** The constant for year and month format. */
    private static final String YEAR_FORMAT = "-01-01";
    
    /** Represent the named query. */
    private static final String STUDENT_LEAVE_BY_STUDENT_ID = "getStudentLeave";
    
    /** Represent the named query for retrieving already exist leaves. */
    private static final String FIND_STUDENT_LEAVE = "findStudentLeave";
    
    /** Error message for retrieving student leave. */
    private static final String ERROR_WHILE_RETRIEVING_STUDENT_LEAVE = "error while retrieving student leave.";
    
    /** Represent the named query for "checkTodayIsWithinLeavePeriod". */
    private static final String CHECK_TODAY_IS_WITHIN_LEAVE_PERIOD = "checkTodayIsWithinLeavePeriod";
    
    /** Represent the named query for "checkSelectedDateIsWithinLeavePeriod". */
    private static final String CHECK_SELECTED_DATE_IS_WITHIN_LEAVE_PERIOD = "checkSelectedDateIsWithinLeavePeriod";
    
    /** Represent the named query for "getStudentPresentDaysList". */
    private static final String GET_STUDENT_PRESENT_DAYS_LIST = "getStudentPresentDaysList";
    
    /** Represent the named query for "error while checking student leave exist.". */
    private static final String ERROR_WHILE_CHECKING_STUDENT_LEAVE_EXIST = "error while checking student leave exist.";
    
    /** Represent the named query for "error while checking present days of a student.". */
    private static final String ERROR_WHILE_CHECKING_STUDENT_PRESENT_DAYS =
            "error while checking present days of a student.";
    
    /**
     * Retrieve all the available StudentLeave information given by student Id.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @return List of StudentLeave
     * @throws AkuraAppException - Throw Exception
     */
    @SuppressWarnings("unchecked")
    public List<StudentLeave> findStudentLeaveByStudentId(int studentId) throws AkuraAppException {

        List<StudentLeave> studentLeaves = null;
        try {
            studentLeaves = getHibernateTemplate().findByNamedQuery(GET_STUDENT_LEAVE_BY_STUDENT_ID, studentId);
        } catch (DataAccessException e) {
            
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_LEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return studentLeaves;
    }
    
    /**
     * Returns the absent days for a given primary key of a student.
     * 
     * @param studentId - the primary key of the student.
     * @param year - the current year.
     * @return - a list of student leave.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @SuppressWarnings("unchecked")
    public List<StudentLeave> getStudentLeaveListByStudentId(int studentId, int year) throws AkuraAppException {

        String yearString = year + YEAR_FORMAT;
        String toYearString = year + YEAR_END_DATE;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        
        try {
            Date formDate = (Date) formatter.parse(yearString);
            Date toDate = (Date) formatter.parse(toYearString);
            return getHibernateTemplate().findByNamedQuery(STUDENT_LEAVE_BY_STUDENT_ID, studentId, formDate, toDate);
        } catch (DataAccessException e) {
            
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_LEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } catch (ParseException parseException) {
            throw new AkuraAppException(DATE_CONVERSION_ERROR, parseException);
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<StudentLeave> findAlreadyExistLeave(int studentId, Date dateFrom, Date dateTo) 
    throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_LEAVE, studentId, dateFrom, dateTo, dateFrom,
                    dateTo, dateFrom, dateTo);
        } catch (DataAccessException e) {
            
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_LEAVE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<StudentLeave> checkTodayIsWithinLeavePeriod(int studentId) throws AkuraAppException {

        try {
            
            return getHibernateTemplate().findByNamedQuery(CHECK_TODAY_IS_WITHIN_LEAVE_PERIOD, studentId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_LEAVE + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<StudentLeave> checkSelectedDateIsWithinLeavePeriod(int studentId, Date date) throws AkuraAppException {

        try {
            
            return getHibernateTemplate().findByNamedQuery
            (CHECK_SELECTED_DATE_IS_WITHIN_LEAVE_PERIOD, studentId, date, date);
        } catch (DataAccessException e) {
            
            LOG.error(ERROR_WHILE_CHECKING_STUDENT_LEAVE_EXIST + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Date> getStudentPresentDaysList(int studentId, Date fromDate, Date toDate) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_STUDENT_PRESENT_DAYS_LIST, fromDate, toDate, studentId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_CHECKING_STUDENT_PRESENT_DAYS + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
}
