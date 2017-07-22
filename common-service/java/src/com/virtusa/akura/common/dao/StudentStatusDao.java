/* < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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
import com.virtusa.akura.api.dto.StudentStatus;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * GradeDao interface provides persistent related functionalities for Grade
 * object.
 * 
 * @author Virtusa Corporation
 */

public interface StudentStatusDao extends BaseDao<StudentStatus> {

	/**
	 * Retrieve all the Student's Status with the description.
	 * 
	 * @param description
	 *            The Student's Status description of the StudentStatus.
	 * @return studentstatus the section with the description "description".
	 * @throws AkuraAppException
	 *             SMS Exceptions.
	 */
	StudentStatus getStudentStatusByName(String description)
			throws AkuraAppException;

	/**
	 * Retrieve all the Student's Status with the description.
	 * 
	 * @return studentstatus list.
	 * @throws AkuraAppException
	 *             AkuraAppException Exceptions.
	 */
	List<StudentStatus> getStudentStatusList() throws AkuraAppException;
}
