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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.DailyStudentAttendance;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * This class provides persistence layer functionality for the DailyStudentAttendance object.
 * 
 * @author Virtusa Corporation
 */
public class DailyStudentAttendanceDaoImpl extends BaseDaoImpl<DailyStudentAttendance> implements
        DailyStudentAttendanceDao {
    

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(DailyStudentAttendanceDaoImpl.class);
    
    /** The constant for date format. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /** The constant for year and month format. */
    private static final String YEAR_FORMAT = "-01-01";
    
    /** Represent the end date of the year. */
    private static final String YEAR_END_DATE = "-12-31";
    
    /** Represent the named query. */
    private static final String STUDENT_LEAVE_BY_STUDENT_ID = "getAttendance";
    
    /** Represent error message when parsing the date. */
    private static final String DATE_CONVERSION_ERROR = "Date Conversion Error";
    
    /**
     * attribute for holding query name.
     */
    private static final String GET_STUDENT_ATTENDANCE = "getStudentAttendance";
    
    /**
     * attribute for holding query name.
     */
    private static final String FIND_DAILY_STUDENT_ATTENDANCE_BY_STUDENTID = "getStudentAttendancebyStudentId";
    
    /**
     * attribute for holding query name.
     */
    private static final String GET_NON_CURRENT_STUDENT_ATTENDANCE_LIST = "getNonCurrentStudentAttendanceList";

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<DailyStudentAttendance> getStudentAttandanceList(Date date, int classGradeId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_STUDENT_ATTENDANCE, classGradeId, date);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DailyStudentAttendance> findByStudentId(int studentId, Date date) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_DAILY_STUDENT_ATTENDANCE_BY_STUDENTID, studentId, date);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    public void loadDataIntoDatabase(Set<DailyStudentAttendance> stuDailyAttendance) throws AkuraAppException {

        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            tx.begin();
            
            for (DailyStudentAttendance dailyStudentAttendance : stuDailyAttendance) {
                String date = DateUtil.getFormatDate(dailyStudentAttendance.getDate());
                
                Query query = session.createQuery("SELECT studentId FROM DailyStudentAttendance " +
                		"WHERE studentId = '"+ dailyStudentAttendance.getStudentId() +"' AND " +
                				"date = '"+ date + "' AND " +
                						"timeIn = '" + dailyStudentAttendance.getTimeIn() + "' AND " +
                								"timeOut = '" + dailyStudentAttendance.getTimeOut() + "'");
                
                if(query.list().isEmpty()){
                session.persist(dailyStudentAttendance);
                }
            }
            tx.commit();
            
        } catch (DataAccessException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_LOADING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_LOADING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<DailyStudentAttendance> searchAttendance(int studentId, int year) throws AkuraAppException {

        String yearString = year + YEAR_FORMAT;
        String toYearString = year + YEAR_END_DATE;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        
        try {
            Date formDate = (Date) formatter.parse(yearString);
            Date toDate = (Date) formatter.parse(toYearString);
            
            try {
                return getHibernateTemplate()
                        .findByNamedQuery(STUDENT_LEAVE_BY_STUDENT_ID, studentId, formDate, toDate);
            } catch (DataAccessException e) {
                throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            }
        } catch (ParseException parceException) {
            throw new AkuraAppException(DATE_CONVERSION_ERROR, parceException);
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<DailyStudentAttendance> getAttendanceBettween(int studentId, Date from, Date to)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(STUDENT_LEAVE_BY_STUDENT_ID, studentId, from, to);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<DailyStudentAttendance> getNonCurrentStudentAttendanceList(int classGradeId, Date date)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_NON_CURRENT_STUDENT_ATTENDANCE_LIST, classGradeId, date);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
}
