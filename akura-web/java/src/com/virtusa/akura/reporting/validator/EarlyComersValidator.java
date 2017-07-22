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

package com.virtusa.akura.reporting.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.AttendeesWrapperTemplate;

/**
 * This class validates fields for Teachers Early comers Form,user input data.
 * 
 * @author Virtusa Corporation
 */
public class EarlyComersValidator implements Validator {

	/**
	 * Return whether object can validated or not.
	 * 
	 * @param myClass
	 *            - the class of the AttendeesWrapperTemplate.
	 * @return - a boolean true or false
	 */
	public boolean supports(Class<?> myClass) {

		return AttendeesWrapperTemplate.class.equals(myClass);
	}

	/**
	 * Validates the user input for criteria.
	 * 
	 * @param object
	 *            - Populated object of AttendeesWrapperTemplate to validate
	 * @param errors
	 *            - contain errors related to fields.
	 */
	public void validate(Object object, Errors errors) {

		AttendeesWrapperTemplate attendeesWrapperTemplate = (AttendeesWrapperTemplate) object;

		if (attendeesWrapperTemplate.getTeacherEarlyComersTemplate()
				.getTimeInFrom().equals("0")
				|| attendeesWrapperTemplate.getTeacherEarlyComersTemplate()
						.getTimeInTo().equals("0")
				|| attendeesWrapperTemplate.getTeacherEarlyComersTemplate()
						.getDateFrom().equals("")
				|| attendeesWrapperTemplate.getTeacherEarlyComersTemplate()
						.getDateTo().equals("")) {

			errors.rejectValue("teacherEarlyComersTemplate.timeInFrom",
					"REF.UI.MANDATORY.FIELD.REQUIRED");

		}

	}

}
