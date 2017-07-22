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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.dto.StudentTermMarkPerformance;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */
public interface StudentTermMarkPerformanceDao extends BaseDao<StudentTermMark> {

    /**
     * Get student term marks Results by class grade, year, marks.
     *
     * @param grade - grade of grade description
     * @param term - term of term description
     * @param year - year of current
     * @param less - less marks
     * @param grater - grater marks
     * @return list of student term marks results objects
     * @throws AkuraAppException - AkuraAppException
     */
    List<StudentTermMarkPerformance> getTermMarksByGradeYear(String grade, int term, int year, float less, float grater)
            throws AkuraAppException;

}
