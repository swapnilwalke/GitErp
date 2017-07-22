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
import com.virtusa.akura.api.dto.StudentSubTermMarkDTO;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * @author Virtusa Corporation
 */
public class StudentSubTermMarkViewDaoImpl extends BaseDaoImpl<StudentSubTermMarkDTO> implements
        StudentSubTermMarkViewDao {

    /** The Constant FIND_STUDENT_SUB_TERM_MARKS_BY_SUBJECT. */
    private static final String FIND_STUDENT_SUB_TERM_MARKS_BY_SUBJECT = "findStudentSubTermMarksBySubject";

    /** The Constant FIND_STUDENT_ALL_SUB_TERM_MARKS. */
    private static final String FIND_STUDENT_ALL_SUB_TERM_MARKS = "findStudentAllSubTermMarks";

    /** The Constant FIND_STUDENT_SUB_TERM_MARKS. */
    private static final String FIND_STUDENT_SUB_TERM_MARKS = "findStudentSubTermMarks";

    /**
     * Gets the student sub term marks.
     *
     * @param studentId the student id
     * @param year the year
     * @return the student sub term marks
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    public List<StudentSubTermMarkDTO> getStudentSubTermMarks(int studentId, int year) throws AkuraAppException {

        List<StudentSubTermMarkDTO> list;
        try {
            if (year > 0) {
                list = getHibernateTemplate().findByNamedQuery(FIND_STUDENT_SUB_TERM_MARKS, studentId, year);
            } else {
                list = getHibernateTemplate().findByNamedQuery(FIND_STUDENT_ALL_SUB_TERM_MARKS, studentId);
            }

        } catch (DataAccessException e) {

            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return list;
    }

    public List<StudentSubTermMarkDTO> getSubTermMarksForStudent(int studentId, int year) throws AkuraAppException {

        try {
               return getHibernateTemplate().
                 findByNamedQuery(FIND_STUDENT_SUB_TERM_MARKS, studentId, DateUtil.getDate(year));

        } catch (DataAccessException e) {

            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /**
     * Gets the student sub term marks by subject.
     *
     * @param studentId the student id
     * @param year the year
     * @param subject the subject
     * @return the student sub term marks by subject
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    public List<StudentSubTermMarkDTO> getStudentSubTermMarksBySubject(int studentId, Date year, String subject)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_SUB_TERM_MARKS_BY_SUBJECT, studentId, year,
                    subject);

        } catch (DataAccessException e) {

            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

}
