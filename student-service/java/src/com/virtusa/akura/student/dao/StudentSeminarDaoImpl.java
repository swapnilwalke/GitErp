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
import com.virtusa.akura.api.dto.StudentSeminar;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * @author Virtusa Corporation
 */
public class StudentSeminarDaoImpl extends BaseDaoImpl<StudentSeminar> implements StudentSeminarDao {
    
    /** query name for get student related seminar description and description for given year and student id. */
    private static final String GET_STUDENT_SEMINAR_BY_STUDENT = "getStudentSeminarByStudent";
    
    /** query name for get student related seminars for given year and student id. */
    private static final String GET_STUDENT_SEMINAR_BY_YEAR = "getStudentSeminarByYear";
    
    /**
     * Gets the student seminar detail for given student and the year.
     * 
     * @param studentId -student Id in the studentSeminar
     * @param dateSelectedYear -student Id in the studentSeminar
     * @return List &gtStudentSeminar> in studentSeminar
     * @throws AkuraAppException exceptions when processing
     */
    @SuppressWarnings("unchecked")
    public List<StudentSeminar> getAllStudentSeminars(int studentId, Date dateSelectedYear) throws AkuraAppException {
    
        try {
            
            return (List<StudentSeminar>) getHibernateTemplate().findByNamedQuery(GET_STUDENT_SEMINAR_BY_YEAR,
                    studentId, dateSelectedYear);
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
     /** {@inheritDoc} */
    public List<List<String>> viewStudentSeminars(int studentId, Date dateSelectedYear) throws AkuraAppException {
    
        try {            
            return getHibernateTemplate().findByNamedQuery(GET_STUDENT_SEMINAR_BY_STUDENT,
                    studentId, dateSelectedYear);
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
}
