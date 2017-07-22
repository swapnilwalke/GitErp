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
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentSearchCritiria;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * Declare the Dao related functionalities.
 * 
 * @author Virtusa Corporation
 */
public interface StudentDao extends BaseDao<Student> {
    
    /**
     * return student list for the given criteria.
     * 
     * @param critiria - student search criteria
     * @exception AkuraAppException -AkuraAppException
     * @return list of students
     */
    List<Object> studentSearch(StudentSearchCritiria critiria) throws AkuraAppException;
    
    /**
     * Get student list for the given criteria.
     * 
     * @param critiria - student search criteria
     * @exception AkuraAppException -AkuraAppException
     * @return list of students
     */
    List<Object> studentAdvanceSearch(StudentSearchCritiria critiria) throws AkuraAppException;
    
    /**
     * Dao method is to get StudentId for the admissionNo.
     * 
     * @param admissionNo - admissionNo.
     * @return student id for the registrationNo.
     * @throws AkuraAppException AkuraAppException
     */
    int findStudentIdForAdmissionNo(String admissionNo) throws AkuraAppException;
    
    /**
     * Gets the student personal details by admission no.
     * 
     * @param admissionNo the admission no
     * @return the student personal details by admission no
     * @throws AkuraAppException the sMS app exception
     */
    List<Student> getStudentPersonalDetailsByAdmissionNo(String admissionNo) throws AkuraAppException;
    
    /**
     * Gets the new students for the given year.
     * 
     * @param year the year.
     * @return the new student list.
     * @throws AkuraAppException AkuraAppException.
     */
    List<Object> getNewStudentForYear(int year) throws AkuraAppException;
    
    /**
     * Returns a list of primary keys of the student.
     * 
     * @param addmissionNo - admission number of the student.
     * @return - a list of primary keys of the student.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Integer> getStudentId(String addmissionNo) throws AkuraAppException;
    
    /**
     * Retrieves the key of the Student by the student name.
     * 
     * @param studentName - the name of the student.
     * @return - the key of the Student by the student name.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Integer> getStudentIdByName(final String studentName) throws AkuraAppException;
    
    /**
     * Gets the student admission nos by class grade id.
     * 
     * @param classGradeId the class grade id
     * @return the student admission nos by class grade id
     * @throws AkuraAppException the akura app exception
     */
    List<String> getStudentAdmissionNosByClassGradeId(final int classGradeId) throws AkuraAppException;

    /**
     * Returns the first date at school of the student.
     * 
     * @param studentId - the key of the student
     * @return - the first date at the school
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Date> getStudentStartedDate(int studentId) throws AkuraAppException;
    
    /**
     * Delete all the Attendance records that have date after the departureDate.
     * 
     * @param studentId student id.
     * @param departureDate departure date.
     * @throws AkuraAppException - The exception details that occurred when deleting StudentAttendance records
     *         with given studentId.
     */
    void deletedStudentAttendanceRecordsByDepartureDate(int studentId, Date departureDate) throws AkuraAppException;
    
    /**
     * Retrieves the list of student status by student ids list.
     * 
     * @param studentIdsList - the list of student ids to get the student status.
     * @return list of student status list.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<Integer> getStudentStatusListByStudentIdsList(List<Integer> studentIdsList) throws AkuraAppException;
    
    /**
     * Retrieves selected student's ID.
     * 
     * @param studentId - the list of student ids to get the student status.
     * @return studentId int value.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    int getStudentStatusId(int studentId) throws AkuraAppException;
    
    /**
     * Get the number of students in the school.
     * 
     * @return the number of students in the school.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    int getNumberOfStudentsInSchool() throws AkuraAppException;
    
}
