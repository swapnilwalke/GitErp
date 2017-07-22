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
import com.virtusa.akura.api.dto.AssignmentMarkView;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;
/**
 * This class provides the persistence level implementation for the AssignmentMarkViewDao
 * domain object.
 * 
 * @author Virtusa Corporation
 */

public class AssignmentMarkViewDaoImpl extends BaseDaoImpl<AssignmentMarkView> implements AssignmentMarkViewDao {
    
    /** The Constant FIND_STUDENT_ASSIGNMENT_MARKS_BY_SUBJECT. */
    private static final String FIND_STUDENT_ASSIGNMENT_MARKS_BY_SUBJECT = "findStudentAssignmentMarksBySubject";
    
    /** The Constant FIND_RETRIEVING_ASSIGNMENT_MARKS. */
    private static final String FIND_STUDENT_ASSIGNMENT_MARKS = "findStudentAssignmentMarks";
    
    /**
     * Gets the student assignment marks by subject.
     * 
     * @param studentId the student id
     * @param year the year
     * @param subject the subject
     * @return the student sub term marks by subject
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @SuppressWarnings("unchecked")
    public List<AssignmentMarkView> getStudentAssignmentMarksBySubject(int studentId, Date year, String subject)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_ASSIGNMENT_MARKS_BY_SUBJECT, studentId, year,
                    subject);
            
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Gets student assignment marks list.
     * 
     * @param studentId the student Id
     * @param year the year
     * @return the student assignment mark list
     * @throws AkuraAppException when exceptions occurred
     */
    @SuppressWarnings("unchecked")
    public List<AssignmentMarkView> getStudentAssignmentMarks(int studentId, int year) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_ASSIGNMENT_MARKS, studentId,
                    DateUtil.getDate(year));
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }

    
    
}
