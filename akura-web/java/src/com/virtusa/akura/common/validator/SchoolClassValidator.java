/*
 * � 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND

 * PROPRIETARY INFORMATION The information contained herein (the

 * 'Proprietary Information') is highly confidential and proprietary to and

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published,

 * communicated, disclosed or divulged to any person, firm, corporation or

 * other legal entity, directly or indirectly, without the prior written

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.common.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This class validates the fields of the School Class domain object.
 *
 * @author Virtusa Corporation
 */
public class SchoolClassValidator implements Validator {

    /** Constant to represent regex expression define in ValidatorExpression.properties. */
    private static final String REFERENCE_CLASS_VALIDATOR = "REFERENCE.CLASS.VALIDATOR";

    /**
     * Return whether or not this object can validate objects of the SchooClass class.
     *
     * @param clazz - the class of the SchoolClass
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return SchoolClass.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the SchoolClass.
     *
     * @param target - Populated object of SchoolClass to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        // required field
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        SchoolClass schoolClass = (SchoolClass) target;
        String description = schoolClass.getDescription().trim();

        Pattern descriptionPattern 
        = Pattern.compile(ValidatorExpressionUtil.getValidatorPattern(REFERENCE_CLASS_VALIDATOR));

        Matcher nameMatcher = descriptionPattern.matcher(schoolClass.getDescription());

        if (description.length() > 0) {
            
            if (nameMatcher.find()) {

                errors.rejectValue("description", AkuraWebConstant.MISMATCH_ERROR);
            }

        }
    }
}
