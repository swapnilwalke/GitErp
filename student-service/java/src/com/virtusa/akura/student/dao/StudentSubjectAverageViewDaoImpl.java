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
import com.virtusa.akura.api.dto.StudentSubjectAverageViewDTO;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * This class provides persistence layer functionality for the StudentSubjectAverageViewDTO object.
 *
 * @author Virtusa Corporation
 */
public class StudentSubjectAverageViewDaoImpl extends BaseDaoImpl<StudentSubjectAverageViewDTO>
implements StudentSubjectAverageViewDao{

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentSubjectAverageViewDaoImpl.class);

    /** Represents the query name. */
    private static final String FIND_SUBJECT_MARKS_FOR_TERM = "studentSubjectTermMarks";

    /** {@inheritDoc} */
    public List<StudentSubjectAverageViewDTO> getStudentSubjectAverage(int studentId, int year)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_SUBJECT_MARKS_FOR_TERM, studentId ,
                    DateUtil.getDate(year));
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }

}
