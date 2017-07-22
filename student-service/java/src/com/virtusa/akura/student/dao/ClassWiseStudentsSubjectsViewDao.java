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
import com.virtusa.akura.api.dto.ClassWiseStudentsSubjectsDTO;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * The Interface ClassWiseStudentsSubjectsViewDao.
 * 
 * @author Virtusa Corporation.
 */
public interface ClassWiseStudentsSubjectsViewDao extends BaseDao<ClassWiseStudentsSubjectsDTO> {
    
    /**
     * Gets the class wise student list by subject.
     * 
     * @param classGradeId the class grade id
     * @param gradeSubjectId the grade subject id
     * @param year the year
     * @return the class wise student list by subject
     * @throws AkuraAppException the akura app exception
     */
    List<ClassWiseStudentsSubjectsDTO> getClassWiseStudentListBySubject(int classGradeId, int gradeSubjectId, Date year)
            throws AkuraAppException;
}
