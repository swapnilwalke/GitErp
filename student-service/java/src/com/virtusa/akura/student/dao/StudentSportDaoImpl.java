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
import com.virtusa.akura.api.dto.StudentSport;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * @author Virtusa Corporation
 */
public class StudentSportDaoImpl extends BaseDaoImpl<StudentSport> implements StudentSportDao {
    
    /** key to hold the named query getStudentListforSportsCategory. */
    private static final String GET_STUDENT_LISTFOR_SPORTS_CATEGORY = "getStudentListforSportsCategory";
    
    /** key to hold the named query findStudentSportByCategory. */
    private static final String FIND_STUDENT_SPORT_BY_CATEGORY = "findStudentSportByCategory";
    
    /** The Constant GET_SPORTS_LIST_BY_STUDENT_ID_ONLY. */
    private static final String GET_SPORTS_LIST_BY_STUDENT_ID_ONLY = "getSportsListByStudentIdOnly";
    
    /** key to hold the named query findStudentSport. */
    private static final String FIND_STUDENT_SPORT = "findStudentSport";
    
    /** key to hold the named query getSportsListForStudentById. */
    private static final String GET_SPORTS_LIST_FOR_STUDENT_BY_ID = "getSportsListForStudentById";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentSport.class);
    
    /**
     * retrieving list of StudentSport for a given studentId and given year.
     * 
     * @param studentId the studentId to get Sports detail.
     * @param year of the StudentSport activity.
     * @return List {@link StudentSport}
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudentSport> getSportsListForStudent(int studentId, Date year) throws AkuraAppException {

        List<StudentSport> studentSports;
        try {
            if (year != null) {
                studentSports =
                        getHibernateTemplate().findByNamedQuery(GET_SPORTS_LIST_FOR_STUDENT_BY_ID, studentId, year);
            } else {
                studentSports = getHibernateTemplate().findByNamedQuery(GET_SPORTS_LIST_BY_STUDENT_ID_ONLY, studentId);
            }
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return studentSports;
    }
    
    /**
     * retrieving list of StudentSport for a given studentId,sportsCategoryId and given year.
     * 
     * @param studentId the studentId to get Sports detail.
     * @param sportsCategoryId the sportsCategory Id.
     * @param year of the StudentSport activity.
     * @return List {@link StudentSport}
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudentSport> findStudentSport(int studentId, int sportsCategoryId, Date year) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_SPORT, studentId, sportsCategoryId, year);
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<StudentSport> getStudentListforSportsCategory(int sportCategoryId, Date year) throws AkuraAppException {

        try {
            String yearOnly = DateUtil.getStringYear(year);
            Date dateTypeYear = DateUtil.getParseDate(yearOnly + "-01-01");
            return getHibernateTemplate().findByNamedQuery(GET_STUDENT_LISTFOR_SPORTS_CATEGORY, sportCategoryId,
                    dateTypeYear);
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudentSport> getStudentSportBySportCategoryId(int sportsCategoryId, Date year)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_SPORT_BY_CATEGORY, sportsCategoryId, year);
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StudentSport> getNonCurrentStudentListforSportsCategory(int sportsCategoryId, Date year, Date date)
            throws AkuraAppException {

        try {
            
            String yearOnly = DateUtil.getStringYear(year);
            Date dateTypeYear = DateUtil.getParseDate(yearOnly + "-01-01");
            return getHibernateTemplate().findByNamedQuery("getNonCurrentStudentListforSportsCategory",
                    sportsCategoryId, dateTypeYear, date);
            
        } catch (DataAccessException e) {
            LOG.error("Error retrieving student sports.", e);
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
}
