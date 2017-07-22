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

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.EducationalQualification;


/**
 * This is the implementation class for EducationalQualificationDao interface.
 * It handles all the persistent related functionalities for the
 * EducationalQualification object.
 * 
 * @author Virtusa Corporation
 */
public class EducationalQualificationDaoImpl extends
		BaseDaoImpl<EducationalQualification> implements
		EducationalQualificationDao {

	/** String Constant for holding respective query name. */
	private static final String GET_EDUQUALIFICATION_BY_DESCRIPTION = "getEducationalQualificationByDescription";

	/**
	 * Method is to retrieve EducationalQualification for given description.
	 * 
	 * @param description
	 *            - description
	 * @return educationalQualification for respective description.
	 */

	@SuppressWarnings("unchecked")
	public EducationalQualification getAnyEducationalQualificationByDes(
			String description) {
		List<EducationalQualification> educationalQualifications = getHibernateTemplate()
				.findByNamedQuery(GET_EDUQUALIFICATION_BY_DESCRIPTION,
						new Object[] { description });

		EducationalQualification educationalQualification = null;

		if (educationalQualifications != null
				&& !educationalQualifications.isEmpty()) {
			educationalQualification = educationalQualifications.get(0);

		}
		return educationalQualification;
	}
}
