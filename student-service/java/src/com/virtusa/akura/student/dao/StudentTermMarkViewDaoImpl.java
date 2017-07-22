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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * Class which provides implementation to StudentTermMarks view.
 * 
 * @author Virtusa Corporation
 */
public class StudentTermMarkViewDaoImpl extends BaseDaoImpl<StudentTermMarkDTO> implements StudentTermMarkViewDao {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentTermMarkViewDaoImpl.class);

    
    /** Constant to represent named query findStudentTermMarksByStudentIdYearClassGrade. */
    private static final String FIND_STUDENT_TERM_MARKS_BY_STUDENT_ID_YEAR_CLASS_GRADE =
            "findStudentTermMarksByStudentIdYearClassGrade";
    
    /** The Constant FIND_STUDENT_TERM_MARKS_BY_STUDENT_ID_YEAR_SUBJECT. */
    private static final String FIND_STUDENT_TERM_MARKS_BY_STUDENT_ID_YEAR_SUBJECT =
            "findStudentTermMarksByStudentIdYearSubject";
    
    /** Constant to represent named query findStudentTermMarksByClassInfoIDAndClassgrade. */
    private static final String FIND_STUDENT_TERM_MARKS_BY_CLASS_INFO_ID_AND_CLASSGRADE =
            "findStudentTermMarksByClassInfoIDAndClassgrade";
    
    /** Represents the named query. */
    private static final String FIND_STUDENT_TERM_MARKS_BY_CLASS_GRADE_AND_TERM =
            "findStudentTermMarksByClassGradeAndTerm";
    
    /** Represents the named query. */
    private static final String FIND_AVERAGE_TERM_MARKS = "findAverageTermMarks";
    
    /** The Constant FIND_STUDENT_ALL_TERM_MARKS. */
    private static final String FIND_STUDENT_ALL_TERM_MARKS = "findStudentAllTermMarks";
    
    /** The term mark query string. */
    private static final String FIND_STUDENT_TERM_MARKS = "findStudentTermMarks";
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<StudentTermMarkDTO> getTermMarksForStudent(int studentId, int year) throws AkuraAppException {
    
        List<StudentTermMarkDTO> list;
        try {
            if (year > 0) {
                list = getHibernateTemplate().findByNamedQuery(FIND_STUDENT_TERM_MARKS, studentId, year);
            } else {
                list = getHibernateTemplate().findByNamedQuery(FIND_STUDENT_ALL_TERM_MARKS, studentId);
            }
            
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return list;
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudentTermMarkDTO> getTermMarksByTermGradeYear(int classGradeId, int termid, int year)
            throws AkuraAppException {
    
        List<StudentTermMarkDTO> list = new ArrayList<StudentTermMarkDTO>();
        try {
            list =
                    getHibernateTemplate().findByNamedQuery(FIND_STUDENT_TERM_MARKS_BY_CLASS_GRADE_AND_TERM, termid,
                            classGradeId, year);
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return list;
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object> populateStudentAverageTerm(int convertedDate, int studentId) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_AVERAGE_TERM_MARKS, studentId,
                    DateUtil.getDate(convertedDate));
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudentTermMarkDTO> getTermMarksByClassInfoIdAndGradeSubject(int classInfoId, int gradeSubjectId)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_TERM_MARKS_BY_CLASS_INFO_ID_AND_CLASSGRADE,
                    gradeSubjectId, classInfoId);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
    /**
     * Gets the student term marks by student id year subject.
     * 
     * @param studentId the student id
     * @param year the year
     * @param gradeSubjectId the grade subject id list
     * @return the student term marks by student id year subject
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    public List<StudentTermMarkDTO> getStudentTermMarksByStudentIdYearSubject(int studentId, int year,
            int gradeSubjectId) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_TERM_MARKS_BY_STUDENT_ID_YEAR_SUBJECT,
                    studentId, DateUtil.getDate(year), gradeSubjectId);
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<StudentTermMarkDTO> findStudentTermMarksByStudentIdYearClassGrade(int studentId, int year,
            int classGradeId) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_TERM_MARKS_BY_STUDENT_ID_YEAR_CLASS_GRADE,
                    studentId, DateUtil.getDate(year), classGradeId);
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

}
