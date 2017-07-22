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
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the student class info domain object.
 * 
 * @author Virtusa Corporation
 */

public interface StudentClassInfoDao extends BaseDao<StudentClassInfo> {
    
    /**
     * Get a List of StudentClassInfo s of a particular gradeClass and a year.
     * 
     * @param classGradeId ClassgradeID to set.
     * @param selectedyear Year to set.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    List<StudentClassInfo> getClassStudentList(int classGradeId, int selectedyear) throws AkuraAppException;
    
    /**
     * Returns a list of students in a specific class grade.
     * 
     * @param studentId - studentId.
     * @param classGradeId - classGradeId
     * @return a list of StudentClassInfo.
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentClassInfo> getStudentClassList(int studentId, int classGradeId) throws AkuraAppException;
    
    /**
     * Returns a list of students in a specific class grade without old boys and the first date of school is
     * less than the selected attendance marking date.
     * 
     * @param classGradeId - class grade key.
     * @param selectedyear - the relevant year.
     * @param attendanceDate - the relevant date for the attendance.
     * @return - a list of StudentClassInfo.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentClassInfo> getPresentClassStudentList(int classGradeId, int selectedyear, Date attendanceDate)
            throws AkuraAppException;
    
    /**
     * Get a List of StudentClassInfo s of a particular grade and a year.
     * 
     * @param gradeId GradeID to set.
     * @param selectedyear Year to set.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    List<StudentClassInfo> getStudentSearchByGradeYear(int gradeId, int selectedyear) throws AkuraAppException;
    
    /**
     * Get a List of StudentClassInfo s of a particular grade and a year.
     * 
     * @param studentId Student Id to set.
     * @param year year of the class.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    List<StudentClassInfo> getStudentClassInfoByStudentId(int studentId, int year) throws AkuraAppException;
    
    /**
     * Get a List of StudentClassInfo s of a particular grade and a year.
     * 
     * @param classGradeIds - classGradeId s to set.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    List<StudentClassInfo> findStudentClassInfoByClassGradeId(List<Integer> classGradeIds) throws AkuraAppException;
    
    /**
     * Get a List of StudentClassInfo s of a particular grade and a year.
     * 
     * @param classGradeId Grade Id to set.
     * @param year year of the class.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    List<StudentClassInfo> getStudentClassInfoOfClassByYear(int classGradeId, Date year) throws AkuraAppException;
    
    /**
     * Get a List of StudentClassInfo s of a particular grade,student id and a year.
     * 
     * @param classGradeId Grade Id to set.
     * @param year year of the class.
     * @param studentId - studentId.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    List<StudentClassInfo> getStudentClassInfoOfStudentClassByYear(int studentId, int classGradeId, Date year)
            throws AkuraAppException;
    
//    /**
//     * Returns a list of studentClassInfo by student and year.
//     * 
//     * @param studentId student id.
//     * @param year the Year.
//     * @return a list of StudentClassInfo.
//     * @throws AkuraAppException throws if exception occurs.
//     */
//    List<StudentClassInfo> getStudentClassInfoByStudent(int studentId, int year) throws AkuraAppException;
     
    /**
     * Get a List of StudentClassInfo s of a particular student.
     * 
     * @param studentId Student Id to set.
     * @return List of StudentClassInfo Objects.
     * @throws AkuraAppException throws exception if occurs.
     */
    List<StudentClassInfo> getStudentClassInfoByStudentId2(int studentId) throws AkuraAppException;
    
    /**
     * Get the non current students in the class.
     * 
     * @param classGradeId - the class grade id to be used.
     * @param year - the year of interest.
     * @param date - the date of interest.
     * @return list of Non of Current ClassInfoList
     * @throws AkuraAppException - throws a detailed exception.
     */
    List<StudentClassInfo> getNonCurrentClassInfoList(int classGradeId, int year, Date date) throws AkuraAppException;
    
    /**
     * Retrieves the classInfo id list for a given class grade key and the current year.
     * 
     * @param classGradeId - the key of the class grade.
     * @param currentYear - the current year
     * @return - the list of class info keys
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Integer> getStudentClassInfoIdByYear(final int classGradeId, final Date currentYear) throws AkuraAppException;
    
    /**
     * Returns a list of students in a specific class grade without old boys.
     * 
     * @param classGradeId classgrade id.
     * @param year the Year.
     * @param dateObj
     * @return a list of StudentClassInfo.
     * @throws AkuraAppException throws if exception occurs.
     */
    List<StudentClassInfo> getPresentClassStudentList(int classGradeId, int year) throws AkuraAppException;
    
    /**
     * This method returns a list of StudentClassInfo who are yet to be activated.
     * 
     * @param classGradeId - the class grade id of the class.
     * @param year - the year of interest.
     * @param currentDateOne - pass the date of interest to check the student is still suspended.
     * @return a list of StudentClassInfo.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<StudentClassInfo> getSuspendedLeaveStudents(int classGradeId, int year, Date currentDateOne)
            throws AkuraAppException;
    
    /**
     * This method returns a list of StudentClassInfo.
     * 
     * @param classGradeId - the class grade id of the grade.
     * @param year - the year of interest.
     * @param currentDate - pass the relevant date to check whether the student is still active.
     * @return a list of StudentClassInfo.
     * @throws AkuraAppException -  throws detailed exception when fails.
     */
    List<StudentClassInfo> getTemporaryLeavedClassStudentList(int classGradeId, int year, Date currentDate)
            throws AkuraAppException;
}
