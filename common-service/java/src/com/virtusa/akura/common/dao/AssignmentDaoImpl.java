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

package com.virtusa.akura.common.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.Assignment;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * This class provides persistence layer functionality for the assignment object.
 * 
 * @author Virtusa Corporation
 */
public class AssignmentDaoImpl extends BaseDaoImpl<Assignment> implements AssignmentDao {
    
    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(AssignmentDaoImpl.class);
    
    /** The Constant FIND_ASSIGNMENTS_BY_GRADE. */
    private static final String FIND_ASSIGNMENTS_BY_GRADE = "findAssignmentsByGrade";
    
    /** The Constant ERROR_WHILE_RETIRVIENG_ASSIGNEMTNS. */
    private static final String ERROR_WHILE_RETIRVIENG_ASSIGNEMTNS = "Error while retirvieng assignemtns...";
    
    /**
     * Represents the query name for finding the existence of the given assignment for selected grade-subject.
     */
    private static final String EXIST_ASSIGNMENT_GRADE_SUBJECT = "existAssignmentGradeSubject";
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<Assignment> isExistAssignmentGradeSubject(final int gradeSubjectId, final String assignmentName,
            final int year, final boolean marksType) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(EXIST_ASSIGNMENT_GRADE_SUBJECT, gradeSubjectId,
                    assignmentName, year, marksType);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Gets the assignments by grade.
     * 
     * @param classGradeId the class grade id
     * @return the assignments by grade
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getAssignmentsByGrade(int classGradeId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_ASSIGNMENTS_BY_GRADE, classGradeId,
                    DateUtil.currentYearOnly());
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETIRVIENG_ASSIGNEMTNS + e.getMessage());
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
}
