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

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StudentAssignmentMark;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * The Class StudentAssignmentMarkDaoImpl.
 */
public class StudentAssignmentMarkDaoImpl extends BaseDaoImpl<StudentAssignmentMark> implements
        StudentAssignmentMarkDao {

    /** The Constant FIND_STUDENTS_CLASS_INFO_IDS_NOT_IN_ASSIGNMENT_MARKS. */
    private static final String FIND_STUDENTS_CLASS_INFO_IDS_NOT_IN_ASSIGNMENT_MARKS =
            "findStudentsClassInfoIdsNotInAssignmentMarks";

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(StudentAssignmentMarkDaoImpl.class);

    /** The Constant ERROR_WHILE_RETRIEVING_ASSIGNMENT_MARKS. */
    private static final String ERROR_WHILE_RETRIEVING_ASSIGNMENT_MARKS = "Error while retrieving assignment marks...";

    /** The Constant FIND_STUDENT_ASSIGNMENT_MARKS. */
    private static final String FIND_STUDENT_ASSIGNMENT_MARKS = "findStudentsAssignmentMarks";

    /** The Constant EXIST_ASSIGNMENT_MARKS. */
    private static final String EXIST_ASSIGNMENT_MARKS = "isExistsStudentsAssignmentMarks";

    /** The Constant FIND_STUDENTS_ASSIGNMENT_MARKS_BY_STUDENT_ID. */
    private static final String FIND_STUDENTS_ASSIGNMENT_MARKS_BY_STUDENT_ID = "findStudentsAssignmentMarksByStudentId";

    /** The Constant FIND_STUDENTS_ASSIGNMENT_MARKS_BY_STUDENT_ID_FOR_REPORT. */
    private static final String FIND_STUDENTS_ASSIGNMENT_MARKS_BY_STUDENT_ID_FOR_REPORT =
            "findStudentsAssignmentMarksByStudentIdForReport";

    /**
     * Gets the student assignment marks list.
     *
     * @param gradeSubjectId the grade subject id
     * @param assignmentId the assignment id
     * @param year the year
     * @param classGradeId the class grade id
     * @return the student assignment marks list
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getStudentAssignmentMarksList(int gradeSubjectId, int assignmentId, int year,
            int classGradeId)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_ASSIGNMENT_MARKS, assignmentId, gradeSubjectId,
                    DateUtil.getDate(year), classGradeId);

        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_ASSIGNMENT_MARKS + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }

    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<StudentAssignmentMark> isExistAssignmentMarks(final int assignmentId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(EXIST_ASSIGNMENT_MARKS, assignmentId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /**
     * Gets the student assignment marks by student id.
     *
     * @param studentId the student id
     * @return the student assignment marks by student id
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getStudentAssignmentMarksByStudentId(int studentId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENTS_ASSIGNMENT_MARKS_BY_STUDENT_ID, studentId);

        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_ASSIGNMENT_MARKS + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<Object[]> getStudentAssignmentMarksByStudentIdForReport(int studentId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENTS_ASSIGNMENT_MARKS_BY_STUDENT_ID_FOR_REPORT,
                    studentId);

        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_ASSIGNMENT_MARKS + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<Integer> getStudentClassInfoIdsNotInAssignmentMarks(int year) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENTS_CLASS_INFO_IDS_NOT_IN_ASSIGNMENT_MARKS,
                    DateUtil.getDate(year));

        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_ASSIGNMENT_MARKS + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
