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

import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentParent;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the StudentParent object.
 * 
 * @author Virtusa Corporation
 */
public interface StudentParentDao extends BaseDao<StudentParent> {
    
    /**
     * Retrieve all the available Student Parent information given by student Id.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @return List of StudentParent
     * @throws AkuraAppException SMS Exception
     */
    List<StudentParent> getStudentParentsByStudentId(int studentId) throws AkuraAppException;
    
    /**
     * Retrieve all the available Student Parent information given by parent Id.
     * 
     * @param parentId specifies the parent Id, defined by an integer
     * @return List of StudentParent
     * @throws AkuraAppException Exception
     */
    List<Student> getStudentByParnetId(int parentId) throws AkuraAppException;
    
    /**
     * To get StudentParent with given student(id) and the relationship (f,m,g).
     * 
     * @param studentId studentId value
     * @param relationship relationship as character 
     * @return StudentParent list
     * @throws AkuraAppException when exception occurs 
     */
    List<StudentParent> getParentListBy(int studentId,Character relationship) throws AkuraAppException;
    
    /**
     * get all students that have the parent and the given relation in used .
     * 
     * @param nic NIC no
     * @param relationship relationship as character
     * @return student list
     * @throws AkuraAppException when exception occurs 
     */
    List<Student> getStudentListBy(String nic,Character relationship) throws AkuraAppException;
    
    /**
     * check whether the given parent is used by other student without this student(Id).
     * 
     * @param nic parent NIC no
     * @param studentID studentId  
     * @return true if user by other student
     * @throws AkuraAppException when exception occurs 
     */
    boolean isUsedInOtherStudent(String nic,int studentID)throws AkuraAppException;
    
    /**
     * check whether the given parent is used by the student(Id).
     * 
     * @param nic parent NIC no
     * @param studentID studentId  
     * @return true if user by same student
     * @throws AkuraAppException when exception occurs 
     */
    boolean isUsedInSameStudent(String nic,int studentID)throws AkuraAppException;
    
    /**
     * Delete the record of student if he/she departures.
     * 
     * @param studentId studentId.
     * @throws AkuraAppException - The exception details that occurred when deleting Student records with given
     *         studentId.
     */
    void deleteStudentByDepartureDate(int studentId) throws AkuraAppException;

    
}
