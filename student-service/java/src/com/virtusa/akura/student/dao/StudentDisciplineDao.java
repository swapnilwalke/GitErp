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
import com.virtusa.akura.api.dto.StudentDiscipline;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This class provides persistence layer functionality for the student discipline domain object.
 * 
 * @author Virtusa Corporation
 */

public interface StudentDisciplineDao extends BaseDao<StudentDiscipline> {
    
    /**
     * Retrieve all the available discipline information given by student Id.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @return List of StudentDiscipline
     * @throws AkuraAppException SMS Exception
     */
    List<StudentDiscipline> viewStudentDisciplineByStudentId(int studentId) throws AkuraAppException;
    
    /**
     * Retrieve all the available discipline information given by student Id and userLoginId.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @param userLoginId specifies the userLogin id, defined by integer.
     * @return List of StudentDiscipline
     * @throws AkuraAppException SMS Exception
     */
    List<StudentDiscipline> viewStudentDisciplineByStudentIdAndUserLoginId(int studentId, int userLoginId)
            throws AkuraAppException;
    
    /**
     * Retrieve all the available discipline information by given date range.
     * 
     * @param startDate specifies the formDate
     * @param endDate specifies the end date.
     * @return List of StudentDiscipline
     * @throws AkuraAppException - The exception details that occurred when retrieving list of StudentDiscipline
     */
	List<StudentDiscipline> viewAllStudentDisciplineInfoByYear(Date startDate,
			Date endDate) throws AkuraAppException;
	
	 /**
     * Retrieve the StudentDiscipline by passing the holiday's holidayname.This returns any holiday with the holiday
     * passed.
     * 
     * @param comment1 - String
     * @param informedtoParent - boolean
     * @param warningLevelId - int
     * @param date - Date
     * @return StudentDiscipline object.
     * @throws AkuraAppException Detailed Exceptions.
     */	
	StudentDiscipline getStudentDisciplineByName
	(String comment1, boolean informedtoParent, int warningLevelId, Date date) throws AkuraAppException;
	
	/**
     * Get the selected disciplinary action for student.
     * 
     * @param studentIdVal - Student Id.
     * @param year - Id of the selected disciplinary action for student.
     * @return ListStudentDiscipline.
     * @throws AkuraAppException - throws detailed exception when fails.
     */

    List<StudentDiscipline> viewCurrentYearStudentDisciplineByStudentId(int studentIdVal, int year)
    throws AkuraAppException;
    
    /**
     * Get the selected disciplinary action for student.
     * 
     * @param disciplinaryActionId - Id of the selected disciplinary action for student.
     * @return ListStudentDiscipline disciplinary action.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<StudentDiscipline> getSelectedDisciplinaryActionByStudent(int disciplinaryActionId)
    throws AkuraAppException;
    
    /**
     * Get the selected disciplinary action for student.
     * 
     * @param studentId the id Student.
     * @param description the description of the StudentDiscipline.
     * @param year the Date
     * @return ListStudentDiscipline disciplinary action.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<StudentDiscipline> getStudentDisciplineListForStudent(int studentId, Date year)
    throws AkuraAppException;
	
	
}
