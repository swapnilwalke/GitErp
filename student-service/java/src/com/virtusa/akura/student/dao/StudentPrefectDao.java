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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.StudentPrefect;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */

public interface StudentPrefectDao extends BaseDao<StudentPrefect> {
    
    /**
     * Retrieve all the available StudentPrefect information given by student Id.
     * 
     * @param year specifies the year holds the prefect details, defined by an integer type object
     * @param studentId specifies the student id, defined by an integer
     * @return List of StudentPrefect
     * @throws AkuraAppException SMS Exception
     */
    List<StudentPrefect> getStudentPrefectDetailsByStudentId(int studentId, int year) throws AkuraAppException;

    /**
     * for mobility
     * Returns a list of prefect type descriptions for the particular student key and the current year.
     * 
     * @param studentKey - the key of the student
     * @param currentYear - the current year
     * @return - a list of the prefect type description.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<String> getPrefectTypeByStudentId(final int studentKey, final Date currentYear)throws AkuraAppException;
}
