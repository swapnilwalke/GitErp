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
import com.virtusa.akura.api.dto.StudentSeminar;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */
public interface StudentSeminarDao extends BaseDao<StudentSeminar> {
    
    /**
     * Gets the student seminar detail for given student and the year.
     * 
     * @param studentId -student Id in the studentSeminar
     * @param dateSelectedYear -student Id in the studentSeminar
     * @return List &gtStudentSeminar> in studentSeminar
     * @throws AkuraAppException exceptions when processing
     */
    List<StudentSeminar> getAllStudentSeminars(int studentId, Date dateSelectedYear) throws AkuraAppException;

    /**
     * Returns the list of student seminar descriptions for a particular student and the current year.
     * 
     * @param studentKey - the key of the student
     * @param date - current date
     * @return - the list of student seminar descriptions
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<List<String>> viewStudentSeminars(final int studentKey, final Date date)throws AkuraAppException;    
}
