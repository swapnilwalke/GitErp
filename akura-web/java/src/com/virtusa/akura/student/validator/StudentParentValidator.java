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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.dto.ParentWrapper;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This validator class validates the fields of the Parent domain object.
 * 
 * @author Virtusa Corporation
 */
public class StudentParentValidator implements Validator {

	/** attribute for holding error message key. */
	private static final String PAR_UI_OFF_EMAIL_FIELD_TYPE = "PAR.UI.OFF.EMAIL.FIELD.TYPE";

	/** the field name to check. */
	private static final String PARENT_OFFICE_EMAIL = "parent.officeEmail";

	/** attribute for holding error message key. */
	private static final String PAR_UI_EMAIL_FIELD_TYPE = "PAR.UI.EMAIL.FIELD.TYPE";

	/** the field name to check. */
	private static final String PARENT_EMAIL = "parent.email";

	/** attribute for holding error message key. */
	private static final String PER_UI_LASTNAME_FIELD_TYPE = "PER.UI.LASTNAME.FIELD.TYPE";

	/** the parent last Name is to check. */
	private static final String PARENT_LAST_NAME = "parent.lastName";

	/** attribute for holding error message key. */
	private static final String PER_UI_FULLNAME_FIELD_TYPE = "PER.UI.FULLNAME.FIELD.TYPE";

	/** key to hold pattern of email field. */
	private static final String PATTERN_EMAIL = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(.\\w{2,3})+$";
	
	/** key to hold field email. */
	private static final String PARENT = "parent";

	/** the field name to check. */
	private static final String PARENT_FULL_NAME = "parent.fullName";

	/**
	 * Error message is to display when the mandatory information is not
	 * entered.
	 */
	private static final String MANDATORY_ERROR = "PAR.UI.MANDATORY.REQUIRED";

	/** Holds the pattern for alphabet characters. */
	private Pattern stringOnly;

	/** Holds the pattern for email address. */
	private Pattern emailAddress;
	  
    /** key to hold pattern of string phone numbers. */
    private static final String PATTERN_PHONE = "[^0-9]";
    
    /** key to hold field residence No. */
    private static final String RESIDENCE_NUMBER = "parent.residenceNo";
    
    

	/**
	 * Return whether or not this object can validate objects of the Parent
	 * class.
	 * 
	 * @param clazz
	 *            - the class of the Parent
	 * @return - a boolean true or false
	 */
	public boolean supports(Class<?> clazz) {

		return Parent.class.isAssignableFrom(clazz);
	}

	/**
	 * Validates the object of the Parent.
	 * 
	 * @param target
	 *            - Populated object of Parent to validate
	 * @param errors
	 *            - Errors object that is building. May contain errors for the
	 *            fields relating to types.
	 */
	public void validate(Object target, Errors errors) {

		ParentWrapper wrapperParent;

		wrapperParent = (ParentWrapper) target;
		Parent parent = wrapperParent.getParent();

		stringOnly = Pattern.compile(ValidatorExpressionUtil
				.getValidatorPattern("PARENT_VALIDATOR.STRING_ONLY_PATTERN"));
		emailAddress = Pattern.compile(ValidatorExpressionUtil
				.getValidatorPattern("PARENT_VALIDATOR.EMAIL_ADDRESS_PATTERN"));

		String emailPattern = PATTERN_EMAIL;
		
		//validate the general & office email addresses
		if (!parent.getEmail().trim().isEmpty()
				&& !parent.getEmail().trim().matches(emailPattern)||!parent.getOfficeEmail().trim().isEmpty()
				&& !parent.getOfficeEmail().trim().matches(emailPattern)) {
			errors.rejectValue(PARENT, AkuraWebConstant.EMAIL_MISMATCH_ERROR);
		}

		// Validate the mandatory fields of the parent
		if (parent.getFullName().trim().isEmpty()
				|| parent.getNationalIdNo().trim().isEmpty()
				|| (parent.getResidenceNo().isEmpty()
						&& parent.getMobileNo().isEmpty() && parent.getEmail()
						.trim().isEmpty())) {
			errors.rejectValue(PARENT_FULL_NAME, MANDATORY_ERROR);
		} else {
			validateParentInfo(errors, parent);
		}
		
		// validate the Phone Number
        Pattern digitsAndSpacesOnly = Pattern.compile(PATTERN_PHONE);
        Matcher makeMatch1 = digitsAndSpacesOnly.matcher(String.valueOf(parent.getResidenceNo()));
        Matcher makeMatch2 = digitsAndSpacesOnly.matcher(String.valueOf(parent.getMobileNo()));
        Matcher makeMatch3 = digitsAndSpacesOnly.matcher(String.valueOf(parent.getOfficeNo()));
        Matcher makeMatch4 = digitsAndSpacesOnly.matcher(String.valueOf(parent.getOfficeFaxNo()));
        if (makeMatch1.find() || makeMatch2.find() || makeMatch3.find() || makeMatch4.find()) {
            errors.rejectValue(RESIDENCE_NUMBER, AkuraWebConstant.COUNTRY_PHONE_NUMBER_MISMATCH_ERROR);
        }
	}

	/**
	 * Validate Guardian and Father information.
	 * 
	 * @param errors
	 *            - Errors object that is building. May contain errors for the
	 *            fields relating to types.
	 * @param parent
	 *            - parent object to validate
	 */
	private void validateParentInfo(Errors errors, Parent parent) {

		Matcher matchFullName = stringOnly.matcher(parent.getFullName());
		if (!parent.getFullName().trim().isEmpty() && matchFullName.find()) {
			errors.rejectValue(PARENT_FULL_NAME, PER_UI_FULLNAME_FIELD_TYPE);
		}
		Matcher matchLastName = stringOnly.matcher(parent.getLastName());
		if (!parent.getLastName().trim().isEmpty() && matchLastName.find()) {
			errors.rejectValue(PARENT_LAST_NAME, PER_UI_LASTNAME_FIELD_TYPE);
		}

		Matcher matchEmail = emailAddress.matcher(parent.getEmail());
		if (!matchEmail.find() && !parent.getEmail().isEmpty()) {
			errors.rejectValue(PARENT_EMAIL, PAR_UI_EMAIL_FIELD_TYPE);
		}

		Matcher matchOfficeEmail = emailAddress
				.matcher(parent.getOfficeEmail());
		if (!matchOfficeEmail.find() && !parent.getOfficeEmail().isEmpty()) {
			errors.rejectValue(PARENT_OFFICE_EMAIL, PAR_UI_OFF_EMAIL_FIELD_TYPE);
		}
	}

}
