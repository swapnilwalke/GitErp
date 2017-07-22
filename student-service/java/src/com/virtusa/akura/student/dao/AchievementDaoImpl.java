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

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.Achievement;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * @author Virtusa Corporation
 */

public class AchievementDaoImpl extends BaseDaoImpl<Achievement> implements AchievementDao {
    
    /** The Constant GET_ACHIEVEMENT_LIST_BY_STUDENT_ID_ONLY. */
    private static final String GET_ACHIEVEMENT_LIST_BY_STUDENT_ID_ONLY = "getAchievementListByStudentIdOnly";
    
    /** key to hold the named query findStudentClubAchievement. */
    private static final String FIND_STUDENT_CLUB_ACHIEVEMENT = "findStudentClubAchievement";
    
    /** key to hold the named query findStudentAchievement. */
    private static final String FIND_STUDENT_ACHIEVEMENT = "findStudentAchievement";
    
    /** key to hold the named query getAchievementsListForStudent. */
    private static final String GET_ACHIEVEMENTS_LIST_FOR_STUDENT = "getAchievementsListForStudent";
    
    /** Represents the achievements. */
    private static final String VIEW_ACHIEVEMENTS = "viewAchievements";
    
    /**
     * Retrieve all the available Achievement information of a given year for a student Id .
     * 
     * @param studentId specifies the student id, defined by an integer
     * @param year the student Date.
     * @return List of Achievement
     * @throws SMSAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Achievement> getAchievementsListForStudent(int studentId, Date year) throws AkuraAppException {
    
        List<Achievement> achievements;
        try {
            if (year != null) {
                achievements =
                        getHibernateTemplate().findByNamedQuery(GET_ACHIEVEMENTS_LIST_FOR_STUDENT, studentId, year);
            } else {
                achievements =
                        getHibernateTemplate().findByNamedQuery(GET_ACHIEVEMENT_LIST_BY_STUDENT_ID_ONLY, studentId);
            }
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return achievements;
    }
    
    /**
     * Retrieve all the available Achievement information for a student using given parameters.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @param strDescription -String
     * @param strActivity - String
     * @param year the student Date.
     * @return List of Achievement
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Achievement> findStudentAchievement(int studentId, String strDescription, String strActivity, Date year)
            throws AkuraAppException {
    
        try {
            
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_ACHIEVEMENT, studentId, strDescription,
                    strActivity, year);
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * Retrieve all the available Achievement information for a student using given parameters.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @param strDescription -String
     * @param clubSocietyId - int
     * @param year the student Date.
     * @return List of Achievement
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Achievement> findStudentClubAchievement(int studentId, String strDescription, int clubSocietyId,
            Date year) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQueryAndNamedParam(FIND_STUDENT_CLUB_ACHIEVEMENT,
                    new String[] { "studentId", "strDescription", "clubSocietyId", "year" },
                    new Object[] { studentId, strDescription, clubSocietyId, year });
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /** {@inheritDoc} */
    public List<String> viewAchievements(int studentKey, Date date, boolean acheivementStatus)
            throws AkuraAppException {
    
        try {
            
            return getHibernateTemplate().findByNamedQuery(VIEW_ACHIEVEMENTS, studentKey, date, acheivementStatus);
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
