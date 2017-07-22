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
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.dto.StudentTermMarkPerformance;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * Class which provides implementation to StudentTermMarkPerformanceDao.
 */
public class StudentTermMarkPerformanceDaoImpl extends BaseDaoImpl<StudentTermMark> implements
        StudentTermMarkPerformanceDao {

    /** holding string of findStudentTermMarksByClassGrade. */
    private static final String FIND_STUDENT_TERM_MARKS_BY_CLASS_GRADE = "findStudentTermMarksByClassGrade";

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudentTermMarkPerformance> getTermMarksByGradeYear(String grade, int term, int year, float less,
            float grater) throws AkuraAppException {

        List<StudentTermMarkPerformance> list = new ArrayList<StudentTermMarkPerformance>();
        try {
            list =
                    getHibernateTemplate().findByNamedQuery(FIND_STUDENT_TERM_MARKS_BY_CLASS_GRADE, grade, term, year,
                            less, grater, Boolean.FALSE);
        } catch (DataAccessException e) {

            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        return list;
    }

}
