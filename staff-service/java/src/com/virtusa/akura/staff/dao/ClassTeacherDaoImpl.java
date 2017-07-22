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

package com.virtusa.akura.staff.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClassTeacher;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the ClassTeacher object.
 * 
 * @author Virtusa Corporation
 */
public class ClassTeacherDaoImpl extends BaseDaoImpl<ClassTeacher> implements ClassTeacherDao {
    
    /** query name for markAsDeletedAllClassTeacherByStaffId. */
    private static final String MARK_AS_DELETED_ALL_BY_STAFF_ID = "markAsDeletedAllClassTeacherByStaffId";
    
    /** query name for get class teacher for given class grade and year. */
    private static final String GET_CURRENT_CLASS_TEACHER = "getCurrentClassTeacher";
    
    /** The constant for String "getClassTeacherByGrade". */
    private static final String GET_CLASS_TEACHER_BY_GRADE = "getClassTeacherByGrade";
    
    /** The constant for String "getClassTeacherByYear". */
    private static final String GET_CLASS_TEACHER_LIST_BY_YEAR = "getClassTeacherByYear";
    
    /** The constant for String "searchClassTeachers". */
    private static final String SEARCH_CLASS_TEACHERS = "searchClassTeachers";
    
    /** The constant for date format. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /** The constant for modulo character. */
    private static final String MODEULO_CHARACTER = "%";
    
    /** The constant for year and month format. */
    private static final String YEAR_FORMAT = "-01-01";
    
    /**
     * key to hold the named query deleteClassTeacherRocordsByDepartureDate.
     */
    private static final String DELETE_CLASS_TEACHER_ROCORDS_BY_DEPARTURE_DATE = 
        "deleteClassTeacherRocordsByDepartureDate";
    
    /**
     * String attribute for error ClassTeacher info.
     */
    private static final String ERROR_CLASS_TEACHER_INFO = "Error while retrieving ClassTeacher info ---> ";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StaffLeaveDaoImpl.class);
    
    /**
     * key to hold the named query deleteClassTeacherRocordsByDepartureDate.
     */
    private static final String GET_CLASS_TEACHER_ROCORDS_BY_STAFF_ID_AND_YEAR = 
        "getClassTeacherRecordsByStaffIdandYear";
    
    /** query name for removeAsDeleteAllSubjectTeacherByStaffId. */
    private static final String REMOVE_AS_DELETE_ALL_CLASS_TEACHER_BY_STAFF_ID =
            "removeAsDeleteAllClassTeacherByStaffId";
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<ClassTeacher> searchClassTeacher(String description, String lName, String searchYear)
            throws AkuraAppException {

        Date date = null;
        String year = searchYear + YEAR_FORMAT;
        String lastName = MODEULO_CHARACTER + lName + MODEULO_CHARACTER;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        try {
            date = (Date) formatter.parse(year);
            
            return getHibernateTemplate().findByNamedQuery(SEARCH_CLASS_TEACHERS, description, description, date,
                    lastName, lastName);
        } catch (ParseException e) {
            throw new AkuraAppException(AkuraConstant.DATE_CONVERSION_ERROR, e);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<ClassTeacher> getClassTeacherByGrade(int gradeId) throws AkuraAppException {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        
        int year = Calendar.getInstance().get(Calendar.YEAR);
        
        String yearString = year + YEAR_FORMAT;
        try {
            
            Date date = (Date) formatter.parse(yearString);
            
            return getHibernateTemplate().findByNamedQuery(GET_CLASS_TEACHER_BY_GRADE, gradeId, date);
        } catch (ParseException e) {
            
            throw new AkuraAppException(AkuraConstant.DATE_CONVERSION_ERROR, e);
            
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public ClassTeacher getClassTeacher(ClassGrade classGrade, int year) throws AkuraAppException {

        try {
            
            List<ClassTeacher> classTeacherList =
                    (List<ClassTeacher>) getHibernateTemplate().findByNamedQuery(GET_CURRENT_CLASS_TEACHER, classGrade,
                            year);
            if (classTeacherList != null && !classTeacherList.isEmpty()) {
                // list should contain one element
                return classTeacherList.get(0);
            }
            
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    public void markAsDeletedAllClassTeacherRecords(int staffId) throws AkuraAppException {

        Session session = null;
        try {
            session = getHibernateTemplate().getSessionFactory().openSession();
            Query query = session.getNamedQuery(MARK_AS_DELETED_ALL_BY_STAFF_ID);
            getHibernateTemplate().bulkUpdate(query.getQueryString(), staffId);
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
    }

    /**
     * {@inheritDoc}
     */
	public void deletedClassTeacherRecordsByDepartureDate(int staffId,Date departureDate) throws AkuraAppException {
		 Session session = null;
	        try {
	            session = getHibernateTemplate().getSessionFactory().openSession();
	            Query query = session.getNamedQuery(DELETE_CLASS_TEACHER_ROCORDS_BY_DEPARTURE_DATE);
	            getHibernateTemplate().bulkUpdate(query.getQueryString(), staffId, departureDate);
	        } catch (DataAccessException e) {
	            LOG.error(ERROR_CLASS_TEACHER_INFO + e.getMessage());
	            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
	        } finally {
	            session.close();
	        }
		
	}

	/**
     * Returns a list of ClassTeacher list by year.
     * 
     * @param year - the year. 
     * @return - a list of ClassTeachers
     * @throws AkuraAppException - The exception details that occurred when retrieving all ClassGrades
     */
	@SuppressWarnings("unchecked")
	public List<ClassTeacher> findClassTeacherListByYear(Date year)
			throws AkuraAppException {

		try {

			return getHibernateTemplate().findByNamedQuery(
					GET_CLASS_TEACHER_LIST_BY_YEAR, year);

		} catch (DataAccessException e) {
			throw new AkuraAppException(
					AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);

		}
	}

	
	/**
     * Returns a list of ClassGrades list by year and a given staffId.
     * 
     * @param year - the staffId. 
     * @return - a list of ClassGrades
     * @throws AkuraAppException - The exception details that occurred when retrieving all ClassTeachers
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<ClassGrade> getClassTeacherRecordsByStaffIdAndYear(int staffId)
			throws AkuraAppException {
		
		 SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
	        
	        int currentyear = Calendar.getInstance().get(Calendar.YEAR);
	        
	        String yearString = currentyear + YEAR_FORMAT;
			
			try {
				 Date year = (Date) formatter.parse(yearString);

					return getHibernateTemplate().findByNamedQuery(
							GET_CLASS_TEACHER_ROCORDS_BY_STAFF_ID_AND_YEAR, staffId, year);

				} catch (ParseException e) {
		            
		            throw new AkuraAppException(AkuraConstant.DATE_CONVERSION_ERROR, e);
		            
		        } catch (DataAccessException e) {
		            
		            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
		            
		        }
	}
    
	/**
     * {@inheritDoc}
     */
    public void removeAsDeletedAllClassTeacherRecords(int staffId) throws AkuraAppException {

        Session session = null;
        try {
            
            session = getHibernateTemplate().getSessionFactory().openSession();
            Query query = session.getNamedQuery(REMOVE_AS_DELETE_ALL_CLASS_TEACHER_BY_STAFF_ID);
            getHibernateTemplate().bulkUpdate(query.getQueryString(), staffId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
        
    }
}
