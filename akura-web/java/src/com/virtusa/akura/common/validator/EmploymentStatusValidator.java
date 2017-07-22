/* < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import com.virtusa.akura.api.dto.EmploymentStatus;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This class validates the fields of the EmploymentStatus domain object.
 *
 * @author Virtusa Corporation
 */

public class EmploymentStatusValidator implements Validator {

    /** Constant to represent regex expression define in ValidatorExpression.properties. */
    private static final String EMPLOYMENTSTATUS_VALIDATOR = "REFERENCE.EMPLOYMENTSTATUS.VALIDATOR";

    /** attribute for holding field name for display error massage. */
    private static final String DESCRIPTION = "description";

    /**
     * Return whether or not this object can validate objects of the EmploymentStatus class.
     *
     * @param clazz - the class of the EmploymentStatus
     * @return - a boolean true or false
     */
	
	public boolean supports(Class<?> clazz) {

	        return EmploymentStatus.class.isAssignableFrom(clazz);
	 }
	 
	/**
     * Validate the supplied object.
     *
     * @param target - the object that is to be validated
     * @param errors - contextual state about the validation process
     */
	public void validate(Object target, Errors errors) {
		
		
		EmploymentStatus employmentStatus = (EmploymentStatus) target;
		
		if (employmentStatus.getDescription().isEmpty() || employmentStatus.getDescription().trim().length() ==0) {
			errors.rejectValue(DESCRIPTION, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
		}
		
		Pattern stringOnly = Pattern.compile(ValidatorExpressionUtil.getValidatorPattern(EMPLOYMENTSTATUS_VALIDATOR));
		Matcher makeMatch = stringOnly.matcher(employmentStatus.getDescription());
		
		if (makeMatch.find()) {
            errors.rejectValue(DESCRIPTION, AkuraWebConstant.MISMATCH_ERROR);
        }
		
	}
	
}
