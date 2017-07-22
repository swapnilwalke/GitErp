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
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * Class which provides implementation to StudentTermMarks view.
 * 
 * @author Virtusa Corporation
 */
public interface StudentTermMarkViewDao extends BaseDao<StudentTermMarkDTO> {
    
    /**
     * Gets the student marks for the student for perticular year.
     * 
     * @param studentId student id.
     * @param year the year.
     * @return the list of StudentTermMarkDTO.
     * @throws AkuraAppException the AkuraAppException
     */
    List<StudentTermMarkDTO> getTermMarksForStudent(int studentId, int year) throws AkuraAppException;
    
    /**
     * Gets list of StudentTermMarkDTO by year,class grade and term.
     * 
     * @param classGradeId class grade id.
     * @param termid term id.
     * @param year the year.
     * @return the list of StudentTermMarkDTO.
     * @throws AkuraAppException the AkuraAppException
     */
    List<StudentTermMarkDTO> getTermMarksByTermGradeYear(int classGradeId, int termid, int year)
            throws AkuraAppException;
    
    /**
     * Returns a list of average.
     * 
     * @param convertedDate - the current year.
     * @param studentId - the primary key of the student.
     * @return - a list of average.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Object> populateStudentAverageTerm(int convertedDate, int studentId) throws AkuraAppException;
    
    /**
     * Gets list of StudentTermMarkDTO by classInfoID and gradeSubjectId.
     * 
     * @param classInfoId class info id.
     * @param gradeSubjectId grade subject id.
     * @return the list of StudentTermMarkDTO.
     * @throws AkuraAppException the AkuraAppException
     */
    List<StudentTermMarkDTO> getTermMarksByClassInfoIdAndGradeSubject(int classInfoId, int gradeSubjectId)
            throws AkuraAppException;
    
    /**
     * Gets the student term marks by student id year subject.
     *
     * @param studentId the student id
     * @param year the year
     * @param gradeSubjectId the grade subject id
     * @return the student term marks by student id year subject
     * @throws AkuraAppException the akura app exception
     */
    List<StudentTermMarkDTO> getStudentTermMarksByStudentIdYearSubject(int studentId, int year, int gradeSubjectId)
            throws AkuraAppException;
    
    /**
     * Gets the student term marks by student id year.
     * 
     * @param studentId the student id
     * @param year the year
     * @param classGradeId class grade id
     * @return the student term marks by student id year subject
     * @throws AkuraAppException the akura app exception
     */
    List<StudentTermMarkDTO> findStudentTermMarksByStudentIdYearClassGrade(int studentId, int year, int classGradeId) 
            throws AkuraAppException;
}
