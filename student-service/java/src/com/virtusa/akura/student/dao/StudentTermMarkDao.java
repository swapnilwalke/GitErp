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

package com.virtusa.akura.student.dao;

import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * Class which provides implementation to StudentTermMarks.
 */
public interface StudentTermMarkDao extends BaseDao<StudentTermMark> {
    
    /**
     * retrieves a list of student subject marks on search criteria.
     * 
     * @param termId to hold term Id.
     * @return List of subject marks for all students matching search criteria.
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentTermMark> getSelectedSubjectMarks(int termId) throws AkuraAppException;
    
    /**
     * retrieves a list of student subject marks on search criteria.
     * 
     * @param termId to hold term Id.
     * @param classGradeId to hold class grade Id.
     * @return List of subject marks for all students matching search criteria.
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentTermMark> getSelectedSubjectMarksByGrade(int termId, int classGradeId) throws AkuraAppException;
    
    /**
     * Method is to return list of StudentTermMark objects of a class grade.
     * 
     * @param classGrade - ClassGrade type object.
     * @return list of StudentTermMark Objects of that ClassGrade.
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentTermMark> getStudentTermMarkInfoOfClassSubjects(ClassGrade classGrade) throws AkuraAppException;
    
    /**
     * Method is to delete list of StudentTermMark objects.
     * 
     * @param deletedTermMarkIdList - StudentTermMarkId s to delete.
     * @return list of StudentTermMark Objects that get deleted.
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentTermMark> deleteStudentTermMarkInfoOfClassSubjects(List<Integer> deletedTermMarkIdList)
            throws AkuraAppException;
    
    /**
     * Method is to get list of StudentTermMark objects that have saved.
     * 
     * @param termMarkList - StudentTermMark that wants to be saved.
     * @return list of StudentTermMark Objects that get saved.
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentTermMark> getSavedStudentSubjectList(List<StudentTermMark> termMarkList) throws AkuraAppException;
    
    /**
     * Method is to get list of StudentTermMark objects that have saved.
     * 
     * @param termMarkList - StudentTermMark that wants to be saved.
     * @throws AkuraAppException when exception occurs.
     */
    void updateStudentTermMark(List<StudentTermMark> termMarkList) throws AkuraAppException;
    
    /**
     * Retreives the marks for the keys of a given class information , grade subject and a term.
     * 
     * @param classInfoId - primary key of the ClassInfo.
     * @param gradeSubjectId - primary key of the GradeSubject.
     * @param termId - primary key of the term.
     * @return - the marks of the student term marks.
     * @throws AkuraAppException - The exception details that occurred.
     */
    List<Float> getStuTermMarks(final int classInfoId, final int gradeSubjectId, final int termId)
            throws AkuraAppException;
    
    /**
     * Checks if is student term mark exist.
     * 
     * @param studentClassInfoId - the student class info id
     * @param gradeSubjectId - the grade subject id
     * @param termId - the term id
     * @return - the status of the existance of the student term mark for a given term key, student class info
     *         key and the grade subject key.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Integer> isStudentTermMarkExist(int studentClassInfoId, int gradeSubjectId, int termId)
            throws AkuraAppException;
    
    /**
     * This method gets the term marks associated with a student by his/her id and year.
     * 
     * @param studentId - the id of the student of interest.
     * @param year - get the marks for the year.
     * @return - a list of student term marks objects.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentTermMark> getTermMarkByStudentIdAndYear(int studentId, Date year) throws AkuraAppException;
    
    /**
     * Method is to delete StudentTermMark objects that have saved.
     * 
     * @param studentId - Student Id.
     * @param termId - termId.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    void deleteTrmMarks(int studentId, int termId) throws AkuraAppException;
    
    /**
     * @param studentClassInfoId - the studentClassInfoId id.
     * @param gradeSubjectId - gradeSubjectId.
     * @param termId - termId.
     * @return List containing the latest StudentTermMark object record.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<StudentTermMark> getStudentTermMarkObject(int studentClassInfoId, int gradeSubjectId, int termId)
            throws AkuraAppException;
}
