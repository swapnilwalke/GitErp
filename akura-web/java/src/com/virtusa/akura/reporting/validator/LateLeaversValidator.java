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

import com.virtusa.akura.api.dto.EarlyLateLeaversTemplate;

/**
 * This class validates fields for Late Levers,user input data.
 * 
 * @author Virtusa Corporation
 */
public class LateLeaversValidator implements Validator {

	/**
	 * Return whether object can validated or not.
	 * 
	 * @param myClass
	 *            - the class of the EarlyLateLeaversTemplate.
	 * @return - a boolean true or false
	 */
	public boolean supports(Class<?> myClass) {

		return EarlyLateLeaversTemplate.class.equals(myClass);

	}

	/**
	 * Validates the user input for criteria.
	 * 
	 * @param object
	 *            - Populated object of EarlyLateLeaversTemplate to validate
	 * @param errors
	 *            - contain errors related to fields.
	 */
	public void validate(Object object, Errors errors) {

		EarlyLateLeaversTemplate earlyLateLeaversTemplate = (EarlyLateLeaversTemplate) object;

		if (earlyLateLeaversTemplate.getLateLeaversTemplate().getTimeOutFrom()
				.equals("0")
				|| earlyLateLeaversTemplate.getLateLeaversTemplate()
						.getTimeOutTo().equals("0")
				|| earlyLateLeaversTemplate.getLateLeaversTemplate()
						.getDateFrom().equals("")
				|| earlyLateLeaversTemplate.getLateLeaversTemplate()
						.getDateTo().equals("")) {

			errors.rejectValue("lateLeaversTemplate.timeOutFrom",
					"REF.UI.MANDATORY.FIELD.REQUIRED");
		}

	}
}
