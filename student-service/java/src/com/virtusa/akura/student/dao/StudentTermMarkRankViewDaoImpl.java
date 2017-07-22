/*
 * < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
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
import com.virtusa.akura.api.dto.StudentTermMarksRank;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the student discipline domain object.
 * 
 * @author Virtusa Corporation
 */
public class StudentTermMarkRankViewDaoImpl extends BaseDaoImpl<StudentTermMarksRank> implements
        StudentTermMarkRankViewDao {
    
    /** The Constant FIND_CLASS_AVERAGE_AND_CLASS_STUDENTS_COUNT. */
    private static final String FIND_CLASS_AVERAGE_AND_CLASS_STUDENTS_COUNT = "findClassAverageAndClassStudentsCount";
    
    /** The Constant FIND_BEST_STUDENT_AVERAGE. */
    private static final String FIND_BEST_STUDENT_AVERAGE = "findBestStudentAverage";
    
    /** key to hold the named query findStudentTermMarksRank. */
    private static final String FIND_STUDENT_TERM_MARKS_RANK = "findStudentTermMarksRank";
    
    /** Represents the name for the query. */
    private static final String FIND_STUDENT_MARKS_RANK = "findStudentRank";
    
    /**
     * {@inheritDoc}
     */
    @Override
    public StudentTermMarksRank getStudentTermMarksRank(int studentClassInfo, int termId) throws AkuraAppException {

        StudentTermMarksRank marksRank = null;
        try {
            List list = getHibernateTemplate().findByNamedQuery(FIND_STUDENT_TERM_MARKS_RANK, studentClassInfo, termId);
            if (list.size() > 0) {
                marksRank = (StudentTermMarksRank) list.get(0);
            }
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return marksRank;
    }
    
    /**
     * {@inheritDoc}
     */
    public List<StudentTermMarksRank> getStudentRank(int studentClassInfoId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_MARKS_RANK, studentClassInfoId);
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Gets the best student average.
     * 
     * @param classGradeId the class grade id
     * @param term the term
     * @param year the year
     * @return the best student average
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    public Double getBestStudentAverage(int classGradeId, String term, Date year) throws AkuraAppException {

        Double bestStudentAverage = null;
        try {
            List<Double> list =
                    getHibernateTemplate().findByNamedQuery(FIND_BEST_STUDENT_AVERAGE, classGradeId, term, year);
            if (list.size() > 0) {
                bestStudentAverage = list.get(0);
            }
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return bestStudentAverage;
    }
    
    /**
     * Gets the class average.
     * 
     * @param classGradeId the class grade id
     * @param term the term
     * @param year the year
     * @return the class average
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    public Object[] getClassAverage(int classGradeId, String term, Date year) throws AkuraAppException {

        Object[] classAverage = null;
        try {
            List<Object[]> list =
                    getHibernateTemplate().findByNamedQuery(FIND_CLASS_AVERAGE_AND_CLASS_STUDENTS_COUNT, classGradeId,
                            term, year);
            if (list.size() > 0) {
                classAverage = list.get(0);
            }
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return classAverage;
    }
}
