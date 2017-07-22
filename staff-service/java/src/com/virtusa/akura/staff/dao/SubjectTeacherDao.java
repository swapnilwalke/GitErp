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
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.SubjectTeacher;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the Class StaffEducation object.
 * 
 * @author Virtusa Corporation
 */
public interface SubjectTeacherDao extends BaseDao<SubjectTeacher> {
    
    /**
     * Get the list of subject teachers by giving last name and registration number.
     * 
     * @param lname - last name.
     * @param regNo - registrationNo.
     * @return list of subject teachers.
     * @throws AkuraAppException - The exception details that occurred when searching SubjectTeacher for a
     *         given criteria.
     */
    List<SubjectTeacher> getSubjectTeacherList(String lname, String regNo) throws AkuraAppException;
    
    /**
     * gets list of SubjectTeacher for given class grade and year.
     * 
     * @param classGrade ClassGrade
     * @param year as int
     * @return list of SubjectTeacher
     * @throws AkuraAppException exception occurs
     */
    List<SubjectTeacher> getSubjectTeachers(ClassGrade classGrade, int year) throws AkuraAppException;
    
    /**
     * Service to delete a SubjectTeacher object.
     * 
     * @param staffId staff id.
     * @param gradeSubjectId grade subject Id.
     * @param year check year.
     * @throws AkuraAppException - The exception details that occurred when deleting SubjectTeacher with given
     *         criteria.
     */
    void deleteSubTeacher(int staffId, int gradeSubjectId, String year) throws AkuraAppException;
    
    /**
     * Service method is to get list of classes for a particular staff for a particular grade subject.
     * 
     * @param staffId - staff id.
     * @param gradeSubjectId - grade subject ID.
     * @param year - asigned year.
     * @return list of classes for a particular staff for a particular grade subject.
     * @throws AkuraAppException - The exception details that occurred when retrieving a Class list.
     */
    List<Integer> getClassList(int staffId, int gradeSubjectId, String year) throws AkuraAppException;
    
    /**
     * Service method to delete Subject Teacher.
     * 
     * @param subTeacher - subject Teacher to be deleted.
     * @throws AkuraAppException - The exception details that occurred when deleting SubjectTeacher.
     */
    void deleteSubTeacher(SubjectTeacher subTeacher) throws AkuraAppException;
    
    /**
     * Service method is to get list of Subject Teachers for a particular staff for a particular grade
     * subject.
     * 
     * @param staffId - staff id.
     * @param gradeSubjectId - grade subject ID.
     * @param year - the subject assigned year.
     * @return list of Subject Teachers for a particular staff for a particular grade subject.
     * @throws AkuraAppException - The exception details that occurred when retrieving a Class list.
     */
    List<SubjectTeacher> getSubjectTeachersList(int staffId, int gradeSubjectId, String year) throws AkuraAppException;
    
    /**
     * Get Subjects taught at present by staff id and current year.
     * 
     * @param staffId staff id
     * @param year current year
     * @return list of subjects
     * @throws AkuraAppException AkuraAppException
     */
    List<String> getCurrentSubjectsByStaffId(int staffId, String year) throws AkuraAppException;
    
    /**
     * Get the class list of subject teacher.
     * 
     * @param staffId - staff id.
     * @param gradeSubjectId - grade subject id.
     * @param year - assigned year.
     * @return List of School classes.
     * @throws AkuraAppException - The exception details that occurred when retrieving a Class list.
     */
    List<SchoolClass> getSchooClassObjectList(int staffId, int gradeSubjectId, String year) throws AkuraAppException;
    
    /**
     * Mark as delete all the SubjectTeacher allocations.
     * 
     * @param staffId staff id.
     * @throws AkuraAppException - The exception details that occurred when updating SubjectTeacher with given
     *         staffId.
     */
    void markAsDeletedAllSubjectTeacherRecords(int staffId) throws AkuraAppException;
    
    /**
     * Delete all the Subject Teacher records that have start date after the departureDate.
     * 
     * @param staffId staff id.
     * @param departureDate departure date.
     * @throws AkuraAppException - The exception details that occurred when deleting StaffLeave records with given
     *         staffId.
     */
    void deletedSubjectTeacherRecordsByDepartureDate(int staffId, Date departureDate) throws AkuraAppException;
    
    /**
     * Remove as delete all the SubjectTeacher allocations.
     * 
     * @param staffId staff id.
     * @throws AkuraAppException - The exception details that occurred when updating SubjectTeacher with given
     *         staffId.
     */
    void removeAsDeletedAllSubjectTeacherRecords(int staffId) throws AkuraAppException;
}
