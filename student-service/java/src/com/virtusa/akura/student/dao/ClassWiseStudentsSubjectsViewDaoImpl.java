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

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.ClassWiseStudentsSubjectsDTO;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * The Class ClassWiseStudentsSubjectsViewDaoImpl.
 * 
 * @author Virtusa Corporation.
 */
public class ClassWiseStudentsSubjectsViewDaoImpl extends BaseDaoImpl<ClassWiseStudentsSubjectsDTO> implements
        ClassWiseStudentsSubjectsViewDao {
    
    /** The Constant FIND_CLASS_WISE_STUDENTS_BY_SUBJECT. */
    private static final String FIND_CLASS_WISE_STUDENTS_BY_SUBJECT = "findClassWiseStudentsBySubject";
    
    /**
     * Gets the class wise student list by subject.
     * 
     * @param classGradeId the class grade id
     * @param gradeSubjectId the grade subject id
     * @param year the year
     * @return the class wise student list by subject
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    public List<ClassWiseStudentsSubjectsDTO> getClassWiseStudentListBySubject(int classGradeId, int gradeSubjectId,
            Date year) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_CLASS_WISE_STUDENTS_BY_SUBJECT, classGradeId,
                    gradeSubjectId, year);
            
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
