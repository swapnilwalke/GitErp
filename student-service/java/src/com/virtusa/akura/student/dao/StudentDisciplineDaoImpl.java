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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StudentDiscipline;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the student discipline domain object.
 * 
 * @author Virtusa Corporation
 */

public class StudentDisciplineDaoImpl extends BaseDaoImpl<StudentDiscipline> implements StudentDisciplineDao {
    
    /** key to hold query string to viewAllStudentDisciplineInfoByYear. */
    private static final String VIEW_ALL_STUDENT_DISCIPLINE_INFO_BY_YEAR = "viewAllStudentDisciplineInfoByYear";
    
    /** key to hold the named query viewStudentDisciplineByStudentIdAndStaffId. */
    private static final String VIEW_STUDENT_DISCIPLINE_BY_STUDENT_ID_AND_USER_ID =
            "viewStudentDisciplineByStudentIdAndUserLoginId";
    
    /** key to hold detailed error while retrieving student discipline information. */
    private static final String ERROR_WHILE_RETRIEVING_STUDENT_DISCIPLINE_INFO =
            "Error while retrieving student discipline info ---> ";
    
    /** key to hold the named query viewStudentDisciplineByStudentId. */
    private static final String VIEW_STUDENT_DISCIPLINE_BY_STUDENT_ID = "viewStudentDisciplineByStudentId";
    
    /** String constant for holding respective query name. */
    private static final String GET_STUDENT_DISCIPLINE_BY_NAME = "getStudentDiscilpineByName";
    
    /** key to hold the named query viewStudentDisciplineByStudentId. */
    private static final String VIEW_CURRENT_YEAR_STUDENT_DISCIPLINE_BY_STUDENT_ID =
            "viewCurrentYearStudentDisciplineByStudentId";
    
    /** key to hold the named query getDisciplineForStudentByDate. */
    private static final String GET_DISCIPLINE_FOR_STUDENT_BY_DATE = "getDisciplineForStudentByDate";
    
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentDisciplineDaoImpl.class);
    
    /**
     * Retrieve all the available discipline information given by student Id.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @return List of StudentDiscipline
     * @throws AkuraAppException SMS Exception
     */
    @SuppressWarnings("unchecked")
    public List<StudentDiscipline> viewStudentDisciplineByStudentId(int studentId) throws AkuraAppException {

        List<StudentDiscipline> studentList = null;
        try {
            studentList = getHibernateTemplate().findByNamedQuery(VIEW_STUDENT_DISCIPLINE_BY_STUDENT_ID, studentId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_DISCIPLINE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
            
        }
        
        return studentList;
    }
    
    /**
     * Retrieve all the available discipline information given by student Id.
     * 
     * @param studentIdVal specifies the student id, defined by an integer
     * @param year specifies the current year.
     * @return List of StudentDiscipline
     * @throws AkuraAppException SMS Exception
     */
    @SuppressWarnings("unchecked")
    public List<StudentDiscipline> viewCurrentYearStudentDisciplineByStudentId(int studentIdVal, int year)
            throws AkuraAppException {

        List<StudentDiscipline> studentList = null;
        try {
            studentList =
                    getHibernateTemplate().findByNamedQuery(VIEW_CURRENT_YEAR_STUDENT_DISCIPLINE_BY_STUDENT_ID,
                            studentIdVal, year);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_DISCIPLINE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
            
        }
        
        return studentList;
    }
    
    /**
     * Retrieve all the available discipline information given by student Id and userLoginId.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @param userLoginId specifies the staff id, defined by an integer
     * @return List of StudentDiscipline
     * @throws AkuraAppException SMS Exception
     */
    @SuppressWarnings("unchecked")
    public List<StudentDiscipline> viewStudentDisciplineByStudentIdAndUserLoginId(int studentId, int userLoginId)
            throws AkuraAppException {

        List<StudentDiscipline> studentList = null;
        try {
            studentList =
                    getHibernateTemplate().findByNamedQuery(VIEW_STUDENT_DISCIPLINE_BY_STUDENT_ID_AND_USER_ID,
                            studentId, userLoginId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_DISCIPLINE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
            
        }
        
        return studentList;
    }
    
    /**
     * Retrieve all the available discipline information by given date range.
     * 
     * @param startDate specifies the formDate
     * @param endDate specifies the end date.
     * @return List of StudentDiscipline
     * @throws AkuraAppException - The exception details that occurred when retrieving list of
     *         StudentDiscipline
     */
    @SuppressWarnings("unchecked")
    public List<StudentDiscipline> viewAllStudentDisciplineInfoByYear(Date startDate, Date endDate)
            throws AkuraAppException {

        List<StudentDiscipline> studentList = null;
        try {
            studentList =
                    getHibernateTemplate().findByNamedQuery(VIEW_ALL_STUDENT_DISCIPLINE_INFO_BY_YEAR, startDate,
                            endDate);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_DISCIPLINE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
            
        }
        
        return studentList;
    }
    
    /**
     * Retrieve all the holiday's with the holidayname.
     * 
     * @param comment1 Detailed Comment.
     * @param informedtoParent If inform to parent or not
     * @param warningLevelId Id of the warning level
     * @param date Comment added date
     * @return studentDiscipline the studentDiscipline details.
     * @throws AkuraAppException detailed Exceptions.
     */
    @SuppressWarnings("unchecked")
    public StudentDiscipline getStudentDisciplineByName(String comment1, boolean informedtoParent, int warningLevelId,
            Date date) throws AkuraAppException {

        List<StudentDiscipline> studentDisciplineList = null;
        StudentDiscipline studentDiscipline = null;
        
        try {
            studentDisciplineList =
                    getHibernateTemplate().findByNamedQuery(GET_STUDENT_DISCIPLINE_BY_NAME, comment1, informedtoParent,
                            warningLevelId, date);
            if (studentDisciplineList != null && !studentDisciplineList.isEmpty()) {
                studentDiscipline = studentDisciplineList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return studentDiscipline;
    }
    
    /**
     * Get the selected disciplinary action for student.
     * 
     * @param disciplinaryActionId - Id of the selected disciplinary action for student.
     * @return ListStudentDiscipline disciplinary action.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    @SuppressWarnings("unchecked")
    public List<StudentDiscipline> getSelectedDisciplinaryActionByStudent(int disciplinaryActionId)
            throws AkuraAppException {

        List<StudentDiscipline> desDisciplinaryAction = null;
        try {
            desDisciplinaryAction =
                    getHibernateTemplate().findByNamedQuery("getDisciplinaryActionById", disciplinaryActionId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_DISCIPLINE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
            
        }
        
        return desDisciplinaryAction;
        
    }

    /**
     * Get the selected disciplinary action for student.
     * 
     * @param studentId the id Student.      
     * @param year the Date
     * @return ListStudentDiscipline disciplinary action.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    public List<StudentDiscipline> getStudentDisciplineListForStudent(int studentId, Date year)
    throws AkuraAppException{   
        
        
        List<StudentDiscipline> studentDisciplinaryActions;
        try {
           
                studentDisciplinaryActions =
                        getHibernateTemplate().findByNamedQuery(GET_DISCIPLINE_FOR_STUDENT_BY_DATE,
                                studentId, year);
            
        } catch (DataAccessException e) {
            LOG.error(ERROR_WHILE_RETRIEVING_STUDENT_DISCIPLINE_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return studentDisciplinaryActions;
    }     
}
