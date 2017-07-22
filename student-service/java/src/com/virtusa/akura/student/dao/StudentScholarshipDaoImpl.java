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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StudentScholarship;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * @author Virtusa Corporation
 */

public class StudentScholarshipDaoImpl extends BaseDaoImpl<StudentScholarship> implements StudentScholarshipDao {
    
    /** The Constant GET_STUDENT_SCHOLARSHIP_DETAILS_BY_STUDENT_ID_ONLY. */
    private static final String GET_STUDENT_SCHOLARSHIP_DETAILS_BY_STUDENT_ID_ONLY =
            "getStudentScholarshipDetailsByStudentIdOnly";
    
    /** key to hold detailed error while retrieving student scholarship information. */
    private static final String ERROR_WHILE_RETRIEVING_STUDENT_SCHOLARSHIP_INFO =
            "Error while retrieving student scholarship info ---> ";
    
    /** key to hold the named query viewStudentScholarshipDetailsByStudentId. */
    private static final String VIEW_STUDENT_SCHOLARSHIP_DETAILS_BY_STUDENT_ID =
            "viewStudentScholarshipDetailsByStudentId";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentScholarshipDaoImpl.class);
    
    /** Represents the query name for the scholarship details. */
    private static final String FIND_SCHOLARSHIP_NAME_BY_STUDENT_KEY = "findScholarshipNameByStudentKey";
    
    /**
     * Retrieve all the available StudentScholarship information given by student Id.
     * 
     * @param year specifies the year holds the prefect details, defined by an integer type object
     * @param studentId specifies the student id, defined by an integer
     * @return List of StudentScholarship
     * @throws AkuraAppException SMS Exception
     */
    @SuppressWarnings("unchecked")
    public List<StudentScholarship> getStudentScholarshipDetailsByStudentId(int studentId, int year)
            throws AkuraAppException {

        List<StudentScholarship> studentScholList = null;
        try {
            if (year > 0) {
                studentScholList =
                        getHibernateTemplate().findByNamedQuery(VIEW_STUDENT_SCHOLARSHIP_DETAILS_BY_STUDENT_ID,
                                studentId, year);
            } else {
                studentScholList =
                        getHibernateTemplate().findByNamedQuery(GET_STUDENT_SCHOLARSHIP_DETAILS_BY_STUDENT_ID_ONLY,
                                studentId);
            }
        } catch (DataAccessException e) {
            
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_SCHOLARSHIP_INFO + e.getMessage());
			throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return studentScholList;
    }

    /** {@inheritDoc} */
    public List<String> getScholarshipNameByStudentId(int studentKey, Date date) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_SCHOLARSHIP_NAME_BY_STUDENT_KEY, studentKey, date);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
