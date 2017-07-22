/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.PropertyReader;

/**
 * This class provides persistence layer functionality for the GradeSubject object.
 * 
 * @author Virtusa Corporation
 */

public class GradeSubjectDaoImpl extends BaseDaoImpl<GradeSubject> implements GradeSubjectDao {
    

    /** The constant for String object "getGradeSubjectIdListByGrade". */
    private static final String GET_GRADE_SUBJECT_ID_LIST_BY_GRADE = "getGradeSubjectIdListByGrade";
    
    /** The constant for String object "getGradeCoreSubjectIdListByGrade". */
    private static final String GET_GRADE_CORE_SUBJECT_ID_LIST_BY_GRADE = "getGradeCoreSubjectIdListByGrade";
    
    /** The constant for String object "findGradeSubjectByDes". */
    private static final String FIND_GRADE_SUBJECT_BY_DES = "findGradeSubjectByDes";
    
    /** The constant for String object "findSubjectsByGrade" . */
    private static final String FIND_SUBJECTS_BY_GRADE = "findSubjectsByGrade";
    
    /** The constant for String object "GradeId". */
    private static final String GRADE_ID = "GradeId";
    
    /** The constant for String object "grade subject list". */
    private static final String GRADESUBJECTLIST = "gradesubjectlist";
    
    /** Query name for the maximum mark of the grade subject. */
    private static final String MAX_MARK_BY_ID = "findGradeSubjectMaxMarkById";
    
    /** . */
    private static final String FIND_GRADESUBJECT_BY_ID = "findGradeSubjectByGradeAndSubjectId";
    
    /** The Constant FIND_GRADE_SUBJECT_LIST_BY_CLASS_GRADE_ID. */
    private static final String FIND_GRADE_SUBJECT_LIST_BY_CLASS_GRADE_ID = "findGradeSubjectListByClassGradeId";
    
    /** Holds the AkuraErrorMsg property file name. */
    private static final String AKURA_ERROR_MSG_PROPERTY = "AkuraErrorMsg";
    
    /** Holds the key for error message. */
    private static final String REF_UI_GRADESUBJECT_DELETE_SUBJECTS = "REF.UI.GRADESUBJECT.DELETE.SUBJECTS";

    /**
     * Get a List of GradeSubjects of a particular grade.
     * 
     * @param gradeID gradeID to set.
     * @return List of GradeSubjectObjects.
     * @throws AkuraAppException The exception details that occurred when searching GradeSubject instance/s.
     */
    @SuppressWarnings("unchecked")
    public List<GradeSubject> getGradeSubjectList(int gradeID) throws AkuraAppException {

        return (List<GradeSubject>) getHibernateTemplate().findByNamedQueryAndNamedParam(GRADESUBJECTLIST,
                new String[] { GRADE_ID }, new Object[] { gradeID });
    }
    
    /**
     * Finds the subjects by the given description of a grade.
     * 
     * @param description - the description of the Grade.
     * @return - a list of Subjects of the given description of the Grade.
     * @throws AkuraAppException - The exception details that occurred when finding subjects for a grade
     *         instance.
     */
    @SuppressWarnings("unchecked")
    public List<GradeSubject> findSubjectsByGrade(String description) throws AkuraAppException {

        return (List<GradeSubject>) getHibernateTemplate().findByNamedQuery(FIND_SUBJECTS_BY_GRADE, description);
    }
    
    /**
     * Finds the GradeSubject object for the given description of a Grade and the description of a Subject.
     * 
     * @param gradeDes - the description of the Grade.
     * @param subjectDes - the description of the Subject.
     * @return - a list of GradeSubjects.
     * @throws AkuraAppException - The exception details that occurred when finding GradeSubject for given
     *         grade and subject instances.
     */
    @SuppressWarnings("unchecked")
    public List<GradeSubject> findGradeSubjectByDes(String gradeDes, String subjectDes) throws AkuraAppException {

        return getHibernateTemplate().findByNamedQuery(FIND_GRADE_SUBJECT_BY_DES, gradeDes, subjectDes);
    }
    
    /**
     * Gets a list of GradeSubjects for a given grade Id.
     * 
     * @param gradeId - the id of a Grade.
     * @return - a list of GradeSubjects.
     * @throws AkuraAppException - The exception details that occurred when finding GradeSubject for given
     *         grade instances.
     */
    @SuppressWarnings("unchecked")
    public List<GradeSubject> getGradeSubjectIdListByGrade(int gradeId) throws AkuraAppException {

        return getHibernateTemplate().findByNamedQuery(GET_GRADE_SUBJECT_ID_LIST_BY_GRADE, gradeId);
    }
    
    /**
     * Gets a list of GradeCoreSubjects for a given grade Id.
     * 
     * @param gradeId - the id of a Grade.
     * @return - a list of GradeSubjects.
     * @throws AkuraAppException - The exception details that occurred when finding GradeSubject for given
     *         grade instances.
     */
    @SuppressWarnings("unchecked")
    public List<GradeSubject> getGradeCoreSubjectIdListByGrade(int gradeId) throws AkuraAppException {

        return getHibernateTemplate().findByNamedQuery(GET_GRADE_CORE_SUBJECT_ID_LIST_BY_GRADE, gradeId, Boolean.FALSE);
    }
    
    /**
     * Delete grade subject list.
     * 
     * @param gradeSubjectList the grade subject list
     * @throws AkuraAppException the akura app exception
     */
    public void deleteGradeSubjectList(List<GradeSubject> gradeSubjectList) throws AkuraAppException {

        try {
            getHibernateTemplate().deleteAll(gradeSubjectList);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(PropertyReader.getPropertyValue(AKURA_ERROR_MSG_PROPERTY,
                    REF_UI_GRADESUBJECT_DELETE_SUBJECTS), ex);
        }
    }
    
    /**
     * Gets the grade subject list by class grade id.
     * 
     * @param classGradeId the class grade id
     * @return the grade subject list by class grade id
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    public List<GradeSubject> getGradeSubjectListByClassGradeId(int classGradeId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_GRADE_SUBJECT_LIST_BY_CLASS_GRADE_ID, classGradeId);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<GradeSubject> findSubjectsByGradeAndSubjectId(int gradeId, int subjectId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery("findSubjectsByGradeAndSubjectId", gradeId, subjectId);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<Integer> findGradeSubjectMaxMarkById(final int gradeSubjectId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(MAX_MARK_BY_ID, gradeSubjectId);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<GradeSubject> findGradeSubjectByGradeAndSubjectId(int subjectId, String gradeDescription)
            throws AkuraAppException {

        try {
            // return getHibernateTemplate().findByNamedQuery(FING_GRADESUBJECT_BY_ID , subjectId
            // ,gradeDescription);
            return getHibernateTemplate().findByNamedQuery(FIND_GRADESUBJECT_BY_ID, subjectId, gradeDescription);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
    }
}
