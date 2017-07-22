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

package com.virtusa.akura.common.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * This is the implementation class for ClassGradeDao interface. It handles all the persistent related
 * functionalities for the ClassGradeDao object.
 * 
 * @author Virtusa Corporation
 */
public class ClassGradeDaoImpl extends BaseDaoImpl<ClassGrade> implements ClassGradeDao {
    
    /** Constant for holding getClassesByClassId query name. */
    private static final String GET_CLASSES_BY_CLASS_ID = "getClassesByClassId";

    /** Constant for holding getClassesByGradeId query name. */
    private static final String GET_CLASSES_BY_GRADE_ID = "getClassesByGradeId";
    
    /**
     * String attribute for holding query name by grade id and class id.
     */
    private static final String QUERY_NAME_BY_GRADE_ID_AND_CLASS_ID = "getClassGradeByGradeIdAndClassId";
    
    /**
     * String attribute for holding query name by grade.
     */
    private static final String QUERY_NAME_BY_GRADE = "getClassesByGrade";
    
    /** The Constant GET_CLASS_GRADE_BY_NAME. */
    private static final String GET_CLASS_GRADE_BY_NAME = "getClassGradeByName";
    
    /** The Constant GET_CLASS_GRADE_BY_STAFF_REGISTRATION_NO. */
    private static final String GET_CLASS_GRADE_BY_STAFF_REGISTRATION_NO = "getClassGradeByStaffRegistrationNo";
    
    /** String attribute for finding the class teacher for a given class grade. */
    private static final String FIND_TEACHER_BY_CLASS_GRADE = "findTeacherByClass";
    
    /**
     * Retrieves the entire list of records for a given object.
     * 
     * @param grade - the object class.
     * @return List - The list of records for the given class.
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    public List<ClassGrade> findClassGradeListByGrade(Grade grade) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(QUERY_NAME_BY_GRADE, grade);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Delete ClassGrade By Grade.
     * 
     * @param classGrades list of classGrade
     * @throws AkuraAppException - throw this.
     */
    public void deleteClassGradeList(List<ClassGrade> classGrades) throws AkuraAppException {
    
        try {
            getHibernateTemplate().deleteAll(classGrades);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
    }
    
    /**
     * Retrieve the list of ClassGrade object for given grade id and class id.
     * 
     * @param gradeId - int type of grade Id for class grade object.
     * @param classId - int type of class Id for class grade object.
     * @return ClassGrade - The ClassGrade object for given grade id and class id.
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    public List<ClassGrade> findClassGradeByGradeIdAndClassId(int gradeId, int classId) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(QUERY_NAME_BY_GRADE_ID_AND_CLASS_ID, gradeId, classId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Gets the class grade by name.
     * 
     * @param description the description
     * @return the class grade by name
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    public List<ClassGrade> getClassGradeByName(String description) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_CLASS_GRADE_BY_NAME, description);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * Gets the class grade by staff registration no.
     * 
     * @param registrationNo the registration no
     * @return the class grade by staff registration no
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    public List<ClassGrade> getClassGradeByStaffRegistrationNo(String registrationNo) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_CLASS_GRADE_BY_STAFF_REGISTRATION_NO,
                    DateUtil.currentYear(), DateUtil.getStringYear(DateUtil.currentYear()), registrationNo);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ClassGrade> getClassGradeListByGradeId(int gradeId) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_CLASSES_BY_GRADE_ID, gradeId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /** {@inheritDoc} */
    public List<?> isExistStaff(int userId, String classGradeKey) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_TEACHER_BY_CLASS_GRADE, userId, classGradeKey);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ClassGrade> findClassGradeListByClassId(int classId) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(GET_CLASSES_BY_CLASS_ID, classId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
