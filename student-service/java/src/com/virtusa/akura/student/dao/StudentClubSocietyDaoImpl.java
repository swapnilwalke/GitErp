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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StudentClubSociety;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * @author Virtusa Corporation
 */
public class StudentClubSocietyDaoImpl extends BaseDaoImpl<StudentClubSociety> implements StudentClubSocietyDao {
    
    /** key to hold the named query getStudentListByClubSociety. */
    private static final String GET_STUDENT_LIST_BY_CLUB_SOCIETY = "getStudentListByClubSociety";
    
    /** key to hold the named query findStudentClubSocietyByClubSocietyId. */
    private static final String FIND_STUDENT_CLUB_SOCIETY_BY_CLUB_SOCIETY_ID = "findStudentClubSocietyByClubSocietyId";
    
    /** key to hold the named query findStudentClubSociety. */
    private static final String FIND_STUDENT_CLUB_SOCIETY = "findStudentClubSociety";
    
    /** key to hold the named query getClubSocietyListForStudentById. */
    private static final String GET_CLUB_SOCIETY_LIST_FOR_STUDENT_BY_ID = "getClubSocietyListForStudentById";
    
    /** key to hold the named query getClubSocietyListForStudent. */
    private static final String GET_CLUB_SOCIETY_LIST_FOR_STUDENT = "getClubSocietyListForStudent";
    
    /** key to hold the error in retrieving StudentClubSociety. */
    private static final String ERROR_IN_RETRIEVING_NON_CURRENT_STUDENT_CLUB_SOCIETY =
            "Error in retrieving non current StudentClubSociety.";
    
    /** key to hold the named query "getNonCurrentStudentListByClubSociety". */
    private static final String GET_NON_CURRENT_STUDENT_LIST_BY_CLUB_SOCIETY = "getNonCurrentStudentListByClubSociety";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentClubSocietyDaoImpl.class);
    
    /** Represents the club society and the position of the student. */
    private static final String CLUB_SOCIETY_AND_POSITION_FOR_STUDENT = "clubSocietyAndPositionForStudent";
    
    /**
     * retrieving list of StudentClubSociety for a given studentId and year.
     * 
     * @param studentId the studentId to get ClubSociety details.
     * @param year the Date to get ClubSociety details.
     * @return List {@link StudentClubSociety}
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudentClubSociety> getClubSocietyListForStudent(int studentId, Date year) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_CLUB_SOCIETY_LIST_FOR_STUDENT, studentId, year);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * retrieving list of StudentClubSociety for a given studentId.
     * 
     * @param studentId the studentId to get ClubSociety details.
     * @return List {@link StudentClubSociety}
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudentClubSociety> getClubSocietiesListForStudent(int studentId) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_CLUB_SOCIETY_LIST_FOR_STUDENT_BY_ID, studentId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * retrieving list of StudentClubSociety for a given studentId,sportsCategoryId and given year.
     * 
     * @param studentId the studentId to get ClubSociety details.
     * @param clubSocietyId the club or society Id.
     * @param year the Date.
     * @return List {@link StudentClubSociety}
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudentClubSociety> findStudentClubSociety(int studentId, int clubSocietyId, Date year)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_CLUB_SOCIETY, studentId, clubSocietyId, year);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<StudentClubSociety> getStudentListforClubSociety(int clubSocietyId, Date year) 
            throws AkuraAppException {
    
        try {
            String yearOnly = DateUtil.getStringYear(year);
            Date dateTypeYear = DateUtil.getParseDate(yearOnly + "-01-01");
            return getHibernateTemplate().findByNamedQuery(GET_STUDENT_LIST_BY_CLUB_SOCIETY, clubSocietyId,
                    dateTypeYear);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /** {@inheritDoc} */
    @Override
    public List<StudentClubSociety> findStudentClubSocietyByClubSocietyId(int clubSocietyId, Date year)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_CLUB_SOCIETY_BY_CLUB_SOCIETY_ID, clubSocietyId,
                    year);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StudentClubSociety> getNonCurrentStudentListByClubSociety(int clubSocietyId, Date year, Date date)
            throws AkuraAppException {
    
        try {
            
            String yearOnly = DateUtil.getStringYear(year);
            Date dateTypeYear = DateUtil.getParseDate(yearOnly + "-01-01");
            return getHibernateTemplate().findByNamedQuery(GET_NON_CURRENT_STUDENT_LIST_BY_CLUB_SOCIETY, clubSocietyId,
                    dateTypeYear, date);
        } catch (DataAccessException e) {
            LOG.error(ERROR_IN_RETRIEVING_NON_CURRENT_STUDENT_CLUB_SOCIETY, e);
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /** {@inheritDoc} */
    public List<List<String>> viewClubAndSocieties(int studentKey, Date date) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(CLUB_SOCIETY_AND_POSITION_FOR_STUDENT, studentKey, date);
        } catch (DataAccessException e) {
            LOG.error(ERROR_IN_RETRIEVING_NON_CURRENT_STUDENT_CLUB_SOCIETY, e);
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
}
