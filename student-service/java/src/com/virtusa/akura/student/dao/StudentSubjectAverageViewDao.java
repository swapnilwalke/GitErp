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
import com.virtusa.akura.api.dto.StudentSubjectAverageViewDTO;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the StudentSubjectAverageViewDTO object.
 *
 * @author Virtusa Corporation
 */
public interface StudentSubjectAverageViewDao extends BaseDao<StudentSubjectAverageViewDTO>{

    /**
     * Returns a list of StudentSubjectAverageViewDTO.
     *
     * @param studentId - id of the given student.
     * @param year - the current year.
     * @return a list of StudentSubjectAverageViewDTO.
     * @throws AkuraAppException - The exception details that occurred
     * when processing.
     */
    List<StudentSubjectAverageViewDTO> getStudentSubjectAverage(int studentId, int year)throws AkuraAppException;

}
