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
import com.virtusa.akura.api.dto.StudentTemporaryLeave;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */

public interface StudentTemporaryLeaveDao extends BaseDao<StudentTemporaryLeave> {
	
	
	/**
     * Retrieve all the available StudentTempLeave information given by student Id.
     *
     * @param studentId specifies the student id, defined by an integer
     * @return List of StudentTempLeave
     * @throws AkuraAppException - Throw Exception
     */
	List<StudentTemporaryLeave> findStudentTempLeaveByStudentId(int studentId) throws AkuraAppException;
    
	/**
	 * Get the most latest StudentTemporaryLeave by student id.
	 * @param studentId - Id of the student.
	 * @return List of StudentTemporaryLeave
	 * @throws AkuraAppException - Throws detailed exception when fails.
	 */
	List<StudentTemporaryLeave> getLatestStudentTempLeaveByStudentId(int studentId) throws AkuraAppException;
    
}
