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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.exception.AkuraAppException;


/**
 * This interface provides persistence layer functionality for the
 * GradeSubject object.
 *
 * @author Virtusa Corporation
 *
 */

public interface GradeSubjectDao extends BaseDao<GradeSubject> {

    /**
     * Get the list of Grade Subjects of a grade ID.
     *
     * @param gradeID Grade ID.
     * @return List of GradeSubject objects.
     * @throws AkuraAppException - The exception details that occurred when
     * getting a GradeSubject instance/s with a grade id of "gradeId".
     */
    List<GradeSubject> getGradeSubjectList(int gradeID)throws AkuraAppException;

    /**
     * Finds the subjects by the given description of a grade.
     *
     * @param description - the description of the Grade.
     * @return - a list of Subjects of the given description of the Grade.
     * @throws AkuraAppException - The exception details that occurred.
     * when finding subjects for a grade instance/s.
     */
    List<GradeSubject> findSubjectsByGrade(String description)throws AkuraAppException;

    /**
     * Finds the GradeSubject object for the given description of a Grade and the description of a Subject.
     *
     * @param gradeDes - the description of the Grade.
     * @param subjectDes - the description of the Subject.
     * @return - a list of GradeSubjects.
     * @throws AkuraAppException - The exception details that occurred
     * when searching for GradeSubject instance/s.
     */
    List<GradeSubject> findGradeSubjectByDes(String gradeDes, String subjectDes)throws AkuraAppException;

    /**
     * Gets a list of GradeSubjects for a given grade Id.
     *
     * @param gradeId - the id of a Grade.
     * @return - a list of GradeSubjects.
     * @throws AkuraAppException - The exception details that occurred
     * when searching for GradeSubject instance/s.
     */
    List<GradeSubject> getGradeSubjectIdListByGrade(int gradeId)throws AkuraAppException;
    
    /**
     * Gets a list of GradeCoreSubjects for a given grade Id.
     *
     * @param gradeId - the id of a Grade.
     * @return - a list of GradeSubjects.
     * @throws AkuraAppException - The exception details that occurred
     * when searching for GradeSubject instance/s.
     */
    List<GradeSubject> getGradeCoreSubjectIdListByGrade(int gradeId)throws AkuraAppException;
    
    /**
     * Delete grade subject list.
     *
     * @param gradeSubjectList the grade subject list
     * @throws AkuraAppException the akura app exception
     */
    void deleteGradeSubjectList(List<GradeSubject> gradeSubjectList) throws AkuraAppException;
    
    /**
     * Gets the grade subject list by class grade id.
     *
     * @param classGradeId the class grade id
     * @return the grade subject list by class grade id
     * @throws AkuraAppException the akura app exception
     */
    List<GradeSubject> getGradeSubjectListByClassGradeId(int classGradeId) throws AkuraAppException;
    
    /**
     * Get the Grade Subject by Grade and subject.
     * @param gradeId - Grade.
     * @param subjectId - Subject.
     * @return List of GradeSubejcts.
     * @throws AkuraAppException detailed exception throws when fails.
     */
    List<GradeSubject> findSubjectsByGradeAndSubjectId(int gradeId,int subjectId) throws AkuraAppException;

    /**
     * Retrieves the maximum mark for the grade subject.
     * 
     * @param gradeSubjectId - the key of the grade subject.
     * @return - the maximum mark.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Integer> findGradeSubjectMaxMarkById(final int gradeSubjectId)throws AkuraAppException;
    
   /**
    *  
    * @param subjectId -key for subject.
    * @param gradeDescription - key for grade 
    * @return return gradeSubject.
    * @throws AkuraAppException The exception details that occurred when processing           
    */
    List<GradeSubject> findGradeSubjectByGradeAndSubjectId(int subjectId ,String gradeDescription)
            throws AkuraAppException;
}
