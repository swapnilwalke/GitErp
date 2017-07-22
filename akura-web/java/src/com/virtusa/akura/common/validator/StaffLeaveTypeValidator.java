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

package com.virtusa.akura.common.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.virtusa.akura.api.dto.StaffLeaveType;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This is a Validator for StaffLeaveType object which validates property
 * description is null/empty and it should contain only alphabet characters and
 * space between words.
 * 
 * @author Virtusa Corporation
 */
public class StaffLeaveTypeValidator implements Validator {

	/** Constant to represent description. */
	private static final String DESCRIPTION = "description";

	/** model attribute of MaxStaffLeaves . */
	private static final String MAXSTAFFLEAVES = "maxStaffLeaves";

	/** Constant to represent string regex expression. */
	private static final String STRING_REGEX = "( )+";

	/**
	 * Constant to represent string only pattern define in
	 * ValidatorExpression.properties.
	 */
	private static final String STRING_ONLY_PATTERN = "REFERENCE.STAFFLEAVETYPE.VALIDATOR";

	/** Constant to represent string space. */
	private static final String STRING_SPACE = "";

	/** Constant to represent string regex expression. */
	private static final String ERROR_MSG_MAXSTAFFLEAVES = "REF.STAFFLEAVETYPE.MAXSTAFFLEAVES.ERROR";

	/**
	 * StaffLeaveType is the class of the actual object instance that is to be
	 * validated.
	 * 
	 * @param clazz
	 *            - the Class that this Validator is being asked if it can
	 *            validate
	 * @return - true if this Validator can indeed validate instances of the
	 *         supplied clazz
	 */
	public boolean supports(Class<?> clazz) {

		return StaffLeaveType.class.isAssignableFrom(clazz);
	}

	/**
	 * Validate the supplied object.
	 * 
	 * @param object
	 *            - the object that is to be validated
	 * @param errors
	 *            - contextual state about the validation process
	 */
	public void validate(Object object, Errors errors) {
		StaffLeaveType staffLeaveType = (StaffLeaveType) object;

		// validate the Mandatory fields
		if ("".equals(staffLeaveType.getDescription().trim())
				|| staffLeaveType.getMaxStaffLeaves() == AkuraWebConstant.SPACE_MAXSTAFFLEAVES) {

			errors.rejectValue(DESCRIPTION,
					AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
		}

		Pattern stringOnly = Pattern.compile(ValidatorExpressionUtil
				.getValidatorPattern(STRING_ONLY_PATTERN));
		String description = staffLeaveType.getDescription().trim();

		// validate the Description and maximum No of staffleaves
		if (staffLeaveType.getDescription().trim().length() > 0
				|| staffLeaveType.getMaxStaffLeaves() == AkuraWebConstant.STRING_MAXSTAFFLEAVES) {

			description = description.replaceAll(STRING_REGEX, STRING_SPACE);
			Matcher makeMatch = stringOnly.matcher(description);

			if (makeMatch.find()
					|| staffLeaveType.getMaxStaffLeaves() == AkuraWebConstant.STRING_MAXSTAFFLEAVES) {
				errors.rejectValue(MAXSTAFFLEAVES,
						AkuraWebConstant.MISMATCH_ERROR);
			}
		}
		if (staffLeaveType.getMaxStaffLeaves() < 0) {
			errors.rejectValue(MAXSTAFFLEAVES, ERROR_MSG_MAXSTAFFLEAVES);
		}

	}
}
