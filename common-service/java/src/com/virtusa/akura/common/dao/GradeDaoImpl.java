/*
 * < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
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
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This is the implementation class for GradeDao interface.
 * It handles all the persistent related functionalities for the
 * Grade object.
 *
 * @author Virtusa Corporation
 */
public class GradeDaoImpl extends BaseDaoImpl<Grade> implements GradeDao {

    /**
     * String attribute for query name by grade name.
     */
	private static final String QUERY_NAME_BY_GRADE_NAME = "getGradeByGradeName";

    /**
	 * Retrieve grades group by grade id.
	 *
	 * @param description type string
	 * @return List - The list of records for the given class.
	 * @throws AkuraAppException - The detailed exception.
	 */
	@SuppressWarnings("unchecked")
	public List<Grade> findGradeByGradeName(String description) throws AkuraAppException {
		try {
			return getHibernateTemplate().findByNamedQuery(
					QUERY_NAME_BY_GRADE_NAME, description);
		} catch (DataAccessException e) {
			throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
		}

	}
}
