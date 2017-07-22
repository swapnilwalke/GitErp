/*
 * © 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND

 * PROPRIETARY INFORMATION The information contained herein (the

 * 'Proprietary Information') is highly confidential and proprietary to and

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published,

 * communicated, disclosed or divulged to any person, firm, corporation or

 * other legal entity, directly or indirectly, without the prior written

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.common.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.Seminar;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This is a Validator for Seminar object which validates property description is null/empty and it should
 * contain only alphabet characters and space between words.
 *
 * @author Virtusa Corporation
 */
public class SeminarValidator implements Validator {

    /** the common field name to check. */
    private static final String COMMON_FIELD_NAME = "seminarId";

    /**
     * Seminar is the class of the actual object instance that is to be validated.
     *
     * @param clazz - the Class that this Validator is being asked if it can validate
     * @return - true if this Validator can indeed validate instances of the supplied clazz
     */
    public boolean supports(Class<?> clazz) {

        return Seminar.class.isAssignableFrom(clazz);
    }

    /**
     * Validate the supplied object.
     *
     * @param object - the object that is to be validated
     * @param errors - contextual state about the validation process
     */
    public void validate(Object object, Errors errors) {

        Seminar seminar = (Seminar) object;

        if ("".equals(seminar.getDescription().trim())) {
            errors.rejectValue(COMMON_FIELD_NAME, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }

    }

}
