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

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.DailyTeacherAttendance;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * This class provides persistence layer functionality for the DailyTeacherAttendance object.
 * 
 * @author Virtusa Corporation
 */
public class DailyTeacherAttendanceDaoImpl extends BaseDaoImpl<DailyTeacherAttendance> implements
        DailyTeacherAttendanceDao {
    
    /**
     * attribute for holding query name.
     */
    private static final String GET_STAFF_LIST_BY_DATE = "getStaffListByDate";
    
    /**
     * attribute for holding query name.
     */
    private static final String GET_TEACHER_BY_STAFF_ID = "getTeacherByStaffId";
    
    /**
     * attribute for holding query name.
     */
    private static final String GET_STAFF_LIST_BY_STAFF_TYPE = "getStaffListByStaffType";
    
    /**
     * attribute for holding query name.
     */
    private static final String GET_STAFF_LIST_BY_STAFF_CATEGORY_ID = "getStaffListByStaffCategoryId";
    
    /**
     * attribute for holding query name.
     */
    private static final String GET_HALF_DAY_STAFF_LIST_BY_DATE_AND_STAFF_TYPE =
            "getHalfDayTeacherListByDateAndStaffType";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(DailyTeacherAttendanceDaoImpl.class);
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<DailyTeacherAttendance> getTeacherAttandanceList(Date date, boolean sType) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_STAFF_LIST_BY_STAFF_TYPE, sType, date);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<DailyTeacherAttendance> getTeacherAttandanceListByCategoryId(Date date, int categoryId)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_STAFF_LIST_BY_STAFF_CATEGORY_ID, categoryId, date);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<StaffLeave> getHalfDayTeacherAttandanceList(Date date, boolean staffType) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_HALF_DAY_STAFF_LIST_BY_DATE_AND_STAFF_TYPE, date);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<DailyTeacherAttendance> getStaffAttandanceList(Date date) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_STAFF_LIST_BY_DATE, date);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DailyTeacherAttendance> findByTeacherId(int staffId, Date date) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_TEACHER_BY_STAFF_ID, staffId, date);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    public void loadDataIntoDatabase(Set<DailyTeacherAttendance> staffDailyAttendance) throws AkuraAppException {
    
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            tx.begin();
            for (DailyTeacherAttendance dailyTeacherAttendance : staffDailyAttendance) {
                String date = DateUtil.getFormatDate(dailyTeacherAttendance.getDate());
                
                Query query = session.createQuery("SELECT staffId FROM DailyTeacherAttendance " +
                        "WHERE staffId = '"+ dailyTeacherAttendance.getStaffId() +"' AND " +
                                "date = '"+ date + "' AND " +
                                        "timeIn = '" + dailyTeacherAttendance.getTimeIn() + "' AND " +
                                                "timeOut = '" + dailyTeacherAttendance.getTimeOut() + "'");
                
                if(query.list().isEmpty()){
                session.persist(dailyTeacherAttendance);
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
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
    
}
