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
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * This interface provides persistence layer functionality for the student class info domain object.
 * 
 * @author Virtusa Corporation
 */

public class StudentClassInfoDaoImpl extends BaseDaoImpl<StudentClassInfo> implements StudentClassInfoDao {
    

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentClassInfoDaoImpl.class);
    
    /** key to hold the named query "nonCurrentClassStudentList". */
    private static final String NON_CURRENT_CLASS_STUDENT_LIST = "nonCurrentClassStudentList";
    
    /**
     * Represents the named query for the present students for the relevant class and the first date at
     * school.
     */
    private static final String PRESENT_CLASS_STUDENT_FOR_DATE = "presentClassStudentForDate";
    
    /** key to hold the named query getStudentClassInfoOfClassByYear. */
    private static final String GET_STUDENT_CLASS_INFO_OF_CLASS_BY_YEAR = "getStudentClassInfoOfClassByYear";
    
    /** key to hold the named query getStudentClassInfoOfStudentClassByYear. */
    private static final String GET_STUDENT_CLASS_INFO_OF_STUDENT_CLASS_BY_YEAR =
            "getStudentClassInfoOfStudentClassByYear";
    
    /** key to hold the detailed error while retrieving student class information. */
    private static final String ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO =
            "Error while retrieving student class info ---> ";
    
    /** key to hold the string classGradeIds. */
    private static final String CLASS_GRADE_IDS = "classGradeIds";
    
    /** key to hold the named query getStudentClassInfoByClassGradeId. */
    private static final String GET_STUDENT_CLASS_INFO_BY_CLASS_GRADE_ID = "getStudentClassInfoByClassGradeId";
    
    /** key to hold the named query getStudentClassInfoByStudentId. */
    private static final String GET_STUDENT_CLASS_INFO_BY_STUDENT_ID2 = "getStudentClassInfoByStudentId2";
    
    /** key to hold the named query getStudentClassInfoByStudentId. */
    private static final String GET_STUDENT_CLASS_INFO_BY_STUDENT_ID = "getStudentClassInfoByStudentId";
    
    /** key to hold the named query studentSearchByGradeYear. */
    private static final String STUDENT_SEARCH_BY_GRADE_YEAR = "studentSearchByGradeYear";
    
    /** key to hold the named query classStudentlist. */
    private static final String CLASSSTUDENTLIST = "classstudentlist";
    
    /** key to hold the named query StudentClasslist. */
    private static final String STUDENTCLASSLIST = "StudentIdByClasslist";
    
    /** key to hold the named query classStudentlist. */
    private static final String PRESENTCLASSSTUDENTLIST = "presentclassstudentlist";
    
    /** Represents the query name for the student class info key list by the year. */
    private static final String GET_STUDENT_CLASS_INFO_ID_BY_YEAR = "getStudentClassInfoIdByYear";
    
    /** Represents the query name for retrieving student class info list of suspended students . */
    private static final String SUSPENDED_CLASS_STUDENT_LIST = "suspendedClassStudentList";
    
    /** Represents the query name for retrieving student class info list of temporary leave students . */
    private static final String TEMPORARY_LEAVED_CLASS_STUDENT_LIST = "temporaryLeavedClassStudentList";
    
    /** key to hold the string "-01-01". */
    private static final String FIRST_DAY_STRING = "-01-01";

    /**
     * Get a List of StudentClassInfo s of a particular gradeClass and a year.
     * 
     * @param classGradeId ClassgradeID to set.
     * @param selectedyear Year to set.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    @SuppressWarnings("unchecked")
    public List<StudentClassInfo> getClassStudentList(int classGradeId, int selectedyear) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(CLASSSTUDENTLIST, classGradeId, selectedyear);
        } catch (DataAccessException e) {
            
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * Get a List of StudentClassInfo s of a particular gradeClass and student.
     * 
     * @param classGradeId ClassgradeID to set.
     * @param studentId - studentId.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    @SuppressWarnings("unchecked")
    public List<StudentClassInfo> getStudentClassList(int studentId, int classGradeId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(STUDENTCLASSLIST, studentId, classGradeId);
        } catch (DataAccessException e) {
            
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<StudentClassInfo> getPresentClassStudentList(int classGradeId, int selectedyear, Date date)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(PRESENT_CLASS_STUDENT_FOR_DATE, classGradeId, selectedyear,
                    date);
        } catch (DataAccessException e) {
            
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * Get a List of StudentClassInfo s of a particular grade and a year.
     * 
     * @param gradeId GradeID to set.
     * @param selectedyear Year to set.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    @SuppressWarnings("unchecked")
    public List<StudentClassInfo> getStudentSearchByGradeYear(int gradeId, int selectedyear) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(STUDENT_SEARCH_BY_GRADE_YEAR, gradeId, selectedyear);
            
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Get a List of StudentClassInfo s of a particular grade and a year.
     * 
     * @param studentId Student Id to set.
     * @param year year of the class.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    @SuppressWarnings("unchecked")
    public List<StudentClassInfo> getStudentClassInfoByStudentId(int studentId, int year) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_STUDENT_CLASS_INFO_BY_STUDENT_ID, studentId, year);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Get a List of StudentClassInfo s of classGradeIds.
     * 
     * @param classGradeIds - classGradeIds to set.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    @SuppressWarnings("unchecked")
    public List<StudentClassInfo> findStudentClassInfoByClassGradeId(List<Integer> classGradeIds)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQueryAndNamedParam(GET_STUDENT_CLASS_INFO_BY_CLASS_GRADE_ID,
                    new String[] { CLASS_GRADE_IDS }, new Object[] { classGradeIds });
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Get a List of StudentClassInfo objects for given classGradeId and year.
     * 
     * @param classGradeId - integer
     * @param year - Date
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    @SuppressWarnings("unchecked")
    public List<StudentClassInfo> getStudentClassInfoOfClassByYear(int classGradeId, Date year)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_STUDENT_CLASS_INFO_OF_CLASS_BY_YEAR, classGradeId, year);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Get a StudentClassInfo objects for given classGradeId,student id, and year.
     * 
     * @param classGradeId - integer
     * @param year - Date
     * @param studentId - studentId.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    @SuppressWarnings("unchecked")
    public List<StudentClassInfo> getStudentClassInfoOfStudentClassByYear(int studentId, int classGradeId, Date year)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_STUDENT_CLASS_INFO_OF_STUDENT_CLASS_BY_YEAR, studentId,
                    classGradeId, year);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Get a List of StudentClassInfo s of a particular student.
     * 
     * @param studentId Student Id to set.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    @SuppressWarnings("unchecked")
    public List<StudentClassInfo> getStudentClassInfoByStudentId2(int studentId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_STUDENT_CLASS_INFO_BY_STUDENT_ID2, studentId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<StudentClassInfo> getNonCurrentClassInfoList(int classGradeId, int year, Date date)
            throws AkuraAppException {

        try {
            
            return getHibernateTemplate().findByNamedQuery(NON_CURRENT_CLASS_STUDENT_LIST, classGradeId, year, date);
            
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<Integer> getStudentClassInfoIdByYear(final int classGradeId, final Date currentYear)
            throws AkuraAppException {

        try {
            return getHibernateTemplate()
                    .findByNamedQuery(GET_STUDENT_CLASS_INFO_ID_BY_YEAR, classGradeId, currentYear);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<StudentClassInfo> getPresentClassStudentList(int classGradeId, int selectedyear)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(PRESENTCLASSSTUDENTLIST, classGradeId, selectedyear);
        } catch (DataAccessException e) {
            
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<StudentClassInfo> getSuspendedLeaveStudents(int classGradeId, int year, Date currentDateOne)
            throws AkuraAppException {

        try {
            
            return getHibernateTemplate().findByNamedQuery(SUSPENDED_CLASS_STUDENT_LIST, classGradeId,
                    year, DateUtil.getParseDate(DateUtil.getFormatDate(currentDateOne)));
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<StudentClassInfo> getTemporaryLeavedClassStudentList(int classGradeId, int year, Date dateOfInterest)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(TEMPORARY_LEAVED_CLASS_STUDENT_LIST, classGradeId,
                    year, DateUtil.getParseDate(DateUtil.getFormatDate(dateOfInterest)));
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_CLASS_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
}
