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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * ClassGradeDao interface provides persistent related functionalities for ClassGrade object.
 * 
 * @author Virtusa Corporation
 */
public interface ClassGradeDao extends BaseDao<ClassGrade> {
    
    /**
     * Retrieves the entire list of records for a given object.
     * 
     * @param grade - the object class.
     * @return List - The list of records for the given class.
     * @throws AkuraAppException - The detailed exception.
     */
    List<ClassGrade> findClassGradeListByGrade(Grade grade) throws AkuraAppException;
    
    /**
     * Delete list of class grades.
     * 
     * @param classGrades list of classGrade
     * @throws AkuraAppException - throw this.
     */
    void deleteClassGradeList(List<ClassGrade> classGrades) throws AkuraAppException;
    
    /**
     * Retrieve the list of ClassGrade object for given grade id and class id.
     * 
     * @param gradeId - int type of grade Id for class grade object.
     * @param classId - int type of class Id for class grade object.
     * @return ClassGrade - The ClassGrade object for given grade id and class id.
     * @throws AkuraAppException - The detailed exception.
     */
    List<ClassGrade> findClassGradeByGradeIdAndClassId(int gradeId, int classId) throws AkuraAppException;
    
    /**
     * Gets the class grade by name.
     * 
     * @param description the description
     * @return the class grade by name
     * @throws AkuraAppException the akura app exception
     */
    List<ClassGrade> getClassGradeByName(String description) throws AkuraAppException;
    
    /**
     * Gets the class grade by staff registration no.
     * 
     * @param registrationNo the registration no
     * @return the class grade by staff registration no
     * @throws AkuraAppException the akura app exception
     */
    List<ClassGrade> getClassGradeByStaffRegistrationNo(String registrationNo) throws AkuraAppException;
    
    /**
     * Gets the class grade list by grade id.
     * 
     * @param gradeId grade id
     * @return list of class grade objects
     * @throws AkuraAppException throws if exception occurs
     */
    List<ClassGrade> getClassGradeListByGradeId(int gradeId) throws AkuraAppException;
    
    /**
     * Checks wether logged staff or the clerical staff is a teacher of the relevant class grade.
     * 
     * @param userName - the name of the staff/ clerical staff
     * @param classGradeKey - the key of the class grade
     * @return - if the class teacher returns true else false
     * @throws AkuraAppException - - The exception details that occurred when retrieving.
     */
    List<?> isExistStaff(final int userName, final String classGradeKey) throws AkuraAppException;
    
    /**
     * This method is used to find class grade by class id.
     * 
     * @param classId int value
     * @return list of class grade objects
     * @throws AkuraAppException throws if exception occurs
     */
    List<ClassGrade> findClassGradeListByClassId(int classId) throws AkuraAppException;
}
