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

package com.virtusa.akura.staff.dao;

import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClassTeacher;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the Class Teacher object.
 *
 * @author Virtusa Corporation
 */
public interface ClassTeacherDao extends BaseDao<ClassTeacher> {

    /**
     * Searches results for the given year, last name of the staff and the class grade criteria.
     *
     * @param des - description of the ClassGrade.
     * @param lName - last name of the Staff member.
     * @param searchYear - year of search.
     * @return - a list of ClassTeachers.
     * @throws AkuraAppException - The exception details that occurred when searching ClassTeachers for a given
     *         criteria.
     */
    List<ClassTeacher> searchClassTeacher(String des, String lName, String searchYear) throws AkuraAppException;

    /**
     * Get the list of Class Teachers of this Grade.
     *
     * @param gradeId the grade of teachers we need.
     * @return a list of Class Teachers.
     * @throws AkuraAppException - The exception details that occurred when retrieving ClassTeachers for a given
     *         grade.
     */
    List<ClassTeacher> getClassTeacherByGrade(int gradeId) throws AkuraAppException;
    
    /**
     * get the class class teacher.
     * @param classGrade class and grade as ClassGrade object
     * @param year year
     * @return ClassTeacher object
     * @throws AkuraAppException exception occurs
     */
    ClassTeacher getClassTeacher(ClassGrade classGrade,int year) throws AkuraAppException;
    
    /**
     * Mark as delete all the classTeacher allocations.
     * 
     * @param staffId staff id.
     * @throws AkuraAppException - The exception details that occurred when updating classTeacher with given
     *         staffId.
     */
    void markAsDeletedAllClassTeacherRecords(int staffId) throws AkuraAppException;
    
    /**
     * Delete all the Class Teacher records that have start date after the departureDate.
     * 
     * @param staffId staff id.
     * @param departureDate departure date.
     * @throws AkuraAppException - The exception details that occurred when deleting StaffLeave records with given
     *         staffId.
     */
    void deletedClassTeacherRecordsByDepartureDate(int staffId, Date departureDate) throws AkuraAppException;
    
   
    /**
     * get all the Classes of a  ClassTeacher .
     * 
     * @param staffId staff id.
     * @return - a list of ClassGrades
     * @throws AkuraAppException - The exception details that occurred when deleting StaffLeave records with given
     *         staffId.
     */
     List<ClassGrade> getClassTeacherRecordsByStaffIdAndYear(int staffId)  throws AkuraAppException;
   
     
     /**
     * Returns a list of ClassTeacher list by year.
     * 
     * @param year - the year. 
     * @return - a list of ClassTeachers
     * @throws AkuraAppException - The exception details that occurred when retrieving all ClassTeachers
     */
    List<ClassTeacher>  findClassTeacherListByYear(Date year) throws AkuraAppException;
    
    /**
     * Remove as delete all the ClassTeacher allocations.
     * 
     * @param staffId staff id.
     * @throws AkuraAppException - The exception details that occurred when updating ClassTeacher with given
     *         staffId.
     */
    void removeAsDeletedAllClassTeacherRecords(int staffId) throws AkuraAppException;
   
}
