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

package com.virtusa.akura.school.validator;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.School;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This class validates the properties of the School domain object.
 * 
 * @author Virtusa Corporation
 */
public class SchoolValidator implements Validator {

	/** Regex pattern for URL. */
	private static final String REGEX_PATTERN_URL = "SCHOOL.VALIDATOR.URL";

	/** Regex pattern for Email. */
	private static final String REGEX_PATTERN_EMAIL = "SCHOOL.VALIDATOR.EMAIL";

	/** Regex pattern for Address. */
	private static final String REGEX_PATTERN_ADDRESS = "SCHOOL.VALIDATOR.ADDRESS";

	/** Regex pattern for SchoolName. */
	private static final String REGEX_PATTERN_SCHOOL = "SCHOOL.VALIDATOR.NAME";

	/** Error message for STAFF_DATA_LONG. */
	private static final String ERROR_MSG_STAFF_DATA_LONG = "REF.UI.STAFF.DATA.LONG";

	/** Error message for STUDENT_NO_ERROR. */
	private static final String ERROR_MSG_STUDENT_NO_ERROR = "REF.UI.STUDENT.NO.ERROR";

	/** Error message for STAFF_NO_ERROR. */
	private static final String ERROR_MSG_STAFF_NO_ERROR = "REF.UI.STAFF.NO.ERROR";

	/** Error message for DSTARTEDDATE_ERROR. */
	private static final String ERROR_MSG_STARTEDDATE_ERROR = "REF.UI.STARTEDDATE.ERROR";

	/** Error message for ADDRESS_TYPE. */
	private static final String ERROR_MSG_ADDRESS_TYPE = "REF.UI.ADDRESS.TYPE";

	/** Error message for NAME_TYPE. */
	private static final String ERROR_MSG_NAME_TYPE = "REF.UI.NAME.TYPE";

	/** Error message for EMAIL_TYPE. */
	private static final String ERROR_MSG_EMAIL_TYPE = "REF.UI.EMAIL.TYPE";

	/** Error message for WEBSITE_TYPE. */
	private static final String ERROR_MSG_WEBSITE_TYPE = "REF.UI.WEBSITE.TYPE";
	
	/** Error message for Vice principal should not be the principal. */
	private static final String ERROR_MSG_VICE_PRINCIPAL_NOT_ALLOWED = "REF.UI.SCHOOL.VICE_PRICIPAL.NOT_ALLOWED";

	/** Field name for primaryAndSecondarySchoolInfo. */
	private static final String FIELD_NAME_PRIMARY_AND_SECONDARY_SCHOOL_INFO = "primaryAndSecondarySchoolInfo";

	/** Field name for facilities. */
	private static final String FIELD_NAME_FACILITIES = "facilities";

	/** Field name for noOfStudents. */
	private static final String FIELD_NAME_NO_OF_STUDENTS = "noOfStudents";

	/** Field name for noOfStaff. */
	private static final String FIELD_NAME_NO_OF_STAFF = "noOfStaff";

	/** Field name for address. */
	private static final String FIELD_NAME_ADDRESS = "address";

	/** Field name for email. */
	private static final String FIELD_NAME_EMAIL = "email";

	/** Field name for website. */
	private static final String FIELD_NAME_WEBSITE = "website";

	/** Field name for startedDate. */
	private static final String FIELD_NAME_STARTED_DATE = "startedDate";

	/** Field name for name. */
	private static final String FIELD_NAME_NAME = "name";
	
	/** Field name of vice principal. */
	private static final String FIELD_NAME_VICE_PRINCIPAL = "vicePrincipalId";

	/** The Constant SCHOOL_ID. */
	private static final String SCHOOL_ID = "schoolId";

	/** key to hold pattern of string phone numbers. */
    private static final String PATTERN_PHONE = "[^0-9]";
    
    /** key to hold field residence No. */
    private static final String CONTACT_NUMBER = "contactNo";
    
	/**
	 * Return whether or not this object can validate objects of the School
	 * class.
	 * 
	 * @param clazz
	 *            - the class of the School
	 * @return - a boolean true or false
	 */
	public boolean supports(Class<?> clazz) {

		return School.class.isAssignableFrom(clazz);
	}

	/**
	 * Validates objects of the School.
	 * 
	 * @param target
	 *            - Populated object of School to validate
	 * @param errors
	 *            - Errors object that is building. May contain errors for the
	 *            fields relating to types.
	 */
	public void validate(Object target, Errors errors) {

		School school = (School) target;

		if (school.getName().trim().equals("")
				|| school.getStartedDate() == null
				|| school.getCountry().getCountryId() == 0				
				|| school.getPrincipalId() == 0) {
			errors.rejectValue(SCHOOL_ID,
					AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
		} else {
			Pattern schoolPattern = Pattern.compile(ValidatorExpressionUtil.getValidatorPattern(REGEX_PATTERN_SCHOOL));
			Pattern addressPattern = Pattern.compile(
			        ValidatorExpressionUtil.getValidatorPattern(REGEX_PATTERN_ADDRESS));
			Pattern emailPattern = Pattern.compile(ValidatorExpressionUtil.getValidatorPattern(REGEX_PATTERN_EMAIL));
			Pattern urlPattern = Pattern.compile(ValidatorExpressionUtil.getValidatorPattern(REGEX_PATTERN_URL));
			Matcher nameMatch = schoolPattern.matcher(school.getName().trim()
					.replaceAll("( )+", " "));
			Matcher addressMatch = addressPattern.matcher(school.getAddress()
					.trim().replaceAll("( )+", " "));
			Matcher emailMatch = emailPattern.matcher(school.getEmail().trim());
			Matcher urlMatch = urlPattern.matcher(school.getWebsite().trim());

			if ((school.getVicePrincipalId() != null)
					&& (school.getPrincipalId() == school.getVicePrincipalId().intValue())) {
				errors.rejectValue(FIELD_NAME_VICE_PRINCIPAL, ERROR_MSG_VICE_PRINCIPAL_NOT_ALLOWED);
			}
			
			if (school.getWebsite().trim().length() > 0) {
				if (!urlMatch.find()) {
					errors.rejectValue(FIELD_NAME_WEBSITE,
							ERROR_MSG_WEBSITE_TYPE);
				}
			}

			if (school.getEmail().trim().length() > 0) {
				if (!emailMatch.find()) {
					errors.rejectValue(FIELD_NAME_EMAIL, ERROR_MSG_EMAIL_TYPE);
				}
			}
			if (nameMatch.find()) {
				errors.rejectValue(FIELD_NAME_NAME, ERROR_MSG_NAME_TYPE);
			}
			if (school.getAddress().trim().length() > 0) {
				if (addressMatch.find()) {
					errors.rejectValue(FIELD_NAME_ADDRESS,
							ERROR_MSG_ADDRESS_TYPE);
				}
			}

			if (school.getStartedDate() != null) {
				if (school.getStartedDate().after(new Date())) {
					errors.rejectValue(FIELD_NAME_STARTED_DATE,
							ERROR_MSG_STARTEDDATE_ERROR);
				}
			}
			
			// validate the Phone Number
	        Pattern digitsOnly = Pattern.compile(PATTERN_PHONE);
	        Matcher makeMatch1 = digitsOnly.matcher(String.valueOf(school.getContactNo()));
	        Matcher makeMatch2 = digitsOnly.matcher(String.valueOf(school.getFaxNo()));
	        if (makeMatch1.find() || makeMatch2.find()) {
	            errors.rejectValue(CONTACT_NUMBER, AkuraWebConstant.COUNTRY_PHONE_NUMBER_MISMATCH_ERROR);
	        }
		}
	}

}
