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
package com.virtusa.akura.student.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.virtusa.akura.api.dto.StudentTemporaryLeave;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This class validates the fields of the StudentTemporaryLeave domain object.
 * 
 * @author Virtusa Corporation
 */

public class StudentTemporaryLeaveTerminateValidator implements Validator {

	/**
	 * attribute for holding field name for display professional qualification
	 * errors.
	 */
	private static final String FIELD_PROFESSIONAL_QUALIFICATION_ID = "studentTemporaryLeave.reason";

	/** error message for date field incorrect. */
	private static final String ERROR_MSG_DATE_FIELD_INCORRECT_DATE = "REF.UI.TEMPLEAVE.DATE.FIELD.INCORRECT_DATE";

	/** variable for date to. */
	private static final String ATT_DATE_TO = "studentTemporaryLeave.student.studentId";

	/**
	 * Return whether or not this object can validate objects of the
	 * StaffPastService class.
	 * 
	 * @param arg0
	 *            - the class of the StudentTemporaryLeave
	 * @return - a boolean true or false
	 */

	@Override
	public boolean supports(Class<?> arg0) {
		return StudentTemporaryLeave.class.isAssignableFrom(arg0);
	}

	/**
	 * Validates the object of the StudentTemporaryLeave.
	 * 
	 * @param target
	 *            - Populated object of StudentTemporaryLeave to validate
	 * @param errors
	 *            - Errors object that is building. May contain errors for the
	 *            fields relating to types.
	 */
	public void validate(Object target, Errors errors) {

		StudentTemporaryLeave studentTemporaryLeave = (StudentTemporaryLeave) target;

		if (studentTemporaryLeave.getToDate() == null
				|| studentTemporaryLeave.getFromDate() == null
				|| studentTemporaryLeave.getReason().isEmpty()
				|| !studentTemporaryLeave.isCompleteClearance()) {

			errors.rejectValue(FIELD_PROFESSIONAL_QUALIFICATION_ID,
					AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
		} else if (studentTemporaryLeave.getToDate() != null) {
			if ((studentTemporaryLeave.getStudent().getFirstSchoolDay())
					.after((studentTemporaryLeave.getToDate()))) {
				errors.rejectValue(ATT_DATE_TO,
						ERROR_MSG_DATE_FIELD_INCORRECT_DATE);
			}

		}

	}
}
