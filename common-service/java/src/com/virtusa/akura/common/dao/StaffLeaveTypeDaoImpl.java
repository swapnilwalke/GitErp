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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.dto.StaffLeaveType;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.common.dao.StaffLeaveTypeDaoImpl;

/**
 * This is the implementation class for StaffLeaveTypeDao interface. It handles
 * all the persistent related functionalities for the StaffLeaveType object.
 * 
 * @author Virtusa Corporation
 * 
 */
public class StaffLeaveTypeDaoImpl extends BaseDaoImpl<StaffLeaveType>
		implements StaffLeaveTypeDao {
	/** String Constant for holding respective query name. */
	private static final String GET_STAFFLEAVETYPE_BY_DESCRIPTION = "getStaffLeaveTypeByDescription";
	
	/** String Constant for holding respective ERROR_STAFFLEAVE_INFO. */
	private static final String ERROR_STAFFLEAVE_INFO = "Error while retrieving staff leave info ---> ";
	/**
	 * Logger to log the exceptions.
	 */
	private static final Logger LOG = Logger
			.getLogger(StaffLeaveTypeDaoImpl.class);

	/**
	 * Method is to retrieve StaffLeaveType for given description.
	 * 
	 * @param description
	 *            - description
	 * @return staffLeaveType for respective description.
	 */
	@SuppressWarnings("unchecked")
	public StaffLeaveType getStaffLeaveTypeByDes(String description) {

		List<StaffLeaveType> staffLeaveTypes = getHibernateTemplate()
				.findByNamedQuery(GET_STAFFLEAVETYPE_BY_DESCRIPTION,
						description);

		StaffLeaveType staffLeaveType = null;

		if (staffLeaveTypes != null && !staffLeaveTypes.isEmpty()) {
			staffLeaveType = staffLeaveTypes.get(0);

		}
		return staffLeaveType;
	}

	/**
	 * Method is to retrieve StaffLeaveId for given staffLeaveTypeId.
	 * 
	 * @param staffLeaveTypeId
	 *            - staffLeaveTypeId
	 * @return StaffLeaveId for respective staffLeaveTypeId.
	 * @throws AkuraAppException
	 * 				AkuraAppException
	 */

	@SuppressWarnings("unchecked")
	public int findStaffLeaveIdByStaffLeaveTypeId(int staffLeaveTypeId)
			throws AkuraAppException {
		List<Object> appliedList = null;
		int sl = 0;
		try {
			appliedList = getHibernateTemplate().findByNamedQuery(
					"getStaffLeaveByStaffLeaveTypeId", staffLeaveTypeId);

			if (appliedList.size() > 0) {
				sl = (Integer) appliedList.get(0);

			}
			return sl;
		} catch (DataAccessException e) {
			LOG.error(ERROR_STAFFLEAVE_INFO + e.getMessage());
			throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
		}
	}
}
