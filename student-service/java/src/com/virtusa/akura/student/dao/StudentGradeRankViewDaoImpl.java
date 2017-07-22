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

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StudentsGradeRankView;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * @author Virtusa Corporation
 */
public class StudentGradeRankViewDaoImpl extends BaseDaoImpl<StudentsGradeRankView> implements StudentGradeRankViewDao {
    
    /** Constant for represent named query findStudentTermMarksGradeRankbyStudentClassInfoId. */
    private static final String FIND_STUDENT_TERM_MARKS_GRADE_RANKBY_STUDENT_CLASS_INFO_ID =
            "findStudentTermMarksGradeRankbyStudentClassInfoId";
    
      /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudentsGradeRankView> getTermWiseStudentTotalMarks(int studentClassInfoId)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_STUDENT_TERM_MARKS_GRADE_RANKBY_STUDENT_CLASS_INFO_ID,
                    studentClassInfoId);
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
}
