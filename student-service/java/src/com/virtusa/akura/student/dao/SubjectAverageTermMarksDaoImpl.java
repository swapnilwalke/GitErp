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

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.SubjectAverageTermMarks;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * @author Virtusa Corporation
 */
public class SubjectAverageTermMarksDaoImpl extends BaseDaoImpl<SubjectAverageTermMarks> implements
        SubjectAverageTermMarksDao {
    
    /** Constant for getSubjectAverageTermMArksById. */
    private static final String GET_SUBJECT_AVERAGE_TERM_M_ARKS_BY_ID = "getSubjectAverageTermMArksById";
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public SubjectAverageTermMarks getSubjectAverageTermMarksById(String classDescription, int year,
            int gradeSubjectId, String term) throws AkuraAppException {
    
        try {
            SubjectAverageTermMarks subjectAverageTermMarks = null;
            
            List<SubjectAverageTermMarks> list =
                    getHibernateTemplate().findByNamedQuery(GET_SUBJECT_AVERAGE_TERM_M_ARKS_BY_ID, classDescription,
                            year, gradeSubjectId, term);
            if (list.size() > 0) {
                subjectAverageTermMarks = list.get(0);
            } else {
                subjectAverageTermMarks = new SubjectAverageTermMarks();
                subjectAverageTermMarks.setAverage(0);
            }
            return subjectAverageTermMarks;
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
