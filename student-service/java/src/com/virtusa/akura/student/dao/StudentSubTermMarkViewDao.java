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
import com.virtusa.akura.api.dto.StudentSubTermMarkDTO;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */
public interface StudentSubTermMarkViewDao extends BaseDao<StudentSubTermMarkDTO> {

    /**
     * Gets the student sub term marks.
     *
     * @param studentId the student id
     * @param year the year
     * @return the student sub term marks
     * @throws AkuraAppException the akura app exception
     */
    List<StudentSubTermMarkDTO> getStudentSubTermMarks(int studentId, int year) throws AkuraAppException;

    /**
     * Gets the student sub term marks by subject.
     *
     * @param studentId the student id
     * @param year the year
     * @param subject the subject
     * @return the student sub term marks by subject
     * @throws AkuraAppException the akura app exception
     */
    List<StudentSubTermMarkDTO> getStudentSubTermMarksBySubject(int studentId, Date year, String subject)
            throws AkuraAppException;

    List<StudentSubTermMarkDTO> getSubTermMarksForStudent(int studentId, int year)throws AkuraAppException;
}
