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

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StudentAnnualGradeRank;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * StudentAnnualGradeRankDaoImpl implementation of StudentAnnualGradeRankDao interface.
 * 
 * @author Virtusa Corporation
 */
public class StudentAnnualGradeRankDaoImpl extends BaseDaoImpl<StudentAnnualGradeRank> implements
        StudentAnnualGradeRankDao {
    
    /** Constant to holding findStudentAnnualGradeRankList query name. */
    private static final String FIND_STUDENT_ANNUAL_GRADE_RANK_LIST = "findStudentAnnualGradeRankList";
    
    /** Logger to log the exceptions. */
    private static final Logger LOG = Logger.getLogger(StudentDaoImpl.class);
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudentAnnualGradeRank> getStudentAnnualGradeRank(int gradeId, int year, int noOfPrizes)
            throws AkuraAppException {
    
        try {
            getHibernateTemplate().setMaxResults(noOfPrizes);
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_ANNUAL_GRADE_RANK_LIST, gradeId, year);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_WHILE_PROCESSING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
}
