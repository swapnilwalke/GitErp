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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.SubjectAverageTermMarks;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */
public interface SubjectAverageTermMarksDao extends BaseDao<SubjectAverageTermMarks> {
    
    /**
     * Get SubjectAverageTermMarks object by class description, year, grade subject id, term.
     * 
     * @param classDescription class description
     * @param year year
     * @param gradeSubjectId grade subject id
     * @param term term
     * @return SubjectAverageTermMarks object
     * @throws AkuraAppException when exception occurs
     */
    SubjectAverageTermMarks getSubjectAverageTermMarksById(String classDescription, int year, int gradeSubjectId,
            String term) throws AkuraAppException;
}
