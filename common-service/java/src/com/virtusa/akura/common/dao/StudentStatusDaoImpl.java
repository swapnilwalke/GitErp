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
package com.virtusa.akura.common.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;

import com.virtusa.akura.api.dto.StudentStatus;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This is the implementation class for GradeDao interface.
 * It handles all the persistent related functionalities for the
 * Grade object.
 *
 * @author Virtusa Corporation
 */


public class StudentStatusDaoImpl extends BaseDaoImpl<StudentStatus> implements
		StudentStatusDao {
	/** String constant for holding respective query name. */
	private static final String GET_ANY_STUDENT_STATUS_BY_NAME = "getStudentStatusByName";

	/** String constant for holding respective query name. */
	private static final String GET_ANY_STUDENT_STATUS = "getAnyStudentStatus";

	/**
	 * Retrieve all the Student's Status with the description.
	 * 
	 * @param description
	 *            The Student's Status description of the StudentStatus.
	 * @return studentstatus the section with the description "description".
	 * @throws AkuraAppException
	 *             SMS Exceptions.
	 */
	@SuppressWarnings("unchecked")
	public StudentStatus getStudentStatusByName(String description)
			throws AkuraAppException {

		List<StudentStatus> studentStatusList = null;
		StudentStatus studentStatus = null;

		try {
			studentStatusList = getHibernateTemplate().findByNamedQuery(
					GET_ANY_STUDENT_STATUS_BY_NAME, description);
			if (studentStatusList != null && !studentStatusList.isEmpty()) {
				studentStatus = studentStatusList.get(0);
			}
		} catch (DataAccessException e) {
			throw new AkuraAppException(
					AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
		}

		return studentStatus;
	}

	/**
	 * Retrieve all the Student's Status with the description.
	 * 
	 * 
	 * @return studentstatusList
	 * @throws AkuraAppException
	 *             SMS Exceptions.
	 */
	
	@SuppressWarnings("unchecked")
	public List<StudentStatus> getStudentStatusList() throws AkuraAppException {

		List<StudentStatus> studentStatusList = null;

		try {
			studentStatusList = getHibernateTemplate().findByNamedQuery(
					GET_ANY_STUDENT_STATUS);

		} catch (DataAccessException e) {
			throw new AkuraAppException(
					AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
		}

		return studentStatusList;

	}
}
